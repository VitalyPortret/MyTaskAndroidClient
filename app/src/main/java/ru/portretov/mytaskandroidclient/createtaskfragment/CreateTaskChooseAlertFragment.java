package ru.portretov.mytaskandroidclient.createtaskfragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ru.portretov.mytaskandroidclient.MainActivity;
import ru.portretov.mytaskandroidclient.R;
import ru.portretov.mytaskandroidclient.entity.Task;

/**
 * Created by adminvp on 11/24/17.
 */

public class CreateTaskChooseAlertFragment extends Fragment implements View.OnClickListener{

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create_task_choose_alert, null);
        setImageViewOnClick(view);

        return view;
    }

    private void setImageViewOnClick(View view ) {
        view.findViewById(R.id.imageView1).setOnClickListener(this);
        view.findViewById(R.id.imageView2).setOnClickListener(this);
        view.findViewById(R.id.imageView3).setOnClickListener(this);
        view.findViewById(R.id.imageView4).setOnClickListener(this);
        view.findViewById(R.id.imageView5).setOnClickListener(this);
        view.findViewById(R.id.imageView6).setOnClickListener(this);
        view.findViewById(R.id.imageView7).setOnClickListener(this);
        view.findViewById(R.id.imageView8).setOnClickListener(this);
        view.findViewById(R.id.imageView9).setOnClickListener(this);
        view.findViewById(R.id.imageView10).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        String alert = null;
        switch (v.getId()){
            case R.id.imageView1:
                alert = "CLEANING";
                break;
            case R.id.imageView2:
                alert = "ASSEMBLY";
                break;
            case R.id.imageView3:
                alert = "HANDYMAN";
                break;
            case R.id.imageView4:
                alert = "DELIVERY";
                break;
            case R.id.imageView5:
                alert = "GARDENING";
                break;
            case R.id.imageView6:
                alert = "REMOVALISTS";
                break;
            case R.id.imageView7:
                alert = "ADMIN";
                break;
            case R.id.imageView8:
                alert = "COMPUTER_IT";
                break;
            case R.id.imageView9:
                alert = "PHOTOGRAPHY";
                break;
            case R.id.imageView10:
                alert = "ANYTHING_ELSE";
                break;
        }
        if (getActivity() == null || alert == null) {
            return;
        }
        MainActivity activity = (MainActivity) getActivity();
        activity.getTask().setAlert(alert);
        activity.toFirstFragment();

    }
}


