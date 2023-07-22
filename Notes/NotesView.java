package com.zybooks.wgu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import DialogFragments.DialogFragment_CantDelete;

public class NotesView extends AppCompatActivity {

    SQLdatabase database;

    TextView notestally;

    int id2;

    LinearLayout viewNotesScroll;

    int tally = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes_view);

        viewNotesScroll = findViewById(R.id.viewNotesScroll);
        notestally = findViewById(R.id.notestally);

        database = new SQLdatabase(this);

        NotesObject.deleteNotes();
        database.readNotes();

        for (int i = 0; i < NotesObject.getListNotes().size(); ++i) {

            View addView = getLayoutInflater().inflate(R.layout.add_a_new_note, null);

            TextView notesid = addView.findViewById(R.id.notes1);
            TextView courseid = addView.findViewById(R.id.notes2);
            TextView notes = addView.findViewById(R.id.notes3);

            Button deletenote = addView.findViewById(R.id.deletenote);
            Button viewnote = addView.findViewById(R.id.viewnote);
            Button addnote = addView.findViewById(R.id.addnote);

            int finalI = i;
            deletenote.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    int rows = database.deleteNotes(NotesObject.getListNotes().get(finalI).getNotesid());

                    if (rows > 0) {

                        ((LinearLayout) addView.getParent()).removeView(addView);

                        DialogFragment_CantDelete delete = new DialogFragment_CantDelete();
                        delete.show(getSupportFragmentManager(), "delete");
                    }
                }
            });

            int finalI1 = i;
            viewnote.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent add = new Intent(getBaseContext(), AssessmentsDetailsPage.class);
                    add.putExtra("notesid", NotesObject.getListNotes().get(finalI1).getNotesid());
                    add.putExtra("courseid", NotesObject.getListNotes().get(finalI1).getCourseID());
                    add.putExtra("assessments", NotesObject.getListNotes().get(finalI1).getNotes());
                    startActivity(add);
                }
            });

            addnote.setOnClickListener(addnotes);

            notesid.setText(String.valueOf(NotesObject.getListNotes().get(i).getNotesid()));
            courseid.setText(String.valueOf(NotesObject.getListNotes().get(i).getCourseID()));
            notes.setText(NotesObject.getListNotes().get(i).getNotes());

            id2 = NotesObject.getListNotes().get(i).getCourseID();

            viewNotesScroll.addView(addView);

            tally = tally + 1;
        }

        notestally.setText("You have " + tally + " notes" );
    }


    View.OnClickListener addnotes = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent add = new Intent(getBaseContext(), NotesAdd.class);
            add.putExtra("id", id2);
            startActivity(add);
        }
    };


}
