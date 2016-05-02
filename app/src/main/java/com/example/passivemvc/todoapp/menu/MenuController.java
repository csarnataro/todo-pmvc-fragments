package com.example.passivemvc.todoapp.menu;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.passivemvc.todoapp.R;

/**
 * @author Christian Sarnataro
 *         Created on 18/04/16.
 */
public class MenuController extends Fragment implements MenuView.MenuSelectionListener {
    public static final String TAG = MenuController.class.getSimpleName();

    private static final String CURRENT_SELECTED_MENU_KEY = "CURRENT_SELECTED_MENU_KEY";
    public static final int MENU_ITEM_TASK_LIST = 0;
    public static final int MENU_ITEM_STATISTICS = 1;
    private int currentSelectedItem = -1;

    private MenuListener menuListener;
    private MenuView view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        MenuView view = (MenuView) inflater.inflate(R.layout.menu_view, container, false);
        this.view = view;
        currentSelectedItem = 0;
        this.setRetainInstance(true);
        view.initComponents();
        initListeners(view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null) {
            currentSelectedItem = savedInstanceState.getInt(CURRENT_SELECTED_MENU_KEY);
        }
    }

    private void initListeners(MenuView view) {
        view.setMenuSelectionListener(this);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(CURRENT_SELECTED_MENU_KEY, currentSelectedItem);
    }

    @Override
    public void onTaskListSelected() {
        Log.d(TAG, "Task list selected...");

         /*
         * Check the currently selected view.
         * If the task list is already active, nothing must be done
         */
        if (currentSelectedItem != MENU_ITEM_TASK_LIST) {
            currentSelectedItem = MENU_ITEM_TASK_LIST;
            menuListener.onTaskListSelected();
        }
    }

    @Override
    public void onStatisticsSelected() {
        Log.d(TAG, "Statistics selected...");

        /*
         * Check the currently selected view.
         * If the statistic fragment is already active, nothing must be done
         */
        if (currentSelectedItem != MENU_ITEM_STATISTICS) {
            currentSelectedItem = MENU_ITEM_STATISTICS;
            menuListener.onStatisticsSelected();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        view.restoreCheckedItem(currentSelectedItem);
    }

    public void showMenu() {
        view.openDrawer(GravityCompat.START);
    }

    public void setMenuListener(MenuListener menuListener) {
        this.menuListener = menuListener;
    }

    public void setToolbarTitle(String toolbarTitle) {
        view.setToolbarTitle(toolbarTitle);
    }

    public interface MenuListener {
        void onTaskListSelected();
        void onStatisticsSelected();
    }
}
