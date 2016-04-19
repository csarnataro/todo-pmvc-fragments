package com.example.passivemvc.todoapp.tasks;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.passivemvc.todoapp.R;
import com.example.passivemvc.todoapp.menu.MenuController;

/**
 * @author Christian Sarnataro
 *         Created on 18/04/16.
 */
public class TasksController extends Fragment implements TasksView.AddTaskListener {

    TasksView view;

    public static TasksController newInstance() {
        return new TasksController();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        TasksView view = (TasksView) inflater.inflate(R.layout.tasks_view, container, false);
        this.view = view;
        view.initComponents();
        initListeners(view);
        return view;
    }

    private void initListeners(TasksView view) {
        view.setAddTaskButtonListener(this);
    }


    @Override
    public void onAddTaskButtonClicked() {
        Toast.makeText(getContext(), "Add button clicked", Toast.LENGTH_SHORT).show();
    }
}
