package com.trishvaleri.example.owemedinner;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * ListActivity.java - a class to display the delete button when you click on a selected item.
 * @author Trish Valeri
 */
public class ListActivity extends AppCompatActivity {

    int indexNum;
    ArrayList<String> contactList;

    /**
     * Sets up activity, and gets/sets extras from MainDinnerActivity.java
     * @param savedInstanceState Bundle
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        TextView oweTextView = (TextView) findViewById(R.id.textView);
        // get the data passed from MainDinnerActivity.java using extras
        Bundle extras = getIntent().getExtras();
        indexNum = extras.getInt("intVariableName");
        contactList = getIntent().getStringArrayListExtra("dinner");
        //sets the TextView in activity_list.xml to display the Object chosen.
        oweTextView.setText(contactList.get(indexNum));

    }

    /**
     * Deletes the selected Arraylist item when the "Delete" button is pressed
     * and returns the focus to MainDinnerActivity.java
     * whilst terminating this subactivity.
     * @param view View
     */
    public void deleteClick(View view) {
        String resultListRemove = contactList.get(indexNum);
        contactList.remove(resultListRemove);
        //returns activity to MainDinnerActivity.java
        setResult(MainDinnerActivity.RESULT_OK);
        //finish this activity.
        finish();
    }
}
