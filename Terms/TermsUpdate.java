package com.zybooks.wgu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.TextView;

import DialogFragments.DialogFragment_AddTerm;
import DialogFragments.DialogFragment_IncorrectDate;
import DialogFragments.DialogFragment_MissingInput;
import DialogFragments.DialogFragment_Update;

public class TermsUpdate extends AppCompatActivity {

    SQLdatabase database;

    int Termid;
    String title;
    String start;
    String end;

    TextView termsTally;

    EditText termInput;

    CalendarView calendarViewStart;
    CalendarView calendarViewEnd;

    TextView starttextView ;
    TextView endtextView;

    int month1;
    int dayOfMonth1;
    int year1;

    int month2;
    int dayOfMonth2;
    int year2;

    String date1;
    String date2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms_upate);

        database = new SQLdatabase(this);

        Termid = getIntent().getIntExtra("id", Termid);
         title = getIntent().getStringExtra("title");
         start = getIntent().getStringExtra("start");
         end = getIntent().getStringExtra("end");

        termsTally = findViewById(R.id.termsTally);
        termsTally.setText("The current term is " + Termid );

        termInput = findViewById(R.id.termInput);

        calendarViewStart = findViewById(R.id.startcalendarView);
        calendarViewEnd = findViewById(R.id.endcalendarView);

        starttextView = findViewById(R.id.starttextView);
        endtextView = findViewById(R.id.endtextView);

        termInput.setText(title);
        starttextView.setText(start);
        endtextView.setText(end);

        calendarViewStart.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                month1 = month; year1 = year; dayOfMonth1 = dayOfMonth;

                date1 = month + "/" + dayOfMonth + "/" + year;
                starttextView.setText(date1);

            }
        });

        calendarViewEnd.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                month2 = month; year2 = year; dayOfMonth2 = dayOfMonth;

                date2 = month + "/" + dayOfMonth + "/" + year;
                endtextView.setText(date2);

            }
        });

    }

    public void submitClick(View view) {

        String termInput2 = termInput.getText().toString();
        String submitStart = starttextView.getText().toString();
        String submitEnd = endtextView.getText().toString();

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

                    if (termInput2.isEmpty() || submitStart.isEmpty() || submitEnd.isEmpty()) {
                        DialogFragment_MissingInput miss = new DialogFragment_MissingInput();
                        miss.show(getSupportFragmentManager(), "miss");
                    }
                    else {

                        long cursor = database.updateTerms(Termid, termInput2, submitStart, submitEnd);

                        if (cursor > 0) {

                            starttextView.setText("");
                            endtextView.setText("");
                            termInput.getText().clear();

                            DialogFragment_Update dia = new DialogFragment_Update();
                            dia.show(getSupportFragmentManager(), "dia");
                        }
                    }
                }
            }
        }
    }

    public void viewClick(View view) {
        Intent viewing = new Intent(this, TermsView.class);
        startActivity(viewing);
    }

    public void homepageClick(View view) {
        Intent home = new Intent(this, HomePage.class);
        startActivity(home);
    }
}