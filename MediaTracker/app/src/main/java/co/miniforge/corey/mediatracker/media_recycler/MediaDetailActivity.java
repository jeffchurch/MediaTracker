package co.miniforge.corey.mediatracker.media_recycler;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.json.JSONException;
import org.json.JSONObject;

import co.miniforge.corey.mediatracker.MyListActivity;
import co.miniforge.corey.mediatracker.R;
import co.miniforge.corey.mediatracker.model.MediaItem;
import co.miniforge.corey.mediatracker.ui_helpers.ThemeHelper;

import static co.miniforge.corey.mediatracker.MyListActivity.mediaExtra;

public class MediaDetailActivity extends AppCompatActivity {

    EditText title, url, description;
    Button save, themeChanger;
    Intent intent;
    ThemeHelper themeHelper;
    JSONObject jsonDataFromIntent;
    private MediaItem mediaItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_media_detail);
        locateViews();
        getIntentData();
        bindData();

        turnOnClickListener();
    }

    private void locateViews() {
        url = (EditText) findViewById(R.id.url);
        description = (EditText) findViewById(R.id.descrition);
        title = (EditText) findViewById(R.id.title);

        save = (Button) findViewById(R.id.save);
    }

    private void getIntentData() {
        if (getIntent().hasExtra(MediaViewHolder.jsonData)) {
            String rawJson = getIntent().getStringExtra(MediaViewHolder.jsonData);
            try {
                this.jsonDataFromIntent = new JSONObject(rawJson);
                mediaItem = new MediaItem(this.jsonDataFromIntent);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private void bindData() {
        url.setText(mediaItem.url);
        description.setText(mediaItem.description);
        title.setText(mediaItem.title);
        themeChanger = (Button) findViewById(R.id.themeButton);

        themeHelper = new ThemeHelper(getApplicationContext());
    }

    private void turnOnClickListener() {
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mediaItem.description = description.getText().toString();
                mediaItem.url = url.getText().toString();
                mediaItem.title = title.getText().toString();

                promptConfirmation();



            }
        });

        themeChanger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(themeHelper.darkThemeEnabled()){
                    themeHelper.enableDarkTheme(false);
                }
                else{
                    themeHelper.enableDarkTheme(true);
                }

                themeHelper.themeTextView(title, description, url);
                themeHelper.themeBackground(title);
                themeHelper.themeBackground(description);
                themeHelper.themeBackground(url);


            }
        });

    }

    void promptConfirmation(){
        //https://developer.android.com/guide/topics/ui/dialogs.html#AlertDialog
//Make sure to put the code in the activity, the builder requires an activity to be passed in
//import android.support.v7.app for the alert dialog

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Save Changes").setMessage("Are you sure you want to save these changes?");

        builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // Put the start activity with intent code here
                intent = new Intent(getApplicationContext(), MyListActivity.class);
                //intent.putExtra("mediaExtra", mediaItem.toString());
                intent.putExtra(mediaExtra, mediaItem.toJson().toString());
                //You should pass this media item into the intent as a string extra, using the tag that is in the MyListActivity
                startActivity(intent);
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // Do nothing, unless you want this button to go back to
                // ListActivity without putting an intent extra
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
