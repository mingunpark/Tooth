package game.juan.app;

import android.app.Activity;
import android.content.CursorLoader;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import game.juan.andenginegame0.R;

/**
 * Created by GP62 on 2017-10-17.
 */

public class b_4 extends Activity {

    ImageView iv_main;
    EditText edt_name;
    Button btn_1;
    Button btn_2;


    Intent act_c;

    Intent externalAlbum;
    String path;

    public static Bitmap image_main;
    public static String name_main;


    @Override
    public void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);


        requestWindowFeature(getWindow().FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_b_4);


        iv_main = (ImageView) findViewById(R.id.iv_main);
        iv_main.setBackground(new ShapeDrawable(new OvalShape()));
        iv_main.setClipToOutline(true);

        edt_name = (EditText) findViewById(R.id.edt_name);

        btn_1 = (Button) findViewById(R.id.btn_1);
        btn_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                externalAlbum = new Intent(Intent.ACTION_PICK);
                externalAlbum.setType("image/jpeg");
                try{
                    startActivityForResult(externalAlbum, 100);
                }catch (Exception e){

                }

            }
        });

        btn_2 = (Button) findViewById(R.id.btn_2);
        btn_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (edt_name.getText().toString().equals("")){
                    Toast.makeText(b_4.this, "이름을 입력하세요", Toast.LENGTH_SHORT).show();
                }else {
                    Drawable d = iv_main.getDrawable();
                    Bitmap image = ((BitmapDrawable) d).getBitmap();

                    image_main = image;

                    name_main = edt_name.getText().toString();

                    act_c = new Intent(getApplicationContext(), b_5.class);
//                test.putExtra("image", image);
//                    act_c.putExtra("name", name);

                    startActivity(act_c);
                    finish();
                }

            }
        });

    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(data == null)
            return;
        switch (requestCode){
            case 100:
                if(resultCode == RESULT_OK){
                    path = getPathFromURI(data.getData());
                    iv_main.setImageURI(data.getData());
                }
        }


    }

    private String getPathFromURI(Uri contentUri){
        String[] proj = { MediaStore.Images.Media.DATA };
        CursorLoader loader = new CursorLoader(getApplicationContext(), contentUri, proj, null, null, null);
        Cursor cursor = loader.loadInBackground();
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }



}