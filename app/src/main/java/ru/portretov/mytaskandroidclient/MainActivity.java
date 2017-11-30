package ru.portretov.mytaskandroidclient;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.Toast;

import java.io.IOException;
import java.net.HttpURLConnection;

import ru.portretov.mytaskandroidclient.createtaskfragment.CreateTaskChapterOneFragment;
import ru.portretov.mytaskandroidclient.createtaskfragment.CreateTaskChapterThreeFragment;
import ru.portretov.mytaskandroidclient.createtaskfragment.CreateTaskChapterTwoFragment;
import ru.portretov.mytaskandroidclient.createtaskfragment.CreateTaskChooseAlertFragment;
import ru.portretov.mytaskandroidclient.entity.Task;
import ru.portretov.mytaskandroidclient.util.DataJsonUtil;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    private FragmentManager fManager;
    private FragmentTransaction fTransaction;

    private Task task;

    public Task getTask() {
        return task;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        task = new Task();
        fManager = getFragmentManager();
        toAlertFragment();

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(this);
    }

    private void toAlertFragment() {
        fTransaction = fManager.beginTransaction();
        CreateTaskChooseAlertFragment alertFragment = new CreateTaskChooseAlertFragment();
        fTransaction.add(R.id.fragmentContainer, alertFragment);
        fTransaction.commit();
    }

    private void toViewFragment(Fragment fragment, String backStackString) {
        fTransaction = fManager.beginTransaction();
        fTransaction.replace(R.id.fragmentContainer, fragment);
        fTransaction.addToBackStack(backStackString);
        fTransaction.commit();
    }

    public void toFirstFragment() {
        toViewFragment(new CreateTaskChapterOneFragment(), "first");
    }

    public void toSecondFragment() {
        toViewFragment(new CreateTaskChapterTwoFragment(), "second");
    }

    public void toThirdFragment() {
        toViewFragment(new CreateTaskChapterThreeFragment(), "third");
    }

    public void sendTaskToServer() {
        new PostTaskAsync().execute(task);
    }

    private void processResult(int httpResult) {
        if (httpResult == HttpURLConnection.HTTP_OK) {
            Toast.makeText(this, "Задание успешно создано", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, BrowseTaskActivity.class);
            startActivity(intent);
        } else if (httpResult == HttpURLConnection.HTTP_INTERNAL_ERROR) {
            Toast.makeText(this, "Ошибка, заполните допустимые поля", Toast.LENGTH_SHORT).show();
        } else if (httpResult == HttpURLConnection.HTTP_BAD_REQUEST){
            Toast.makeText(this, "Произошла непредвиденная ошибка", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Intent intent;

        switch (item.getItemId()) {
            case R.id.navigation_post_task:
                intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                return true;
            case R.id.navigation_my_task:
                intent = new Intent(this, PersonalTasksActivity.class);
                startActivity(intent);
                return true;
            case R.id.navigation_browse:
                intent = new Intent(this, BrowseTaskActivity.class);
                startActivity(intent);
                return true;
            case R.id.navigation_messages:
                return true;
            case R.id.navigation_profile:
                return true;
        }
        return false;
    }

    //Обработка запросов в фоновом потоке
    private class PostTaskAsync extends AsyncTask<Task, Void, Integer> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Integer doInBackground(Task... tasks) {
            try {
                return DataJsonUtil.postTask(tasks[0]);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Integer httpResult) {
            super.onPostExecute(httpResult);
            processResult(httpResult);
        }
    }

}
