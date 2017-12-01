package ru.portretov.mytaskandroidclient;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

import java.io.IOException;

import ru.portretov.mytaskandroidclient.entity.Task;
import ru.portretov.mytaskandroidclient.entity.enumirate.TaskType;
import ru.portretov.mytaskandroidclient.util.DataJsonUtil;
import ru.portretov.mytaskandroidclient.util.ServerURL;
import ru.portretov.mytaskandroidclient.util.WidgetUtil;

/**
 * Created by adminvp on 11/21/17.
 */

public class DetailTaskActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    private TextView tvTitle, tvUserName, tvAddress, tvDueDate, tvPrice, tvDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_task);
        Intent intent = getIntent();
        final String idTask = intent.getStringExtra("id");

        BottomNavigationView navigation = findViewById(R.id.navigation);
        tvTitle = findViewById(R.id.tvTitle);
        tvUserName = findViewById(R.id.tvUserName);
        tvAddress = findViewById(R.id.tvAddress);
        tvDueDate = findViewById(R.id.tvDueDate);
        tvPrice = findViewById(R.id.tvPrice);
        tvDescription = findViewById(R.id.tvDescription);

        navigation.setOnNavigationItemSelectedListener(this);

        new GetTaskById().execute(ServerURL.URL_TASK_BY_ID + idTask);
    }

    public void fillWidget(Task task){
        tvTitle.setText(task.getTitle());
        if (task.getTaskType() == TaskType.ONLINE_TASK) {
            tvAddress.setText(R.string.online);
        } else {
            tvAddress.setText(task.getLocation());
        }
        //todo: Изменить адрес на адекватный и добавить картинки
        tvDueDate.setText(task.getDueDate().toString());
        tvPrice.setText(String.format("%f Р", task.getBudget()));
        tvDescription.setText(task.getDescription());
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return WidgetUtil.setBottomNavigationItemSelected(this, item);
    }

    private class GetTaskById extends AsyncTask<String, Void, Task> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Task doInBackground(String... urls) {
            try {
                return DataJsonUtil.getTasksById(urls[0]);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Task task) {
            super.onPostExecute(task);
            if (task == null) {
                return;
            }
            fillWidget(task);
        }
    }
}
