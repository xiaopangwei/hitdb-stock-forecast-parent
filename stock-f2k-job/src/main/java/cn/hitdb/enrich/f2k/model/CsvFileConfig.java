package cn.hitdb.enrich.f2k.model;

import java.io.Serializable;
import java.net.URL;
import java.util.Arrays;

public class CsvFileConfig implements Serializable{
    private byte[] lineDelimiter;
    private String encoding;
    private URL resourcePath;

    public CsvFileConfig(byte[] lineDelimiter, String encoding, URL resourcePath) {
        this.lineDelimiter = lineDelimiter;
        this.encoding = encoding;
        this.resourcePath = resourcePath;
    }

    public byte[] getLineDelimiter() {
        return lineDelimiter;
    }

    public void setLineDelimiter(byte[] lineDelimiter) {
        this.lineDelimiter = lineDelimiter;
    }

    public String getEncoding() {
        return encoding;
    }

    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }

    public URL getResourcePath() {
        return resourcePath;
    }

    public void setResourcePath(URL resourcePath) {
        this.resourcePath = resourcePath;
    }

    @Override
    public String toString() {
        return "CsvFileConfig{" +
                "lineDelimiter=" + Arrays.toString(lineDelimiter) +
                ", encoding='" + encoding + '\'' +
                ", resourcePath=" + resourcePath +
                '}';
    }
}
