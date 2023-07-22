package com.zybooks.wgu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import DialogFragments.DialogFragment_Delete;

// view all courses programmatically
public class CoursesView extends AppCompatActivity {

    SQLdatabase database;
    int tally = 0;
    LinearLayout viewCoursesScroll;
    TextView coursetexttally;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_courses_view);

        // start database
        database = new SQLdatabase(this);

        coursetexttally = findViewById(R.id.coursetexttally);

        // delete existing objects to make new ones

        // pull data from database
        CoursesObject.deleteCourses();
        database.readCourses();

        // automatically take objects' info from list and place them on the screen
        for (int i=0; i < CoursesObject.getCourses().size(); ++i) {

            // create child view
           View addView = getLayoutInflater().inflate(R.layout.add_a_new_course, null);

            // create textviews
            TextView title = addView.findViewById(R.id.Coursetext);
            TextView startDate = addView.findViewById(R.id.Coursetext2);
            TextView endDate = addView.findViewById(R.id.Coursetext3);
            TextView status = addView.findViewById(R.id.Coursetext4);
            TextView name = addView.findViewById(R.id.Coursetext5);
            TextView phone = addView.findViewById(R.id.Coursetext6);
            TextView email = addView.findViewById(R.id.Coursetext7);
            TextView Termid = addView.findViewById(R.id.Coursetext8);
            TextView courseID = addView.findViewById(R.id.Coursetext9);

            // set text for views from objects
            courseID.setText(String.valueOf(CoursesObject.getCourses().get(i).getCourseid()));
            title.setText(CoursesObject.getCourses().get(i).getTitle());
            startDate.setText(CoursesObject.getCourses().get(i).getStartDate());
            endDate.setText(CoursesObject.getCourses().get(i).getEndDate());
            status.setText(CoursesObject.getCourses().get(i).getStatus());
            name.setText(CoursesObject.getCourses().get(i).getInstructorsName());
            phone.setText(CoursesObject.getCourses().get(i).getInstructorsPhone());
            email.setText(CoursesObject.getCourses().get(i).getInstructorsEmail());
            Termid.setText(String.valueOf(CoursesObject.getCourses().get(i).getTermid()));

            // parent view
            viewCoursesScroll = findViewById(R.id.viewCoursesScroll);

            // add child to parent view
            viewCoursesScroll.addView(addView);

            // buttons
            Button deleteCoursebutton = addView.findViewById(R.id.deleteCoursebutton);
            Button viewCoursebutton = addView.findViewById(R.id.viewCoursebutton);
            Button courseassessmentbutton = addView.findViewById(R.id.courseassessmentbutton);


            // delete listener
            int finalI1 = i;
            deleteCoursebutton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    int db = CoursesObject.getCourses().get(finalI1).getCourseid();

                    if (db > 0) {

                        // delete database
                        database.deleteCourses(db);

                        DialogFragment_Delete delete = new DialogFragment_Delete();
                        delete.show(getSupportFragmentManager(), "delete");

                        tally = tally - 1;

                        // delete from view
                        ((LinearLayout) addView.getParent()).removeView(addView);

                    }
                }
            });

            // view details listener
            int finalI = i;
            viewCoursebutton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    // send info to next page
                    Intent view = new Intent(getBaseContext(), CoursesDetailPage.class);
                    view.putExtra("courseid", CoursesObject.getCourses().get(finalI).getCourseid());
                   view.putExtra("title", CoursesObject.getCourses().get(finalI).getTitle());
                    view.putExtra("startDate", CoursesObject.getCourses().get(finalI).getStartDate());
                    view.putExtra("endDate", CoursesObject.getCourses().get(finalI).getEndDate());
                    view.putExtra("status", CoursesObject.getCourses().get(finalI).getStatus());
                    view.putExtra("name", CoursesObject.getCourses().get(finalI).getInstructorsName());
                    view.putExtra("phone", CoursesObject.getCourses().get(finalI).getInstructorsPhone());
                    view.putExtra("email", CoursesObject.getCourses().get(finalI).getInstructorsEmail());
                    view.putExtra("termid", CoursesObject.getCourses().get(finalI).getTermid());
                    startActivity(view);

                }
            });

            tally = tally + 1;

            // choose assessment page
            courseassessmentbutton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent assessment = new Intent(getBaseContext(), AssessmentChoose.class);
                    startActivity(assessment);
                }
            });

        }

        coursetexttally.setText("You have " + tally + " courses" );

    }





}