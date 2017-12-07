package ru.portretov.mytaskandroidclient;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

/**
 * Created by adminvp on 12/6/17.
 */

public abstract class BottomNavigationStateActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    protected BottomNavigationView bottomNavigation;

    protected void updateBottomNavigationViewState() {
        int actionId = getNavigationMenuItemId();
        selectBottomNavigationBarItem(actionId);
    }

    protected abstract int getNavigationMenuItemId();

    private void selectBottomNavigationBarItem(int itemId) {
        Menu menu = bottomNavigation.getMenu();
        MenuItem item = menu.findItem(itemId);
        if (item != null) {
            item.setChecked(true);
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.navigation_post_task:
                startActivity(new Intent(this, MainActivity.class));
                break;
            case R.id.navigation_my_task:
                startActivity(new Intent(this, PersonalTasksActivity.class));
                break;
            case R.id.navigation_browse:
                startActivity(new Intent(this, BrowseTaskActivity.class));
                break;
        }
        finish();
        return true;
    }
}