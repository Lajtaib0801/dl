package lajtaib0801.com.github.dl.download;

import lajtaib0801.com.github.dl.model.EntryToDownload;

import java.util.concurrent.Callable;

public class DownloadTask implements Callable<DownloadResult> {
    private final EntryToDownload entry;
    private final Downloader downloader;

    public DownloadTask(EntryToDownload entry, Downloader downloader) {
        this.entry = entry;
        this.downloader = downloader;
    }

    @Override
    public DownloadResult call() throws Exception {
        try {
            downloader.download(entry);
            return new DownloadResult(entry, true, null);
        } catch (Exception e) {
            return new DownloadResult(entry, false, e);
        }
    }
}
