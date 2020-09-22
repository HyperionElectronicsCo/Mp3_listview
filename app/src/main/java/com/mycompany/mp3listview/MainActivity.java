package com.mycompany.mp3listview;

import android.Manifest;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;

import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import android.widget.MediaController;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    Context context = this;
    MediaPlayer mp;
    

    public static final int RUNTIME_PERMISSION_CODE = 7;

    String[] ListElements = new String[] { };
    
    ListView listView;

    List ListElementsArrayList ;

    ArrayAdapter adapter ;

    ContentResolver contentResolver;

    Cursor cursor;

    Uri uri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.main);
        
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false); 
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_waveform);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        getSupportActionBar().setElevation(0); 
        
        listView = (ListView) findViewById(R.id.listView1);

        context = getApplicationContext();

        ListElementsArrayList = new ArrayList<>(Arrays.asList(ListElements));

        adapter = new ArrayAdapter
        (MainActivity.this, android.R.layout.simple_list_item_1, ListElementsArrayList);

        // Requesting run time permission for Read External Storage.
        AndroidRuntimePermission();
        GetAllMediaMp3Files();

        listView.setAdapter(adapter);
  /*      button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    GetAllMediaMp3Files();

                    listView.setAdapter(adapter);

                }
            }); */
        
        final MediaPlayer mp = new MediaPlayer();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView parent, View view, int position, long id) {
                    try {
                        if (mp.isPlaying()) {
                            mp.stop();
                            mp.reset();
                            Toast.makeText(getBaseContext(), "Again Playing", Toast.LENGTH_LONG).show();
                            //mp.setDataSource(context, uri);
                            mp.setDataSource(String.valueOf(cursor));
                            mp.prepare();
                            mp.start();
                        } else {
                            Toast.makeText(getBaseContext(), "Playing", Toast.LENGTH_LONG).show();
                       //     mp.setDataSource(context, uri);
                            mp.setDataSource(String.valueOf(cursor));
                            mp.prepare();
                            mp.start();
                        }
                    } catch (Exception e) {        
                        Toast.makeText(getBaseContext(),e.toString(),Toast.LENGTH_LONG).show();
                    }
                }
            });
            }
            

    
                    


    public void GetAllMediaMp3Files(){

        contentResolver = context.getContentResolver();

        uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;

        cursor = contentResolver.query(
            uri, // Uri
            null,
            null,
            null,
            null
        );

        if (cursor == null) {

            Toast.makeText(MainActivity.this,"Something Went Wrong.", Toast.LENGTH_LONG);

        } else if (!cursor.moveToFirst()) {

            Toast.makeText(MainActivity.this,"No Music Found on SD Card.", Toast.LENGTH_LONG);

        }
        else {

            int Title = cursor.getColumnIndex(MediaStore.Audio.Media.TITLE);

            //Getting Song ID From Cursor.
            //int id = cursor.getColumnIndex(MediaStore.Audio.Media._ID);

            do {

                // You can also get the Song ID using cursor.getLong(id).
                //long SongID = cursor.getLong(id);

                String SongTitle = cursor.getString(Title);

                // Adding Media File Names to ListElementsArrayList.
                ListElementsArrayList.add(SongTitle);

            } while (cursor.moveToNext());
        }
    }

    // Creating Runtime permission function.
    public void AndroidRuntimePermission(){

        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){

            if(checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){

                if(shouldShowRequestPermissionRationale(Manifest.permission.READ_EXTERNAL_STORAGE)){

                    AlertDialog.Builder alert_builder = new AlertDialog.Builder(MainActivity.this);
                    alert_builder.setMessage("External Storage Permission is Required.");
                    alert_builder.setTitle("Please Grant Permission.");
                    alert_builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                ActivityCompat.requestPermissions(
                                    MainActivity.this,
                                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                                    RUNTIME_PERMISSION_CODE

                                );
                            }
                        });

                    alert_builder.setNeutralButton("Cancel",null);

                    AlertDialog dialog = alert_builder.create();

                    dialog.show();

                }
                else {

                    ActivityCompat.requestPermissions(
                        MainActivity.this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        RUNTIME_PERMISSION_CODE
                    );
                }
            }else {

            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults){

        switch(requestCode){

            case RUNTIME_PERMISSION_CODE:{

                    if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                        recreate();
                    }
                    else {

                    }                   }}}
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        // return true so that the menu pop up is opened
        return true; 
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.one) {
            // your code
            return true;
        }
        if (id == R.id.two) {
            // your code
            return true;
        }
        if (id == R.id.three) {
            // your code
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}

    

