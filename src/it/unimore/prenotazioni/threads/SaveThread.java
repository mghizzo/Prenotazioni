package it.unimore.prenotazioni.threads;

import it.unimore.prenotazioni.managers.ReservationManager;

import java.io.File;

/**
 * The type Save thread.
 */
public class SaveThread extends Thread {

    private final ReservationManager reservationManager;
    private final File file;

    /**
     * Instantiates a new Save thread.
     *
     * @param reservationManager the reservation manager
     */
    public SaveThread(ReservationManager reservationManager) {
        super();
        this.reservationManager = reservationManager;
        this.file = new File("reservations.temp.dat");
    }


    @Override
    public void run() {
        while(true) {
            try {
                Thread.sleep(30 * 1000L);
                System.out.println("Autosaving...");
                this.reservationManager.saveReservations(file);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
