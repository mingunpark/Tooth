package game.juan.app;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.VideoView;

import game.juan.andenginegame0.R;

/**
 * Created by GP62 on 2017-10-24.
 */

public class b_3 extends Activity {

    @Override
    public void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);

        requestWindowFeature(getWindow().FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_b_3);

        final VideoView video_b3 = (VideoView)findViewById(R.id.video_b_3);
        Uri videoUri = Uri.parse("android.resource://"+getPackageName()+"/"+R.raw.b_2);
        video_b3.setVideoURI(videoUri);
        video_b3.start();
        video_b3.setOnCompletionListener(new MediaPlayer.OnCompletionListener(){
            public void onCompletion(MediaPlayer mp){
                video_b3.start();
            }
        });

    }

    public void understand_b_3(View v){
        Intent intent_b_3 = new Intent(getApplicationContext(),b_4.class);
        startActivity(intent_b_3);
        finish();
    }
}