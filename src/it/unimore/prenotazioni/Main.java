package it.unimore.prenotazioni;

import it.unimore.prenotazioni.managers.ClassroomManager;
import it.unimore.prenotazioni.managers.ReservationManager;
import it.unimore.prenotazioni.ui.windows.MainWindow;
import it.unimore.prenotazioni.ui.windows.ManageClassroomWindow;

import javax.swing.*;

/**
 * The type Main.
 */
public class Main {

    private static Main instance;

    private final ClassroomManager classroomManager;
    private final ReservationManager reservationManager;

    /**
     * Instantiates a new Main.
     */
    public Main() {
        this.classroomManager = new ClassroomManager();
        this.reservationManager = new ReservationManager();
        instance = this;
    }

    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {
        new Main();
        if(args.length == 1 && args[0].equalsIgnoreCase("-mc")) {
            SwingUtilities.invokeLater(ManageClassroomWindow::new);
            return;
        }
        SwingUtilities.invokeLater(MainWindow::new);
    }

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static Main getInstance() {
        return instance;
    }

    /**
     * Gets classroom manager.
     *
     * @return the classroom manager
     */
    public ClassroomManager getClassroomManager() {
        return classroomManager;
    }

    /**
     * Gets reservation manager.
     *
     * @return the reservation manager
     */
    public ReservationManager getReservationManager() {
        return reservationManager;
    }
}
