package ru.portretov.mytaskandroidclient.createtaskfragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import ru.portretov.mytaskandroidclient.MainActivity;
import ru.portretov.mytaskandroidclient.R;
import ru.portretov.mytaskandroidclient.entity.Task;

/**
 * Created by adminvp on 11/24/17.
 */

public class CreateTaskChapterOneFragment extends Fragment {

    private Button btnContinue;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create_task_chapter_1, null);

        view.findViewById(R.id.btnContinue).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                MainActivity activity = (MainActivity) getActivity();
                Task task = activity.getTask();
                activity.toSecondFragment();
            }
        });
        return view;
    }
}
