package lajtaib0801.com.github.dl.download;

import lajtaib0801.com.github.dl.model.EntryToDownload;

import java.io.IOException;

public interface Downloader {
    void download(EntryToDownload entry) throws IOException;
}
