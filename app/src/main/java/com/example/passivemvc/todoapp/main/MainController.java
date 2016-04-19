package com.example.passivemvc.todoapp.main;

import android.content.res.Configuration;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.widget.Toast;

import com.example.passivemvc.todoapp.R;
import com.example.passivemvc.todoapp.menu.MenuController;
import com.example.passivemvc.todoapp.statistics.StatisticsController;
import com.example.passivemvc.todoapp.tasks.TasksController;

/**
 * @author Christian Sarnataro
 *         Created on 18/04/16.
 */
public class MainController extends Fragment implements MenuController.MenuListener {

    public static final String TAG = "MediatingController";


    private MenuController menuController;
    private TasksController taskListController;
    private StatisticsController statisticsController;
    private FragmentManager fragmentManager;

    public MainController() {
    }

    public static MainController newInstance(FragmentManager fm) {
        MainController mainController = new MainController();
        mainController.setFragmentManager(fm);
        mainController.initSubControllers();
        mainController.setRetainInstance(true);

        return mainController;
    }


    /** Setter called by the activity which creates this controller */
    public void setFragmentManager(FragmentManager fragmentManager) {
        this.fragmentManager = fragmentManager;
    }

    private void initSubControllers() {
        menuController = (MenuController) fragmentManager.findFragmentById(R.id.main_app_fragment);
        menuController.setMenuListener(this);

        // task controller is injected in menu controller due to its nested structure
        injectTaskListController();

        Log.d(MainController.class.getSimpleName(), "Initialized menuController...");
    }

    private void injectTaskListController() {
        try {
            if (taskListController == null) {
                taskListController = TasksController.newInstance();
            }
            menuController
                    .getFragmentManager()
                    .beginTransaction()
                    .replace(R.id.main_content_fragment, taskListController)
                    .commit();

        } catch (Exception e) {
            // do nothing
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
            // do nothing
        }
    }


    public void showMenu() {
        menuController.showMenu();
    }

    @Override
    public void onTaskListSelected() {
        /*
         * If the task list is already active, nothing must be done
         */

        injectTaskListController();
    }

    @Override
    public void onStatisticsSelected() {
        /*
         * If the statistic fragment is already active, nothing must be done
         */
        injectStatisticsController();
    }

}
