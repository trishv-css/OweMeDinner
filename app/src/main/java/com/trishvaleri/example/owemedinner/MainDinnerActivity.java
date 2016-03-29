//===============================================
//Title: OweMeDinner
//Author: Trish Valeri
//Contributors: stackandroid.com & developer.android.com
//Date: 3/22/16 - 3/29/16
//Purpose: a class used to decide whose turn it is to buy dinner.
//===============================================

package com.trishvaleri.example.owemedinner;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.R;
import java.util.ArrayList;

/**
 * MainDinnerActivity.java - a class used to decide whose turn it is to buy dinner.
 * @author Trish Valeri
 * Contributors: stackandroid.com & developer.android.com
 * Date: 3/22/16 - 3/28/16
 */
public class MainDinnerActivity extends AppCompatActivity {

    private final int CONTACT_PICKER_RESULT = 1001;
    private final int ME_RESULT = 15;
    private final int LIST_RESULT = 16;
    int indexList;
    ListView contactListView;
    private ArrayAdapter<String> listAdapter;
    private ArrayList<String> contactList = new ArrayList<String>();

    /**
     * Sets up activity and onItemClick listener for the ListView.
     * Creates intent to start ListActivity.java
     * @param savedInstanceState Bundle
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.trishvaleri.example.owemedinner.R.layout.activity_main_dinner);
        //get listView from xml
        contactListView = (ListView) findViewById(com.trishvaleri.example.owemedinner.R.id.listView);
        contactListView.setClickable(true);
        //the listview handles click events, and launches ListActivity.java through an intent.
        contactListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Object o = contactListView.getItemAtPosition(position);
                indexList = contactList.indexOf(o);
                //launches intent to ListActivity.java
                Intent i = new Intent(getApplicationContext(), ListActivity.class);
                //packages the extras to send to ListActivity.java
                i.putStringArrayListExtra("dinner", contactList);
                i.putExtra("intVariableName", indexList);
                //start activity that expects a return.
                startActivityForResult(i, LIST_RESULT);
            }
        });
    }

    /**
     * Constructs an intent to launch the contact manager, invoked by "select a contact" button.
     * @param view View
     */
    public void selectContact(View view) {
        Intent contactPickerIntent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
        //start activity that will return the user to this application
        startActivityForResult(contactPickerIntent, CONTACT_PICKER_RESULT);
    }

    /**
     * Retrieves the Intent Activity results.
     * @param requestCode int that identifies which intent you came back from
     * @param resultCode int if >= 0 this code will be returned when activity exits
     * @param data Intent is the data returned from the intent called
     */
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case CONTACT_PICKER_RESULT:
                    //handle contact results from Contact Manager
                    //contactPick method call using intent data
                    contactPick(data);
                    break;
                case ME_RESULT:
                    //handles contact results from Contact Manager via IOUActivity.java
                    mePick(data);
                    break;
                case LIST_RESULT:
                    //after return from ListActivity.java the listAdapter removes the selected data
                    //and notifies the ListView that the data has changed.
                    listAdapter.remove(contactList.get(indexList));
                    listAdapter.notifyDataSetChanged();
            }
        }
        else {
            //Handles failure
            Log.w("WARN","Warning: activity result not okay");
        }
    }

    /**
     * Adds the DISPLAY_NAME to the ArrayList (contactList) and sets the ArrayAdapter to the ListView
     * ContactList is set to display in the ListView.
     * @param data Intent
     */
    private void contactPick(Intent data) {
        Cursor cursor = null;
        try {
            String name = null;
            Uri contactUri = data.getData();
            cursor = getContentResolver().query(contactUri, null, null, null, null);
            cursor.moveToFirst();
            int nameIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME);
            name = cursor.getString(nameIndex);
            contactList.add(name + " owes you dinner.");
            listAdapter = new ArrayAdapter<String>(this, R.layout.simple_list_item_1, android.R.id.text1, contactList);
            contactListView.setAdapter(listAdapter);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Adds the DISPLAY_NAME to the ArrayList (contactList) and sets the ArrayAdapter to the ListView
     * ContactList is set to display in the ListView.
     * @param data Intent
     */
    private void mePick(Intent data) {
        Cursor cursor = null;
        try {
            String name = null;
            Uri contactUri = data.getData();
            cursor = getContentResolver().query(contactUri, null, null, null, null);
            cursor.moveToFirst();
            int nameIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME);
            name = cursor.getString(nameIndex);
            contactList.add("You owe " + name + " dinner.");
            listAdapter = new ArrayAdapter<String>(this, R.layout.simple_list_item_1, android.R.id.text1, contactList);
            contactListView.setAdapter(listAdapter);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Constructs an intent to launch IOUActivity.java, invoked by "Me" button.
     * @param view View
     */
    public void meClick(View view) {
        Intent meIntent = new Intent(getApplicationContext(), IOUActivity.class);
        startActivityForResult(meIntent, ME_RESULT);
    }
}
