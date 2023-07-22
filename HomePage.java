package com.zybooks.wgu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

import DialogFragments.DialogFragment_Dates;
import DialogFragments.DialogFragment_NoDates;

public class HomePage extends AppCompatActivity {

    SQLdatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);

        database = new SQLdatabase(this);

        // get today's date
        String todaysDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("M/dd/yyyy"));

        // see if any assessments are due today
        if (database.readObjectiveassessments(todaysDate) || database.readPerformanceassessmentsDates(todaysDate)) {
            DialogFragment_Dates date = new DialogFragment_Dates();
            date.show(getSupportFragmentManager(), "date");
        }

        else {
            DialogFragment_NoDates date = new DialogFragment_NoDates();
            date.show(getSupportFragmentManager(), "date");
        }

        // see if any assessments are due today
        if (database.readObjectiveEnddates(todaysDate) || database.readPerformanceEndDates(todaysDate)) {
            DialogFragment_Dates date = new DialogFragment_Dates();
            date.show(getSupportFragmentManager(), "date");
        }

        else {
            DialogFragment_NoDates date = new DialogFragment_NoDates();
            date.show(getSupportFragmentManager(), "date");
        }

    }

    // go to terms page
    public void termsClick(View view) {
        Intent open = new Intent(this, TermsView.class);
        startActivity(open);
    }

    // go to assessments page
    public void assessmentsClick(View view) {
        Intent open = new Intent(this, AssessmentChoose.class);
        startActivity(open);
    }

    // go to courses page
    public void coursesClick(View view) {
        Intent open = new Intent(this, CoursesView.class);
        startActivity(open);
    }

    // go to notes page
    public void viewnotesClick(View view) {
        Intent VIEW = new Intent(this, NotesView.class);
        startActivity(VIEW);
    }

    // add a course page
    public void coursesAddClick(View view) {
        Intent open = new Intent(this, CoursesAdd.class);
        startActivity(open);
    }

    // add a term page
    public void termsAddClick(View view) {
        Intent open = new Intent(this, TermsAdd.class);
        startActivity(open);
    }
}