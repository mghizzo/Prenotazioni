package it.unimore.prenotazioni.ui.tables;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

/**
 * The type Table.
 */
public abstract class Table extends JTable {

    private DefaultTableModel model;

    /**
     * Instantiates a new Table.
     */
    public Table() {
        this(new Object[] {});
    }

    /**
     * Instantiates a new Table.
     *
     * @param columnNames the column names
     */
    public Table(Object[] columnNames) {
        super();
        this.model = new DefaultTableModel(columnNames, 0);
        this.setModel(model);
        this.render(model);
    }

    /**
     * Render.
     *
     * @param model the model
     */
    protected abstract void render(DefaultTableModel model);

    /**
     * Reload.
     */
    public void reload() {
        this.render(this.model);
    }
}
