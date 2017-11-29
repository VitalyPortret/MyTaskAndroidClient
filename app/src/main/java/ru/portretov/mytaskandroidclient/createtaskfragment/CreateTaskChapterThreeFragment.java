package ru.portretov.mytaskandroidclient.createtaskfragment;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import ru.portretov.mytaskandroidclient.MainActivity;
import ru.portretov.mytaskandroidclient.R;
import ru.portretov.mytaskandroidclient.entity.Task;

/**
 * Created by adminvp on 11/24/17.
 */

public class CreateTaskChapterThreeFragment extends Fragment implements AdapterView.OnClickListener {

    private EditText etTotal, etHourlyRateHours, etHourlyRatePriPerHour;
    private TextView tvCountWorkers;
    private RadioGroup toggle;
    private LinearLayout llTotal, llHourlyRate;

    private byte countWorker = 1;

    private double budget = 0;

    public void setCountWorker(byte countWorker) {
        if (countWorker >= 1 && countWorker <= 10) {
            this.countWorker = countWorker;
        }
    }

    public void setBudget(double budget) {
        this.budget = budget;
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create_task_chapter_3, null);

        etTotal = view.findViewById(R.id.etTotal);
        etHourlyRateHours = view.findViewById(R.id.etHourlyRateHours);
        etHourlyRatePriPerHour = view.findViewById(R.id.etHourlyRatePriPerHour);

        tvCountWorkers = view.findViewById(R.id.tvCountWorkers);

        llTotal = view.findViewById(R.id.llTotal);
        llHourlyRate = view.findViewById(R.id.llHourlyRate);

        toggle = view.findViewById(R.id.toggle);

        toggle.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rbTotalBudget:
                        llTotal.setVisibility(View.VISIBLE);
                        llHourlyRate.setVisibility(View.GONE);
                        break;
                    case R.id.rbHourlyRateBudget:
                        llTotal.setVisibility(View.GONE);
                        llHourlyRate.setVisibility(View.VISIBLE);
                        break;
                }
            }
        });

        view.findViewById(R.id.btnContinue).setOnClickListener(this);
        view.findViewById(R.id.btnMinus).setOnClickListener(this);
        view.findViewById(R.id.btnPlus).setOnClickListener(this);
        return view;
    }

    //Подсчитать бюджет
    private double countBudget() {
        double moneyPerPerson = 0;
        switch (toggle.getCheckedRadioButtonId()) {
            case R.id.rbTotalBudget:
                try {
                    moneyPerPerson = Double.parseDouble(etTotal.getText().toString());
                } catch (NumberFormatException e) {
                    showDialog("Обозначте ваш бюджет", "Введите общий бюджет (Только цифры)");
                }
                break;
            case R.id.rbHourlyRateBudget:
                try {
                    moneyPerPerson = Double.parseDouble(etHourlyRateHours.getText().toString());
                    moneyPerPerson = moneyPerPerson * Double.parseDouble(etHourlyRatePriPerHour.getText().toString());
                } catch (NumberFormatException e) {
                    showDialog("Обозначте ваш бюджет", "Введите кол-во часов, и денег за час (Только цифры)");
                }
                break;
        }
        return moneyPerPerson * countWorker;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnContinue:
                MainActivity activity = (MainActivity) getActivity();
                Task task = activity.getTask();
                if (validateTask(task)) {
                    activity.sendTaskToServer();
                }
                break;
            case R.id.btnMinus:
                setCountWorker((byte) (countWorker - 1));
                tvCountWorkers.setText(String.format("%s", countWorker));
                break;
            case R.id.btnPlus:
                setCountWorker((byte) (countWorker + 1));
                tvCountWorkers.setText(String.format("%s", countWorker));
                break;
        }
    }

    private boolean validateTask(Task task) {
        double budget = countBudget();
        //TODO Переписать бюджет на сервере, и здесь(учитывать кол-во работников, полная или почасовая оплата)
        if (budget > 300 && budget < 100000) {
            task.setBudget(budget);
            task.setCountPeople(countWorker);
            return true;
        }
        showDialog("Ошибка с бюджетом", "Бюджет не должен быть меньше 300 Р и превышать 100000 Р");
        return false;
    }

    //Отображение диалогового окна
    private void showDialog(String title, String message) {
        AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
        alert.setTitle(title);
        alert.setMessage(message);

        alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                //Что-то сделать
            }
        });
        alert.show();
    }
}
