package com.example.passivemvc.todoapp.statistics;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.passivemvc.todoapp.R;
import com.example.passivemvc.todoapp.tasks.TasksView;

/**
 * @author Christian Sarnataro
 *         Created on 18/04/16.
 */
public class StatisticsController extends Fragment {

    StatisticsView view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        StatisticsView view = (StatisticsView) inflater.inflate(R.layout.statistics_view, container, false);
        this.view = view;
        view.initComponents();
        initListeners(view);
        return view;
    }

    private void initListeners(StatisticsView view) {

    }

    public static StatisticsController newInstance() {
        return new StatisticsController();
    }
}

