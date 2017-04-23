package com.kuena.kinket.ard_duino;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class Sensor extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensor);
    }
    public void basic(View v){
        Intent intent = new Intent(this, Basic.class);
        startActivity(intent);
    }
    public void iot (View v){
        Intent intent = new Intent(this, Iot.class);
        startActivity(intent);
    }
}
