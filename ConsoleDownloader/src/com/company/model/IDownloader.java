package com.company.model;

import java.io.IOException;

public interface IDownloader {
    byte[] getFile (String pathToURL) throws IOException;
}
