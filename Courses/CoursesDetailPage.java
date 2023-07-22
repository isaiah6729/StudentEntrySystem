package com.zybooks.wgu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import DialogFragments.DialogFragment_CantDelete;

// view details of an individual course
public class CoursesDetailPage extends AppCompatActivity {

    SQLdatabase database;

    int termid;
    int courseid;
    String title;
    String start;
    String end;
    String status;
    String name;
    String phone;
    String email;

    TextView textView;
    TextView textView2;
    TextView textView3;
    TextView textView4;
    TextView textView5;
    TextView textView6;
    TextView textView7;
    TextView textView8;
    TextView textView9;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_courses_detail_page);

        // start database
        database =  new SQLdatabase(this);

        // get values from course page
         termid = getIntent().getIntExtra("termid", termid);
         courseid = getIntent().getIntExtra("courseid", courseid);
         title = getIntent().getStringExtra("title");
         start = getIntent().getStringExtra("startDate");
         end = getIntent().getStringExtra("endDate");
         status = getIntent().getStringExtra("status");
         name = getIntent().getStringExtra("name");
         phone = getIntent().getStringExtra("phone");
         email = getIntent().getStringExtra("email");

        textView9 = findViewById(R.id.detailsCoursetext9);
         textView8 = findViewById(R.id.detailsCoursetext8);
         textView = findViewById(R.id.detailsCoursetext);
         textView2 = findViewById(R.id.detailsCoursetext2);
         textView3 = findViewById(R.id.detailsCoursetext3);
         textView4 = findViewById(R.id.detailsCoursetext4);
         textView5 = findViewById(R.id.detailsCoursetext5);
         textView6 = findViewById(R.id.detailsCoursetext6);
         textView7 = findViewById(R.id.detailsCoursetext7);

         // set text of textviews
         textView.setText(title);
        textView2.setText(start);
        textView3.setText(end);
        textView4.setText(status);
        textView5.setText(name);
        textView6.setText(phone);
        textView7.setText(email);
        textView8.setText(String.valueOf(termid));
        textView9.setText(String.valueOf(courseid));

    }

    public void deleteCourseClick(View view) {

        // delete course by courseID
        int row = database.deleteCourses(courseid);

        if (row > 0) {
            DialogFragment_CantDelete del = new DialogFragment_CantDelete();
            del.show(getSupportFragmentManager(), "del");

            Intent course = new Intent(this, CoursesView.class);
            startActivity(course);
        }

    }

    // go to home page
    public void homePageClick(View view) {
        Intent home = new Intent(this, HomePage.class);
        startActivity(home);
    }

    // add a note page
    public void addNoteClick(View view) {
        Intent note = new Intent(this, NotesAdd.class);
        note.putExtra("courseid", courseid);
        startActivity(note);
    }

    // update course page and send info
    public void updateCourseClick(View view) {
        Intent update = new Intent(getBaseContext(), CoursesUpdate.class);
        update.putExtra("id", termid);
        update.putExtra("title", title);
        update.putExtra("start", start);
        update.putExtra("end", end);
        update.putExtra("status", status);
        update.putExtra("name", name);
        update.putExtra("phone", phone);
        update.putExtra("email", email);
        update.putExtra("courseid", courseid);
        startActivity(update);
    }

    // add an assessment page
    public void addAssessmentClick(View view) {
        Intent note = new Intent(this, AssessmentAdd.class);
        note.putExtra("courseid", courseid);
        startActivity(note);
    }

    // view notes page
    public void viewnotesClick(View view) {
        Intent note = new Intent(this, NotesView.class);
        note.putExtra("courseid", courseid);
        startActivity(note);
    }
}