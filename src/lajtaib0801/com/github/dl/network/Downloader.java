package lajtaib0801.com.github.dl.network;

import lajtaib0801.com.github.dl.io.EntryToDownload;
import lajtaib0801.com.github.dl.network.exception.DownloadException;

import java.io.IOException;
import java.nio.file.Path;

public interface Downloader {
    void download(EntryToDownload entry, Path targetDir) throws DownloadException, IOException;
}
