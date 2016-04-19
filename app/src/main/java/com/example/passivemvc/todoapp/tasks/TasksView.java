package com.example.passivemvc.todoapp.tasks;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.util.AttributeSet;
import android.view.View;

import com.example.passivemvc.todoapp.R;

/**
 * @author Christian Sarnataro
 *         Created on 18/04/16.
 */
public class TasksView extends CoordinatorLayout {

    FloatingActionButton addTaskButton;

    public TasksView(Context context) {
        super(context);
    }

    public TasksView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TasksView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void initComponents() {
        addTaskButton = (FloatingActionButton) findViewById(R.id.fab_add_task);
    }

    public void setAddTaskButtonListener(final AddTaskListener listener) {
        addTaskButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onAddTaskButtonClicked();
            }
        });
    }

    public interface AddTaskListener {
        void onAddTaskButtonClicked();
    }

}
