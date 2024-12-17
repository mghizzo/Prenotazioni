package it.unimore.prenotazioni.ui.fields;

import javax.swing.*;
import javax.swing.text.DateFormatter;
import javax.swing.text.DefaultFormatterFactory;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * The type Date field.
 */
public class DateField extends JFormattedTextField implements FocusListener {

    private final DateFormat dateFormat;

    /**
     * Instantiates a new Date field.
     */
    public DateField() {
        super();
        this.dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        DateFormatter dateFormatter = new DateFormatter(this.dateFormat);
        this.dateFormat.setLenient(false);
        dateFormatter.setAllowsInvalid(false);
        dateFormatter.setOverwriteMode(true);
        dateFormatter.setCommitsOnValidEdit(true);
        this.setValue(new Date());
        this.setFormatterFactory(new DefaultFormatterFactory(dateFormatter));
    }

    @Override
    public void setValue(Object value) {
        if (value instanceof Date || value == null) {
            super.setValue(value);
        } else {
            throw new IllegalArgumentException("Value must be of type Date");
        }
    }

    @Override
    public Date getValue() {
        Object value = super.getValue();
        return value instanceof Date ? (Date) value : null;
    }

    /**
     * Parse date.
     *
     * @throws ParseException the parse exception
     */
    public void parseDate() throws ParseException {
        String text = getText();
        if (text != null && !text.isEmpty()) {
            setValue(this.dateFormat.parse(text));
        }
    }


    @Override
    public void focusGained(FocusEvent e) {}

    @Override
    public void focusLost(FocusEvent e) {
        try {
            this.parseDate();
        } catch (ParseException ex) {
            throw new RuntimeException(ex);
        }
    }
}