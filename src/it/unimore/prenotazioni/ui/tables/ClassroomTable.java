package it.unimore.prenotazioni.ui.tables;

import it.unimore.prenotazioni.Main;
import it.unimore.prenotazioni.data.Classroom;
import it.unimore.prenotazioni.managers.ClassroomManager;
import it.unimore.prenotazioni.ui.dialogs.ManageClassroomDialog;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

/**
 * The type Classroom table.
 */
public class ClassroomTable extends Table {

    private final ClassroomManager classroomManager;

    /**
     * Instantiates a new Classroom table.
     */
    public ClassroomTable() {
        super(new Object[] {"Numero", "Capacità", "Tipologia"});
        this.classroomManager = Main.getInstance().getClassroomManager();
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
                Point point = mouseEvent.getPoint();
                int row = table.rowAtPoint(point);
                if (mouseEvent.getClickCount() == 2 && table.getSelectedRow() != -1) {
                    Classroom classroom = classroomManager.getClassrooms().get(row);
                    ManageClassroomDialog dialog = new ManageClassroomDialog(classroom);
                    Classroom newClassroom = dialog.getClassroom();
                    if(newClassroom == null) {
                        classroomManager.getClassrooms().remove(row);
                    } else {
                        classroomManager.getClassrooms().set(row, newClassroom);
                    }
                    reload();
                }
            }
        });
    }

    @Override
    protected void render(DefaultTableModel model) {
        if(this.classroomManager == null) return;
        List<Classroom> classroomList = this.classroomManager.getClassrooms();
        model.setRowCount(0);
        for(Classroom classroom : classroomList) {
            model.addRow(new Object[]{classroom.getNumber(), classroom.getCapacity(), classroom.getType()});
        }
    }

    public boolean isCellEditable(int row, int column) {
        return false;
    };
}
