package com.example.passivemvc.todoapp.edittask;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;

import com.example.passivemvc.todoapp.R;

/**
 * @author Christian Sarnataro
 *         Created on 29/04/16.
 */
public class EditTaskView extends CoordinatorLayout {

    private EditText title;
    private EditText description;
    private FloatingActionButton saveTaskButton;

    public EditTaskView(Context context) {
        super(context);
    }

    public EditTaskView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public EditTaskView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void initComponents() {
        title = (EditText) findViewById(R.id.add_task_title);
        description = (EditText) findViewById(R.id.add_task_description);

        saveTaskButton = (FloatingActionButton) findViewById(R.id.fab_edit_task);
    }

    public void setSaveTaskButtonListener(final OnSaveTaskButtonListener listener) {
        saveTaskButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onSaveTaskButtonClicked();
            }
        });
    }

    public void showEmptyTaskError() {
        Snackbar.make(title, getResources().getString(R.string.empty_task_message), Snackbar.LENGTH_SHORT).show();
    }

    public void resetFields() {
        title.setText("");
        description.setText("");
    }

    public void setTitle(String title) {
        this.title.setText(title);
    }

    public void setDescription(String description) {
        this.description.setText(description);
    }

    public String getTitle() {
        return title.getText().toString();
    }

    public String getDescription() {
        return description.getText().toString();
    }

    public interface OnSaveTaskButtonListener {
        void onSaveTaskButtonClicked();
    }
}
