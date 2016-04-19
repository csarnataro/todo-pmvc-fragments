package com.example.passivemvc.todoapp.menu;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
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
    public static final String TAG = "MenuController";
    public static final int MENU_ITEM_TASK_LIST = 0;
    public static final int MENU_ITEM_STATISTICS = 1;
    private int currentSelectedItem;

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

    private void initListeners(MenuView view) {
        view.setMenuSelectionListener(this);
    }


    @Override
    public void onTaskListSelected() {
        Log.d(MenuController.class.getSimpleName(), "Selected task list...");

        // check current selected view
        if (currentSelectedItem != MENU_ITEM_TASK_LIST) {
            currentSelectedItem = MENU_ITEM_TASK_LIST;
            menuListener.onTaskListSelected();
        }
    }

    @Override
    public void onStatisticsSelected() {
        Log.d(MenuController.class.getSimpleName(), "Selected statistics...");

        // check current selected view
        if (currentSelectedItem != MENU_ITEM_STATISTICS) {
            currentSelectedItem = MENU_ITEM_STATISTICS;
            menuListener.onStatisticsSelected();
        }


    }

    public void showMenu() {
        view.openDrawer(GravityCompat.START);
    }

    public void setMenuListener(MenuListener menuListener) {
        this.menuListener = menuListener;
    }

    public interface MenuListener {
        void onTaskListSelected();
        void onStatisticsSelected();
    }
}
