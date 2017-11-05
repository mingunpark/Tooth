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

import game.juan.andenginegame0.MainActivity;
import game.juan.andenginegame0.R;

/**
 * Created by GP62 on 2017-10-24.
 */

public class f_2 extends Activity {
    @Override
    public void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);


        requestWindowFeature(getWindow().FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_f_2);

        final VideoView video_b5 = (VideoView)findViewById(R.id.video_f_2);
        Uri videoUri = Uri.parse("android.resource://"+getPackageName()+"/"+R.raw.f_2);
        video_b5.setVideoURI(videoUri);
        video_b5.start();

        video_b5.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                Log.e("video end", "video end");
                Intent intent = new Intent(f_2.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }

    public void skip_f_2(View v){
        Intent intent_f_2 = new Intent(getApplicationContext(),MainActivity.class);
        startActivity(intent_f_2);
        finish();
    }

}
