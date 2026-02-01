package lajtaib0801.com.github.dl.download.scheme;

import lajtaib0801.com.github.dl.download.Downloader;
import lajtaib0801.com.github.dl.download.scheme.http.HttpDownloader;
import lajtaib0801.com.github.dl.model.EntryToDownload;
import lajtaib0801.com.github.dl.download.exception.SchemeNotSupportedException;


public class DownloaderFactory {
    private DownloaderFactory() {}

    public static Downloader create(EntryToDownload entry) throws SchemeNotSupportedException {
        String scheme = entry.getUri().getScheme();
        if (scheme == null) {
            throw new SchemeNotSupportedException("'" + scheme + "' scheme is not supported!");
        }

        switch (scheme.toLowerCase()) {
            case "http":
            case "https":
                return new HttpDownloader();
            default:
                throw new SchemeNotSupportedException("'" + scheme + "' scheme is not supported!");
        }
    }
}
