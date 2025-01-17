package com.example.alex.literary.mainactivity;

import com.example.alex.literary.dictionary.*;

import android.Manifest;
import android.app.SearchManager;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.alex.literary.R;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

public class Main extends AppCompatActivity {

    EditText dfnField;
    static TextView dfnDisplay, errorDisplay;
    Button dfnBtn, addBtn;
    String query;

    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.INTERNET,
            Manifest.permission.ACCESS_NETWORK_STATE
    };

    Main (String query){
        this.query = query;
    }

    Main(){

    }
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);




        dfnField = (EditText) findViewById(R.id.dfnField);
        dfnDisplay = (TextView) findViewById(R.id.dfnDisplay);
        dfnBtn = (Button) findViewById(R.id.dfnBtn);
        addBtn = (Button)findViewById(R.id.addBtn);



        try{
            PermissionWriteFiles.verifyStoragePermissions(this);
            new ImportFiles(this.getApplicationContext());
        }finally{


        }

        handleIntent(getIntent());

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), WordManagement.class);
                startActivityForResult(intent, 0);

            }
        });


        dfnBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String hello = "";
                Log.d(hello, "I'm here");
                String inputWord = dfnField.getText().toString();
//                dfnDisplay.setText(English.processDefinition(inputWord));
                dfnDisplay.setText(English.processDefinition(query));
                dfnDisplay.setMovementMethod(new ScrollingMovementMethod());

            }
        });


        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }


    @Override
    public void onStart() {
        super.onStart();

        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.example.alex.literary/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    protected void onNewIntent(Intent intent) {

        super.onNewIntent(intent);
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {

        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            dfnDisplay.setText(English.processDefinition(query));
            dfnDisplay.setMovementMethod(new ScrollingMovementMethod());

        }
    }

    @Override
    public void onStop() {
        super.onStop();

        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.example.alex.literary/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }
}
