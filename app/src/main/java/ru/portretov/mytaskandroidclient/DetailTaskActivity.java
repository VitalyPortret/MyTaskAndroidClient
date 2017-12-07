package ru.portretov.mytaskandroidclient;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import ru.portretov.mytaskandroidclient.entity.Profile;
import ru.portretov.mytaskandroidclient.entity.Task;
import ru.portretov.mytaskandroidclient.entity.enumirate.TaskType;
import ru.portretov.mytaskandroidclient.util.DataJsonUtil;
import ru.portretov.mytaskandroidclient.util.ImageUtil;
import ru.portretov.mytaskandroidclient.util.ServerURL;

/**
 * Created by adminvp on 11/21/17.
 */

public class DetailTaskActivity extends BottomNavigationStateActivity {

    private TextView tvTitle, tvUserName, tvAddress,
            tvDueDate, tvPrice, tvDescription, tvPublicationDate,
            tvExecutorName, tvExecutorAddress;
    private ImageView ivTaskerPhoto, ivTaskAlert, ivExecutorTaskerPhoto;
    private Button  btnAccept;
    private LinearLayout llExecutor;
    private String idTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_task);
        Intent intent = getIntent();
        idTask = intent.getStringExtra("id");

        tvTitle = findViewById(R.id.tvTitle);
        tvUserName = findViewById(R.id.tvUserName);
        tvAddress = findViewById(R.id.tvAddress);
        tvDueDate = findViewById(R.id.tvDueDate);
        tvPublicationDate = findViewById(R.id.tvPublicationDate);
        tvPrice = findViewById(R.id.tvPrice);
        tvDescription = findViewById(R.id.tvDescription);

        ivTaskerPhoto = findViewById(R.id.ivTaskerPhoto);
        ivTaskAlert = findViewById(R.id.ivTaskAlert);

        btnAccept = findViewById(R.id.btnAccept);
        btnAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new GetTaskById().execute(ServerURL.URL_EXECUTOR_TASKS + idTask);
            }
        });

        bottomNavigation = findViewById(R.id.navigation);
        updateBottomNavigationViewState();
        bottomNavigation.setOnNavigationItemSelectedListener(this);

        llExecutor = findViewById(R.id.llExecutor);
        ivExecutorTaskerPhoto = findViewById(R.id.ivExecutorTaskerPhoto);

        tvExecutorName = findViewById(R.id.tvExecutorName);
        tvExecutorAddress = findViewById(R.id.tvExecutorAddress);

        new GetTaskById().execute(ServerURL.URL_TASK_BY_ID + idTask);
    }

    @Override
    protected int getNavigationMenuItemId() {
        return R.id.navigation_my_task;
    }

    public void fillWidget(Task task){
        if (task == null) {
            return;
        }

        tvTitle.setText(task.getTitle());

        if (task.getTaskType() == TaskType.ONLINE_TASK) {
            tvAddress.setText(R.string.online);
        } else {
            tvAddress.setText(task.getLocation());
        }

        if (task.getExecutor() != null) {
            //TODO: Удалить и заполнять не поля, а списки ListView
            Profile executor = task.getExecutor();
            btnAccept.setVisibility(View.GONE);
            llExecutor.setVisibility(View.VISIBLE);
            if (executor.getImage() != null && executor.getImage().getImageData() != null) {
                Bitmap bitmap = ImageUtil.createBitmapFromByteArray(executor.getImage().getImageData());
                ivExecutorTaskerPhoto.setImageBitmap(bitmap);
            }

            tvExecutorName.setText(String.format("%s %s", executor.getFirstName(), executor.getLastName()));
            tvExecutorAddress.setText(executor.getLocation());

        } else if (task.getCreator() != null && task.getCreator().getId() != null &&
                task.getCreator().getId().equals("cebe7767-c4cc-9799-ar87-f821f1d9")) {
            //TODO: Удалить task.getId().equals("cebe7767-c4cc-9799-ar87-f821f1d9")
            btnAccept.setVisibility(View.GONE);

        } else {
            btnAccept.setVisibility(View.VISIBLE);
            llExecutor.setVisibility(View.GONE);
        }
        //todo: Изменить адрес на адекватный и добавить картинки
        tvDueDate.setText(getDateString(task.getDueDate()));
        tvPublicationDate.setText(getDateString(task.getPublicationDate()));
        tvPrice.setText(String.format("%s", task.getBudget() + " ₽"));
        tvDescription.setText(task.getDescription());
        ivTaskAlert.setImageResource(ImageUtil.getTaskAlertImageRes(task.getAlert()));
        Profile creator = task.getCreator();
        if (creator != null) {
            tvUserName.setText(String.format("%s %s", creator.getFirstName(), creator.getLastName()));
            if (creator.getImage() != null && creator.getImage().getImageData() != null) {
                Bitmap bitmap = ImageUtil.createBitmapFromByteArray(creator.getImage().getImageData());
                ivTaskerPhoto.setImageBitmap(bitmap);
            }
        }
    }

    private String getDateString(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        return String.format("%d/%d/%d", day, month, year);
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
            fillWidget(task);
        }
    }
}
