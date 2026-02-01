package lajtaib0801.com.github.dl.io;

import lajtaib0801.com.github.dl.io.exception.EmptyFileNameException;
import lajtaib0801.com.github.dl.io.exception.EmptyUrlException;
import lajtaib0801.com.github.dl.io.exception.InvalidInputException;

import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

public class EntryToDownload {
    private final URI uri;
    private final Path outputPath;

    public EntryToDownload(String line) throws InvalidInputException, URISyntaxException {
        if (line.contains("-as")) {
            String[] splittedLine = Arrays.stream(line.split(" -as ")).map(x -> x.strip()).toArray(String[]::new);
            if (splittedLine[0].isEmpty()) {
                throw new EmptyUrlException("Empty url in line: '" + line + "'");
            }
            uri = new URI(splittedLine[0]);
            if (splittedLine[1].isEmpty()) {
                throw new EmptyFileNameException("Empty file name for path: " + uri);
            }
            outputPath = PathParser.parse(splittedLine[1]);
        } else {
            uri = new URI(line.strip());
            Path downloadsDir = Paths.get(System.getProperty("user.home"), "Downloads");
            String fileNameFromUrl = Paths.get(uri.getPath())
                    .getFileName() != null
                    ? Paths.get(uri.getPath()).getFileName().toString()
                    : "downloaded_file";
            outputPath = PathParser.parse(downloadsDir.resolve(fileNameFromUrl).toString());
        }
    }
    public URI getUri() {
        return uri;
    }

    public Path getOutputPath() {
        return outputPath;
    }
}
