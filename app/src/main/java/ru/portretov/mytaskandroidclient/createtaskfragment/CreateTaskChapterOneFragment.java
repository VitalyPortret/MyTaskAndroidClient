package ru.portretov.mytaskandroidclient.createtaskfragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ru.portretov.mytaskandroidclient.R;

/**
 * Created by adminvp on 11/24/17.
 */

public class CreateTaskChapterOneFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_create_task_chapter_1, null);
    }
}
