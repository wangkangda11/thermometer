package com.dazzle.thermometer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {
    private ThermometerView thermometerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        thermometerView =findViewById(R.id.view_temp);
    }

    @Override
    protected void onResume() {
        super.onResume();
        try {
            double fTemp = Double.parseDouble(getTemp(this));
            double rT = fTemp / 1000;
            DecimalFormat DEEE = new java.text.DecimalFormat("#.0");
            String temp = DEEE.format(rT);
            Log.e("zhuw", "temp =" +temp);
            thermometerView.setTemp(this,temp);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getTemp(Context context) throws IOException {
        StringBuilder sb = new StringBuilder("");
        FileInputStream inputStream = new FileInputStream(new File("/sys/class/thermal/thermal_zone0/temp"));
        byte[] buffer = new byte[1024];
        int len = inputStream.read(buffer);
        while(len > 0){
            sb.append(new String(buffer,0,len));
            len = inputStream.read(buffer);
        }
        inputStream.close();
        return sb.toString();
    }
}
