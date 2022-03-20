import java.io.*;
import java.util.LinkedList;
import java.util.List;

/**
 *
 */
public class Parser {
    private int cWorker, tWorker, tLorry, capLorry, capFerry;
    private final String INPUT_FILE;
    private final String SPACE = " ";
    private List<Integer> blocks;

    public Parser(String inputFile) {
        this.INPUT_FILE = inputFile;

        try {
            blocks = readFile(
                    new BufferedReader(new FileReader(INPUT_FILE)));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Integer> readFile(BufferedReader br) throws IOException {
        List<Integer> result = new LinkedList<>();
        String line; String[] tokens;

        while((line = br.readLine()) != null){
            tokens = line.split(SPACE);
            for(String token: tokens){
                if(!token.isEmpty()) result.add(token.length());
            }
        }

        System.out.println("Boss -  closing file.");
        try {
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }
}
