package com.zybooks.wgu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import DialogFragments.DialogFragment_CantDelete;
import DialogFragments.DialogFragment_Delete;

public class TermsDetailsPage extends AppCompatActivity {

    SQLdatabase sqLdatabase;
    int tally = 0;

    int Termid;
    String title;
    String start;
    String end;

    TextView textView1;
    TextView textView2;
    TextView textView3;
    TextView textView4;

    LinearLayout TermCourseScroll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms_details_page);

        sqLdatabase = new SQLdatabase(this);

        title = getIntent().getStringExtra("title");
        start = getIntent().getStringExtra("startDate");
        end = getIntent().getStringExtra("endDate");
        Termid = getIntent().getIntExtra("id", Termid);

        textView1 = findViewById(R.id.detailsTermstext);
        textView2 = findViewById(R.id.detailsTermstext2);
        textView3 = findViewById(R.id.detailsTermstext3);
        textView4 = findViewById(R.id.detailsTermstext4);

        textView1.setText(String.valueOf(Termid));
        textView2.setText(start);
        textView3.setText(end);
        textView4.setText(title);

        TextView detailsTallyTerms = findViewById(R.id.detailsTallyTerms);

        CoursesObject.deleteCourses();
        sqLdatabase.readTermCourses(Termid);

        for (int i=0; i < CoursesObject.getCourses().size(); ++i) {

            View addView = getLayoutInflater().inflate(R.layout.add_a_new_course, null);

            TextView courseID = addView.findViewById(R.id.Coursetext9);
            TextView title = addView.findViewById(R.id.Coursetext);
            TextView startDate = addView.findViewById(R.id.Coursetext2);
            TextView endDate = addView.findViewById(R.id.Coursetext3);
            TextView status = addView.findViewById(R.id.Coursetext4);
            TextView name = addView.findViewById(R.id.Coursetext5);
            TextView phone = addView.findViewById(R.id.Coursetext6);
            TextView email = addView.findViewById(R.id.Coursetext7);
            TextView Termid = addView.findViewById(R.id.Coursetext8);

             courseID.setText(String.valueOf(CoursesObject.getCourses().get(i).getCourseid()));
             title.setText(CoursesObject.getCourses().get(i).getTitle());
             startDate.setText(CoursesObject.getCourses().get(i).getStartDate());
             endDate.setText(CoursesObject.getCourses().get(i).getEndDate());
             status.setText(CoursesObject.getCourses().get(i).getStatus());
             name.setText(CoursesObject.getCourses().get(i).getInstructorsName());
             phone.setText(CoursesObject.getCourses().get(i).getInstructorsPhone());
             email.setText(CoursesObject.getCourses().get(i).getInstructorsEmail());
             Termid.setText(String.valueOf(CoursesObject.getCourses().get(i).getTermid()));

            TermCourseScroll = findViewById(R.id.TermCourseScroll);

            TermCourseScroll.addView(addView);
            tally = tally + 1;
        }


        if (tally == 0) {
            detailsTallyTerms.setText("You have " + tally + " courses. Add some!");
        }

        else {
            detailsTallyTerms.setText("You have " + tally + " courses. Great!");
        }

    }

    public void deleteTermClick(View view) {

        if (sqLdatabase.checkTerms(Termid)) {
            DialogFragment_CantDelete cant = new DialogFragment_CantDelete();
            cant.show(getSupportFragmentManager(), "cant");
        }
        else {
            sqLdatabase.deleteTerms(Termid);

            DialogFragment_Delete del = new DialogFragment_Delete();
            del.show(getSupportFragmentManager(), "del");

            Intent del2 = new Intent(this, TermsView.class);
            startActivity(del2);
        }
    }

    public void updateClick(View view) {
        Intent add = new Intent(getBaseContext(), TermsUpdate.class);
        add.putExtra("id", Termid);
        add.putExtra("title", title);
        add.putExtra("start", start);
        add.putExtra("end", end);
        startActivity(add);
    }

    public void coursesAddClick(View view) {
        Intent add = new Intent(getBaseContext(), CoursesAdd.class);
        add.putExtra("id", Termid);
        startActivity(add);
    }
}