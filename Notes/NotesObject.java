package com.zybooks.wgu;

import java.util.ArrayList;

// objects to hold notes
public class NotesObject {

    private final static ArrayList<NotesObject> Notes = new ArrayList<>();

    private final int courseID;
    private final int notesid;
    private final String notes;

    public NotesObject () {
        notesid = 0;
        courseID = 0;
        notes = "notes";
    }

    public NotesObject (int  notesid, int courseID, String notes) {
        this.notesid = notesid;
        this.courseID = courseID;
        this.notes = notes;
    }

    public int getCourseID() {
        return courseID;
    }

    public int getNotesid() {
        return notesid;
    }

    public String getNotes() {
        return notes;
    }

    public static void addNotes(NotesObject notesObject) {
        Notes.add(notesObject);
    }

    public static void deleteNotes() {Notes.clear();}

    public static ArrayList<NotesObject> getListNotes() {
        return Notes;
    }

}
