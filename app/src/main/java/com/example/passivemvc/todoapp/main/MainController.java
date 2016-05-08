package com.example.passivemvc.todoapp.main;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;

import com.example.passivemvc.todoapp.R;
import com.example.passivemvc.todoapp.menu.MenuController;
import com.example.passivemvc.todoapp.statistics.StatisticsController;
import com.example.passivemvc.todoapp.edittask.EditTaskController;
import com.example.passivemvc.todoapp.tasks.TasksController;
import com.example.passivemvc.todoapp.tasks.TasksFilterType;


/**
 * @author Christian Sarnataro
 *         Created on 18/04/16.
 */
public class MainController extends Fragment implements
        MenuController.MenuListener,
        TasksController.TasksControllerListener,
        EditTaskController.EditTaskControllerListener {

    public static final String TAG = MainController.class.getSimpleName();

    private MenuController menuController;
    private TasksController tasksController;
    private EditTaskController editTaskController;
    private StatisticsController statisticsController;
    private FragmentManager fragmentManager;

    public MainController() {
    }

    public static MainController newInstance(FragmentManager fm) {
        MainController mainController = new MainController();
        mainController.setRetainInstance(true);
        return mainController;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        initFragmentManager();
        initSubControllers();
    }

    private void initFragmentManager() {
        fragmentManager = getActivity().getSupportFragmentManager();
    }

    private void initSubControllers() {
        menuController = (MenuController) fragmentManager.findFragmentById(R.id.main_app_fragment);
        menuController.setMenuListener(this);

        // task controller is injected in menu controller due to its nested structure
        injectTaskListController();

        Log.d(TAG, "Initialized menuController...");
    }

    private void injectTaskListController() {
        try {
            if (tasksController == null) {
                tasksController = new TasksController();
                tasksController.setTasksControllerListener(this);
            }

            menuController.replaceFragment(tasksController);

        } catch (Exception e) {
            Log.e(TAG, "Unexpected exception injecting task list controller:", e);
        }
    }

    private void injectEditTaskController(String taskId) {
        try {
            if (editTaskController == null) {
                editTaskController = new EditTaskController();
                editTaskController.setEditTaskControllerListener(this);
            }
            if (taskId != null) {
                Bundle b = new Bundle();
                b.putString(EditTaskController.ARGUMENT_EDIT_TASK_ID, taskId);
                editTaskController.setArguments(b);
            } else {
                editTaskController.setArguments(null);
            }
            menuController.replaceFragment(editTaskController, true);

        } catch (Exception e) {
            Log.e(TAG, "Unexpected exception injecting task list controller:", e);
        }
    }

    private void injectStatisticsController() {
        try {
            if (statisticsController == null) {
                statisticsController = new StatisticsController();
            }
            menuController.replaceFragment(statisticsController);
        } catch (Exception e) {
            Log.e(TAG, "Unexpected exception injecting statistics controller:", e);
        }
    }


    public void homeButtonClicked() {
        menuController.showMenu();
    }

    @Override
    public void onTaskListSelected() {
        menuController.setToolbarTitle(getString(R.string.app_name));
        injectTaskListController();
    }

    @Override
    public void onStatisticsSelected() {
        menuController.setToolbarTitle(getString(R.string.statistics_title));
        injectStatisticsController();
    }

    @Override
    public void onClearMenuSelected() {
        tasksController.removeCompletedTasks();
        tasksController.refreshTasks();
    }

    @Override
    public void onRefreshMenuSelected() {
        tasksController.refreshTasks();
    }

    @Override
    public void onDeleteMenuSelected() {
        editTaskController.deleteCurrentTask();
        injectTaskListController();
    }

    @Override
    public void onFilterSelected(TasksFilterType type) {
        tasksController.showFilteredTasks(type);
    }


    @Override
    public void onAddTaskButtonClicked() {
        menuController.setToolbarTitle(getString(R.string.add_task));
        injectEditTaskController(null);
    }


    @Override
    public void onTaskClicked(String taskId) {
        menuController.setToolbarTitle(getString(R.string.edit_task));
        injectEditTaskController(taskId);
    }

    @Override
    public void onTaskCreated() {
        menuController.setToolbarTitle(getString(R.string.app_name));
        injectTaskListController();
        tasksController.showTaskCreatedMessage();
    }

    @Override
    public void onTaskUpdated() {
        menuController.setToolbarTitle(getString(R.string.app_name));
        injectTaskListController();
        tasksController.showTaskUpdatedMessage();

    }

}
