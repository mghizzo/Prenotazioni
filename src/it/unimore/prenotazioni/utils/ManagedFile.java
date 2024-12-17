package it.unimore.prenotazioni.utils;

import java.io.*;

/**
 * The type Managed file.
 *
 * @param <T> the type parameter
 */
public class ManagedFile<T> {
    private File file;

    /**
     * Instantiates a new Managed file.
     *
     * @param file the file
     */
    public ManagedFile(File file) {
        this.file = file;
    }

    /**
     * Load t.
     *
     * @return the t
     */
    @SuppressWarnings("unchecked")
    public T load() {
        if(!file.exists()) return null;

        try {
            FileInputStream fis = new FileInputStream(this.file);
            ObjectInputStream ois = new ObjectInputStream(fis);
            T o = (T) ois.readObject();
            ois.close();
            return o;
        } catch (IOException | ClassNotFoundException e) {
            return null;
        }
    }

    /**
     * Save boolean.
     *
     * @param o the o
     * @return the boolean
     */
    public boolean save(T o) {

        try {
            FileOutputStream fos = new FileOutputStream(this.file);
            ObjectOutputStream ois = new ObjectOutputStream(fos);
            ois.writeObject(o);
            ois.flush();
            ois.close();
            return true;
        } catch (IOException e) {
            return false;
        }
    }
}
