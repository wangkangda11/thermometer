package com.dazzle.thermometer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.dazzle.thermometer.view.ThermometerView1;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {
    private ThermometerView thermometerView;
    private ThermometerView1 view_a,view_b,view_c;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        thermometerView =findViewById(R.id.view_temp);
        view_a =findViewById(R.id.view_a);
        view_b =findViewById(R.id.view_b);
        view_c =findViewById(R.id.view_c);

        view_a.setTemp(getApplicationContext(),"36");
        view_b.setTemp(getApplicationContext(),"72");
        view_c.setTemp(getApplicationContext(),"120");
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
