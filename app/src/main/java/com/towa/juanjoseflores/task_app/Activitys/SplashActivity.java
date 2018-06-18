package com.towa.juanjoseflores.task_app.Activitys;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.towa.juanjoseflores.task_app.R;

public class SplashActivity extends BaseActivity {
    private final int DURATION = 3000;
    private Thread mSplashThread;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splash);
        mSplashThread = new Thread() {

            @Override
            public void run() {
                synchronized (this) {
                    try {
                        wait(DURATION);
                    } catch (InterruptedException e) {
                    } finally {
                        finish();
                        Intent intent = new Intent(getBaseContext(),
                                MainActivity.class);
                        startActivity(intent);
                    }
                }
            }

        };
        mSplashThread.start();
    }
}
