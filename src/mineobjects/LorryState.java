package mineobjects;

/**
 * LorryState enum class - all possible states of Lorry object.
 * Used primarily in early stages of development
 * @author Long
 * @version 1.0
 */
public enum LorryState {
    EMPTY(0), NOT_FULL(1), FULL(2),
    TO_FERRY(3), ON_FERRY(4),
    TO_DESTINATION(5), IN_DESTINATION(6);

    /** Value of enum */
    private final int VALUE;

    /**
     * Constructor for enum
     * @param i value of enum
     */
    LorryState(int i) {
        this.VALUE = i;
    }

    /**
     * Getter for VALUE
     * @return value of enum
     */
    public int getValue() {
        return VALUE;
    }

    /**
     * To String method
     * @return String representation of enum
     */
    @Override
    public String toString() {
        return this.name();
    }
}
