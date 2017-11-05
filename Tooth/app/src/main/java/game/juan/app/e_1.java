package game.juan.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import game.juan.andenginegame0.R;

/**
 * Created by GP62 on 2017-10-20.
 */

public class e_1 extends Activity {

    ImageView iv_profile;
    TextView tv_profile;


    @Override
    public void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);


        requestWindowFeature(getWindow().FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_e_1);

        iv_profile = (ImageView) findViewById(R.id.iv_profile_main);
        tv_profile = (TextView) findViewById(R.id.tv_profile_main);

        iv_profile.setImageBitmap(b_4.image_main);
        tv_profile.setText(b_4.name_main);

    }

    public void btn_back_e_1(View v){
        Intent intent_back = new Intent(getApplicationContext(),c.class);
        startActivity(intent_back);
        finish();
    }

    public void btn_info(View v){
        Intent intent_info = new Intent(getApplicationContext(),e_2.class);
        startActivity(intent_info);
        finish();
    }

    public void btn_set(View v){
        Intent intent_set = new Intent(getApplicationContext(),e_3.class);
        startActivity(intent_set);
        finish();
    }
}