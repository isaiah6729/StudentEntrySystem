package com.zybooks.wgu;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SQLdatabase extends SQLiteOpenHelper {

    public SQLdatabase( Context context) { super(context, "SQLdatabase.db", null, 1); }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table terms (termID integer primary key autoincrement, title text, startDate text, endDate text)");

        db.execSQL("create table courses(courseID integer primary key  autoincrement, title text, startDate text, endDate text, status text, " +
                "instructorsname text, instructorsphoneNumber text, instructorsemail text, termid integer)");

        db.execSQL("create table notes(notesid integer primary key autoincrement,  notes text, courseID integer)");

        db.execSQL("create table Objectiveassessments(assessmentsid integer primary key autoincrement,  assessments text, startDate text, endDate text, courseID integer)");

        db.execSQL("create table Performanceassessments(assessmentsid integer primary key autoincrement,  assessments text, startDate text, endDate text, courseID integer)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("drop table if exists terms");
            db.execSQL("drop table if exists courses");
    }

    public long insertTerms(String title, String startDate, String endDate) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put("title", title);
        contentValues.put("startDate", startDate);
        contentValues.put("endDate", endDate);

        long wgu = db.insert("terms", null, contentValues);

        return wgu;

    }

    public long insertCourses(String title, String startDate, String endDate , String status,
                              String instructorsname , String instructorsphoneNumber , String instructorsemail, int termid ) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put("title", title);
        contentValues.put("startDate", startDate);
        contentValues.put("endDate", endDate);
        contentValues.put("status", status);
        contentValues.put("instructorsname", instructorsname);
        contentValues.put("instructorsphoneNumber", instructorsphoneNumber);
        contentValues.put("instructorsemail", instructorsemail);
        contentValues.put("termid", termid);

        long wgu = db.insert("courses", null, contentValues);
        return wgu;
    }

    public long insertNotes(String notes , int courseID ) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put("notes", notes);
        contentValues.put("courseID", courseID);

        long wgu = db.insert("notes", null, contentValues);

        return wgu;

    }

    public long insertObjectiveassessments(String assessments , String startDate , String endDate , int courseID ) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put("assessments", assessments);
        contentValues.put("courseID", courseID);
        contentValues.put("startDate", startDate);
        contentValues.put("endDate", endDate);


        long wgu = db.insert("Objectiveassessments", null, contentValues);

        return wgu;

    }

    public long insertPerformanceassessments(String assessments , String startDate , String endDate , int courseID ) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put("assessments", assessments);
        contentValues.put("courseID", courseID);
        contentValues.put("startDate", startDate);
        contentValues.put("endDate", endDate);

        long wgu = db.insert("Performanceassessments", null, contentValues);

        return wgu;

    }

    public Cursor readNotes () {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("select * from notes ", null);

        while (cursor.moveToNext()) {
            int notesid = cursor.getInt(0);
            String notes = cursor.getString(1);
            int courseID = cursor.getInt(2);

            NotesObject.addNotes(new NotesObject(notesid,  courseID, notes));
        }

        return cursor;
    }

    public Cursor readObjectiveAssessments () {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("select assessmentsid,  startDate , endDate ,courseID, assessments from Objectiveassessments ", null);

        while (cursor.moveToNext()) {
            int assessmentsid = cursor.getInt(0);
            int courseID = cursor.getInt(3);
            String assessments = cursor.getString(4);
            String startDate = cursor.getString(1);
            String endDate = cursor.getString(2);

            //        TermsObject.deleteTerms();
            AssessmentObjectObjective.addObjectiveAssessments(new AssessmentObjectObjective(assessmentsid, courseID, assessments, startDate , endDate));
        }

        return cursor;

    }

    public Cursor readPerformanceAssessments () {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("select assessmentsid, startDate , endDate ,courseID, assessments from Performanceassessments ", null);

        while (cursor.moveToNext()) {
            int assessmentsid = cursor.getInt(0);
            int courseID = cursor.getInt(3);
            String assessments = cursor.getString(4);
            String startDate = cursor.getString(1);
            String endDate = cursor.getString(2);

            int id = 0;

            AssessmentObjectPerformance.addPerformanceAssessments(new AssessmentObjectPerformance(assessmentsid, courseID, assessments, startDate , endDate));
        }

        return cursor;

    }

    public Cursor readTerms () {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("select termID, title, startDate, endDate from terms ", null);

        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String title2 = cursor.getString(1);
            String startDate2 = cursor.getString(2);
            String endDate2 = cursor.getString(3);

    //        TermsObject.deleteTerms();
            TermsObject.addTerms(new TermsObject(id, title2, startDate2, endDate2));
        }

        return cursor;

    }

    public Cursor readCourses() {
        SQLiteDatabase db2 = this.getReadableDatabase();

        Cursor cursor = db2.rawQuery("select courseID, title,startDate , endDate, status, instructorsname, " +
                "instructorsphoneNumber, instructorsemail, termid from courses ", null);

        while (cursor.moveToNext()) {
            int courseid = cursor.getInt(0);
            String title = cursor.getString(1);
            String startDate = cursor.getString(2);
            String endDate = cursor.getString(3);
            String status = cursor.getString(4);
            String instructorsname = cursor.getString(5);
            String instructorsphoneNumber = cursor.getString(6);
            String instructorsemail = cursor.getString(7);
            int termid = cursor.getInt(8);


            CoursesObject.addCourses(new CoursesObject(courseid, title,  startDate,  endDate ,  status,
                    instructorsname ,  instructorsphoneNumber ,  instructorsemail, termid));
        }

        return cursor;

    }


    public Cursor readTermCourses(int termID) {
        SQLiteDatabase db2 = this.getReadableDatabase();

        String[] args = {String.valueOf(termID)};

        Cursor cursor = db2.rawQuery("select * from courses where termID = ?", args);

        while (cursor.moveToNext()) {
            int courseid = cursor.getInt(0);
            String title = cursor.getString(1);
            String startDate = cursor.getString(2);
            String endDate = cursor.getString(3);
            String status = cursor.getString(4);
            String instructorsname = cursor.getString(5);
            String instructorsphoneNumber = cursor.getString(6);
            String instructorsemail = cursor.getString(7);
            int termid = cursor.getInt(8);

            CoursesObject.addCourses(new CoursesObject( courseid, title,  startDate,  endDate ,  status,
                    instructorsname ,  instructorsphoneNumber ,  instructorsemail, termid));
        }

        return cursor;

    }

    public Boolean readObjectiveassessments (String date1) {

        SQLiteDatabase database = this.getReadableDatabase();
        String[] args = {date1};
        Cursor cursor = database.rawQuery("select startDate from Objectiveassessments where startDate = ?", args);

        if (cursor.getCount() > 0) {
            return true;
        }
        else {
            return  false;
        }
    }

    public Boolean readPerformanceassessmentsDates (String date1) {

        SQLiteDatabase database = this.getReadableDatabase();
        String[] args = {date1};
        Cursor cursor = database.rawQuery("select startDate from Performanceassessments where startDate = ?", args);

        if (cursor.getCount() > 0) {
            return true;
        }
        else {
            return  false;
        }
    }

    public Boolean readObjectiveEnddates (String date1) {

        SQLiteDatabase database = this.getReadableDatabase();
        String[] args = {date1};
        Cursor cursor = database.rawQuery("select endDate from Objectiveassessments where endDate = ?", args);

        if (cursor.getCount() > 0) {
            return true;
        }
        else {
            return  false;
        }
    }

    public Boolean readPerformanceEndDates (String date1) {

        SQLiteDatabase database = this.getReadableDatabase();
        String[] args = {date1};
        Cursor cursor = database.rawQuery("select endDate from Performanceassessments where endDate = ?", args);

        if (cursor.getCount() > 0) {
            return true;
        }
        else {
            return  false;
        }
    }


    /**
     * delete term
     * @param termID delete
     * @return delete
     */
    public boolean checkTerms(int termID) {
        SQLiteDatabase db = this.getWritableDatabase();
        String[] strings = {String.valueOf(termID)};
        Cursor cursor = db.rawQuery("select * from courses where termID = ?", strings);

        if (cursor.getCount()  > 0) {
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * delete term
     * @param termID delete
     * @return delete
     */
    public int deleteTerms(int termID) {
        SQLiteDatabase db = this.getWritableDatabase();
        String[] strings = {String.valueOf(termID)};
        int   deleterows = db.delete("terms", "termID = ?", strings);
        return deleterows;
    }

    /**
     * delete term
     * @param courseID delete
     * @return delete
     */
    public int deleteCourses(int courseID) {
        SQLiteDatabase db = this.getWritableDatabase();
        String[] strings = {String.valueOf(courseID)};
        int   deleterows = db.delete("courses", "termID = ?", strings);
        return deleterows;
    }

    public  int deleteObjectiveassessments(int assessmentID) {
        SQLiteDatabase db = this.getWritableDatabase();
        String[] args = {String.valueOf(assessmentID)};

        int rows = db.delete("Objectiveassessments", "assessmentsid = ?", args);
        return rows;
    }

    public  int deletePerformanceassessments(int assessmentID) {
        SQLiteDatabase db = this.getWritableDatabase();
        String[] args = {String.valueOf(assessmentID)};

        int rows = db.delete("Performanceassessments", "assessmentsid = ?", args);
        return rows;
    }

    public  int deleteNotes(int notesID) {
        SQLiteDatabase db = this.getWritableDatabase();
        String[] args = {String.valueOf(notesID)};

        int rows = db.delete("notes", "notesid = ?", args);
        return rows;
    }


 /*   public Cursor readCourseDates (String date1, String date2) {

        SQLiteDatabase database = this.getReadableDatabase();
        String[] args = {date1, date2};
        Cursor cursor = database.rawQuery("select courseID, title from courses where startDate = ? and endDate = ?", args);
        return cursor;
    }
*/

    public long updateTerms(int TermID, String title, String startDate, String endDate) {

        SQLiteDatabase db = this.getWritableDatabase();

        String[] args = {String.valueOf(TermID)};

        ContentValues contentValues = new ContentValues();
        contentValues.put("title", title);
        contentValues.put("startDate", startDate);
        contentValues.put("endDate", endDate);

        long wgu = db.update("terms", contentValues, "termID = ?", args);

        return wgu;

    }

    public long updateCourses(int courseID, String title , String startDate , String endDate , String status ,
                              String instructorsname , String instructorsphoneNumber , String instructorsemail) {

        SQLiteDatabase db = this.getWritableDatabase();

        String[] args = {String.valueOf(courseID)};

        ContentValues contentValues = new ContentValues();
        contentValues.put("title", title);
        contentValues.put("startDate", startDate);
        contentValues.put("endDate", endDate);
        contentValues.put("status", status);
        contentValues.put("instructorsname", instructorsname);
        contentValues.put("instructorsphoneNumber", instructorsphoneNumber);
        contentValues.put("instructorsemail", instructorsemail);

        long wgu = db.update("courses", contentValues, "courseID = ?", args);

        return wgu;

    }

    public long updateObjectiveassessments(int assessmentsid ,  String assessments) {

        SQLiteDatabase db = this.getWritableDatabase();

        String[] args = {String.valueOf(assessmentsid)};

        ContentValues contentValues = new ContentValues();
        contentValues.put("assessments", assessments);

        long wgu = db.update("Objectiveassessments", contentValues, "assessmentsid = ?", args);

        return wgu;

    }

    public long updatePerformanceassessments(int assessmentsid ,  String assessments) {

        SQLiteDatabase db = this.getWritableDatabase();

        String[] args = {String.valueOf(assessmentsid)};

        ContentValues contentValues = new ContentValues();
        contentValues.put("assessments", assessments);

        long wgu = db.update("Performanceassessments", contentValues, "assessmentsid = ?", args);

        return wgu;

    }
}
