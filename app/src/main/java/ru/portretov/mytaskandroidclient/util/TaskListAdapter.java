package ru.portretov.mytaskandroidclient.util;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.List;

import ru.portretov.mytaskandroidclient.entity.Task;

/**
 * Created by adminvp on 11/30/17.
 */

public abstract class TaskListAdapter extends ArrayAdapter<Task> {

    private List<Task> tasks;
    private int resource;
    private LayoutInflater inflater;

    public List<Task> getTasks() {
        return tasks;
    }

    public int getResource() {
        return resource;
    }

    public LayoutInflater getInflater() {
        return inflater;
    }

    public TaskListAdapter(Context context, int resource, List<Task> tasks) {
        super(context, resource, tasks);
        this.tasks = tasks;
        this.resource = resource;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @NonNull
    @Override
    public abstract View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent);
}