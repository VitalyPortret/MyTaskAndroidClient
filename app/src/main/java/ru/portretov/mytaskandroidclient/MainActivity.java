package ru.portretov.mytaskandroidclient;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import java.io.IOException;

import ru.portretov.mytaskandroidclient.createtaskfragment.CreateTaskChapterOneFragment;
import ru.portretov.mytaskandroidclient.createtaskfragment.CreateTaskChapterThreeFragment;
import ru.portretov.mytaskandroidclient.createtaskfragment.CreateTaskChapterTwoFragment;
import ru.portretov.mytaskandroidclient.createtaskfragment.CreateTaskChooseAlertFragment;
import ru.portretov.mytaskandroidclient.entity.Task;
import ru.portretov.mytaskandroidclient.util.DataUtil;

public class MainActivity extends AppCompatActivity {

    private FragmentManager fManager;
    private FragmentTransaction fTransaction;
    //    private FrameLayout fragmentContainer;
    private CreateTaskChooseAlertFragment alertFragment;

    private Task task;

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        task = new Task();
        fManager = getFragmentManager();
        toAlertFragment();

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    private void toAlertFragment() {
        fTransaction = fManager.beginTransaction();
        alertFragment = new CreateTaskChooseAlertFragment();
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

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_post_task:
                    return true;
                case R.id.navigation_my_task:
                    return true;
                case R.id.navigation_browse:
                    return true;
                case R.id.navigation_messages:
                    return true;
                case R.id.navigation_profile:
                    return true;
            }
            return false;
        }
    };

    //Обработка запросов в фоновом потоке
    private class PostTaskAsync extends AsyncTask<Task, Void, Task> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Task doInBackground(Task... tasks) {
            try {
                return DataUtil.postTask(tasks[0]);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Task result) {
            super.onPostExecute(result);
        }
    }

}
