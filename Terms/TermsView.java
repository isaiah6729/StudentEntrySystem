package com.zybooks.wgu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import DialogFragments.DialogFragment_CantDelete;
import DialogFragments.DialogFragment_Delete;

public class TermsView extends AppCompatActivity {

    SQLdatabase database;
    int tally = 0;
    int id2;
    TextView termTally;
    LinearLayout viewTermScroll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms_view);

         viewTermScroll = findViewById(R.id.viewTermScroll);
        termTally = findViewById(R.id.termTally);

          database = new SQLdatabase(this);

                TermsObject.deleteTerms();
          database.readTerms();

        for (int i = 0; i < TermsObject.getTerms().size(); ++i) {

            View addView = getLayoutInflater().inflate(R.layout.add_a_new_term, null);

            TextView id = addView.findViewById(R.id.Termtext1);
            TextView name = addView.findViewById(R.id.Termtext2);
            TextView startDate = addView.findViewById(R.id.Termtext3);
            TextView endDate = addView.findViewById(R.id.Termtext4);

            id.setText(String.valueOf(TermsObject.getTerms().get(i).getId()));
            name.setText(TermsObject.getTerms().get(i).getTitle());
            startDate.setText(TermsObject.getTerms().get(i).getStartDate());
            endDate.setText(TermsObject.getTerms().get(i).getEndDate());

            id2 = TermsObject.getTerms().get(i).getId();

            viewTermScroll.addView(addView);

            Button deleteTermbutton = addView.findViewById(R.id.deleteTermbutton);
            Button viewTermbutton = addView.findViewById(R.id.viewTermbutton);
            Button addCourseTermbutton = addView.findViewById(R.id.addCourseTermbutton);

            int finalI = i;
            deleteTermbutton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                   boolean cursor = database.checkTerms(TermsObject.getTerms().get(finalI).getId());

                    if (cursor) {
                        DialogFragment_CantDelete delete = new DialogFragment_CantDelete();
                        delete.show(getSupportFragmentManager(), "delete");
                    }
                    else {
                        database.deleteTerms(TermsObject.getTerms().get(finalI).getId());

                        ((LinearLayout) addView.getParent()).removeView(addView);

                        DialogFragment_Delete del = new DialogFragment_Delete();
                        del.show(getSupportFragmentManager(), "del");
                    }
                }
            });

            int finalI1 = i;
            viewTermbutton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent view = new Intent(getBaseContext(), TermsDetailsPage.class);
                    view.putExtra("title", TermsObject.getTerms().get(finalI1).getTitle());
                    view.putExtra("id", TermsObject.getTerms().get(finalI1).getId());
                    view.putExtra("startDate", TermsObject.getTerms().get(finalI1).getStartDate());
                    view.putExtra("endDate", TermsObject.getTerms().get(finalI1).getEndDate()) ;
                    startActivity(view);
                }
            });

            addCourseTermbutton.setOnClickListener(addCourse);

            tally = tally + 1;
        }

        termTally.setText("You have " + tally + " terms" );
    }


    View.OnClickListener addCourse = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent add = new Intent(getBaseContext(), CoursesAdd.class);
            add.putExtra("id", id2);
            startActivity(add);
        }
    };


}