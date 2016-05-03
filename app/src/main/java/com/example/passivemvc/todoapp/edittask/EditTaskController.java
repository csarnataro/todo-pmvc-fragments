package com.example.passivemvc.todoapp.edittask;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.passivemvc.todoapp.R;
import com.example.passivemvc.todoapp.model.Task;

/**
 * @author Christian Sarnataro
 *         Created on 29/04/16.
 */
public class EditTaskController extends Fragment implements EditTaskView.OnSaveTaskButtonListener {

    public static final String ARGUMENT_EDIT_TASK_ID = "EDIT_TASK_ID";

    private String mEditedTaskId;

    private EditTaskView view;
    private EditTaskControllerListener editTaskControllerListener;

    public EditTaskController() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        EditTaskView view = (EditTaskView) inflater.inflate(R.layout.edit_task_view, container, false);
        this.view = view;
        view.initComponents();
        this.initListeners(view);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setTaskIdIfAny();
    }

    private void initListeners(EditTaskView view) {

        view.setSaveTaskButtonListener(this);


    }

    @Override
    public void onSaveTaskButtonClicked() {
        if (isNewTask()) {
            createTask(
                    view.getTitle(),
                    view.getDescription());
        } else {
            updateTask(
                    view.getTitle(),
                    view.getDescription());
        }

    }

    public void createTask(String title, String description) {
        Task newTask = new Task(title, description);
        if (newTask.isEmpty()) {
            view.showEmptyTaskError();
        } else {
            newTask.save();
            editTaskControllerListener.onTaskCreated();
//            mTasksRepository.saveTask(newTask);
//            mAddTaskView.showTasksList();
        }
    }

    public void updateTask(String title, String description) {
//        if (mTaskId == null) {
//            throw new RuntimeException("updateTask() was called but task is new.");
//        }
//        mTasksRepository.saveTask(new Task(title, description, mTaskId));
//        mAddTaskView.showTasksList(); // After an edit, go back to the list.
    }


    private void setTaskIdIfAny() {
        if (getArguments() != null && getArguments().containsKey(ARGUMENT_EDIT_TASK_ID)) {
            mEditedTaskId = getArguments().getString(ARGUMENT_EDIT_TASK_ID);
        }
    }

    private boolean isNewTask() {
        return mEditedTaskId == null;
    }

    public void setEditTaskControllerListener(EditTaskControllerListener listener) {
        this.editTaskControllerListener = listener;
    }

    public interface EditTaskControllerListener {
        void onTaskCreated();
        void onTaskUpdated();

    }

}
