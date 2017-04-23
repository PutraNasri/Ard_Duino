package com.kuena.kinket.ard_duino;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class Iot extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_iot);
    }
    public void basic(View v){
        Intent intent = new Intent(this, Basic.class);
        startActivity(intent);
    }
    public void sensor(View v){
        Intent intent = new Intent(this, Sensor.class);
        startActivity(intent);
    }
}
