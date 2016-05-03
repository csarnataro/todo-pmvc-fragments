package com.example.passivemvc.todoapp.main;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;

import com.example.passivemvc.todoapp.R;
import com.example.passivemvc.todoapp.menu.MenuController;
import com.example.passivemvc.todoapp.statistics.StatisticsController;
import com.example.passivemvc.todoapp.edittask.EditTaskController;
import com.example.passivemvc.todoapp.tasks.TasksController;

/**
 * @author Christian Sarnataro
 *         Created on 18/04/16.
 */
public class MainController extends Fragment implements MenuController.MenuListener, TasksController.TasksControllerListener, EditTaskController.EditTaskControllerListener {

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
            menuController
                    .getFragmentManager()
                    .beginTransaction()
                    .replace(R.id.main_content_fragment, tasksController)
                    .commit();

        } catch (Exception e) {
            Log.e(TAG, "Unexpected exception injecting task list controller:", e);
        }
    }

    private void injectEditTaskController() {
        try {
            if (editTaskController == null) {
                editTaskController = new EditTaskController();
                editTaskController.setEditTaskControllerListener(this);
            }
            menuController
                    .getFragmentManager()
                    .beginTransaction()
                    .replace(R.id.main_content_fragment, editTaskController)
                    .commit();

        } catch (Exception e) {
            Log.e(TAG, "Unexpected exception injecting task list controller:", e);
        }
    }

    private void injectStatisticsController() {
        try {
            if (statisticsController == null) {
                statisticsController = StatisticsController.newInstance();
            }
            menuController
                    .getFragmentManager()
                    .beginTransaction()
                    .replace(R.id.main_content_fragment, statisticsController)
                    .commit();
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
    public void onAddTaskButtonClicked() {
        menuController.setToolbarTitle(getString(R.string.add_task));
        injectEditTaskController();
    }

    @Override
    public void onTaskCreated() {
        menuController.setToolbarTitle(getString(R.string.app_name));
        injectTaskListController();
    }

    @Override
    public void onTaskUpdated() {

    }
}
