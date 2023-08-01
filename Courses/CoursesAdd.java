package Courses;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.TextView;

import com.zybooks.wgu.HomePage;
import com.zybooks.wgu.R;
import com.zybooks.wgu.SQLdatabase;

import java.util.Calendar;

import DialogFragments.DialogFragment_AddCourse;
import DialogFragments.DialogFragment_IncorrectDate;
import DialogFragments.DialogFragment_MissingInput;
import Notifications.Assessment_Notification_Service1;
import Notifications.Assessment_Notification_Service2;
import Notifications.Course_Notification_Service1;
import Notifications.Course_Notification_Service2;

public class CoursesAdd extends AppCompatActivity {


    Calendar calendar1;
    Calendar calendar2;

    SQLdatabase database;

    TextView courseTally;

    int termid;
    int tally = 0;

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
        setContentView(R.layout.activity_add_courses);

        database = new SQLdatabase(this);

        courseTally = findViewById(R.id.courseTally);

        if (tally == 0) {
            courseTally.setText("You have added " + tally + " courses. You need to add some.");
        }
        else {
            courseTally.setText("You have added " + tally + " courses. Great.");
        }

        courseInput = findViewById(R.id.courseInput);

        startcalendarCourse = findViewById(R.id.startcalendarCourse);
        endcalendarCourse = findViewById(R.id.endcalendarCourse);

        startCourseView = findViewById(R.id.startCourseView);
        endCourseView = findViewById(R.id.endCourseView);

         status2 = findViewById(R.id.courseStatus);
         name2 = findViewById(R.id.courseInstructorsName);
         phone2 = findViewById(R.id.courseInstructorsPhone);
         email2 = findViewById(R.id.courseInstructorsEmail);

        startcalendarCourse.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                month1 = month; year1 = year; dayOfMonth1 = dayOfMonth;

                calendar1 = Calendar.getInstance();
                calendar1.set( year1, month1, dayOfMonth1, 0, 0, 0);

                date1 = month+1 + "/" + dayOfMonth + "/" + year ;
                startCourseView.setText(date1);
            }
        });

        endcalendarCourse.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                month2 = month; year2 = year; dayOfMonth2 = dayOfMonth;

                calendar2 = Calendar.getInstance();
                calendar2.set( year2, month2, dayOfMonth2, 0, 0, 0);

                date2 = month+1 + "/" + dayOfMonth + "/" + year ;
                endCourseView.setText(date2);
            }
        });

    }

    public void courseSubmit(View view) {

        termid = getIntent().getIntExtra("id", termid);
        title = courseInput.getText().toString();
        status = status2.getText().toString();
        name = name2.getText().toString();
        phone = phone2.getText().toString();
        email = email2.getText().toString();
        date1 = startCourseView.getText().toString();
        date2 = endCourseView.getText().toString();


        if (year1 > year2) {
            DialogFragment_IncorrectDate cant = new DialogFragment_IncorrectDate();
            cant.show(getSupportFragmentManager(), "cant");
        } else {

            if (month1 == month2) {
                DialogFragment_IncorrectDate cant = new DialogFragment_IncorrectDate();
                cant.show(getSupportFragmentManager(), "cant");
            } else {

                if (month1 > month2 && year1 == year2) {
                    DialogFragment_IncorrectDate cant = new DialogFragment_IncorrectDate();
                    cant.show(getSupportFragmentManager(), "cant");
                } else {

                    if (title.isEmpty() || date1.isEmpty() || date2.isEmpty() ||
                            status.isEmpty() || name.isEmpty() || phone.isEmpty() ||
                            email.isEmpty()) {

                        DialogFragment_MissingInput cant = new DialogFragment_MissingInput();
                        cant.show(getSupportFragmentManager(), "cant");

                    } else {

                        long cursor = database.insertCourses(title, date1, date2, status, name, phone, email, termid);

                        if (cursor > 0) {
                            tally = tally + 1;
                            courseTally.setText("You have added " + tally + " terms. Great.");

                            courseInput.getText().clear();
                            status2.getText().clear();
                            name2.getText().clear();
                            phone2.getText().clear();
                            email2.getText().clear();
                            startCourseView.setText("");
                            endCourseView.setText("");

                            DialogFragment_AddCourse C = new DialogFragment_AddCourse();
                            C.show(getSupportFragmentManager(), "c");

                        }
                    }
                }
            }
        }
    }

        public void viewcourseClick (View view){
            Intent viewCO = new Intent(this, CoursesView.class);
            startActivity(viewCO);
        }

    public void homePageClick(View view) {
        Intent home = new Intent(this, HomePage.class);
        startActivity(home);
    }

}