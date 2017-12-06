package ru.portretov.mytaskandroidclient;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * Created by adminvp on 11/21/17.
 */

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        findViewById(R.id.btnStart).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent main = new Intent(this, MainActivity.class);
        startActivity(main);
    }
}
