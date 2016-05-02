package com.example.passivemvc.todoapp.edittask;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import com.example.passivemvc.todoapp.R;

/**
 * @author Christian Sarnataro
 *         Created on 29/04/16.
 */
public class EditTaskView extends CoordinatorLayout {

    private TextView mTitle;
    private TextView mDescription;
    private FloatingActionButton fab;

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
        mTitle = (TextView) findViewById(R.id.add_task_title);
        mDescription = (TextView) findViewById(R.id.add_task_description);

        fab = (FloatingActionButton) findViewById(R.id.fab_edit_task);
        fab.setImageResource(R.drawable.ic_done);

    }

    public void setSaveTaskButtonListener(final OnSaveTaskButtonListener listener) {
        fab.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onSaveTaskButtonClicked();
            }
        });
    }

    public String getTitle() {
        return mTitle.getText().toString();
    }

    public String getDescription() {
        return mDescription.getText().toString();
    }

    public void showEmptyTaskError() {
        Snackbar.make(mTitle, getResources().getString(R.string.empty_task_message), Snackbar.LENGTH_LONG).show();
    }

    public interface OnSaveTaskButtonListener {
        void onSaveTaskButtonClicked();
    }
}
