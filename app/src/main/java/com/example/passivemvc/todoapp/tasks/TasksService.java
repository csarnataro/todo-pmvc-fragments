package com.example.passivemvc.todoapp.tasks;

import com.example.passivemvc.todoapp.model.Task;

import java.util.List;

/**
 * @author Christian Sarnataro
 *         Created on 27/04/16.
 */
public class TasksService {

    public void loadTasks(LoadTasksCallback listener) {
        // TODO: sync call... could be better an async call instead?
        List<Task> tasks = Task.findAll();
        listener.onTasksLoaded(tasks);

    }

    public void refreshTasks() {
        // TODO: clear repository cache
    }

    interface LoadTasksCallback {

        void onTasksLoaded(List<Task> tasks);

        void onDataNotAvailable();
    }

}
