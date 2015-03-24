package com.example.abby.stovetemperatureapp;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import java.io.File;
import android.content.Intent;
import android.text.TextUtils;
import android.net.Uri;
import android.provider.MediaStore;
import android.database.Cursor;


public class CollectData extends ActionBarActivity {

    // A File object containing the path to the transferred files
    private File mParentPath;
    // Incoming Intent
    private Intent mIntent;
    /*
     * Called from onNewIntent() for a SINGLE_TOP Activity
     * or onCreate() for a new Activity. For onNewIntent(),
     * remember to call setIntent() to store the most
     * current Intent
     *
     */
    private void handleViewIntent() {
        // Get the Intent action
        mIntent = getIntent();
        String action = mIntent.getAction();
        /*
         * For ACTION_VIEW, the Activity is being asked to display data.
         * Get the URI.
         */
        if (TextUtils.equals(action, Intent.ACTION_VIEW)) {
            // Get the URI from the Intent
            Uri beamUri = mIntent.getData();
            /*
             * Test for the type of URI, by getting its scheme value
             */
            if (TextUtils.equals(beamUri.getScheme(), "file")) {
                mParentPath = handleFileUri(beamUri);
            } else if (TextUtils.equals(
                    beamUri.getScheme(), "content")) {
                mParentPath = handleContentUri(beamUri);
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        handleViewIntent();
        setContentView(R.layout.activity_collect_data);
    }

    public File handleFileUri(Uri beamUri) {
        // Get the path part of the URI
        String fileName = beamUri.getPath();
        // Create a File object for this filename
        File copiedFile = new File(fileName);
        // Get a string containing the file's parent directory
        return new File(copiedFile.getParent());
    }

    public File handleContentUri(Uri beamUri) {
        // Position of the filename in the query Cursor
        int filenameIndex;
        // File object for the filename
        File copiedFile;
        // The filename stored in MediaStore
        String fileName;
        // Test the authority of the URI
        // Get the column that contains the file name
        String[] projection = { MediaStore.MediaColumns.DATA };
        Cursor pathCursor =
                getContentResolver().query(beamUri, projection,
                        null, null, null);
        // Check for a valid cursor
        if (pathCursor != null &&
                pathCursor.moveToFirst()) {
            // Get the column index in the Cursor
            filenameIndex = pathCursor.getColumnIndex(
                    MediaStore.MediaColumns.DATA);
            // Get the full file name including path
            fileName = pathCursor.getString(filenameIndex);
            // Create a File object for the filename
            copiedFile = new File(fileName);
            // Return the parent directory of the file
            return new File(copiedFile.getParent());
        } else {
            // The query didn't work; return null
            return null;
        }
    }

    public void onClickTemp () {
        Intent intent = new Intent(this, ViewTempData.class);
        intent.putExtra("path", mParentPath.getPath());
        startActivityForResult(intent, 0);
    }

    public void onClickSmoke () {
        Intent intent = new Intent(this, ViewSmokeData.class);
        intent.putExtra("path", mParentPath.getPath());
        startActivityForResult(intent, 0);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_collect_data, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
