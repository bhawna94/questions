import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExampleTwo {

    public static void main(String[] args) {

        List<String> fileNames = new ArrayList<>();
        Map<String, Integer> map = new HashMap<>();
        int count = 0;

        getAllFilesFromGivenPath("/home/Documents/questions", fileNames);

        fileNames.forEach(result -> {
            Path path = new File(result).toPath();
            if (Files.isRegularFile(path)) {
                BasicFileAttributes attr = null;
                try {
                    attr = Files.readAttributes(path, BasicFileAttributes.class);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                FileTime fileTime = attr.creationTime();
                String month = getCreatedMonthValue(fileTime);
                map.put(month, map.getOrDefault(month, count) + 1);
            }
        });
        System.out.println("Following output shows the Creation Month and count of the file corresponding to the month");
        System.out.println(map);
    }

    /**
     * Add all the files and directories for the path to the list.
     * @param fileNames
     */
    private static void getAllFilesFromGivenPath(String path, List<String> fileNames) {
        try {
            DirectoryStream<Path> directoryStream = Files.newDirectoryStream(Paths.get(path));
            for (Path paths : directoryStream) {
                fileNames.add(paths.toString());
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Return the month in which file is created.
     * @param fileTime
     * @return
     */
    private static String getCreatedMonthValue(FileTime fileTime) {
        SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");
        String dateCreated = df.format(fileTime.toMillis());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern( "MM/dd/yyyy" );
        LocalDate localDate = LocalDate.parse(dateCreated , formatter);
        return localDate.getMonth().toString();
    }
}
