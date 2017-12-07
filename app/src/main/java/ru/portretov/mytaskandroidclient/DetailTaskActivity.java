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
    private ImageView ivTaskerPhoto, ivTaskAlert;
    private LinearLayout llDetailTask;
    private String idTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_task);
        Intent intent = getIntent();
        idTask = intent.getStringExtra("id");

        bottomNavigation = findViewById(R.id.navigation);
        updateBottomNavigationViewState();
        bottomNavigation.setOnNavigationItemSelectedListener(this);

        tvTitle = findViewById(R.id.tvTitle);
        tvUserName = findViewById(R.id.tvUserName);
        tvAddress = findViewById(R.id.tvAddress);
        tvDueDate = findViewById(R.id.tvDueDate);
        tvPublicationDate = findViewById(R.id.tvPublicationDate);
        tvPrice = findViewById(R.id.tvPrice);
        tvDescription = findViewById(R.id.tvDescription);
        llDetailTask = findViewById(R.id.llDetailTask);
        ivTaskerPhoto = findViewById(R.id.ivTaskerPhoto);
        ivTaskAlert = findViewById(R.id.ivTaskAlert);

        new GetTaskById().execute(ServerURL.URL_TASK_BY_ID + idTask);
    }

    @Override
    protected int getNavigationMenuItemId() {
        return R.id.navigation_my_task;
    }

    public void fillWidget(Task task){
        if (task == null) {
            ImageView imageView = new ImageView(this);
            imageView.setBackgroundColor(Color.RED);
            imageView.setImageResource(R.drawable.ic_mail);
            imageView.layout(0, 0, 100, 0);
            imageView.setLayoutParams(
                    new LinearLayout.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.MATCH_PARENT
                    )
            );
            Button btn = new Button(this);
            btn.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            btn.setText("Refresh");
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new GetTaskById().execute(ServerURL.URL_TASK_BY_ID + idTask);
                }
            });

            llDetailTask.removeAllViews();
            llDetailTask.addView(imageView);
            llDetailTask.addView(btn);
            return;
        }
        tvTitle.setText(task.getTitle());
        if (task.getTaskType() == TaskType.ONLINE_TASK) {
            tvAddress.setText(R.string.online);
        } else {
            tvAddress.setText(task.getLocation());
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
