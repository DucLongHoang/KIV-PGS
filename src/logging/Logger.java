package logging;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.LinkedList;
import java.util.List;

/**
 * Logger class - Project Objects will have the ability to log their tasks
 * @author Long
 * @version 1.0
 */
public class Logger {
    /** List of all Logs */
    private final List<Log> ALL_LOGS;
    /** Name of to-be-created output file */
    private final Path OUTPUT_FILE;

    /**
     * Constructor for Logger
     * @param outputFileName name of the output file
     */
    public Logger(String outputFileName) {
        this.ALL_LOGS = new LinkedList<>();
        this.OUTPUT_FILE = Paths.get(outputFileName);
    }

    /**
     * Method adds a new log to the List of all Logs
     * @param newLog to be added
     */
    public synchronized void log(Log newLog) {
        this.ALL_LOGS.add(newLog);
    }

    /**
     * Method sorts all Logs according to their natural ordering, which is time
     */
    private void sortLogs() {

    }

    /**
     * Method returns a List of Log by their String representation
     * @return List of all Logs in String
     */
    public List<String> getFormattedLogs() {
        this.sortLogs();

        List<String> result = new LinkedList<>();
        for(Log l: ALL_LOGS) {
            result.add(l.toString());
        }
        return result;
    }

    /**
     * Method writes all Logs into an output file
     * @param header of the file that will be created
     */
    public void writeLogsToFile(String header) {
        try {
            PrintWriter pw = new PrintWriter(OUTPUT_FILE.toString());
            pw.println(header);
            pw.close();

            Files.write(OUTPUT_FILE, this.getFormattedLogs(),
                    StandardCharsets.UTF_8, StandardOpenOption.WRITE,
                    StandardOpenOption.CREATE, StandardOpenOption.APPEND);
        }
        catch (IOException e) {
            System.out.println("Failed to create output file!!!");
        }
    }
}
