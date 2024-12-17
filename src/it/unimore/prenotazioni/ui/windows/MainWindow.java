package it.unimore.prenotazioni.ui.windows;


import it.unimore.prenotazioni.Main;
import it.unimore.prenotazioni.data.Classroom;
import it.unimore.prenotazioni.data.Reservation;
import it.unimore.prenotazioni.managers.ReservationManager;
import it.unimore.prenotazioni.threads.SaveThread;
import it.unimore.prenotazioni.ui.dialogs.ManageReservationDialog;
import it.unimore.prenotazioni.ui.fields.DateField;
import it.unimore.prenotazioni.ui.tables.ReservationTable;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.print.PrinterException;
import java.io.File;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

/**
 * The type Main window.
 */
public class MainWindow extends Window implements WindowListener {

    private Date selectedDate;

    private final ReservationManager reservationManager;

    private File file;
    private ReservationTable reservationTable;
    private Thread saveThread;


    /**
     * Instantiates a new Main window.
     */
    public MainWindow() {
        super("Prenotazioni", false);
        this.reservationManager = Main.getInstance().getReservationManager();
        this.selectedDate = Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant());
        this.saveThread = new SaveThread(Main.getInstance().getReservationManager());
        this.saveThread.start();
        this.initComponents();
        this.setVisible(true);
    }


    @Override
    void initComponents() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        JPanel topPanel = getTopPanel();

        this.reservationTable = new ReservationTable(this.selectedDate);
        JScrollPane scrollPane = new JScrollPane(reservationTable);

        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

        JButton addReservationButton = getAddReservationButton(reservationTable);
        bottomPanel.add(addReservationButton);

        panel.add(topPanel, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(bottomPanel, BorderLayout.SOUTH);

        this.add(panel);
    }

    private JPanel getTopPanel() {
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

        JLabel dateLabel = new JLabel("Data");
        DateField dateField = new DateField();
        JButton applyButton = new JButton("Applica");
        applyButton.addActionListener(e -> {
            this.selectedDate = dateField.getValue();
            this.reservationTable.setDate(this.selectedDate);
        });

        topPanel.add(dateLabel);
        topPanel.add(dateField);
        topPanel.add(applyButton);
        return topPanel;
    }

    private JButton getAddReservationButton(ReservationTable reservationTable) {
        JButton addReservationButton = new JButton("Aggiungi prenotazione");
        addReservationButton.addActionListener(e -> {
            int selectedColumnIndex = reservationTable.getSelectedColumn();
            int selectedRowIndex = reservationTable.getSelectedRow();

            Classroom classroom = reservationTable.getClassroom(selectedColumnIndex);
            if(classroom == null) {
                JOptionPane.showMessageDialog(this, "Colonna selezionata non valida", "Errore", JOptionPane.ERROR_MESSAGE);
                return;
            }
            int startHour = reservationTable.getStartHour(selectedRowIndex);
            if(startHour == -1) {
                JOptionPane.showMessageDialog(this, "Riga selezionata non valida", "Errore", JOptionPane.ERROR_MESSAGE);
                return;
            }
            int endHour = startHour + 1;
            ManageReservationDialog dialog = new ManageReservationDialog(classroom, this.selectedDate, startHour, endHour);
            Reservation reservation = dialog.getReservation();
            if(reservation == null) {
                return;
            }
            this.reservationManager.addReservation(classroom, reservation);
            this.reservationTable.reload();
        });
        return addReservationButton;
    }


    /**
     * Save reservations.
     */
    public void saveReservations() {
        if(this.file == null) {
            JFileChooser fileChooser = new JFileChooser();
            if(fileChooser.showSaveDialog(this) != JFileChooser.APPROVE_OPTION) return;
            File file = fileChooser.getSelectedFile();

            if(file.exists()) {
                int result = JOptionPane.showConfirmDialog(this,"Attenzione, file già esistente, vuoi sovrascriverlo?", "Attenzione",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE);
                if(result != JOptionPane.YES_OPTION) return;
            }
            this.file = file;
        }
        this.reservationManager.saveReservations(this.file);
    }

    /**
     * Open reservations.
     */
    public void openReservations() {
        JFileChooser fileChooser = new JFileChooser();
        if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            this.file = fileChooser.getSelectedFile();

            if(!this.reservationManager.loadReservations(this.file)) {
                JOptionPane.showMessageDialog(this, "Impossibile aprire il file", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            this.reservationTable.reload();
        }
    }

    @Override
    JMenuBar createMenuBar() {
        JMenuBar menu = new JMenuBar();
        JMenu file = new JMenu("File");

        JMenuItem save = new JMenuItem("Salva");
        save.addActionListener(e -> this.saveReservations());

        JMenuItem open = new JMenuItem("Apri");
        open.addActionListener(e -> this.openReservations());

        JMenuItem print = new JMenuItem("Stampa");
        print.addActionListener(e -> {
            try {
                this.reservationTable.print();
            } catch (PrinterException ex) {
                throw new RuntimeException(ex);
            }
        });

        JMenuItem exit = new JMenuItem("Esci");
        exit.addActionListener(e -> System.exit(0));


        file.add(save);
        file.add(open);
        file.add(print);
        file.add(exit);


        menu.add(file);
        return menu;
    }

    @Override
    public void windowOpened(WindowEvent e) {

    }

    @Override
    public void windowClosing(WindowEvent e) {
        this.saveThread.interrupt();

    }

    @Override
    public void windowClosed(WindowEvent e) {

    }

    @Override
    public void windowIconified(WindowEvent e) {

    }

    @Override
    public void windowDeiconified(WindowEvent e) {

    }

    @Override
    public void windowActivated(WindowEvent e) {}

    @Override
    public void windowDeactivated(WindowEvent e) {

    }


}
