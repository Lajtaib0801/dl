package lajtaib0801.com.github.dl.network;

import lajtaib0801.com.github.dl.io.EntryToDownload;
import lajtaib0801.com.github.dl.network.exception.DownloadException;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public class HttpDownloader implements Downloader {
    @Override
    public void download(EntryToDownload entry, Path targetDir) throws DownloadException, IOException {
        boolean downloadFinished = false;
        long existingSize = Files.exists(entry.getOutputPath()) ? Files.size(entry.getOutputPath()) : 0;
        while (!downloadFinished) {
            try (HttpClient client = HttpClient.newBuilder().followRedirects(HttpClient.Redirect.NORMAL).build()) {
                HttpRequest request = HttpRequest.newBuilder().setHeader("Range", "bytes=" + existingSize + "-")
                                                 .uri(entry.getUri()).GET().build();

                HttpResponse<InputStream> response = client.send(request, HttpResponse.BodyHandlers.ofInputStream());
                InputStream inputStream = response.body();
                int statusCode = response.statusCode();

                if (statusCode == 206) { /* Server supports Partial Contents */
                    try (OutputStream fileOutputStream = Files.newOutputStream(entry.getOutputPath(), StandardOpenOption.CREATE, StandardOpenOption.APPEND)) {
                        copyStreamToFile(inputStream, fileOutputStream);
                    }
                } else if (statusCode == 200) { /* We'll need to restart the download */
                    try (OutputStream fileOutputStream = Files.newOutputStream(entry.getOutputPath(), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING)) {
                        copyStreamToFile(inputStream, fileOutputStream);
                    }
                } else if (statusCode == 416) {
                    downloadFinished = true;
                } else {
                    throw new DownloadException("HTTP error: " + statusCode);
                }
            } catch (Exception e) {
                try {
                    Thread.sleep(10_000);
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                }
                existingSize = Files.exists(entry.getOutputPath()) ? Files.size(entry.getOutputPath()) : 0;
            }
        }
    }

    private void copyStreamToFile(InputStream inputStream, OutputStream fileOutputStream) throws IOException {
        int BUFFER_SIZE = 8192;
        byte[] buffer = new byte[BUFFER_SIZE];
        int bytesRead = 0;
        while (-1 != (bytesRead = inputStream.read(buffer))) {
            fileOutputStream.write(buffer, 0, bytesRead);
        }
    }
}
