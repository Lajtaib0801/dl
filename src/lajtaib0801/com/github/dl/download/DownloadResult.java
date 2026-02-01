package lajtaib0801.com.github.dl.download;

import lajtaib0801.com.github.dl.model.EntryToDownload;

public class DownloadResult {
    private final EntryToDownload entry;
    private final boolean successful;
    private final Exception error;

    public DownloadResult(EntryToDownload entry, boolean success, Exception error) {
        this.entry = entry;
        this.successful = success;
        this.error = error;
    }

    public boolean isSuccessful() { return successful; }
    public EntryToDownload getEntry() { return entry; }
    public Exception getError() { return error; }
}
