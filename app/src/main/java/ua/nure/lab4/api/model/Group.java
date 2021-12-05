package ua.nure.lab4.api.model;

import java.util.ArrayList;

public class Group {
    private String group;
    private ArrayList<String> students;

    public Group(String group, ArrayList<String> students) {
        this.group = group;
        this.students = students;
    }

    public String getGroup() {
        return group;
    }

    public ArrayList<String> getStudents() {
        return students;
    }
}
