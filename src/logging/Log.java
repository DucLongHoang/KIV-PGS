package logging;

/**
 * Log class - represents a log that every Object can make and is accumulated in a Logger
 * @author Long
 * @version 1.0
 */
public class Log implements Comparable<Log>{
    /** Time when the Log was made */
    private final long TIME;
    /** Role of the caller class e.g. Worker, Ferry, Lorry, etc. */
    private final String ROLE;
    /** ID number of Thread */
    private final long THREAD_NUM;
    /** Short description of the Log */
    private final String DESCRIPTION;

    /**
     * Constructor for Log
     * @param role of caller class
     * @param description of Log
     */
    public Log(String role, String description) {
        this.TIME = System.currentTimeMillis();
        this.ROLE = role;
        this.THREAD_NUM = Thread.currentThread().getId();
        this.DESCRIPTION = description;
    }

    /**
     * To String method
     * @return String representation of a Log
     */
    @Override
    public String toString() {
        return TIME + "," + ROLE + "," + THREAD_NUM + "," + DESCRIPTION;
    }

    /**
     * Method compares this and another Log by their time of logging
     * @param other Log to be compared to
     * @return -1 if this Log was made sooner, 0 if at the same time and 1 otherwise
     */
    @Override
    public int compareTo(Log other) {
        return (int)(this.TIME - other.TIME);
    }
}
