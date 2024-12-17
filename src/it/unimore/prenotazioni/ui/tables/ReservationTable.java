package it.unimore.prenotazioni.ui.tables;

import it.unimore.prenotazioni.Main;
import it.unimore.prenotazioni.data.Classroom;
import it.unimore.prenotazioni.data.Reservation;
import it.unimore.prenotazioni.managers.ClassroomManager;
import it.unimore.prenotazioni.managers.ReservationManager;
import it.unimore.prenotazioni.ui.dialogs.ManageReservationDialog;
import it.unimore.prenotazioni.ui.windows.MainWindow;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Date;
import java.util.List;
import java.util.Vector;

/**
 * The type Reservation table.
 */
public class ReservationTable extends Table {

    private ClassroomManager classroomManager;
    private ReservationManager reservationManager;
    private Date date;

    /**
     * Instantiates a new Reservation table.
     *
     * @param date the date
     */
    public ReservationTable(Date date) {
        super();
        this.classroomManager = Main.getInstance().getClassroomManager();
        this.reservationManager = Main.getInstance().getReservationManager();
        this.date = date;
        this.setupListeners();
        this.reload();
    }

    /**
     * Sets listeners.
     */
    void setupListeners() {
        this.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent mouseEvent) {
                JTable table =(JTable) mouseEvent.getSource();
                if (mouseEvent.getClickCount() == 2 && table.getSelectedRow() != -1) {
                    int selectedRowIndex = getSelectedRow();
                    int selectedColumnIndex = getSelectedColumn();
                    Classroom classroom = getClassroom(selectedColumnIndex);
                    Reservation reservation = getReservation(selectedRowIndex, selectedColumnIndex);
                    if(reservation == null) return;
                    int reservationIndex = reservationManager.getReservations(classroom).indexOf(reservation);

                    ManageReservationDialog dialog = new ManageReservationDialog(classroom, reservation);
                    Reservation newReservation = dialog.getReservation();
                    if(newReservation == null) {
                        reservationManager.removeReservation(classroom, reservationIndex);
                    } else {
                        reservationManager.setReservation(classroom, reservationIndex, newReservation);
                    }
                    reload();
                }
            }
        });
    }

    /**
     * Gets classroom.
     *
     * @param colIndex the col index
     * @return the classroom
     */
    public Classroom getClassroom(int colIndex) {
        if(colIndex < 0) return null;
        String columnName = getColumnName(colIndex);
        if(!columnName.contains("-")) return null;
        String[] classroomParams = columnName.split("-");
        if(classroomParams.length != 2) return null;

        int classroomNumber;

        try {
            classroomNumber = Integer.parseInt(classroomParams[1]);
        } catch (NumberFormatException ex) {
            return null;
        }

        return this.classroomManager.getClassroom(classroomNumber);
    }

    /**
     * Gets start hour.
     *
     * @param rowIndex the row index
     * @return the start hour
     */
    public int getStartHour(int rowIndex) {
        if(rowIndex < 0) return -1;
        String timeSlot = getModel().getValueAt(rowIndex, 0).toString();
        if(!timeSlot.contains("-")) return -1;

        String[] timeSlotParams = timeSlot.split("-");
        if(timeSlotParams.length != 2) return -1;

        int startHour;

        try {
            startHour = Integer.parseInt(timeSlotParams[0]);
        } catch(NumberFormatException ex) {
            return -1;
        }

        return startHour;
    }

    /**
     * Gets reservation.
     *
     * @param rowIndex the row index
     * @param colIndex the col index
     * @return the reservation
     */
    public Reservation getReservation(int rowIndex, int colIndex) {
        Classroom classroom = getClassroom(colIndex);
        if(classroom == null) return null;
        int startHour = getStartHour(rowIndex);
        int endHour = startHour + 1;

        return this.reservationManager.getReservation(classroom, date, startHour, endHour);
    }

    @Override
    protected void render(DefaultTableModel model) {
        if(this.classroomManager == null || this.reservationManager == null || this.date == null) return;
        model.setRowCount(0);
        model.setColumnCount(0);
        model.addColumn("Orario");
        List<Classroom> classrooms = this.classroomManager.getClassrooms();
        classrooms.forEach(classroom -> {
            model.addColumn(classroom.getReadableName());
        });

        for(int startHour = Reservation.MIN_START_HOUR; startHour < Reservation.MAX_START_HOUR; startHour++) {
            int endHour = startHour + 1;
            String timeSlot = startHour + "-" + endHour;
            Vector<String> rows = new Vector<>();
            rows.add(timeSlot);

            classrooms.forEach(classroom -> {
                rows.add(null);
            });

            for (int i = 0; i < classrooms.size(); i++) {
                Classroom classroom = classrooms.get(i);
                Reservation reservation = this.reservationManager.getReservation(classroom, this.date, startHour, endHour);
                if(reservation == null) continue;
                int rowIndex = i + 1;
                rows.set(rowIndex, reservation.getReservedBy());
            }
            model.addRow(rows);
        }
    }

    @Override
    public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
        Component cell = super.prepareRenderer(renderer, row, column);
        cell.setBackground(Color.WHITE);
        cell.setForeground(Color.BLACK);
        if(column == 0) return cell;

        if(getValueAt(row, column) == null) return cell;

        cell.setBackground(Color.RED);
        cell.setForeground(Color.WHITE);

        return cell;
    }

    /**
     * Sets date.
     *
     * @param date the date
     */
    public void setDate(Date date) {
        this.date = date;
        this.reload();
    }

    public boolean isCellEditable(int row, int column) {
        return false;
    };
}
