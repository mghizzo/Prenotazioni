package it.unimore.prenotazioni.ui.fields;
import javax.swing.*;


/**
 * The type Enum combo box.
 *
 * @param <T> the type parameter
 */
public class EnumComboBox<T extends Enum<T>> extends JComboBox<T> {

    /**
     * Instantiates a new Enum combo box.
     *
     * @param enumType the enum type
     */
    public EnumComboBox(Class<T> enumType) {
        super(enumType.getEnumConstants());
        setBounds(50, 100,90,20);
    }
}
