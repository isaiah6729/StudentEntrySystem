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

import DialogFragments.DialogFragment_IncorrectDate;
import DialogFragments.DialogFragment_MissingInput;
import DialogFragments.DialogFragment_Update;
import Notifications.Course_Notification_Service1;
import Notifications.Course_Notification_Service2;

public class CoursesUpdate extends AppCompatActivity {

    Calendar calendar1;
    Calendar calendar2;

    SQLdatabase database;

    TextView courseTally;

    int courseid;
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
        setContentView(R.layout.activity_courses_update);

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

        termid = getIntent().getIntExtra("id", termid);
        title = getIntent().getStringExtra("title");
        date1 = getIntent().getStringExtra("start");
        date2 = getIntent().getStringExtra("end");
        status = getIntent().getStringExtra("status" );
        name = getIntent().getStringExtra("name" );
        phone = getIntent().getStringExtra("phone" );
        email = getIntent().getStringExtra("email" );
        courseid = getIntent().getIntExtra("courseid", courseid);

        courseTally.setText("The Course ID is " + courseid + " and Term ID is " + termid);

        courseInput.setText(title);
        startCourseView.setText(date1);
        endCourseView.setText(date2);
        status2.setText(status);
        name2.setText(name);
        phone2.setText(phone);
        email2.setText(email);

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
        }
        else {

            if (month1 == month2) {
                DialogFragment_IncorrectDate cant = new DialogFragment_IncorrectDate();
                cant.show(getSupportFragmentManager(), "cant");
            }
            else {

                if (month1 > month2 && year1 == year2) {
                    DialogFragment_IncorrectDate cant = new DialogFragment_IncorrectDate();
                    cant.show(getSupportFragmentManager(), "cant");
                }
                else {

                    if (title.isEmpty() || date1.isEmpty() || date2.isEmpty() ||
                            status.isEmpty() || name.isEmpty() || phone.isEmpty() ||
                            email.isEmpty()) {

                        DialogFragment_MissingInput cant = new DialogFragment_MissingInput();
                        cant.show(getSupportFragmentManager(), "cant");

                    } else {

                        long cursor = database.updateCourses(courseid, title, date1, date2, status, name, phone, email);

                        if (cursor > 0) {

                            AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                            Intent intent2 = new Intent(getBaseContext(), Course_Notification_Service1.class);
                            PendingIntent pendingIntent = PendingIntent.getBroadcast(getBaseContext(), 1, intent2, PendingIntent.FLAG_IMMUTABLE);

                            alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar1.getTimeInMillis(), pendingIntent);

                            AlarmManager alarmManager2 = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                            Intent intent22 = new Intent(getBaseContext(), Course_Notification_Service2.class);
                            PendingIntent pendingIntent2 = PendingIntent.getBroadcast(getBaseContext(), 1, intent22, PendingIntent.FLAG_IMMUTABLE);

                            alarmManager2.setExact(AlarmManager.RTC_WAKEUP, calendar2.getTimeInMillis(), pendingIntent2);

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

    public void viewcourseClick (View view){
        Intent viewCO = new Intent(this, CoursesView.class);
        startActivity(viewCO);
    }

    public void homePageClick(View view) {
        Intent home = new Intent(this, HomePage.class);
        startActivity(home);
    }
}