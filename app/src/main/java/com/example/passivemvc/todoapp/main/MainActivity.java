package com.example.passivemvc.todoapp.main;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.example.passivemvc.todoapp.R;
import com.example.passivemvc.todoapp.menu.MenuController;

/**
 * @author Christian Sarnataro
 *         Created on 15/04/16.
 */
public class MainActivity extends AppCompatActivity {

    private static final String CURRENT_FILTERING_KEY = "CURRENT_FILTERING_KEY";

    private MainController mainController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
    }

    @Override
    protected void onResume() {
        super.onResume();
        initMainController();
    }

    private void initMainController() {
        FragmentManager fm = getSupportFragmentManager();

        // Check to see if we have retained the worker fragment.
        mainController = (MainController) fm.findFragmentByTag(MenuController.TAG);

        // If not retained (or first time running), we need to create it.
        if (mainController == null) {
            mainController = MainController.newInstance(fm);   //create instance of NON UI Fragment
            fm.beginTransaction().add(mainController, MenuController.TAG).commit();  //NON UI Fragment
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // Open the navigation drawer when the home icon is selected from the toolbar.
                mainController.homeButtonClicked();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
