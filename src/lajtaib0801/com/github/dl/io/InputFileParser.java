package lajtaib0801.com.github.dl.io;

import lajtaib0801.com.github.dl.io.exception.*;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class InputFileParser {
    private final ArrayList<DownloadEntry> filesToDownload;

    public InputFileParser(String inputFile) throws InvalidInputException, IOException {
        filesToDownload = loadInputFile(inputFile);
    }

    private ArrayList<DownloadEntry> loadInputFile(String inputFileName) throws InvalidInputException, IOException {
        ArrayList<DownloadEntry> entries = new ArrayList<>();

        try (Scanner scanner = new Scanner(new FileReader(inputFileName))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().strip();
                if (line.isEmpty()) {
                    continue;
                }
                if (!line.contains("-as")) {
                    throw new NoSeparatorInLineException("Every line that holds data should contain \"-as\" as separator!");
                }
                DownloadEntry file = new DownloadEntry(line.split("\\\\s*-as\\\\s*"));
                if (entries.stream().anyMatch(x -> file.getFileName().equals(x.getFileName()))) {
                    throw new EntryAlreadyExistsWithFileNameException(file.getFileName());
                }
                entries.add(file);
            }
        }

        return entries;
    }


}
