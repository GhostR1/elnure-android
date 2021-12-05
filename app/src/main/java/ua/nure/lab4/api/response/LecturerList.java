package ua.nure.lab4.api.response;

import java.util.ArrayList;

import ua.nure.lab4.api.model.Group;
import ua.nure.lab4.api.model.Lecturer;

public class LecturerList {
    ArrayList<Lecturer> lecturers;

    public ArrayList<Lecturer> getLecturers() {
        return lecturers;
    }
}
