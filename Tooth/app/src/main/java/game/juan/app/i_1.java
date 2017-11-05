package game.juan.app;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.VideoView;

import game.juan.andenginegame0.R;

/**
 * Created by GP62 on 2017-10-20.
 */

public class i_1 extends Activity {
    @Override
    public void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);


        requestWindowFeature(getWindow().FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_i_1);

        final VideoView video_b5 = (VideoView)findViewById(R.id.video_b_5);
        Uri videoUri = Uri.parse("android.resource://"+getPackageName()+"/"+R.raw.i_1);
        video_b5.setVideoURI(videoUri);
        video_b5.start();

        video_b5.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                Log.e("video end", "video end");
                Intent intent = new Intent(i_1.this, i_2.class);
                startActivity(intent);
                finish();
            }
        });


    }

    public void skip_i_1(View v){
        Intent intent_i_1 = new Intent(getApplicationContext(),i_2.class);
        startActivity(intent_i_1);
        finish();
    }

}
