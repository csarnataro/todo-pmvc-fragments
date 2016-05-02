/*
 * Copyright 2016, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.passivemvc.todoapp.model;

import android.support.annotation.Nullable;

import com.example.passivemvc.todoapp.utils.db.AppDatabase;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.structure.BaseModel;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

/**
 * Immutable model class for a Task.
 */
@Table(database = AppDatabase.class)
public class Task extends BaseModel implements Serializable {

    @Column
    @PrimaryKey
    public String id;

    @Column @Nullable
    public String title;

    @Column @Nullable
    public String description;

    @Column
    public boolean completed;

    public Task() {
    }

    /**
     * Use this constructor to create a new active Task.
     *
     * @param title
     * @param description
     */
    public Task(@Nullable String title, @Nullable String description) {
        id = UUID.randomUUID().toString();
        this.title = title;
        this.description = description;
        completed = false;
    }

    /**
     * Use this constructor to create an active Task if the Task already has an id (copy of another
     * Task).
     *
     * @param title
     * @param description
     * @param id of the class
     */
    public Task(@Nullable String title, @Nullable String description, String id) {
        this.id = id;
        this.title = title;
        this.description = description;
        completed = false;
    }

    /**
     * Use this constructor to create a new completed Task.
     *
     * @param title
     * @param description
     * @param completed
     */
    public Task(@Nullable String title, @Nullable String description, boolean completed) {
        id = UUID.randomUUID().toString();
        this.title = title;
        this.description = description;
        this.completed = completed;
    }

    /**
     * Use this constructor to specify a completed Task if the Task already has an id (copy of
     * another Task).
     *
     * @param title
     * @param description
     * @param id
     * @param completed
     */
    public Task(@Nullable String title, @Nullable String description, String id, boolean completed) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.completed = completed;
    }

    @Nullable
    public String getTitleForList() {
        if (title != null && !title.equals("")) {
            return title;
        } else {
            return description;
        }
    }

    public boolean isEmpty() {
        return (title == null || "".equals(title)) &&
                (description == null || "".equals(description));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;


        boolean idEq = (id == null && task.id == null) || (id != null && id.equals(task.id));
        boolean titleEq = (title == null && task.title == null) || (title != null && title.equals(task.title));
        boolean descEq = (description == null && task.description == null) || (description != null && description.equals(task.description));

        return idEq && titleEq || descEq;
    }

    @Override
    public int hashCode() {
        return (id + title + description).hashCode();
    }

    @Override
    public String toString() {
        return "Task with title " + title;
    }


    public static List<Task> findAll() {
        return SQLite.select()
                .from(Task.class)
                .queryList();
    }

    public boolean isActive() {
        return !completed;
    }
}
