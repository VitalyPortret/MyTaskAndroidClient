package ru.portretov.mytaskandroidclient.createtaskfragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ru.portretov.mytaskandroidclient.MainActivity;
import ru.portretov.mytaskandroidclient.R;

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
        view.findViewById(R.id.ivClean).setOnClickListener(this);
        view.findViewById(R.id.ivAsembly).setOnClickListener(this);
        view.findViewById(R.id.ivHandyman).setOnClickListener(this);
        view.findViewById(R.id.ivDelivery).setOnClickListener(this);
        view.findViewById(R.id.ivGardening).setOnClickListener(this);
        view.findViewById(R.id.ivRemovalists).setOnClickListener(this);
        view.findViewById(R.id.ivAdmin).setOnClickListener(this);
        view.findViewById(R.id.ivITcomp).setOnClickListener(this);
        view.findViewById(R.id.ivPhoto).setOnClickListener(this);
        view.findViewById(R.id.ivAnyhingElse).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        String alert = null;
        switch (v.getId()){
            case R.id.ivClean:
                alert = "CLEANING";
                break;
            case R.id.ivAsembly:
                alert = "ASSEMBLY";
                break;
            case R.id.ivHandyman:
                alert = "HANDYMAN";
                break;
            case R.id.ivDelivery:
                alert = "DELIVERY";
                break;
            case R.id.ivGardening:
                alert = "GARDENING";
                break;
            case R.id.ivRemovalists:
                alert = "REMOVALISTS";
                break;
            case R.id.ivAdmin:
                alert = "ADMIN";
                break;
            case R.id.ivITcomp:
                alert = "COMPUTER_IT";
                break;
            case R.id.ivPhoto:
                alert = "PHOTOGRAPHY";
                break;
            case R.id.ivAnyhingElse:
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


