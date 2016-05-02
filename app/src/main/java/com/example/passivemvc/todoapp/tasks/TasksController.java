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
public class TasksController extends Fragment implements TasksView.AddTaskListener, TasksService.LoadTasksCallback, TasksView.TaskItemListener {

    private TasksFilterType currentFiltering = TasksFilterType.ALL_TASKS;
    private TasksView view;
    private TasksService service;
    private boolean firstLoad = true;
    private TasksControllerListener taskControllerListener;

    public TasksController() {
        service = new TasksService();

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
        loadTasks(false);

    }

    public void loadTasks(boolean forceUpdate) {
        // Simplification for sample: a network reload will be forced on first load.
        loadTasks(forceUpdate || firstLoad, true);
        firstLoad = false;
    }

    private void loadTasks(boolean forceUpdate, final boolean showLoadingUI) {
        if (showLoadingUI) {
            view.setLoadingIndicator(true);
        }
        if (forceUpdate) {
            service.refreshTasks();
        }

        service.loadTasks(this);
    }


        @Override
    public void onTasksLoaded(List<Task> tasks) {
        List<Task> tasksToShow = new ArrayList<>();
        // We filter the tasks based on the requestType
        for (Task task : tasks) {
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
            view.setLoadingIndicator(false);

        processTasks(tasksToShow);


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
    public void onDataNotAvailable() {
    }

    @Override
    public void onTaskClick(Task clickedTask) {

    }

    @Override
    public void onCompleteTaskClick(Task completedTask) {

    }

    @Override
    public void onActivateTaskClick(Task activatedTask) {

    }

    public void setTasksControllerListener(TasksControllerListener taskControllerListener) {
        this.taskControllerListener = taskControllerListener;
    }

    public interface TasksControllerListener {
        void onAddTaskButtonClicked();
    }


}
