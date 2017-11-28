package ru.portretov.mytaskandroidclient.createtaskfragment;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import ru.portretov.mytaskandroidclient.MainActivity;
import ru.portretov.mytaskandroidclient.R;
import ru.portretov.mytaskandroidclient.entity.Task;

/**
 * Created by adminvp on 11/24/17.
 */

public class CreateTaskChapterTwoFragment extends Fragment {

    private final long ONE_MONTH = 2629800000L;

    private CalendarView calendarViewDueDate;
    private Date dateToday, dueDate;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create_task_chapter_2, null);

        dueDate = dateWithoutTime();
        dateToday = new Date(dueDate.getTime());

        calendarViewDueDate = view.findViewById(R.id.calendarViewDueDate);
        calendarViewDueDate.setMinDate(dateToday.getTime());
        calendarViewDueDate.setMaxDate(dateToday.getTime() + ONE_MONTH);

        calendarViewDueDate.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int day) {
                GregorianCalendar g = new GregorianCalendar();
                g.set(year, month, day);
                clearTime(g);
                dueDate = g.getTime();
            }
        });

        view.findViewById(R.id.btnContinue).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity activity = (MainActivity) getActivity();
                Task task = activity.getTask();
                if (validateTask(task)) {
                    activity.toThirdFragment();
                }
            }
        });
        return view;
    }

    //Удаление времени из даты
    private void clearTime(GregorianCalendar calendar) {
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
    }

    private Date dateWithoutTime() {
        Date date = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            date = sdf.parse(sdf.format(new Date()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return (date != null) ? date : new Date();
    }

    private boolean validateTask(Task task) {
        switch (dueDate.compareTo(dateToday)) {
            case 1:
                task.setDueDate(dueDate);
                return true;
            case 0:
                showDialog();
                return false;
            case -1:
                return false;
        }
        return false;
    }

    //Отображение диалогового окна
    private void showDialog() {
        AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
        alert.setTitle("К чему такая спешка?");
         alert.setMessage("Ваше задание скоро закончится,\n помните - как только оно истечет,\n вы не сможете принимать какие-либо предложения\n");

        alert.setPositiveButton("Сохранить дату", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                //TODO: Дописать логику если пользователь хочет выложить задание которое закончится сегодня
            }
        });
        alert.setNegativeButton("Изменить дату", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) { }
        });
        alert.show();
    }
}
