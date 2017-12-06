package ru.portretov.mytaskandroidclient;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by adminvp on 12/6/17.
 */

public class LoadingScreenActivity extends AppCompatActivity {

    private final int WELCOME_SCREEN_DELAY = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading_screen);

        Thread welcomeThread = new Thread() {
            @Override
            public void run() {
                try {
                    super.run();
                    sleep(WELCOME_SCREEN_DELAY);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    Intent i = new Intent(LoadingScreenActivity.this, LoginActivity.class);
                    startActivity(i);
                    finish();
                }
            }
        };
        welcomeThread.start();
    }
}
