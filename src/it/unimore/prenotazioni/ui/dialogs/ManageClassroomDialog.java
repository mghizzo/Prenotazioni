package it.unimore.prenotazioni.ui.dialogs;

import it.unimore.prenotazioni.Main;
import it.unimore.prenotazioni.data.Classroom;
import it.unimore.prenotazioni.data.LabClassroom;
import it.unimore.prenotazioni.data.TeachingClassroom;
import it.unimore.prenotazioni.enums.ClassroomType;
import it.unimore.prenotazioni.managers.ClassroomManager;
import it.unimore.prenotazioni.ui.fields.EnumComboBox;
import it.unimore.prenotazioni.ui.fields.IntField;

import javax.swing.*;

/**
 * The type Manage classroom dialog.
 */
public class ManageClassroomDialog extends Dialog {

    private final ClassroomManager classroomManager;
    private Classroom classroom;
    private ClassroomType classroomType;
    private boolean edit;

    /**
     * Instantiates a new Manage classroom dialog.
     */
    public ManageClassroomDialog() {
        super(null, "Aggiungi Aula");
        this.edit = false;
        this.classroomType = ClassroomType.values()[0];
        this.classroomManager = Main.getInstance().getClassroomManager();
        this.initComponents();
        this.setVisible(true);
    }

    /**
     * Instantiates a new Manage classroom dialog.
     *
     * @param classroom the classroom
     */
    public ManageClassroomDialog(Classroom classroom) {
        super(null, "Modifica Aula");
        this.classroom = classroom;
        this.edit = true;
        this.classroomType = classroom.getType();
        this.classroomManager = Main.getInstance().getClassroomManager();
        this.initComponents();
        this.setVisible(true);
    }

    @Override
    void initComponents() {
        if(this.classroomManager == null) return;
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JLabel classroomNumberLabel = new JLabel("Numero aula");
        IntField classroomNumberField = new IntField();

        JLabel classroomCapacityLabel = new JLabel("Capacità aula");
        IntField classroomCapacityField = new IntField();

        JLabel classroomTypeLabel = new JLabel("Tipologia");
        EnumComboBox<ClassroomType> classroomTypeField = new EnumComboBox<>(ClassroomType.class);
        if(classroomType != null) classroomTypeField.setSelectedItem(classroomType);
        classroomTypeField.addItemListener(e -> {
            this.classroomType = (ClassroomType) e.getItem();
            this.edit = false;
            this.reload();
        });

        if(classroom != null) {
            classroomNumberField.setValue(classroom.getNumber());
            classroomCapacityField.setValue(classroom.getCapacity());
            classroomTypeField.setSelectedItem(classroomType);
        }

        panel.add(classroomNumberLabel);
        panel.add(classroomNumberField);

        panel.add(classroomCapacityLabel);
        panel.add(classroomCapacityField);

        panel.add(classroomTypeLabel);
        panel.add(classroomTypeField);

        JLabel outletsLabel = new JLabel("Prese");
        IntField outletsField = new IntField();

        JLabel computersLabel = new JLabel("Computer");
        IntField computersField = new IntField();


        if(classroomType == ClassroomType.LAB) {
            if(edit) {
                LabClassroom labClassroom = (LabClassroom) classroom;
                outletsField.setValue(labClassroom.getOutlets());
                computersField.setValue(labClassroom.getComputers());
            }
            panel.add(outletsLabel);
            panel.add(outletsField);


            panel.add(computersLabel);
            panel.add(computersField);
        }

        JLabel projectorLabel = new JLabel("Proiettore");
        JCheckBox projectorField = new JCheckBox();

        JLabel whiteboardLabel = new JLabel("Lavagna");
        JCheckBox whiteboardField = new JCheckBox();

        if(classroomType == ClassroomType.TEACHING) {
            if(edit) {
                TeachingClassroom teachingClassroom = (TeachingClassroom) classroom;
                projectorField.setSelected(teachingClassroom.isProjector());
                whiteboardField.setSelected(teachingClassroom.isWhiteboard());
            }
            panel.add(projectorLabel);
            panel.add(projectorField);

            panel.add(whiteboardLabel);
            panel.add(whiteboardField);
        }

        JButton saveButton = new JButton("Salva");
        saveButton.addActionListener(e -> {
            int number = classroomNumberField.getValue();
            Classroom exists = this.classroomManager.getClassroom(number);
            if(exists != null && !exists.equals(classroom)) {
                JOptionPane.showMessageDialog(this, "Numero aula già presente", "Errore", JOptionPane.ERROR_MESSAGE);
                return;
            }
            int capacity = classroomCapacityField.getValue();
           if(classroomType == ClassroomType.LAB) {
               int outlets = outletsField.getValue();
               int computers = computersField.getValue();
               this.classroom = new LabClassroom(number, capacity, classroomType, outlets, computers);
           }
           if(classroomType == ClassroomType.TEACHING) {
               boolean projector = projectorField.isSelected();
               boolean whiteboard = whiteboardField.isSelected();
               this.classroom = new TeachingClassroom(number, capacity, classroomType, projector, whiteboard);
           }
           close();
        });
        panel.add(saveButton);

        if(classroom != null) {
            JButton deleteButton = new JButton("Elimina");
            deleteButton.addActionListener(e -> {
                this.classroom = null;
                close();
            });

            panel.add(deleteButton);
        }

        this.add(panel);
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
