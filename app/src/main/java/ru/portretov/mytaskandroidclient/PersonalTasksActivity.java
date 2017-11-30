package ru.portretov.mytaskandroidclient;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import ru.portretov.mytaskandroidclient.entity.Task;
import ru.portretov.mytaskandroidclient.entity.enumirate.TaskType;
import ru.portretov.mytaskandroidclient.util.TaskListAdapter;

/**
 * Created by adminvp on 11/22/17.
 */

public class PersonalTasksActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_tasks);

        TaskListAdapter taskListAdapter = new TaskListAdapter(this, R.layout.task_list_item, new ArrayList<Task>()) {
            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                if (convertView == null) {
                    convertView = getInflater().inflate(getResource(), null);
                }

                ImageView ivTaskerPhoto, ivTaskStatus;
                TextView tvTaskName, tvTaskAddressOrOnline, tvTaskOffersAndComments, tvTaskCost;

                tvTaskName = convertView.findViewById(R.id.tvTaskName);
                tvTaskAddressOrOnline = convertView.findViewById(R.id.tvTaskAddressOrOnline);
                tvTaskOffersAndComments = convertView.findViewById(R.id.tvTaskOffersAndComments);
                tvTaskCost = convertView.findViewById(R.id.tvTaskCost);

                Task task = getTasks().get(position);
                tvTaskName.setText(task.getTitle());
                tvTaskCost.setText(String.format("%s", task.getBudget() + " ₽"));
                if (task.getTaskType() == TaskType.ONLINE_TASK) {
                    tvTaskAddressOrOnline.setText(R.string.Online);
                } else if (task.getTaskType() == TaskType.TASK_WITH_LOCATION) {
                    tvTaskAddressOrOnline.setText(String.format("%s", task.getLocation()));
                }

//                if (task.getImage() != null) {
//                    Bitmap bitmap = ImageUtil.createBitmapFromByteArray(task.getImage());
//                    ivGiftList.setImageBitmap(bitmap);
//                }
                return convertView;
            }
        };
    }


}
