package lajtaib0801.com.github.dl.io;

import java.nio.file.Path;
import java.nio.file.Paths;

public final class PathParser {
    private PathParser() {
        throw new AssertionError("Utility class cannot be instantiated!");
    }

    public static Path parse(String rawPath) {
        if (rawPath == null || rawPath.isBlank()) {
            throw new IllegalArgumentException("File path cannot be empty!");
        }

        String path = rawPath.strip();

        if (path.equals("~") || path.startsWith("~/") || path.startsWith("~\\")) {
            String home = System.getProperty("user.home");
            path = home + path.substring(1);
        }

        return Paths.get(path).toAbsolutePath().normalize();
    }

    public static String toUnixLikePath(Path path) {
        return toUnixLikePath(path, false);
    }

    public static String toUnixLikePath(Path path, boolean useTildeAsUserHome) {
        String unixPath = path.toAbsolutePath().normalize().toString().replace('\\', '/');

        if (useTildeAsUserHome) {
            String home = System.getProperty("user.home").replace('\\', '/');
            if (unixPath.startsWith(home)) {
                unixPath = "~" + unixPath.substring(home.length());
            }
        }

        return unixPath;
    }
}

