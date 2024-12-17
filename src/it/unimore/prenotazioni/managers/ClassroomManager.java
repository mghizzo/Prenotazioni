package it.unimore.prenotazioni.managers;

import it.unimore.prenotazioni.data.Classroom;
import it.unimore.prenotazioni.utils.ManagedFile;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * The type Classroom manager.
 */
public class ClassroomManager {


    private final File file = new File("aule.dat");

    private List<Classroom> classrooms = new ArrayList<>();

    /**
     * Instantiates a new Classroom manager.
     */
    public ClassroomManager() {
        this.loadClassrooms();
    }

    /**
     * Load classrooms.
     */
    public void loadClassrooms() {
        ManagedFile<List<Classroom>> mf = new ManagedFile<>(file);
        List<Classroom> classroomList = mf.load();
        if(classroomList == null) return;
        this.classrooms = classroomList;
    }

    /**
     * Save classrooms.
     */
    public void saveClassrooms() {
        ManagedFile<List<Classroom>> mf = new ManagedFile<>(file);
        mf.save(classrooms);
    }

    /**
     * Gets classrooms.
     *
     * @return the classrooms
     */
    public List<Classroom> getClassrooms() {
        return classrooms;
    }

    /**
     * Gets classroom.
     *
     * @param number the number
     * @return the classroom
     */
    public Classroom getClassroom(int number) {
        return classrooms.stream().filter(classroom -> classroom.getNumber() == number).findFirst().orElse(null);
    }
}
