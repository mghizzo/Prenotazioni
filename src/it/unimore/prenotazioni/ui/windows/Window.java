package it.unimore.prenotazioni.ui.windows;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * The type Window.
 */
public abstract class Window extends JFrame {

    /**
     * Instantiates a new Window.
     *
     * @param title          the title
     * @param initComponents the init components
     */
    public Window(String title, boolean initComponents) {
        super(title);
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                if(java.awt.Window.getWindows().length == 0) {
                    System.exit(0);
                }
            }
        });
        this.setJMenuBar(this.createMenuBar());
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setPreferredSize(new Dimension(640, 480));
        this.pack();
        this.setLocationRelativeTo(null);
        if(initComponents) {
            this.initComponents();
            this.setVisible(true);
        }
    }

    /**
     * Init components.
     */
    abstract void initComponents();

    /**
     * Create menu bar j menu bar.
     *
     * @return the j menu bar
     */
    abstract JMenuBar createMenuBar();
}
