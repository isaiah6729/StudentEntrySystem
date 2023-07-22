package com.zybooks.wgu;

import java.util.ArrayList;

// object to hold courses
public class CoursesObject {


    private final static ArrayList<CoursesObject> Courses = new ArrayList<>();

    private final int courseid;
    private final String title;
    private final String startDate;
    private final String endDate;
    private final String status;
    private final String instructorsName;
    private final String instructorsPhone;
    private final String instructorsEmail;
    private final int termid;

    public CoursesObject () {
        courseid = 0;
        title = "You need a term, buster";
        startDate = "12/16/1985";
        endDate = "12/16/2023";
        status = "none";
        instructorsName = "none";
        instructorsPhone = "none";
        instructorsEmail = "none";
        termid = 0;
    }

    public CoursesObject (int courseid, String title, String startDate, String endDate,   String status , String instructorsName, String instructorsPhone, String instructorsEmail, int termid) {
        this.courseid = courseid;
        this.title = title;
        this.startDate = startDate;
        this.endDate = endDate;
       this.status = status;
        this.instructorsName = instructorsName;
        this.instructorsPhone = instructorsPhone;
        this.instructorsEmail = instructorsEmail;
        this.termid = termid;
    }

    public int getCourseid() {
        return courseid;
    }

    public int getTermid() {
        return termid;
    }

    public String getEndDate() {
        return endDate;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getTitle() {
        return title;
    }

    public String getInstructorsEmail() {
        return instructorsEmail;
    }

    public String getInstructorsName() {
        return instructorsName;
    }

    public String getInstructorsPhone() {
        return instructorsPhone;
    }

    public String getStatus() {
        return status;
    }

    public static void addCourses (CoursesObject courses) {
        Courses.add(courses);
    }

    public static void deleteCourses() {Courses.clear();}

    public static ArrayList<CoursesObject> getCourses() {
        return Courses;
    }
}
