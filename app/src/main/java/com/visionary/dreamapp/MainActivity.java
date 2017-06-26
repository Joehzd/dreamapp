package com.visionary.dreamapp;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.AppCompatSeekBar;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.SeekBar;

public class MainActivity extends AppCompatActivity {

    AppCompatSeekBar seekBar;
    AppCompatCheckBox checkbox = null;
    WindowManager windowManager = null;
    View tv = null;
    WindowManager.LayoutParams params = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        int hasWriteContactsPermission = 0;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            hasWriteContactsPermission = checkSelfPermission(Manifest.permission.SYSTEM_ALERT_WINDOW);
            if (hasWriteContactsPermission != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[] {Manifest.permission.SYSTEM_ALERT_WINDOW},0);
                return;
            }
        }

        //夜间模式用的WindowManager

        seekBar= (AppCompatSeekBar) findViewById(R.id.seek_bar);
        checkbox= (AppCompatCheckBox) findViewById(R.id.checkbox);
        windowManager = (WindowManager)getApplicationContext().getSystemService(WINDOW_SERVICE);
        params = new WindowManager.LayoutParams();
        params.type=WindowManager.LayoutParams.TYPE_TOAST;
        //params.type = WindowManager.LayoutParams.TYPE_SYSTEM_OVERLAY;
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.MATCH_PARENT;
        params.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
                | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        params.alpha = 10;
        tv = new View(this);
        tv.setBackgroundColor(Color.argb(10, 10, 10, 10));




        //设置初始值
//        seekBar.setProgress((int) (android.provider.Settings.System.getInt(
//                getContentResolver(),
//                android.provider.Settings.System.SCREEN_BRIGHTNESS, 255) ));
//        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener()
//        {
//
//            public void onProgressChanged(SeekBar seekBar, int progress,boolean fromUser)
//            {
//                if (fromUser)
//                {
//                    Integer tmpInt = seekBar.getProgress();
//                    System.out.println(tmpInt);
//                    // 51 (seek scale) * 5 = 255 (max brightness)
//                    // Old way
//                    android.provider.Settings.System.putInt(getContentResolver(),
//                            android.provider.Settings.System.SCREEN_BRIGHTNESS,tmpInt); // 0-255
//                    tmpInt = Settings.System.getInt(getContentResolver(),
//                            Settings.System.SCREEN_BRIGHTNESS, -1);
//                    WindowManager.LayoutParams lp = getWindow().getAttributes();
//                    // lp.screenBrightness = 1.0f;
//                    // Float tmpFloat = (float)tmpInt / 255;
//                    if (0< tmpInt && tmpInt < 255)
//                    {
//                        lp.screenBrightness = tmpInt + 1;//TODO:亮度调节的问题
//                    }
//                    getWindow().setAttributes(lp);
//                }
//
//            }
//
//            @Override
//            public void onStartTrackingTouch(SeekBar seekBar) {
//            }
//
//            @Override
//            public void onStopTrackingTouch(SeekBar seekBar) {
//            }
//        });

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                if(checkbox.isChecked())
                {
                    windowManager.removeView(tv);
                    params.alpha = seekBar.getProgress();
                    if (params.alpha < 30)
                        params.alpha = 30;
                    windowManager.addView(tv, params);


                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress,
                                          boolean fromUser) {

            }
        });

        checkbox.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if(checkbox.isChecked())
                {
                    params.alpha = seekBar.getProgress();
                    if (params.alpha < 30)
                        params.alpha = 30;
                    windowManager.addView(tv, params);
                }
                else
                {
                    windowManager.removeView(tv);
                }
            }
        });

    }
    protected void onResume()
    {

        super.onResume();
    }
    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode ==0){
            if (permissions[0].equals(Manifest.permission.SYSTEM_ALERT_WINDOW)
                    &&grantResults[0] == PackageManager.PERMISSION_GRANTED){

            }else{

            }
        }
    }
}
