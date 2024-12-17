package it.unimore.prenotazioni.data;

import java.io.Serializable;
import java.util.Date;

/**
 * The type Reservation.
 */
public class Reservation implements Serializable {


    /**
     * The constant MIN_START_HOUR.
     */
    public static final int MIN_START_HOUR = 8;
    /**
     * The constant MAX_START_HOUR.
     */
    public static final int MAX_START_HOUR = 18;

    private Date date;
    private int startHour;
    private int endHour;
    private String reservedBy;
    private String reason;

    /**
     * Instantiates a new Reservation.
     *
     * @param date       the date
     * @param startHour  the start hour
     * @param endHour    the end hour
     * @param reservedBy the reserved by
     * @param reason     the reason
     */
    public Reservation(Date date, int startHour, int endHour, String reservedBy, String reason) {
        this.date = date;
        this.startHour = startHour;
        this.endHour = endHour;
        this.reservedBy = reservedBy;
        this.reason = reason;
    }

    /**
     * Gets date.
     *
     * @return the date
     */
    public Date getDate() {
        return date;
    }

    /**
     * Sets date.
     *
     * @param date the date
     */
    public void setDate(Date date) {
        this.date = date;
    }

    /**
     * Gets end hour.
     *
     * @return the end hour
     */
    public int getEndHour() {
        return endHour;
    }

    /**
     * Sets end hour.
     *
     * @param endHour the end hour
     */
    public void setEndHour(int endHour) {
        this.endHour = endHour;
    }

    /**
     * Gets reason.
     *
     * @return the reason
     */
    public String getReason() {
        return reason;
    }

    /**
     * Sets reason.
     *
     * @param reason the reason
     */
    public void setReason(String reason) {
        this.reason = reason;
    }

    /**
     * Gets reserved by.
     *
     * @return the reserved by
     */
    public String getReservedBy() {
        return reservedBy;
    }

    /**
     * Sets reserved by.
     *
     * @param reservedBy the reserved by
     */
    public void setReservedBy(String reservedBy) {
        this.reservedBy = reservedBy;
    }

    /**
     * Gets start hour.
     *
     * @return the start hour
     */
    public int getStartHour() {
        return startHour;
    }

    /**
     * Sets start hour.
     *
     * @param startHour the start hour
     */
    public void setStartHour(int startHour) {
        this.startHour = startHour;
    }

    /**
     * Overlaps boolean.
     *
     * @param other the other
     * @return the boolean
     */
    public boolean overlaps(Reservation other) {
        if(!this.date.equals(other.date)) return false;

        return this.startHour < other.endHour && this.endHour > other.startHour;
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "date=" + date +
                ", startHour=" + startHour +
                ", endHour=" + endHour +
                ", reservedBy='" + reservedBy + '\'' +
                ", reason='" + reason + '\'' +
                '}';
    }
}
