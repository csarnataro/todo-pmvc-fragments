package com.example.passivemvc.todoapp.tasks;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.passivemvc.todoapp.R;
import com.example.passivemvc.todoapp.model.Task;

import java.util.ArrayList;
import java.util.List;


/**
 * @author Christian Sarnataro
 *         Created on 18/04/16.
 */
public class TasksController extends Fragment implements TasksView.AddTaskListener, TasksView.TaskItemListener {

    private TasksFilterType currentFiltering = TasksFilterType.ALL_TASKS;
    private TasksView view;
    private TasksControllerListener taskControllerListener;
    private List<Task> allTasks;

    public TasksController() {
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
        view.setTasksItemListener(this);
    }


    @Override
    public void onAddTaskButtonClicked() {
        taskControllerListener.onAddTaskButtonClicked();
    }

    @Override
    public void onResume() {
        super.onResume();
        loadTasks();

    }

    public void loadTasks() {
        loadTasks(true);
    }

    private void loadTasks(final boolean showLoadingUI) {
        if (showLoadingUI) {
            view.setLoadingIndicator(true);
        }
        List<Task> tasks = Task.findAll();
        onTasksLoaded(tasks);

    }

    public void onTasksLoaded(List<Task> tasks) {
        allTasks = tasks;

        view.setLoadingIndicator(false);
        processTasks(getTasksToShow());
    }

    private List<Task> getTasksToShow() {
        List<Task> tasksToShow = new ArrayList<>();
        // We filter the tasks based on the requestType
        for (Task task : allTasks) {
            switch (currentFiltering) {
                case ALL_TASKS:
                    tasksToShow.add(task);
                    break;
                case ACTIVE_TASKS:
                    if (task.isActive()) {
                        tasksToShow.add(task);
                    }
                    break;
                case COMPLETED_TASKS:
                    if (task.completed) {
                        tasksToShow.add(task);
                    }
                    break;
                default:
                    tasksToShow.add(task);
                    break;
            }
        }
        return tasksToShow;

    }

    private void processTasks(List<Task> tasks) {
        if (tasks.isEmpty()) {
            // Show a message indicating there are no tasks for that filter type.
            processEmptyTasks();
        } else {
            // Show the list of tasks
            view.showTasks(tasks);
            // Set the filter label's text.
            showFilterLabel();
        }
    }

    private void processEmptyTasks() {
        switch (currentFiltering) {
            case ACTIVE_TASKS:
                view.showNoActiveTasks();
                break;
            case COMPLETED_TASKS:
                view.showNoCompletedTasks();
                break;
            default:
                view.showNoTasks();
                break;
        }
    }

    private void showFilterLabel() {
        switch (currentFiltering) {
            case ACTIVE_TASKS:
                view.showActiveFilterLabel();
                break;
            case COMPLETED_TASKS:
                view.showCompletedFilterLabel();
                break;
            default:
                view.showAllFilterLabel();
                break;
        }
    }

    @Override
    public void onTaskClick(Task clickedTask) {
        taskControllerListener.onTaskClicked(clickedTask.id);
    }

    @Override
    public void onCompleteTaskClick(Task task, boolean isChecked) {
        task.completed = isChecked;
        task.save();
        loadTasks();

        if (isChecked) {
            view.showTaskMarkedComplete();
        } else {
            view.showTaskMarkedActive();
        }
        refreshTasks();

    }

    public void setTasksControllerListener(TasksControllerListener taskControllerListener) {
        this.taskControllerListener = taskControllerListener;
    }

    public void showFilteredTasks(TasksFilterType type) {
        currentFiltering = type;
        processTasks(getTasksToShow());

    }

    public void removeCompletedTasks() {
        Task.removeCompleted();
    }

    public void refreshTasks() {
        loadTasks(true);
    }

    public void showTaskCreatedMessage() {
        view.showTaskSaved();
    }

    public void showTaskUpdatedMessage() {
        view.showTaskSaved();
    }

    public interface TasksControllerListener {
        void onAddTaskButtonClicked();
        void onTaskClicked(String taskId);
    }

}
