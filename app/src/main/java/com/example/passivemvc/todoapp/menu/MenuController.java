package com.example.passivemvc.todoapp.menu;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v7.widget.PopupMenu;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.passivemvc.todoapp.R;
import com.example.passivemvc.todoapp.edittask.EditTaskController;
import com.example.passivemvc.todoapp.tasks.TasksFilterType;

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
    private boolean showDeleteMenuItem;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        MenuView view = (MenuView) inflater.inflate(R.layout.menu_view, container, false);
        this.view = view;
        currentSelectedItem = 0;
        this.setRetainInstance(true);
        view.initComponents();
        initListeners(view);

        setHasOptionsMenu(true);

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

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.tasks_fragment_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        if (showDeleteMenuItem) {
            menu.findItem(R.id.menu_delete).setVisible(true);
            menu.findItem(R.id.menu_filter).setVisible(false);
            menu.findItem(R.id.menu_clear).setVisible(false);
            menu.findItem(R.id.menu_refresh).setVisible(false);

        } else {
            menu.findItem(R.id.menu_delete).setVisible(false);
            menu.findItem(R.id.menu_filter).setVisible(true);
            menu.findItem(R.id.menu_clear).setVisible(true);
            menu.findItem(R.id.menu_refresh).setVisible(true);
        }
        super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_clear:
                menuListener.onClearMenuSelected();
                break;
            case R.id.menu_filter:
                showFilteringPopUpMenu();
                break;
            case R.id.menu_refresh:
                menuListener.onRefreshMenuSelected();
                break;
            case R.id.menu_delete:
                menuListener.onDeleteMenuSelected();
                break;
        }
        return true;
    }

    public void showFilteringPopUpMenu() {
        PopupMenu popup = new PopupMenu(getContext(), getActivity().findViewById(R.id.menu_filter));
        popup.getMenuInflater().inflate(R.menu.filter_tasks, popup.getMenu());

        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {
                TasksFilterType currentFilter;
                switch (item.getItemId()) {
                    case R.id.active:
                        currentFilter = TasksFilterType.ACTIVE_TASKS;
                        break;
                    case R.id.completed:
                        currentFilter = TasksFilterType.COMPLETED_TASKS;
                        break;
                    default:
                        currentFilter = TasksFilterType.ALL_TASKS;
                        break;
                }
                menuListener.onFilterSelected(currentFilter);
                return true;
            }
        });
        popup.show();
    }

    public void replaceFragment(Fragment fragment, boolean addToBackStack) {
        if (fragment instanceof EditTaskController) {
            currentSelectedItem = -1;
            if (fragment.getArguments() != null
                    && fragment.getArguments().getString(EditTaskController.ARGUMENT_EDIT_TASK_ID) != null) {
                showDeleteMenuItem = true;
            } else {
                showDeleteMenuItem = false;
            }
        } else {
            showDeleteMenuItem = false;
        }

        // force the menu to be redrawn
        getActivity().invalidateOptionsMenu();

        FragmentManager fm = getFragmentManager();
        FragmentTransaction transaction = fm
                .beginTransaction()
                .replace(R.id.main_content_fragment, fragment);

        if (addToBackStack) {
            // transaction.addToBackStack(fragment.getTag());
        }

        transaction.commit();

        // execute the transaction now, e.g. otherwise the snackbar is not shown
        fm.executePendingTransactions();

    }

    public void replaceFragment(Fragment fragment) {
        replaceFragment(fragment, false);
    }

    public interface MenuListener {
        void onTaskListSelected();
        void onStatisticsSelected();

        void onClearMenuSelected();
        void onRefreshMenuSelected();
        void onDeleteMenuSelected();

        void onFilterSelected(TasksFilterType type);

    }

}
