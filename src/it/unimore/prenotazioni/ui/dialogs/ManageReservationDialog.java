package it.unimore.prenotazioni.ui.dialogs;

import it.unimore.prenotazioni.Main;
import it.unimore.prenotazioni.data.Classroom;
import it.unimore.prenotazioni.data.Reservation;
import it.unimore.prenotazioni.managers.ClassroomManager;
import it.unimore.prenotazioni.managers.ReservationManager;
import it.unimore.prenotazioni.ui.fields.DateField;
import it.unimore.prenotazioni.ui.fields.IntField;

import javax.swing.*;
import java.util.Date;

/**
 * The type Manage reservation dialog.
 */
public class ManageReservationDialog extends Dialog {
    private ClassroomManager classroomManager;
    private ReservationManager reservationManager;
    private Classroom classroom;
    private Reservation reservation;

    private Date date;
    private int startHour;
    private int endHour;

    /**
     * Instantiates a new Manage reservation dialog.
     *
     * @param classroom the classroom
     * @param date      the date
     * @param startHour the start hour
     * @param endHour   the end hour
     */
    public ManageReservationDialog(Classroom classroom, Date date, int startHour, int endHour) {
        super(null, "Aggiungi prenotazione");
        this.classroomManager = Main.getInstance().getClassroomManager();
        this.classroom = classroom;
        this.reservationManager = Main.getInstance().getReservationManager();
        this.date = date;
        this.startHour = startHour;
        this.endHour = endHour;
        this.reservation = null;
        this.initComponents();
        this.setVisible(true);

    }

    /**
     * Instantiates a new Manage reservation dialog.
     *
     * @param classroom   the classroom
     * @param reservation the reservation
     */
    public ManageReservationDialog(Classroom classroom, Reservation reservation) {
        super(null, "Modifica prenotazione");

        this.classroomManager = Main.getInstance().getClassroomManager();
        this.reservationManager = Main.getInstance().getReservationManager();
        this.classroom = classroom;
        this.date = reservation.getDate();
        this.startHour = reservation.getStartHour();
        this.endHour = reservation.getEndHour();
        this.reservation = reservation;
        this.initComponents();
        this.setVisible(true);
//        System.out.println(Helpers.getWindow(this, MainWindow.class));

    }

    @Override
    void initComponents() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JLabel classroomLabel = new JLabel("Aula");
        JTextField classroomNameField = new JTextField();
        classroomNameField.setEnabled(false);
        classroomNameField.setText(this.classroom.getReadableName());

        JLabel dateLabel = new JLabel("Data");
        DateField dateField = new DateField();
        dateField.setValue(date);

        JLabel startHourLabel = new JLabel("Ora inizio");
        IntField startHourField = new IntField();
        startHourField.setValue(startHour);

        JLabel endHourLabel = new JLabel("Ora fine");
        IntField endHourField = new IntField();
        endHourField.setValue(endHour);

        JLabel reservedByLabel = new JLabel("Richiedente");
        JTextField reservedByField = new JTextField();

        JLabel reasonLabel = new JLabel("Motivo");
        JTextField reasonField = new JTextField();

        if(reservation != null) {
            reservedByField.setText(reservation.getReservedBy());
            reasonField.setText(reservation.getReason());
        }

        JButton saveButton = new JButton("Salva");
        saveButton.addActionListener(e -> {
            Date date = dateField.getValue();
            int startHour = startHourField.getValue();
            int endHour = endHourField.getValue();
            String reservedBy = reservedByField.getText();
            String reason = reasonField.getText();
            Reservation temporaryReservation = new Reservation(date, startHour, endHour, reservedBy, reason);

            if(startHour >= endHour || startHour < Reservation.MIN_START_HOUR || endHour >= Reservation.MAX_START_HOUR || !classroom.validateTime(startHour, endHour) || this.reservationManager.reservationOverlaps(classroom, this.reservation, temporaryReservation)) {
                JOptionPane.showMessageDialog(this, "Periodo non valido", "Errore", JOptionPane.ERROR_MESSAGE);
                return;
            }
            this.reservation = temporaryReservation;
            close();
        });

        JButton deleteButton = new JButton("Elimina");
        deleteButton.addActionListener(e -> {
            this.reservation = null;
            close();
        });

        panel.add(classroomLabel);
        panel.add(classroomNameField);

        panel.add(dateLabel);
        panel.add(dateField);

        panel.add(startHourLabel);
        panel.add(startHourField);

        panel.add(endHourLabel);
        panel.add(endHourField);

        panel.add(reservedByLabel);
        panel.add(reservedByField);

        panel.add(reasonLabel);
        panel.add(reasonField);

        panel.add(saveButton);
        panel.add(deleteButton);

        this.add(panel);
    }

    /**
     * Gets reservation.
     *
     * @return the reservation
     */
    public Reservation getReservation() {
        return reservation;
    }

    /**
     * Gets classroom.
     *
     * @return the classroom
     */
    public Classroom getClassroom() {
        return classroom;
    }
}
