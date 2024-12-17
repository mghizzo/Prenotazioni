package it.unimore.prenotazioni.data;

import it.unimore.prenotazioni.enums.ClassroomType;

import java.io.Serializable;
import java.util.Objects;

/**
 * The type Classroom.
 */
public abstract class Classroom implements Serializable {
    private int number;
    private int capacity;
    private ClassroomType type;


    /**
     * Instantiates a new Classroom.
     *
     * @param number   the number
     * @param capacity the capacity
     * @param type     the type
     */
    public Classroom(int number, int capacity, ClassroomType type) {
        this.number = number;
        this.capacity = capacity;
        this.type = type;
    }

    /**
     * Gets readable name.
     *
     * @return the readable name
     */
    public String getReadableName() {
        return this.type + "-" + this.number;
    }

    /**
     * Gets capacity.
     *
     * @return the capacity
     */
    public int getCapacity() {
        return capacity;
    }

    /**
     * Sets capacity.
     *
     * @param capacity the capacity
     */
    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    /**
     * Gets number.
     *
     * @return the number
     */
    public int getNumber() {
        return number;
    }

    /**
     * Sets number.
     *
     * @param number the number
     */
    public void setNumber(int number) {
        this.number = number;
    }

    /**
     * Gets type.
     *
     * @return the type
     */
    public ClassroomType getType() {
        return type;
    }

    /**
     * Sets type.
     *
     * @param type the type
     */
    public void setType(ClassroomType type) {
        this.type = type;
    }

    /**
     * Validate time boolean.
     *
     * @param startTime the start time
     * @param endTime   the end time
     * @return the boolean
     */
    public abstract boolean validateTime(int startTime, int endTime);

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Classroom classroom)) return false;
        return number == classroom.number;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(number);
    }
}
