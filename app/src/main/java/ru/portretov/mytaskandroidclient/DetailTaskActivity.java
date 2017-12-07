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

    private TextView tvTitle, tvUserName, tvAddress, tvDueDate, tvPrice, tvDescription, tvPublicationDate;
    private ImageView ivTaskerPhoto, ivTaskAlert, ivError;
    private Button btnRefreshError, btnAccept;
    private LinearLayout llDetailTask;
    private String idTask;
    private ScrollView scrollDetailTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_task);
        Intent intent = getIntent();
        idTask = intent.getStringExtra("id");

        createWidgets();

        bottomNavigation = findViewById(R.id.navigation);
        updateBottomNavigationViewState();
        bottomNavigation.setOnNavigationItemSelectedListener(this);

        new GetTaskById().execute(ServerURL.URL_TASK_BY_ID + idTask);
    }

    private void createWidgets() {
        scrollDetailTask = findViewById(R.id.scrollDetailTask);
        llDetailTask = findViewById(R.id.llDetailTask);

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
    }

    @Override
    protected int getNavigationMenuItemId() {
        return R.id.navigation_my_task;
    }

    public void fillWidget(Task task){
        //Делаю видемым, если Task не пришел, и элемент был невидем
        llDetailTask.setVisibility(View.VISIBLE);

        if (task == null) {
            ivError = new ImageView(this);
            ivError.setBackgroundColor(Color.RED);
            ivError.setImageResource(R.drawable.astronaut_error);
            ivError.setScaleType(ImageView.ScaleType.FIT_XY);
            ivError.layout(0, 0, 100, 0);
            ivError.setLayoutParams(
                    new LinearLayout.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.MATCH_PARENT
                    )
            );
            btnRefreshError = new Button(this);
            btnRefreshError.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            btnRefreshError.setText("Refresh");
            btnRefreshError.layout(0, 0, 100, 0);
            btnRefreshError.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new GetTaskById().execute(ServerURL.URL_TASK_BY_ID + idTask);
                }
            });

            llDetailTask.setVisibility(View.GONE);

            scrollDetailTask.addView(ivError);
            scrollDetailTask.addView(btnRefreshError);
            return;
        }

        tvTitle.setText(task.getTitle());

        if (task.getTaskType() == TaskType.ONLINE_TASK) {
            tvAddress.setText(R.string.online);
        } else {
            tvAddress.setText(task.getLocation());
        }
        if (task.getExecutor() != null) {
            btnAccept.setVisibility(View.GONE);
        } else {
            btnAccept.setVisibility(View.VISIBLE);
        }
        //todo: Изменить адрес на адекватный и добавить картинки
        tvDueDate.setText(task.getDueDate().toString());
        tvPublicationDate.setText(task.getPublicationDate().toString());
        tvPrice.setText(String.format("%s", task.getBudget() + " ₽"));
        tvDescription.setText(task.getDescription());
        ivTaskAlert.setImageResource(ImageUtil.getTaskAlertImageRes(task.getAlert()));
        Profile creator = task.getCreator();
        if (creator != null) {
            tvUserName.setText(String.format("%s %s", creator.getFirstName(), creator.getLastName()));
            if (creator.getImage() != null && creator.getImage().getImageData() != null) {
                Bitmap bitmap = ImageUtil.createBitmapFromByteArray(
                        task.getCreator().getImage().getImageData());
                ivTaskerPhoto.setImageBitmap(bitmap);
            }
        }
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
