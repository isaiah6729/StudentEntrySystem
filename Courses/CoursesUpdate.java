package com.zybooks.wgu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.TextView;

import DialogFragments.DialogFragment_AddCourse;
import DialogFragments.DialogFragment_IncorrectDate;
import DialogFragments.DialogFragment_MissingInput;
import DialogFragments.DialogFragment_Update;

// update courses page
public class CoursesUpdate extends AppCompatActivity {

    SQLdatabase database;

    TextView courseTally;

    int courseid;
    int termid;

    CalendarView startcalendarCourse;
    CalendarView endcalendarCourse;

    int month1;
    int dayOfMonth1;
    int year1;

    int month2;
    int dayOfMonth2;
    int year2;

    TextView startCourseView ;
    TextView endCourseView;

    EditText courseInput;
    EditText name2;
    EditText status2;
    EditText phone2;
    EditText email2;

    String title;
    String status;
    String name;
    String phone;
    String email;

    String date1;
    String date2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_courses_update);

        // start database
        database = new SQLdatabase(this);

        courseTally = findViewById(R.id.courseTally);
        courseInput = findViewById(R.id.courseInput);

        startcalendarCourse = findViewById(R.id.startcalendarCourse);
        endcalendarCourse = findViewById(R.id.endcalendarCourse);

        startCourseView = findViewById(R.id.startCourseView);
        endCourseView = findViewById(R.id.endCourseView);

        status2 = findViewById(R.id.courseStatus);
        name2 = findViewById(R.id.courseInstructorsName);
        phone2 = findViewById(R.id.courseInstructorsPhone);
        email2 = findViewById(R.id.courseInstructorsEmail);

        // get values from previous page placed into strings and ints
        termid = getIntent().getIntExtra("id", termid);
        title = getIntent().getStringExtra("title");
        date1 = getIntent().getStringExtra("start");
        date2 = getIntent().getStringExtra("end");
        status = getIntent().getStringExtra("status" );
        name = getIntent().getStringExtra("name" );
        phone = getIntent().getStringExtra("phone" );
        email = getIntent().getStringExtra("email" );
        courseid = getIntent().getIntExtra("courseid", courseid);

        // tell what course and term you're on
        courseTally.setText("The Course ID is " + courseid + " and Term ID is " + termid);

        // set values to text
        courseInput.setText(title);
        startCourseView.setText(date1);
        endCourseView.setText(date2);
        status2.setText(status);
        name2.setText(name);
        phone2.setText(phone);
        email2.setText(email);

        // set existing date values
        startcalendarCourse.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                month1 = month; year1 = year; dayOfMonth1 = dayOfMonth;

                date1 = month + "/" + dayOfMonth + "/" + year ;

                // show date
                startCourseView.setText(date1);
            }
        });

        // set existing date values
        endcalendarCourse.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                month2 = month; year2 = year; dayOfMonth2 = dayOfMonth;

                date2 = month + "/" + dayOfMonth + "/" + year ;

                // show date
                endCourseView.setText(date2);
            }
        });

    }

// submit updated course information
    public void courseSubmit(View view) {

        // gather input
        title = courseInput.getText().toString();
        status = status2.getText().toString();
        name = name2.getText().toString();
        phone = phone2.getText().toString();
        email = email2.getText().toString();
        date1 = startCourseView.getText().toString();
        date2 = endCourseView.getText().toString();

        // validate no year contradictions
        if (year1 > year2) {
            DialogFragment_IncorrectDate cant = new DialogFragment_IncorrectDate();
            cant.show(getSupportFragmentManager(), "cant");
        }
        else {

            // validate no month contradictions
            if (month1 == month2) {
                DialogFragment_IncorrectDate cant = new DialogFragment_IncorrectDate();
                cant.show(getSupportFragmentManager(), "cant");
            }
            else {

                // validate no month and year contradictions
                if (month1 > month2 && year1 == year2) {
                    DialogFragment_IncorrectDate cant = new DialogFragment_IncorrectDate();
                    cant.show(getSupportFragmentManager(), "cant");
                }
                else {

                    // validate no empty spaces
                    if (title.isEmpty() || date1.isEmpty() || date2.isEmpty() ||
                            status.isEmpty() || name.isEmpty() || phone.isEmpty() ||
                            email.isEmpty()) {

                        DialogFragment_MissingInput cant = new DialogFragment_MissingInput();
                        cant.show(getSupportFragmentManager(), "cant");

                    } else {

                        // place input into database
                        long cursor = database.updateCourses(courseid, title, date1, date2, status, name, phone, email);

                        if (cursor > 0) {

                            // clear selection
                            courseInput.getText().clear();
                            status2.getText().clear();
                            name2.getText().clear();
                            phone2.getText().clear();
                            email2.getText().clear();
                            startCourseView.setText("");
                            endCourseView.setText("");

                            DialogFragment_Update C = new DialogFragment_Update();
                            C.show(getSupportFragmentManager(), "c");

                        }
                    }
                }
            }
        }
    }

    // go to view courses page
    public void viewcourseClick (View view){
        Intent viewCO = new Intent(this, CoursesView.class);
        startActivity(viewCO);
    }

    // go to home page
    public void homePageClick(View view) {
        Intent home = new Intent(this, HomePage.class);
        startActivity(home);
    }
}