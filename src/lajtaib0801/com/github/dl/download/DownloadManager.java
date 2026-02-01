package lajtaib0801.com.github.dl.download;

import lajtaib0801.com.github.dl.download.scheme.DownloaderFactory;
import lajtaib0801.com.github.dl.model.EntryToDownload;
import lajtaib0801.com.github.dl.download.exception.SchemeNotSupportedException;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class DownloadManager implements AutoCloseable {
    private final ExecutorService executorService;

    public DownloadManager(int parallelDownloadsNumber) {
        executorService = Executors.newFixedThreadPool(parallelDownloadsNumber);
    }

    public List<DownloadResult> downloadEntries(List<EntryToDownload> entries) throws SchemeNotSupportedException {
        List<Future<DownloadResult>> downloadResultFutures = new ArrayList<>(entries.size());

        for (var entry : entries) {
            Downloader downloader = DownloaderFactory.create(entry);
            downloadResultFutures.add(executorService.submit(new DownloadTask(entry, downloader)));
        }
        List<DownloadResult> results = new ArrayList<>(entries.size());

        for (int i = 0; i < downloadResultFutures.size(); i++) {
            Future<DownloadResult> future = downloadResultFutures.get(i);
            EntryToDownload entry = entries.get(i);

            try {
                results.add(future.get());

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                results.add(new DownloadResult(entry, false, e));

            } catch (ExecutionException e) {
                results.add(new DownloadResult(entry, false, (Exception) e.getCause()));
            }
        }


        return results;
    }

    @Override
    public void close() throws Exception {
        executorService.shutdown();
    }
}
