/**
 * Parser class - parses args input and input file
 */
public class Parser {
    private int workerCount, workerTime, lorryCap, lorryTime, ferryCap;
    private String jarPath, inputFile, outputFile;

    /**
     * Constructor for Parser
     * @param args arguments of application
     */
    public Parser(String[] args) {
        parseArgs(args);
        printArgs();
    }

    /**
     * Method parses arguments and assigns values to variables
     * @param args arguments to be parsed
     */
    private void parseArgs(String[] args) {
        for(int i = 1; i < args.length; i+=2){
            switch(args[i]){
                case "-jar" -> this.jarPath = args[i + 1];
                case "-i" -> this.inputFile = args[i + 1];
                case "-o" -> this.outputFile = args[i + 1];
                case "-cWorker" -> this.workerCount = Integer.parseInt(args[i + 1]);
                case "-tWorker" -> this.workerTime = Integer.parseInt(args[i + 1]);
                case "-capLorry" -> this.lorryCap = Integer.parseInt(args[i + 1]);
                case "-tLorry" -> this.lorryTime = Integer.parseInt(args[i + 1]);
                case "-capFerry" -> this.ferryCap = Integer.parseInt(args[i + 1]);
            }
        }
    }

    public void printArgs() {
        StringBuilder sb = new StringBuilder("Input parameters:");
        sb.append("\n\t-jar = ").append(this.jarPath);
        sb.append("\n\t-i = ").append(this.inputFile);
        sb.append("\n\t-o = ").append(this.outputFile);
        sb.append("\n\t-cWorker = ").append(this.workerCount);
        sb.append("\n\t-tWorker = ").append(this.workerTime);
        sb.append("\n\t-capLorry = ").append(this.lorryCap);
        sb.append("\n\t-tLorry = ").append(this.lorryTime);
        sb.append("\n\t-capFerry = ").append(this.ferryCap);
        sb.append('\n');
        System.out.println(sb);
    }

    /**
     * Getter for workerCount
     * @return number of Workers
     */
    public int getWorkerCount() {
        return workerCount;
    }

    /**
     * Getter for workerTime
     * @return max time a Worker is working on a block
     */
    public int getWorkerTime() {
        return workerTime;
    }

    /**
     * Getter for lorryCap
     * @return capacity of a Lorry
     */
    public int getLorryCap() {
        return lorryCap;
    }

    /**
     * Getter for lorryTime
     * @return time it takes a Lorry to get to the Ferry
     */
    public int getLorryTime() {
        return lorryTime;
    }

    /**
     * Getter for ferryCap
     * @return number of Lorries a Ferry can transport
     */
    public int getFerryCap() {
        return ferryCap;
    }

    /**
     * Getter for jarPath
     * @return path to the .jar file
     */
    public String getJarPath() {
        return jarPath;
    }

    /**
     * Getter for inputFile
     * @return path to input map
     */
    public String getInputFile() {
        return inputFile;
    }

    /**
     * Getter for outputFile
     * @return path where to write the output
     */
    public String getOutputFile() {
        return outputFile;
    }
}