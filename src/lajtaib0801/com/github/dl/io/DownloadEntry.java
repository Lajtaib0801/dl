package lajtaib0801.com.github.dl.io;

import lajtaib0801.com.github.dl.io.exception.EmptyFileNameException;
import lajtaib0801.com.github.dl.io.exception.InvalidInputException;
import lajtaib0801.com.github.dl.io.exception.TooManyArgumentsForFileException;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.stream.Collectors;

public class DownloadEntry {
    private URL path;
    private String fileName;

    public DownloadEntry(String[] fileParams) throws InvalidInputException, MalformedURLException {
        if (fileParams.length > 2) {
            throw new TooManyArgumentsForFileException(
                    Arrays.stream(fileParams)
                            .map(x -> '"' + x + '"').collect(Collectors.joining(", "))
            );
        }
        String url = fileParams[0].strip();
        path = new URL(url);
        if (fileParams.length == 1) {
            fileName = path.getPath().substring(path.getPath().lastIndexOf('/') + 1);
            if (fileName.isEmpty()) {
                throw new EmptyFileNameException("Empty file name for path: " + path);
            }
        } else {
            fileName = fileParams[1].strip();
        }
    }
    public URL getPath() {
        return path;
    }

    public String getFileName() {
        return fileName;
    }
}
