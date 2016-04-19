package com.example.passivemvc.todoapp.menu;

import android.content.Context;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.util.AttributeSet;
import android.view.MenuItem;

import com.example.passivemvc.todoapp.R;

/**
 * @author Christian Sarnataro
 *         Created on 18/04/16.
 */
public class MenuView extends DrawerLayout {

    NavigationView navigationView;

    public MenuView(Context context) {
        super(context);
    }

    public MenuView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MenuView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void initComponents() {
        navigationView = (NavigationView) findViewById(R.id.nav_view);
    }


    public void setMenuSelectionListener(final MenuSelectionListener listener) {
        navigationView.setNavigationItemSelectedListener(
            new NavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(MenuItem menuItem) {
                    switch (menuItem.getItemId()) {
                        case R.id.list_navigation_menu_item:
                            listener.onTaskListSelected();
                            break;
                        case R.id.statistics_navigation_menu_item:
                            listener.onStatisticsSelected();
                            break;
                        default:
                            break;
                    }
                    /* TODO: Check if the navigation drawer must be closed when an item is selected
                     * TODO: or in the controller? Currently is closed here...
                     */
                    closeMenu();
                    for (int i = 0, size = navigationView.getMenu().size(); i < size; i++) {
                        navigationView.getMenu().getItem(i).setChecked(false);
                    }

                    menuItem.setChecked(true);
                    return true;
                }
            });

    }

    private void closeMenu() {
        closeDrawers();
    }

    public interface MenuSelectionListener {
        void onTaskListSelected();
        void onStatisticsSelected();
    }

}
