package ua.nure.lab4.api.model;

public class Lecturer {
    private String lecturer;
    private String courses;

    public Lecturer(String lecturer, String courses) {
        this.lecturer = lecturer;
        this.courses = courses;
    }

    public String getLecturer() {
        return lecturer;
    }

    public String getCourses() {
        return courses;
    }
}
