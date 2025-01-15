package it.unimore.prenotazioni.data;

import it.unimore.prenotazioni.enums.ClassroomType;

/**
 * The type Teaching classroom.
 */
public class TeachingClassroom extends Classroom {

    private boolean projector;
    private boolean whiteboard;

    /**
     * Instantiates a new Teaching classroom.
     *
     * @param number     the number
     * @param capacity   the capacity
     * @param type       the type
     * @param projector  the projector
     * @param whiteboard the whiteboard
     */
    public TeachingClassroom(int number, int capacity, ClassroomType type, boolean projector, boolean whiteboard) {
        super(number, capacity, type);
        this.projector = projector;
        this.whiteboard = whiteboard;
    }

    /**
     * Is projector boolean.
     *
     * @return the boolean
     */
    public boolean isProjector() {
        return projector;
    }

    /**
     * Sets projector.
     *
     * @param projector the projector
     */
    public void setProjector(boolean projector) {
        this.projector = projector;
    }

    /**
     * Is whiteboard boolean.
     *
     * @return the boolean
     */
    public boolean isWhiteboard() {
        return whiteboard;
    }

    /**
     * Sets whiteboard.
     *
     * @param whiteboard the whiteboard
     */
    public void setWhiteboard(boolean whiteboard) {
        this.whiteboard = whiteboard;
    }

    @Override
    public String toString() {
        return "TeachingClassroom{" +
                "projector=" + projector +
                ", whiteboard=" + whiteboard +
                '}';
    }

    @Override
    public boolean validateTime(int startTime, int endTime) {
        int totalHours = endTime - startTime;

        return totalHours <= 8;
    }
}
