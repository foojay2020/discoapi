/*
 * Copyright (c) 2021 by Gerrit Grunwald
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.foojay.api.distribution;

import com.google.gson.JsonObject;
import io.foojay.api.CacheManager;
import io.foojay.api.pkg.Architecture;
import io.foojay.api.pkg.ArchiveType;
import io.foojay.api.pkg.Bitness;
import io.foojay.api.pkg.Distro;
import io.foojay.api.pkg.HashAlgorithm;
import io.foojay.api.pkg.MajorVersion;
import io.foojay.api.pkg.OperatingSystem;
import io.foojay.api.pkg.PackageType;
import io.foojay.api.pkg.Pkg;
import io.foojay.api.pkg.ReleaseStatus;
import io.foojay.api.pkg.SemVer;
import io.foojay.api.pkg.SignatureType;
import io.foojay.api.pkg.TermOfSupport;
import io.foojay.api.pkg.VersionNumber;
import io.foojay.api.util.Constants;
import io.foojay.api.util.Helper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map.Entry;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static io.foojay.api.pkg.PackageType.JDK;
import static io.foojay.api.pkg.PackageType.JRE;
import static io.foojay.api.pkg.ReleaseStatus.EA;
import static io.foojay.api.pkg.ReleaseStatus.GA;


public class Microsoft implements Distribution {
    private static final Logger        LOGGER = LoggerFactory.getLogger(Microsoft.class);

    private static final Pattern       FILENAME_PREFIX_PATTERN = Pattern.compile("microsoft-");
    private static final Matcher       FILENAME_PREFIX_MATCHER = FILENAME_PREFIX_PATTERN.matcher("");
    private static final String        PACKAGE_URL             = "https://docs.microsoft.com/java/openjdk/download";
    public  static final String        PKGS_PROPERTIES         = "https://github.com/foojay2020/openjdk_releases/raw/main/microsoft.properties";

    // URL parameters
    private static final String        ARCHITECTURE_PARAM      = "";
    private static final String        OPERATING_SYSTEM_PARAM  = "";
    private static final String        ARCHIVE_TYPE_PARAM      = "";
    private static final String        PACKAGE_TYPE_PARAM      = "";
    private static final String        RELEASE_STATUS_PARAM    = "";
    private static final String        SUPPORT_TERM_PARAM      = "";
    private static final String        BITNESS_PARAM           = "";

    private static final HashAlgorithm HASH_ALGORITHM          = HashAlgorithm.NONE;
    private static final String        HASH_URI                = "";
    private static final SignatureType SIGNATURE_TYPE          = SignatureType.NONE;
    private static final HashAlgorithm SIGNATURE_ALGORITHM     = HashAlgorithm.NONE;
    private static final String        SIGNATURE_URI           = "";


    @Override public Distro getDistro() { return Distro.MICROSOFT; }

    @Override public String getName() { return getDistro().getUiString(); }

    @Override public String getPkgUrl() { return PACKAGE_URL; }

    @Override public String getArchitectureParam() { return ARCHITECTURE_PARAM; }

    @Override public String getOperatingSystemParam() { return OPERATING_SYSTEM_PARAM; }

    @Override public String getArchiveTypeParam() { return ARCHIVE_TYPE_PARAM; }

    @Override public String getPackageTypeParam() { return PACKAGE_TYPE_PARAM; }

    @Override public String getReleaseStatusParam() { return RELEASE_STATUS_PARAM; }

    @Override public String getTermOfSupportParam() { return SUPPORT_TERM_PARAM; }

    @Override public String getBitnessParam() { return BITNESS_PARAM; }

    @Override public HashAlgorithm getHashAlgorithm() { return HASH_ALGORITHM; }

    @Override public String getHashUri() { return HASH_URI; }

    @Override public SignatureType getSignatureType() { return SIGNATURE_TYPE; }

    @Override public HashAlgorithm getSignatureAlgorithm() { return SIGNATURE_ALGORITHM; }

    @Override public String getSignatureUri() { return SIGNATURE_URI; }

    @Override public List<String> getSynonyms() {
        return List.of("microsoft", "Microsoft", "MICROSOFT", "Microsoft OpenJDK", "Microsoft Build of OpenJDK");
    }

    @Override public List<SemVer> getVersions() {
        return CacheManager.INSTANCE.pkgCache.getPkgs()
                                             .stream()
                                             .filter(pkg -> Distro.MICROSOFT.get().equals(pkg.getDistribution()))
                                             .map(pkg -> pkg.getSemver())
                                             .collect(Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(SemVer::toString)))).stream().sorted(Comparator.comparing(SemVer::getVersionNumber).reversed()).collect(Collectors.toList());
    }


    @Override public String getUrlForAvailablePkgs(final VersionNumber versionNumber,
                                                   final boolean latest, final OperatingSystem operatingSystem,
                                                   final Architecture architecture, final Bitness bitness, final ArchiveType archiveType, final PackageType packageType,
                                                   final Boolean javafxBundled, final ReleaseStatus releaseStatus, final TermOfSupport termOfSupport) {
        StringBuilder queryBuilder = new StringBuilder(PACKAGE_URL);

        LOGGER.debug("Query string for {}: {}", this.getName(), queryBuilder.toString());

        return queryBuilder.toString();
    }

    @Override public List<Pkg> getPkgFromJson(final JsonObject jsonObj, final VersionNumber versionNumber, final boolean latest, final OperatingSystem operatingSystem,
                                              final Architecture architecture, final Bitness bitness, final ArchiveType archiveType, final PackageType packageType,
                                              final Boolean javafxBundled, final ReleaseStatus releaseStatus, final TermOfSupport termOfSupport) {
        List<Pkg> pkgs = new ArrayList<>();

        return pkgs;
    }

    public List<Pkg> getAllPkgs() {
        List<Pkg> pkgs = new ArrayList<>();
        try {
            String html = Helper.getTextFromUrl(PACKAGE_URL);
            pkgs.addAll(getAllPkgsFromHtml(html));
        } catch (Exception e) {
            LOGGER.error("Error fetching all packages from Microsoft. {}", e);
        }
        return pkgs;
    }

    public List<Pkg> getAllPkgsFromHtml(final String html) {
        List<Pkg> pkgs = new ArrayList<>();
        if (null == html || html.isEmpty()) { return pkgs; }

        List<String> fileHrefs    = new ArrayList<>(Helper.getFileHrefsFromString(html));
        List<String> sigFileHrefs = new ArrayList<>(Helper.getSigFileHrefsFromString(html));
        for (String href : fileHrefs) {
            final String filename = Helper.getFileNameFromText(href);
            if (filename.contains("debugsymbols") || filename.startsWith("jdk")) { continue; }

            final String          withoutPrefix   = filename.replace("microsoft-", "");
            final VersionNumber   versionNumber   = VersionNumber.fromText(withoutPrefix);

            if (versionNumber.getPatch().isPresent() && versionNumber.getPatch().getAsInt() != 0 &&
                versionNumber.getFifth().isPresent() && versionNumber.getFifth().getAsInt() != 0) {
                    versionNumber.setPatch(0);
                    versionNumber.setFifth(0);
            } else if (versionNumber.getPatch().isPresent() && versionNumber.getPatch().getAsInt() != 0 &&
                       versionNumber.getFifth().isPresent() && versionNumber.getFifth().getAsInt() != 0 &&
                       versionNumber.getSixth().isPresent() && versionNumber.getSixth().getAsInt() != 0) {
                versionNumber.setFifth(0);
                versionNumber.setSixth(0);
            }

            final MajorVersion    majorVersion    = versionNumber.getMajorVersion();
            final PackageType     packageType     = withoutPrefix.startsWith("jdk") ? JDK : JRE;

            OperatingSystem operatingSystem = Constants.OPERATING_SYSTEM_LOOKUP.entrySet()
                                                                               .stream()
                                                                               .filter(entry -> withoutPrefix.contains(entry.getKey()))
                                                                               .findFirst()
                                                                               .map(Entry::getValue)
                                                                               .orElse(OperatingSystem.NOT_FOUND);
            if (OperatingSystem.NOT_FOUND == operatingSystem) {
                LOGGER.debug("Operating System not found in {} for filename: {}", getName(), filename);
                continue;
            }

            final Architecture architecture = Constants.ARCHITECTURE_LOOKUP.entrySet()
                                                                           .stream()
                                                                           .filter(entry -> withoutPrefix.contains(entry.getKey()))
                                                                           .findFirst()
                                                                           .map(Entry::getValue)
                                                                           .orElse(Architecture.NOT_FOUND);
            if (Architecture.NOT_FOUND == architecture) {
                LOGGER.debug("Architecture not found in {} for filename: {}", getName(), filename);
                continue;
            }

            ArchiveType archiveType = ArchiveType.getFromFileName(filename);
            if (OperatingSystem.MACOS == operatingSystem) {
                switch(archiveType) {
                    case DEB:
                    case RPM: operatingSystem = OperatingSystem.LINUX; break;
                    case CAB:
                    case MSI:
                    case EXE: operatingSystem = OperatingSystem.WINDOWS; break;
                }
            }

            Pkg pkg = new Pkg();
            pkg.setDistribution(Distro.MICROSOFT.get());
            pkg.setArchitecture(architecture);
            pkg.setBitness(architecture.getBitness());
            pkg.setVersionNumber(versionNumber);
            pkg.setJavaVersion(versionNumber);
            pkg.setDistributionVersion(versionNumber);
            pkg.setDirectDownloadUri(href);
            pkg.setFileName(filename);
            if (sigFileHrefs.contains(href.toLowerCase() + ".sig")) { pkg.setSignatureUri(href.toLowerCase() + ".sig"); }
            pkg.setArchiveType(archiveType);
            pkg.setJavaFXBundled(false);
            pkg.setTermOfSupport(majorVersion.getTermOfSupport());
            pkg.setReleaseStatus((filename.contains("-ea.") || majorVersion.equals(MajorVersion.getLatest(true))) ? EA : GA);
            pkg.setPackageType(packageType);
            pkg.setOperatingSystem(operatingSystem);
            pkg.setFreeUseInProduction(Boolean.TRUE);

            pkgs.add(pkg);
        }

        return pkgs;
    }
}

