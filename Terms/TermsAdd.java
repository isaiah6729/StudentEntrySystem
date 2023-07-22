package com.zybooks.wgu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.TextView;

import java.time.LocalDateTime;

import DialogFragments.DialogFragment_AddTerm;
import DialogFragments.DialogFragment_IncorrectDate;
import DialogFragments.DialogFragment_MissingInput;

public class TermsAdd extends AppCompatActivity {

    SQLdatabase database;

    TextView termsTally;
    int tally = 0;

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
        setContentView(R.layout.activity_add_terms);

        database = new SQLdatabase(this);

        termsTally = findViewById(R.id.termsTally);
        if (tally == 0) {
            termsTally.setText("You've added " + tally + " terms. You need to add some.");
        }
        else {
            termsTally.setText("You added " + tally + " terms. Great.");
        }

        termInput = findViewById(R.id.termInput);

        calendarViewStart = findViewById(R.id.startcalendarView);
        calendarViewEnd = findViewById(R.id.endcalendarView);

         starttextView = findViewById(R.id.starttextView);
         endtextView = findViewById(R.id.endtextView);

        calendarViewStart.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
             @Override
             public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                        month1 = month+1; year1 = year; dayOfMonth1 = dayOfMonth;

                     date1 = month+1 + "/" + dayOfMonth + "/" + year;
                     starttextView.setText(date1);

             }
         });

        calendarViewEnd.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                month2 = month+1; year2 = year; dayOfMonth2 = dayOfMonth;

                    date2 = month+1 + "/" + dayOfMonth + "/" + year;
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

                        long cursor = database.insertTerms(termInput2, submitStart, submitEnd);

                        if (cursor > 0) {
                            tally = tally + 1;
                        }

                        starttextView.setText("");
                        endtextView.setText("");
                        termInput.getText().clear();


                        termsTally.setText("You have added " + tally + " terms. Great.");

                        DialogFragment_AddTerm dia = new DialogFragment_AddTerm();
                        dia.show(getSupportFragmentManager(), "dia");
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