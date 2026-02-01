package lajtaib0801.com.github.dl.io;

import lajtaib0801.com.github.dl.io.exception.*;

import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Scanner;

public class InputFileParser {
    private final ArrayList<EntryToDownload> filesToDownload;

    public InputFileParser(String inputFile) throws InvalidInputException, IOException, URISyntaxException {
        Path inputFilePath = PathParser.parse(inputFile);
        filesToDownload = loadInputFile(inputFilePath);
    }

    private ArrayList<EntryToDownload> loadInputFile(Path inputFile) throws InvalidInputException, IOException, URISyntaxException {
        ArrayList<EntryToDownload> entries = new ArrayList<>();

        try (Scanner scanner = new Scanner(new FileReader(inputFile.toString()))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().strip();
                if (line.isEmpty()) {
                    continue;
                }
                EntryToDownload file = new EntryToDownload(line);

                if (entries.stream().anyMatch(x -> file.getOutputPath().equals(x.getOutputPath()))) {
                    throw new EntryAlreadyExistsWithFileNameException(file.getOutputPath().toString());
                }
                entries.add(file);
            }
        }

        return entries;
    }

    public ArrayList<EntryToDownload> getFilesToDownload() {
        return filesToDownload;
    }

}
