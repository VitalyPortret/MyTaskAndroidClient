package ru.portretov.mytaskandroidclient;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import ru.portretov.mytaskandroidclient.createtaskfragment.CreateTaskChapterOneFragment;
import ru.portretov.mytaskandroidclient.createtaskfragment.CreateTaskChapterThreeFragment;
import ru.portretov.mytaskandroidclient.createtaskfragment.CreateTaskChapterTwoFragment;
import ru.portretov.mytaskandroidclient.createtaskfragment.CreateTaskChooseAlertFragment;
import ru.portretov.mytaskandroidclient.entity.Task;

public class MainActivity extends AppCompatActivity {

    private FragmentManager fManager;
    private FragmentTransaction fTransaction;
    //    private FrameLayout fragmentContainer;
    private CreateTaskChooseAlertFragment alertFragment;
    private CreateTaskChapterOneFragment oneFragment;
    private CreateTaskChapterTwoFragment twoFragment;
    private CreateTaskChapterThreeFragment threeFragment;

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

        fManager = getFragmentManager();
        fTransaction = fManager.beginTransaction();
        toAlertFragment();

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    private void toAlertFragment() {
        alertFragment = new CreateTaskChooseAlertFragment();
        fTransaction.add(R.id.fragmentContainer, alertFragment);
        fTransaction.commit();
    }

    public void toFirstFragment() {
        oneFragment = new CreateTaskChapterOneFragment();
        fTransaction.replace(R.id.fragmentContainer, oneFragment);
        fTransaction.commit();
    }

    public void toSecondFragment() {
        twoFragment = new CreateTaskChapterTwoFragment();
        fTransaction.replace(R.id.fragmentContainer, twoFragment);
        fTransaction.commit();
    }

    public void toTheardFragment() {
        threeFragment = new CreateTaskChapterThreeFragment();
        fTransaction.replace(R.id.fragmentContainer, threeFragment);
        fTransaction.commit();
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

}
