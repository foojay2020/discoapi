/*
 * Copyright (c) 2021.
 *
 * This file is part of DiscoAPI.
 *
 *     DiscoAPI is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 2 of the License, or
 *     (at your option) any later version.
 *
 *     DiscoAPI is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with DiscoAPI.  If not, see <http://www.gnu.org/licenses/>.
 */

package io.foojay.api.pkg;

public enum HashAlgorithm implements ApiFeature {
    MD5("MSD5", "md5"),
    SHA1("SHA1", "sha1"),
    SHA256("SHA256", "sha256"),
    SHA224("SHA224", "sha224"),
    SHA384("SHA384", "sha384"),
    SHA512("SHA512", "sha512"),
    SHA3_256("SHA-3 256", "sha3_256"),
    NONE("-", ""),
    NOT_FOUND("", "");

    private final String uiString;
    private final String apiString;


    HashAlgorithm(final String uiString, final String apiString) {
        this.uiString  = uiString;
        this.apiString = apiString;
    }


    @Override public String getUiString() { return uiString; }

    @Override public String getApiString() { return apiString; }

    @Override public ApiFeature getDefault() { return HashAlgorithm.NONE; }

    @Override public ApiFeature getNotFound() { return HashAlgorithm.NOT_FOUND; }

    @Override public ApiFeature[] getAll() { return values(); }

    public static HashAlgorithm fromText(final String text) {
        if (null == text) { return NOT_FOUND; }
        switch(text) {
            case "md5":
            case "MD5":
            case "md-5":
            case "md_5":
            case "MD-5":
            case "MD_5":
                return MD5;
            case "sha1":
            case "SHA1":
            case "sha-1":
            case "SHA-1":
            case "sha_1":
            case "SHA_1":
                return SHA1;
            case "sha256":
            case "SHA256":
            case "sha_256":
            case "SHA_256":
            case "sha-256":
            case "SHA-256":
                return SHA256;
            case "sha224":
            case "SHA224":
            case "sha_224":
            case "SHA_224":
            case "sha-224":
            case "SHA-224":
                return SHA224;
            case "sha384":
            case "SHA384":
            case "sha_384":
            case "SHA_384":
            case "sha-384":
            case "SHA-384":
                return SHA384;
            case "sha512":
            case "SHA512":
            case "sha_512":
            case "SHA_512":
            case "sha-512":
            case "SHA-512":
                return SHA512;
            case "sha3_256":
            case "SHA3_256":
            case "sha-3-256":
            case "SHA-3-256":
            case "sha_3_256":
            case "SHA_3_256":
                return SHA3_256;
            default:
                return NOT_FOUND;
        }
    }
}
