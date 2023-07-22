package com.zybooks.wgu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import DialogFragments.DialogFragment_AddCourse;

// add a note to a course
public class NotesAdd extends AppCompatActivity {

    SQLdatabase database;
    int courseid;
    String notes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_notes);

        // start database
            database = new SQLdatabase(this);

            EditText noteMulti;

            // retrieve course ID
            courseid = getIntent().getIntExtra("courseid", courseid);

            noteMulti = findViewById(R.id.noteMulti);

            // save input buttons
            Button saveNotebutton = findViewById(R.id.saveNotebutton);

            saveNotebutton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    // get input value
                    notes = noteMulti.getText().toString();

                    // place in database
                    long count = database.insertNotes(notes, courseid);

                    if (count > 0 ) {

                        // send an email to a recipient of your note
                        Intent send = new Intent(Intent.ACTION_SEND);
                        send.putExtra(Intent.EXTRA_EMAIL, "Notes");
                        send.putExtra(Intent.EXTRA_SUBJECT, "WGU Notes");
                        send.putExtra(Intent.EXTRA_TEXT, notes);
                        send.setType("message/rfc822");
                        startActivity(Intent.createChooser(send, "Choose an email to send: "));

                        // clear selection
                        noteMulti.getText().clear();

                        DialogFragment_AddCourse add = new DialogFragment_AddCourse();
                        add.show(getSupportFragmentManager(), "add");
                    }
                }
            });

    }

    // view your notes
    public void viewnotesClick(View view) {
        Intent VIEW = new Intent(this, NotesView.class);
        startActivity(VIEW);
    }
}