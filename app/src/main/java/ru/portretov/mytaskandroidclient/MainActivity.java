package ru.portretov.mytaskandroidclient;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Toast;

import java.io.IOException;
import java.net.HttpURLConnection;

import ru.portretov.mytaskandroidclient.createtaskfragment.CreateTaskChapterOneFragment;
import ru.portretov.mytaskandroidclient.createtaskfragment.CreateTaskChapterThreeFragment;
import ru.portretov.mytaskandroidclient.createtaskfragment.CreateTaskChapterTwoFragment;
import ru.portretov.mytaskandroidclient.createtaskfragment.CreateTaskChooseAlertFragment;
import ru.portretov.mytaskandroidclient.entity.Task;
import ru.portretov.mytaskandroidclient.util.DataJsonUtil;

public class MainActivity extends BottomNavigationStateActivity {

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

        bottomNavigation = findViewById(R.id.navigation);
        updateBottomNavigationViewState();
        bottomNavigation.setOnNavigationItemSelectedListener(this);

        task = new Task();
        fManager = getFragmentManager();
        toAlertFragment();
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
            Intent intent = new Intent(this, PersonalTasksActivity.class);
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
    protected int getNavigationMenuItemId() {
        return R.id.navigation_post_task;
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
