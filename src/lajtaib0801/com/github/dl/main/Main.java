package lajtaib0801.com.github.dl.main;


import lajtaib0801.com.github.dl.io.InputFileParser;

public class Main {
    public static void main(String[] args) {
        try {
            InputFileParser parser = new InputFileParser("~/Desktop/pics.txt");
            System.out.printf("Test output:");
            int counter = 1;
            for (var parsedLine : parser.getFilesToDownload()) {
                System.out.printf("File%d\n\tUrl:\t%s\n\tFile name:\t%s\n", counter, parsedLine.getUrl(), parsedLine.getFileName());
                counter++;
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
            System.out.printf(exceptionMessage.toString());
        }
    }
}
