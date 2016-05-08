package com.example.passivemvc.todoapp.statistics;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.passivemvc.todoapp.R;
import com.example.passivemvc.todoapp.model.Task;
import com.example.passivemvc.todoapp.tasks.TasksView;

import java.util.List;

/**
 * @author Christian Sarnataro
 *         Created on 18/04/16.
 */
public class StatisticsController extends Fragment {

    private StatisticsView view;

    public StatisticsController() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        StatisticsView view = (StatisticsView) inflater.inflate(R.layout.statistics_view, container, false);
        this.view = view;
        view.initComponents();
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        int activeTasks = 0;
        int completedTasks = 0;

        List<Task> tasks = Task.findAll();

        // We calculate number of active and completed tasks
        for (Task task : tasks) {
            if (task.completed) {
                completedTasks += 1;
            } else {
                activeTasks += 1;
            }
        }

        String displayString = "";
        if (completedTasks == 0 && activeTasks == 0) {
            displayString = getResources().getString(R.string.statistics_no_tasks);
        } else {
            displayString = getResources().getString(R.string.statistics_active_tasks) + " "
                    + activeTasks + "\n" + getResources().getString(
                    R.string.statistics_completed_tasks) + " " + completedTasks;
        }
        this.view.setStatisticsText(displayString);


    }
}

