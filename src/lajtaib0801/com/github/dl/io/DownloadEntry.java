package lajtaib0801.com.github.dl.io;

import lajtaib0801.com.github.dl.io.exception.EmptyFileNameException;
import lajtaib0801.com.github.dl.io.exception.EmptyUrlException;
import lajtaib0801.com.github.dl.io.exception.InvalidInputException;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Arrays;

public class DownloadEntry {
    private final URL url;
    private final String fileName;

    public DownloadEntry(String line) throws InvalidInputException, MalformedURLException, URISyntaxException {
        if (line.contains("-as")) {
            String[] splittedLine = Arrays.stream(line.split("-as")).map(x -> x.strip()).toArray(String[]::new);
            if (splittedLine[0].isEmpty()) {
                throw new EmptyUrlException("Empty url in line: '" + line + "'");
            }
            url = new URI(splittedLine[0]).toURL();
            if (splittedLine[1].isEmpty()) {
                throw new EmptyFileNameException("Empty file name for path: " + url);
            }
            fileName = splittedLine[1];
        } else {
            url = new URI(line.strip()).toURL();
            fileName = url.getPath().substring(url.getPath().lastIndexOf('/') + 1);
        }
    }
    public URL getUrl() {
        return url;
    }

    public String getFileName() {
        return fileName;
    }
}
