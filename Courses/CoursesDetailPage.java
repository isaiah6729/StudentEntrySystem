package Courses;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.zybooks.wgu.HomePage;
import com.zybooks.wgu.NotesAdd;
import com.zybooks.wgu.NotesObject;
import com.zybooks.wgu.NotesView;
import com.zybooks.wgu.R;
import com.zybooks.wgu.SQLdatabase;

import java.util.Calendar;

import Assessments.AssessmentAdd;
import Assessments.AssessmentObjectObjective;
import DialogFragments.DialogFragment_CantDelete;
import DialogFragments.DialogFragment_IncorrectDate;
import DialogFragments.DialogFragment_MissingInput;
import Notifications.Assessment_Notification_Service1;
import Notifications.Assessment_Notification_Service2;
import Notifications.Course_Notification_Service1;
import Notifications.Course_Notification_Service2;

public class CoursesDetailPage extends AppCompatActivity {

    private static CoursesObject TheAlert;

    public static CoursesObject getAlert() {    return TheAlert;}

    int code = 1;
    int code2 = 1;

    String Alert;
    EditText alert;

    Calendar calendar1;
    Calendar calendar2;

    CalendarView startcalendarObjective;
    CalendarView endcalendarObjective;

    TextView startobjectiveinput;
    TextView endObjectiveInput;

    int month1;
    int dayOfMonth1;
    int year1;

    int month2;
    int dayOfMonth2;
    int year2;

    String date1;
    String date2;

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

        database =  new SQLdatabase(this);

        alert = findViewById(R.id.Alert);

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
         TextView textView10 = findViewById(R.id.detailsCoursetext10);

         database.readANote(courseid);

         textView.setText(String.valueOf(termid));
        textView2.setText(String.valueOf(courseid));
        textView3.setText(title);
        textView4.setText(status);
        textView5.setText(name);
        textView6.setText(phone);
        textView7.setText(email);
        textView8.setText(start);
        textView9.setText(end);

        for (int i = 0; i< NotesObject.getListNotes().size(); i++) {
            textView10.setText(NotesObject.getListNotes().get(i).getNotes());
        }

        startobjectiveinput = findViewById(R.id.startobjectiveinput);
        endObjectiveInput = findViewById(R.id.endObjectiveInput);

        startcalendarObjective = findViewById(R.id.startcalendarObjective);
        startcalendarObjective.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                month1 = month;
                year1 = year;
                dayOfMonth1 = dayOfMonth;

                calendar1 = Calendar.getInstance();
                calendar1.set(year1, month1, dayOfMonth1, 0, 0, 0);

                date1 = month + 1 + "/" + dayOfMonth + "/" + year;
                startobjectiveinput.setText(date1);
            }
        });

        endcalendarObjective = findViewById(R.id.endcalendarObjective);
        endcalendarObjective.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                month2 = month;
                year2 = year;
                dayOfMonth2 = dayOfMonth;

                calendar2 = Calendar.getInstance();
                calendar2.set(year2, month2, dayOfMonth2, 0, 0, 0);

                date2 = month + 1 + "/" + dayOfMonth + "/" + year;
                endObjectiveInput.setText(date2);
            }
        });

        Button objectiveSavebutton = findViewById(R.id.objectiveSavebutton);
        objectiveSavebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Alert = alert.getText().toString();
                date1 = startobjectiveinput.getText().toString();
                date2 = endObjectiveInput.getText().toString();

                TheAlert = new CoursesObject(0, null, null, null, null, null,null, null, 0, Alert);

                if (year1 > year2) {
                    DialogFragment_IncorrectDate cant = new DialogFragment_IncorrectDate();
                    cant.show(getSupportFragmentManager(), "cant");
                }
                else {

                    if (month1 == month2) {
                        DialogFragment_IncorrectDate cant = new DialogFragment_IncorrectDate();
                        cant.show(getSupportFragmentManager(), "cant");
                    } else {

                        if (month1 > month2 && year1 == year2) {
                            DialogFragment_IncorrectDate cant = new DialogFragment_IncorrectDate();
                            cant.show(getSupportFragmentManager(), "cant");
                        } else {


                            if (Alert.isEmpty() || calendar1 == null || calendar2 == null) {
                                DialogFragment_MissingInput miss = new DialogFragment_MissingInput();
                                miss.show(getSupportFragmentManager(), "miss");
                            }
                            else {

                                database.insertAlerts(Alert, date1, date2);

                                AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                                Intent intent2 = new Intent(getBaseContext(), Course_Notification_Service1.class);
                                PendingIntent pendingIntent = PendingIntent.getBroadcast(getBaseContext(), code, intent2, PendingIntent.FLAG_IMMUTABLE);

                                alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar1.getTimeInMillis(), pendingIntent);

                                AlarmManager alarmManager2 = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                                Intent intent22 = new Intent(getBaseContext(), Course_Notification_Service2.class);
                                PendingIntent pendingIntent2 = PendingIntent.getBroadcast(getBaseContext(), code2, intent22, PendingIntent.FLAG_IMMUTABLE);

                                alarmManager2.setExact(AlarmManager.RTC_WAKEUP, calendar2.getTimeInMillis(), pendingIntent2);

                                code = code + 1;
                                code2 = code2 + 1;

                                alert.getText().clear();
                                startobjectiveinput.setText("");
                                endObjectiveInput.setText("");

                                toast();
                            }
                        }
                    }
                }
            }
        });
    }

    public void toast () {
        Toast.makeText(this, "Your alert has been created " + Alert + " " + calendar1.getTime(), Toast.LENGTH_LONG).show();
    }


    public void deleteCourseClick(View view) {
        int row = database.deleteCourses(courseid);

        if (row > 0) {
            DialogFragment_CantDelete del = new DialogFragment_CantDelete();
            del.show(getSupportFragmentManager(), "del");
        }

    }

    public void homePageClick(View view) {
        Intent home = new Intent(this, HomePage.class);
        startActivity(home);
    }

    public void addNoteClick(View view) {
        Intent note = new Intent(this, NotesAdd.class);
        note.putExtra("courseid", courseid);
        startActivity(note);
    }

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

    public void addAssessmentClick(View view) {
        Intent note = new Intent(this, AssessmentAdd.class);
        note.putExtra("courseid", courseid);
        startActivity(note);
    }

    public void viewnotesClick(View view) {
        Intent note = new Intent(this, NotesView.class);
        note.putExtra("courseid", courseid);
        startActivity(note);
    }
}