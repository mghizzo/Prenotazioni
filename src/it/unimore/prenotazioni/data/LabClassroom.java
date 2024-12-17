package it.unimore.prenotazioni.data;

import it.unimore.prenotazioni.enums.ClassroomType;

/**
 * The type Lab classroom.
 */
public class LabClassroom extends Classroom {
    private int outlets;
    private int computers;

    /**
     * Instantiates a new Lab classroom.
     *
     * @param number    the number
     * @param capacity  the capacity
     * @param type      the type
     * @param computers the computers
     * @param outlets   the outlets
     */
    public LabClassroom(int number, int capacity, ClassroomType type, int computers, int outlets) {
        super(number, capacity, type);
        this.computers = computers;
        this.outlets = outlets;
    }

    /**
     * Gets computers.
     *
     * @return the computers
     */
    public int getComputers() {
        return computers;
    }

    /**
     * Sets computers.
     *
     * @param computers the computers
     */
    public void setComputers(int computers) {
        this.computers = computers;
    }

    /**
     * Gets outlets.
     *
     * @return the outlets
     */
    public int getOutlets() {
        return outlets;
    }

    /**
     * Sets outlets.
     *
     * @param outlets the outlets
     */
    public void setOutlets(int outlets) {
        this.outlets = outlets;
    }

    @Override
    public String toString() {
        return "LabClassroom{" +
                "computers=" + computers +
                ", outlets=" + outlets +
                '}';
    }

    @Override
    public boolean validateTime(int startTime, int endTime) {
        int totalHours = endTime - startTime;
        if(totalHours > 4) return false;
        return totalHours % 2 != 1;
    }
}
