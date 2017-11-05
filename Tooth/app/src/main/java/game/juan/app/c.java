package game.juan.app;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import game.juan.andenginegame0.R;

/**
 * Created by GP62 on 2017-10-17.
 */

public class c extends BlunoLibrary {

    public static String str;

    Button btn_scan;

    @Override
    public void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);

        requestWindowFeature(getWindow().FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_c);

        onCreateProcess();

        serialBegin(115200);



        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
        if (permissionCheck != PackageManager.PERMISSION_GRANTED){
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)){
                Toast.makeText(this, "The permission to get BLE location data is required", Toast.LENGTH_SHORT).show();
            }else{
                requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            }
        }else{
            Toast.makeText(this, "Location permissions already granted", Toast.LENGTH_SHORT).show();
        }

        btn_scan=(Button)findViewById(R.id.btn_scan);
        btn_scan.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view){

                buttonScanOnClickProcess();


            }
        });


    }

    public void btn_stage(View v){
        Intent intent_stage = new Intent(getApplicationContext(),f_2.class);
        startActivity(intent_stage);
    }

    public void btn_parent(View v){
        Intent intent_parent = new Intent(getApplicationContext(),e_1.class);
        startActivity(intent_parent);
    }

    public void btn_medal(View v){
        Intent intent_medal = new Intent(getApplicationContext(),d.class);
        startActivity(intent_medal);
    }

    //버튼 클릭시
    public void btn_21(View v){
        Intent intent_fin = new Intent(getApplicationContext(),i_1.class);
        startActivity(intent_fin);
    }
    public void btn_2(View v){
        Intent intent_2 = new Intent(getApplicationContext(),f_2.class);
        startActivity(intent_2);
    }

    @Override
    public void onSerialReceived(String theString) {                            //Once connection data received, this function will be called
        // TODO Auto-generated method stub

        str=theString;

    }

    @Override
    public void onConectionStateChange(connectionStateEnum theConnectionState) {//Once connection state changes, this function will be called
        switch (theConnectionState) {                                            //Four connection state
            case isConnected:
                btn_scan.setText("Connected");
                break;
            case isConnecting:
                btn_scan.setText("Connecting");
                break;
            case isToScan:
                btn_scan.setText("Scan");
                break;
            case isScanning:
                btn_scan.setText("Scanning");
                break;
            case isDisconnecting:
                btn_scan.setText("isDisconnecting");
                break;
            default:
                break;
        }
    }

    protected void onResume(){
        super.onResume();
        System.out.println("BlUNOActivity onResume");
        onResumeProcess();														//onResume Process by BlunoLibrary
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        onActivityResultProcess(requestCode, resultCode, data);					//onActivityResult Process by BlunoLibrary


    }
}
