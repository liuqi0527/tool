package com.server.tool.file;

import java.io.File;
import java.io.FilenameFilter;
import java.util.Objects;

/**
 * @author LiuQi - [Created on 2018-05-23]
 */
public enum FileType implements FilenameFilter {

    any(""),
    js(".js"),
    css(".css"),
    jsp(".jsp"),
    html(".html"),

    xml(".xml"),
    txt(".txt"),
    csv(".csv"),
    sql(".sql"),

    java(".java"),
    clazz(".class"),
    properties(".properties");

    final String extension;

    FileType(final String endString) {
        this.extension = endString;
    }

    @Override
    public final boolean accept(final File dir, final String name) {
        return Objects.nonNull(name) && name.toLowerCase().endsWith(extension);
    }

    public final boolean accept(final File dir) {
        return accept(dir, dir.getName());
    }

    public final String getExtension() {
        return extension;
    }

    public static boolean isSvn(File file) {
        return file != null && ".svn".equals(file.getName());
    }
}
