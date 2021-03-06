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
 * You should have received a copy of the GNU General Public License
 * along with DiscoAPI.  If not, see <http://www.gnu.org/licenses/>.
 */

package io.foojay.api.pkg;

import java.util.Arrays;
import java.util.List;


public enum LibCType implements ApiFeature {
    GLIBC("glibc", "glibc"),
    MUSL("musl", "musl"),
    LIBC("libc", "libc"),
    C_STD_LIB("c std. lib", "c_std_lib"),
    NONE("-", ""),
    NOT_FOUND("", "");

    private final String uiString;
    private final String apiString;


    LibCType(final String uiString, final String apiString) {
        this.uiString  = uiString;
        this.apiString = apiString;
    }


    @Override public String getUiString() { return uiString; }

    @Override public String getApiString() { return apiString; }

    @Override public LibCType getDefault() { return LibCType.NONE; }

    @Override public LibCType getNotFound() { return LibCType.NOT_FOUND; }

    @Override public LibCType[] getAll() { return values(); }

    public static LibCType fromText(final String text) {
        if (null == text) { return NOT_FOUND; }
        switch (text) {
            case "musl":
            case "MUSL":
            case "linux_musl":
            case "linux-musl":
            case "alpine_linux":
            case "alpine":
            case "alpine-linux":
                return MUSL;
            case "glibc":
            case "GLIBC":
            case "linux":
            case "Linux":
            case "LINUX":
                return GLIBC;
            case "c_std_lib":
            case "C_STD_LIB":
            case "c-std-lib":
            case "C-STD-LIB":
            case "windows":
            case "Windows":
            case "win":
            case "Win":
                return C_STD_LIB;
            case "libc":
            case "LIBC":
            case "macos":
            case "MACOS":
            case "macosx":
            case "MACOSX":
            case "aix":
            case "AIX":
            case "qnx":
            case "QNX":
            case "solaris":
            case "SOLARIS":
            case "darwin":
            case "DARWIN":
                return LIBC;
            default:
                return NOT_FOUND;
        }
    }

    public static List<LibCType> getAsList() { return Arrays.asList(values()); }
}
