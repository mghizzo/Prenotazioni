package it.unimore.prenotazioni.ui.dialogs;


import javax.swing.*;
import java.awt.*;

/**
 * The type Dialog.
 */
public abstract class Dialog extends JDialog {
    /**
     * Instantiates a new Dialog.
     *
     * @param owner the owner
     * @param title the title
     */
    public Dialog(JFrame owner, String title) {
        super(owner, title, true);
        this.setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);
        this.setMinimumSize(new Dimension(400, 350));
        this.pack();
        this.setLocationRelativeTo(null);
    }

    /**
     * Init components.
     */
    abstract void initComponents();

    /**
     * Close.
     */
    public void close() {
        this.dispose();
    }

    /**
     * Reload.
     */
    public void reload() {
        this.getContentPane().removeAll();
        this.initComponents();
        this.getContentPane().revalidate();
        this.getContentPane().repaint();
    }


}
