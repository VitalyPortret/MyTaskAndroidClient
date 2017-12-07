package ru.portretov.mytaskandroidclient;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import ru.portretov.mytaskandroidclient.entity.Task;
import ru.portretov.mytaskandroidclient.entity.enumirate.TaskType;
import ru.portretov.mytaskandroidclient.util.DataJsonUtil;
import ru.portretov.mytaskandroidclient.util.DownScrollListener;
import ru.portretov.mytaskandroidclient.util.ImageUtil;
import ru.portretov.mytaskandroidclient.util.ServerURL;
import ru.portretov.mytaskandroidclient.util.TaskListAdapter;

/**
 * Created by adminvp on 11/22/17.
 */

public class PersonalTasksActivity extends BottomNavigationStateActivity implements AdapterView.OnItemClickListener {

    private ListView taskListView;
    private TaskListAdapter taskListAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_tasks);

        bottomNavigation = findViewById(R.id.navigation);
        updateBottomNavigationViewState();
        bottomNavigation.setOnNavigationItemSelectedListener(this);

        taskListView = findViewById(R.id.taskListView);

        taskListAdapter = new TaskListAdapter(this, R.layout.task_list_item, new ArrayList<Task>()) {
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
                ivTaskerPhoto = convertView.findViewById(R.id.ivTaskerPhoto);

                Task task = getTasks().get(position);
                tvTaskName.setText(task.getTitle());
                tvTaskCost.setText(String.format("%s", Math.round(task.getBudget()) + " ₽"));
                if (task.getTaskType() == TaskType.ONLINE_TASK) {
                    tvTaskAddressOrOnline.setText(R.string.online);
                } else if (task.getTaskType() == TaskType.TASK_WITH_LOCATION) {
                    tvTaskAddressOrOnline.setText(String.format("%s", task.getLocation()));
                }

                if (task.getCreator() != null && task.getCreator().getImage() != null
                        && task.getCreator().getImage().getImageData() != null) {

                    Bitmap bitmap = ImageUtil.createBitmapFromByteArray(
                            task.getCreator().getImage().getImageData());
                    ivTaskerPhoto.setImageBitmap(bitmap);
                }
                return convertView;
            }
        };

        new GetMyTasks().execute(ServerURL.URL_MY_TASKS);
        taskListView.setAdapter(taskListAdapter);
        taskListView.setOnItemClickListener(this);

//        TODO: Пока не нужен, подключить когда нужен будет
//        taskListView.setOnScrollListener(new DownScrollListener<Task>(taskListView, taskListAdapter, 3) {
//            @Override
//            public void onLoadMore(int page) {
//                new GetMyTasks().execute(ServerURL.URL_MY_TASKS + "?page=" + page);
//            }
//
//            @Override
//            public boolean onUpdatePage() {
//                return false;
//            }
//        });
    }

    @Override
    protected int getNavigationMenuItemId() {
        return R.id.navigation_my_task;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(this, DetailTaskActivity.class);
        intent.putExtra("id", taskListAdapter.getItem(position).getId());
        startActivity(intent);
    }

    private void repeatedRequest() {
        //TODO: Дописать вывод кнопки с перезапросом и картинки
    }

    private class GetMyTasks extends AsyncTask<String, Void, List<Task>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected List<Task> doInBackground(String... urls) {
            try {
                return DataJsonUtil.getTasks(urls[0]);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(List<Task> result) {
            super.onPostExecute(result);
            if (result != null) {
                taskListAdapter.addAll(result);
                taskListAdapter.notifyDataSetChanged();
            } else {
                repeatedRequest();
            }
        }
    }
}
