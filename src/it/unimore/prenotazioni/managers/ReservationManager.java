package it.unimore.prenotazioni.managers;


import it.unimore.prenotazioni.data.Classroom;
import it.unimore.prenotazioni.data.Reservation;
import it.unimore.prenotazioni.utils.ManagedFile;

import java.io.File;
import java.util.*;

/**
 * The type Reservation manager.
 */
public class ReservationManager {

    private Map<Classroom, List<Reservation>> reservations = new HashMap<>();

    /**
     * Gets reservations.
     *
     * @return the reservations
     */
    public Map<Classroom, List<Reservation>> getReservations() {
        return reservations;
    }

    /**
     * Add reservation.
     *
     * @param classroom   the classroom
     * @param reservation the reservation
     */
    public void addReservation(Classroom classroom, Reservation reservation) {
        if(!this.reservations.containsKey(classroom)) {
            this.reservations.put(classroom, new ArrayList<>());
        }
        this.reservations.get(classroom).add(reservation);
    }

    /**
     * Sets reservation.
     *
     * @param classroom   the classroom
     * @param index       the index
     * @param reservation the reservation
     */
    public void setReservation(Classroom classroom, int index, Reservation reservation) {
        if(!this.reservations.containsKey(classroom)) throw new IllegalArgumentException();
        List<Reservation> reservations = this.reservations.get(classroom);
        reservations.set(index, reservation);
        this.reservations.put(classroom, reservations);
    }

    /**
     * Remove reservation.
     *
     * @param classroom the classroom
     * @param index     the index
     */
    public void removeReservation(Classroom classroom, int index) {
        if(!this.reservations.containsKey(classroom)) throw new IllegalArgumentException();
        List<Reservation> reservations = this.reservations.get(classroom);
        reservations.remove(index);
        this.reservations.put(classroom, reservations);
    }

    /**
     * Gets reservations.
     *
     * @param classroom the classroom
     * @return the reservations
     */
    public List<Reservation> getReservations(Classroom classroom) {
        return this.reservations.get(classroom);
    }

    /**
     * Gets reservations by date.
     *
     * @param date the date
     * @return the reservations by date
     */
    public Map<Classroom, List<Reservation>> getReservationsByDate(Date date) {
        Map<Classroom, List<Reservation>> filteredReservations = new HashMap<>();


        reservations.forEach((classroom, reservationList) -> {
            List<Reservation> filteredList = reservationList.stream().filter(reservation -> reservation.getDate().equals(date)).toList();
            if(!filteredList.isEmpty()) {
                filteredReservations.put(classroom, filteredList);
            }
        });

        return filteredReservations;
    }

    /**
     * Reservation overlaps boolean.
     *
     * @param classroom          the classroom
     * @param currentReservation the current reservation
     * @param newReservation     the new reservation
     * @return the boolean
     */
    public boolean reservationOverlaps(Classroom classroom, Reservation currentReservation, Reservation newReservation) {
        Map<Classroom, List<Reservation>> reservationsByDate = this.getReservationsByDate(newReservation.getDate());
        if(!reservationsByDate.containsKey(classroom)) return false;
        List<Reservation> reservations = reservationsByDate.get(classroom);
        return reservations.stream().filter(r -> !r.equals(currentReservation)).anyMatch(r -> r.overlaps(newReservation));
    }

    /**
     * Gets reservation.
     *
     * @param classroom the classroom
     * @param date      the date
     * @param startHour the start hour
     * @param endHour   the end hour
     * @return the reservation
     */
    public Reservation getReservation(Classroom classroom, Date date, int startHour, int endHour) {
        Map<Classroom, List<Reservation>> reservationMap = getReservationsByDate(date);
        if(!reservationMap.containsKey(classroom)) return null;
        List<Reservation> reservationList = reservationMap.get(classroom);
        return reservationList.stream().filter(reservation -> reservation.getStartHour() <= startHour && reservation.getEndHour() >= endHour).findFirst().orElse(null);
    }

    /**
     * Save reservations.
     *
     * @param file the file
     */
    public void saveReservations(File file) {
        ManagedFile<Map<Classroom, List<Reservation>>> mf = new ManagedFile<>(file);
        mf.save(reservations);
    }

    /**
     * Load reservations boolean.
     *
     * @param file the file
     * @return the boolean
     */
    public boolean loadReservations(File file) {
        ManagedFile<Map<Classroom, List<Reservation>>> mf = new ManagedFile<>(file);
        Map<Classroom, List<Reservation>> reservationListMap = mf.load();
        if(reservationListMap == null) return false;
        this.reservations = reservationListMap;
        return true;
    }
}
