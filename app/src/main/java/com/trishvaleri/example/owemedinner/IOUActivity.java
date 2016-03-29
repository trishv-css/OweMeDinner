package com.trishvaleri.example.owemedinner;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

/**
 * IOUActivity.java - class that displays the contact manager to select a contact
 * that owes you dinner.
 * @author Trish Valeri
 */
public class IOUActivity extends AppCompatActivity {

    final int CONTACT_RESULT = 15;

    /**
     * Sets up IOUActivity
     * @param savedInstanceState Bundle
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_iou);
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

    }

    /**
     * Constructs intent to launch the contact manager when "select a contact" button is pressed
     * @param view View
     */
    public void selectContactIOU(View view) {
        Intent contactPickerIntent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
        //start activity that will return the user to this application (MainDinnerActivity.java)
        startActivityForResult(contactPickerIntent, CONTACT_RESULT);
    }

    /**
     * Retrieves the Intent Activity results from the contact manager.
     * @param requestCode int that identifies which intent you came back from
     * @param resultCode int if >= 0 this code will be returned when activity exits
     * @param contactData Intent is the data returned from the intent called
     */
    protected void onActivityResult(int requestCode, int resultCode, Intent contactData) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case CONTACT_RESULT:
                    //handle contact results, by returning the result to MainDinnerActivity.java
                    setResult(MainDinnerActivity.RESULT_OK, contactData);
                    finish();
                    break;
            }
        }
        else {
            //gracefully handle failure
            Log.w("WARN", "Warning: activity result not okay");
        }
    }

}
