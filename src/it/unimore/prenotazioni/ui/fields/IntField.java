package it.unimore.prenotazioni.ui.fields;

import javax.swing.*;
import javax.swing.text.NumberFormatter;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.text.NumberFormat;

/**
 * The type Int field.
 */
public class IntField extends JFormattedTextField implements FocusListener {

    /**
     * Instantiates a new Int field.
     */
    public IntField() {
        super();
        NumberFormat numberFormat = NumberFormat.getNumberInstance();
        NumberFormatter numberFormatter = new NumberFormatter(numberFormat);
        numberFormatter.setValueClass(Integer.class);
        numberFormatter.setAllowsInvalid(false);
        numberFormatter.setCommitsOnValidEdit(true);
        this.setFormatter(numberFormatter);
        this.addFocusListener(this);
    }

    @Override
    public void focusGained(FocusEvent e) {}

    @Override
    public void focusLost(FocusEvent e) {
        try {
            String text = getText();
            int value = Integer.parseInt(text);
            setValue(value);
        } catch (NumberFormatException ex) {
            setValue(0);
        }
    }

    @Override
    public Integer getValue() {
        return (Integer) super.getValue();
    }
}
