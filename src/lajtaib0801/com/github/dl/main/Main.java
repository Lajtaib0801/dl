package lajtaib0801.com.github.dl.main;


import lajtaib0801.com.github.dl.download.DownloadManager;
import lajtaib0801.com.github.dl.download.DownloadResult;
import lajtaib0801.com.github.dl.io.InputFileParser;
import lajtaib0801.com.github.dl.model.EntryToDownload;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try {
            Scanner scanner = new Scanner(System.in);
            System.out.print("Input file path: ");
            ArrayList<EntryToDownload> entries = new InputFileParser(scanner.nextLine()).getFilesToDownload();
            System.out.print("Number of threads [default 5]: ");
            String nThreads = scanner.nextLine();

            try (DownloadManager downloadManager = new DownloadManager(nThreads.isEmpty() ? 5 : Integer.parseInt(nThreads))) {
                List<DownloadResult> results = downloadManager.downloadEntries(entries);
                for (var result : results) {
                    if (result.isSuccessful()) {
                        System.out.printf("Download of %s is successful!\n", result.getEntry().getOutputPath());
                    } else {
                        System.out.printf("Could not download: %s", result.getEntry().getUri());
                    }
                }
            }


        } catch (Exception e) {
            StringBuilder exceptionMessage = new StringBuilder();
            exceptionMessage.append("Exception occurred:\n");
            exceptionMessage.append(e.getClass().getSimpleName());
            exceptionMessage.append(": ");
            exceptionMessage.append(e.getMessage());
            exceptionMessage.append("\n");

            if (e.getCause() != null) {
                exceptionMessage.append("Cause: ");
                exceptionMessage.append(e.getCause());
                exceptionMessage.append("\n");
            }
            System.err.printf(exceptionMessage.toString());
        }
    }
}
