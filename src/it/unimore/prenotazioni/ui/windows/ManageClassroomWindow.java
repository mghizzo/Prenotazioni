package it.unimore.prenotazioni.ui.windows;

import it.unimore.prenotazioni.Main;
import it.unimore.prenotazioni.data.Classroom;
import it.unimore.prenotazioni.managers.ClassroomManager;
import it.unimore.prenotazioni.ui.dialogs.ManageClassroomDialog;
import it.unimore.prenotazioni.ui.tables.ClassroomTable;

import javax.swing.*;
import java.awt.*;

/**
 * The type Manage classroom window.
 */
public class ManageClassroomWindow extends Window {
    private final ClassroomManager classroomManager;

    /**
     * Instantiates a new Manage classroom window.
     */
    public ManageClassroomWindow() {
        super("Gestione aule", true);
        this.classroomManager = Main.getInstance().getClassroomManager();
    }

    @Override
    void initComponents() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));

        ClassroomTable classroomTable = new ClassroomTable();
        JScrollPane scrollPane = new JScrollPane(classroomTable);

        JButton addButton = new JButton("Aggiungi");
        addButton.addActionListener(e -> {
            ManageClassroomDialog dialog = new ManageClassroomDialog();
            Classroom classroom = dialog.getClassroom();
            if(classroom == null) return;
            this.classroomManager.getClassrooms().add(classroom);
            classroomTable.reload();
        });
        topPanel.add(addButton);

        panel.add(topPanel, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);

        this.add(panel);
    }

    @Override
    JMenuBar createMenuBar() {
        JMenuBar menu = new JMenuBar();
        JMenu file = new JMenu("File");

        JMenuItem save = new JMenuItem("Salva");
        save.addActionListener(e -> {
            this.classroomManager.saveClassrooms();
        });

        file.add(save);

        menu.add(file);
        return menu;
    }
}