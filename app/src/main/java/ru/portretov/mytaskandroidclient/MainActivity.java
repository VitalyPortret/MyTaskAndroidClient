package ru.portretov.mytaskandroidclient;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
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
import ru.portretov.mytaskandroidclient.util.WidgetUtil;

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

        BottomNavigationView navigation = findViewById(R.id.navigation);
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
        new SendCreatedTask().execute(task);
    }

    private void processResult(int httpResult) {
        if (httpResult == HttpURLConnection.HTTP_OK) {
            showToast( "Задание успешно создано");
            Intent intent = new Intent(this, BrowseTaskActivity.class);
            startActivity(intent);
        } else if (httpResult == HttpURLConnection.HTTP_INTERNAL_ERROR) {
            showToast( "Ошибка, заполните допустимые поля");
        } else if (httpResult == HttpURLConnection.HTTP_BAD_REQUEST){
            showToast( "Произошла непредвиденная ошибка");
        }
    }

    private void showToast(String toastText){
        Toast.makeText(this, toastText, Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return WidgetUtil.setBottomNavigationItemSelected(this, item);
    }

    //Обработка запросов в фоновом потоке
    private class SendCreatedTask extends AsyncTask<Task, Void, Integer> {
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
