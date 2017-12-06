package ru.portretov.mytaskandroidclient.util;

import android.content.Context;
import android.content.Intent;
import android.view.MenuItem;

import ru.portretov.mytaskandroidclient.BrowseTaskActivity;
import ru.portretov.mytaskandroidclient.MainActivity;
import ru.portretov.mytaskandroidclient.PersonalTasksActivity;
import ru.portretov.mytaskandroidclient.R;

/**
 * Created by adminvp on 11/30/17.
 */

public class WidgetUtil {

    //TODO: сделать чтобы не просто переходило по меню, но и отображалось куда перешло
    public static boolean setBottomNavigationItemSelected(Context context, MenuItem item) {
        Intent intent;
        switch (item.getItemId()) {
            case R.id.navigation_post_task:
                intent = new Intent(context, MainActivity.class);
                context.startActivity(intent);
                return true;
            case R.id.navigation_my_task:
                intent = new Intent(context, PersonalTasksActivity.class);
                context.startActivity(intent);
                return true;
            case R.id.navigation_browse:
                intent = new Intent(context, BrowseTaskActivity.class);
                context.startActivity(intent);
                return true;
        }
        return true;
    }
}
