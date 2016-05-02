package com.example.passivemvc.todoapp.utils.db;

import com.raizlabs.android.dbflow.annotation.Database;

/**
 * @author Christian Sarnataro
 *         Created on 29/04/16.
 */
@Database(name = AppDatabase.NAME, version = AppDatabase.VERSION)
public class AppDatabase {

    public static final String NAME = "AppDatabase"; // we will add the .db extension

    public static final int VERSION = 1;
}