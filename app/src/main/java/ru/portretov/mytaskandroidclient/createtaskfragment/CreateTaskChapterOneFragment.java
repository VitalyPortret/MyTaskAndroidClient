package ru.portretov.mytaskandroidclient.createtaskfragment;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import ru.portretov.mytaskandroidclient.MainActivity;
import ru.portretov.mytaskandroidclient.R;
import ru.portretov.mytaskandroidclient.entity.Task;
import ru.portretov.mytaskandroidclient.entity.enumirate.TaskType;
import ru.portretov.mytaskandroidclient.util.AfterChangeTextWatcher;

/**
 * Created by adminvp on 11/24/17.
 */

public class CreateTaskChapterOneFragment extends Fragment {

    private final int TASK_MAX_TITLE = 50;
    private final int TASK_MAX_DESCRIPTION = 200;

    private EditText etTitle, etDescription, etLocation;
    private Switch switchTaskType;
    private TextView tvTitleLength, tvDescriptionLength;


    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create_task_chapter_1, null);

        etTitle = view.findViewById(R.id.etTitle);
        etDescription = view.findViewById(R.id.etDescription);
        etLocation = view.findViewById(R.id.etLocation);
        switchTaskType = view.findViewById(R.id.switchTaskType);
        tvTitleLength = view.findViewById(R.id.tvTitleLength);
        tvDescriptionLength = view.findViewById(R.id.tvDescriptionLength);

        //При изменение текста изменяется TextView в котором написано сколько введено символов
        etTitle.addTextChangedListener(new AfterChangeTextWatcher() {
            @Override
            public void afterTextChanged(Editable str) {
                if (str.length() < 10) {
                    tvTitleLength.setText(R.string.minimun_10_char);
                } else {
                    tvTitleLength.setText(String.format("Осталось %d символов", TASK_MAX_TITLE - str.length()));
                }
            }
        });

        //При изменение текста изменяется TextView в котором написано сколько введено символов
        etDescription.addTextChangedListener(new AfterChangeTextWatcher() {
            @Override
            public void afterTextChanged(Editable str) {
                if (str.length() < 25) {
                    tvDescriptionLength.setText(R.string.minimum_25_char);
                } else if (str.length() > 150) {
                    tvDescriptionLength.setText(String.format("Осталось %d символов", TASK_MAX_DESCRIPTION - str.length()));
                } else {
                    tvDescriptionLength.setText("");
                }
            }
        });

        view.findViewById(R.id.btnContinue).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity activity = (MainActivity) getActivity();
                Task task = activity.getTask();
                if (validateTask(task)) {
                    activity.toSecondFragment();
                }
            }
        });
        return view;
    }

    //Валидация и заполнение Task
    private boolean validateTask(Task task) {
        if (task == null){
            return false;
        }
        if (etTitle.length() < 10) {
            showDialog("Заголовок задания: Минимум 10 символов");
            return false;
        }
        task.setTitle(etTitle.getText().toString());

        if (etDescription.length() < 25) {
            showDialog("Описание задачи: Минимум 25 символов");
            return false;
        }
        task.setDescription(etDescription.getText().toString());

        if (switchTaskType.isChecked()){
            task.setTaskType(TaskType.ONLINE_TASK);
        } else {
            task.setTaskType(TaskType.TASK_WITH_LOCATION);
            if (etLocation.length() < 10) {
                showDialog("Местоположение задания: Минимум 10 символов");
                return false;
            }
            task.setLocation(etLocation.getText().toString());
        }
        return true;
    }

    //Отображение диалогового окна
    private void showDialog(String title) {
        AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
        alert.setTitle(title);
        // alert.setMessage("Message");

        alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                //Что-то сделать
            }
        });
        alert.show();
    }
}
