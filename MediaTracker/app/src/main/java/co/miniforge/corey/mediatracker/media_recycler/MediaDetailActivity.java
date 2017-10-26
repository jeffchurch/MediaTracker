package co.miniforge.corey.mediatracker.media_recycler;

import android.content.Intent;
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

import static co.miniforge.corey.mediatracker.MyListActivity.mediaExtra;

public class MediaDetailActivity extends AppCompatActivity {

    EditText title, url, description;
    Button save;
    Intent intent;

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

    }

    private void turnOnClickListener() {
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mediaItem.description = description.getText().toString();
                mediaItem.url = url.getText().toString();
                mediaItem.title = title.getText().toString();

                intent = new Intent(getApplicationContext(), MyListActivity.class);
                //intent.putExtra("mediaExtra", mediaItem.toString());
                intent.putExtra(mediaExtra, mediaItem.toJson().toString());
                //You should pass this media item into the intent as a string extra, using the tag that is in the MyListActivity
                startActivity(intent);

            }
        });
    }
}
