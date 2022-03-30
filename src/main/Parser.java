package main;

/**
 * Parser class - parses args input and input file
 */
public class Parser {
    /** Values to be parsed and saved from command line arguments */
    private int workerCount, workerTime, lorryCap, lorryTime, ferryCap;
    /** Paths to the JAR file, input and output files */
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
            switch(args[i].substring(1)){
                case "jar" -> this.jarPath = args[i + 1];
                case "i" -> this.inputFile = args[i + 1];
                case "o" -> this.outputFile = args[i + 1];
                case "cWorker" -> this.workerCount = Integer.parseInt(args[i + 1]);
                case "tWorker" -> this.workerTime = Integer.parseInt(args[i + 1]);
                case "capLorry" -> this.lorryCap = Integer.parseInt(args[i + 1]);
                case "tLorry" -> this.lorryTime = Integer.parseInt(args[i + 1]);
                case "capFerry" -> this.ferryCap = Integer.parseInt(args[i + 1]);
            }
        }
    }

    /**
     * Method prints command line parameters
     */
    public void printArgs() {
        String s = "Input parameters:" +
                "\n\t-jar = " + this.jarPath +
                "\n\t-i = " + this.inputFile +
                "\n\t-o = " + this.outputFile +
                "\n\t-cWorker = " + this.workerCount +
                "\n\t-tWorker = " + this.workerTime +
                "\n\t-capLorry = " + this.lorryCap +
                "\n\t-tLorry = " + this.lorryTime +
                "\n\t-capFerry = " + this.ferryCap +
                '\n';
        System.out.println(s);
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