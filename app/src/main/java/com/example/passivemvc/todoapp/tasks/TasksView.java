package com.example.passivemvc.todoapp.tasks;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.passivemvc.todoapp.R;
import com.example.passivemvc.todoapp.model.Task;
import com.example.passivemvc.todoapp.ui.ScrollChildSwipeRefreshLayout;

import java.util.ArrayList;
import java.util.List;


/**
 * @author Christian Sarnataro
 *         Created on 18/04/16.
 */
public class TasksView extends CoordinatorLayout {

    private static final String TAG = TasksView.class.getSimpleName();

    private FloatingActionButton addTaskButton;
    private ScrollChildSwipeRefreshLayout refreshLayout;
    private TasksAdapter tasksAdapter;
    private View tasksListContainer;
    private View mNoTasksView;
    private ImageView mNoTaskIcon;
    private TextView mNoTaskMainView;
    private TextView mNoTaskAddView;
    private TextView mFilteringLabelView;
    private RecyclerView tasksList;


    public TasksView(Context context) {
        super(context);
    }

    public TasksView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TasksView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void initComponents() {
        addTaskButton = (FloatingActionButton) findViewById(R.id.fab_add_task);
        refreshLayout = (ScrollChildSwipeRefreshLayout) findViewById(R.id.refresh_layout);

        tasksListContainer = findViewById(R.id.tasks_list_container);

        mNoTaskAddView = (TextView) findViewById(R.id.noTasksAdd);

        mNoTasksView = findViewById(R.id.noTasks);
        mNoTasksView = findViewById(R.id.noTasks);
        mNoTaskIcon = (ImageView) findViewById(R.id.noTasksIcon);
        mNoTaskMainView = (TextView) findViewById(R.id.noTasksMain);
        mFilteringLabelView = (TextView) findViewById(R.id.filteringLabel);

        tasksAdapter = new TasksAdapter(new ArrayList<Task>(0), this);

        initTaskList();

    }

    private void initTaskList() {
        tasksList = (RecyclerView) findViewById(R.id.tasks_list);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        tasksList.setLayoutManager(layoutManager);
        tasksList.setAdapter(tasksAdapter);

    }

    public void setAddTaskButtonListener(final AddTaskListener listener) {
        addTaskButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onAddTaskButtonClicked();
            }
        });
    }

    public void setLoadingIndicator(boolean active) {
        refreshLayout.setRefreshing(active);
    }

    public void showTasks(List<Task> tasks) {
        tasksAdapter.refreshData(tasks);

        tasksListContainer.setVisibility(View.VISIBLE);
        mNoTasksView.setVisibility(View.GONE);

    }

    public void setTasksItemListener(TaskItemListener taskItemListener) {
        tasksAdapter.setTaskItemListener(taskItemListener);
    }

    public void showTaskMarkedComplete() {
        Snackbar.make(this, getResources().getString(R.string.task_marked_complete), Snackbar.LENGTH_LONG)
                .show();
    }

    public void showTaskMarkedActive() {
        Snackbar.make(this, getResources().getString(R.string.task_marked_active), Snackbar.LENGTH_LONG)
                .show();
    }


    public interface AddTaskListener {
        void onAddTaskButtonClicked();
    }


    class TasksAdapter extends RecyclerView.Adapter<TasksAdapter.ViewHolder> {

        private List<Task> items;
        private TasksView view;
        private TaskItemListener taskItemListener;

        public TasksAdapter(
                List<Task> items,
                TasksView view) {
            this.items = items;
            this.view = view;
        }

        @Override
        public int getItemViewType(int position) {
            return position;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, final int viewType) {

            final View view = LayoutInflater.
                    from(parent.getContext()).
                    inflate(R.layout.tasks_task_item, parent, false);


            final ViewHolder viewHolder = new ViewHolder(view);

            viewHolder.title = (TextView) view.findViewById(R.id.title);
            viewHolder.completedCheckBox = (CheckBox) view.findViewById(R.id.complete);

            final int position = viewType;

            viewHolder.completedCheckBox.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    Task item = items.get(position);
                    taskItemListener.onCompleteTaskClick(viewHolder.completedCheckBox.isChecked());
                }
            });

            return viewHolder;
        }


        @Override
        public void onBindViewHolder(ViewHolder viewHolder, final int position) {
            final Task item = items.get(position);

            viewHolder.completedCheckBox.setChecked(item.completed);
            viewHolder.title.setText(item.getTitleForList());

        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public int getItemCount() {
            return items.size();
        }

        public List<Task> getItems() {
            return items;
        }

        public void setTaskItemListener(TaskItemListener taskItemListener) {
            this.taskItemListener = taskItemListener;
        }

        public void refreshData(List<Task> tasks) {
            this.items = tasks;
            notifyDataSetChanged();
        }


        class ViewHolder extends RecyclerView.ViewHolder {
            TextView title;
            CheckBox completedCheckBox;

            public ViewHolder(View itemView) {
                super(itemView);
            }

        }
    }


//    private static class TasksAdapter_ extends BaseAdapter {
//
//        private List<Task> tasks;
//        private TasksView parentView;
//        // private TaskItemListener mItemListener;
//
//        public TasksAdapter_(List<Task> tasks, TasksView view) {
//            this.parentView = view;
//            setList(tasks);
//
//        }
//
//        public void replaceData(List<Task> tasks) {
//            setList(tasks);
//            notifyDataSetChanged();
//        }
//
//        private void setList(List<Task> tasks) {
//            this.tasks = tasks;
//        }
//
//        @Override
//        public int getCount() {
//            return tasks.size();
//        }
//
//        @Override
//        public Task getItem(int i) {
//            return tasks.get(i);
//        }
//
//        @Override
//        public long getItemId(int i) {
//            return i;
//        }
//
//        @Override
//        public View getView(int i, View view, ViewGroup viewGroup) {
//            View rowView = view;
//            if (rowView == null) {
//                LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
//                rowView = inflater.inflate(R.layout.tasks_task_item, viewGroup, false);
//            }
//
//            final Task task = getItem(i);
//
//            TextView titleTV = (TextView) rowView.findViewById(R.id.title);
//            titleTV.setText(task.getTitleForList());
//
//            CheckBox completeCB = (CheckBox) rowView.findViewById(R.id.complete);
//
//            // Active/completed task UI
//
//            completeCB.setChecked(task.completed);
//            if (task.completed) {
//                rowView.setBackgroundDrawable(viewGroup.getContext()
//                        .getResources().getDrawable(R.drawable.list_completed_touch_feedback));
//            } else {
//                rowView.setBackgroundDrawable(viewGroup.getContext()
//                        .getResources().getDrawable(R.drawable.touch_feedback));
//            }
//
//            completeCB.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    if (!task.completed) {
//                        mItemListener.onCompleteTaskClick(task);
//                    } else {
//                        mItemListener.onActivateTaskClick(task);
//                    }
//                }
//            });
//
//            rowView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    mItemListener.onTaskClick(task);
//                }
//            });
//
//            return rowView;
//        }
//    }

    public void showNoActiveTasks() {
        showNoTasksViews(
                getResources().getString(R.string.no_tasks_active),
                R.drawable.ic_check_circle_24dp,
                false
        );
    }

    public void showNoTasks() {
        showNoTasksViews(
                getResources().getString(R.string.no_tasks_all),
                R.drawable.ic_assignment_turned_in_24dp,
                false
        );
    }

    public void showNoCompletedTasks() {
        showNoTasksViews(
                getResources().getString(R.string.no_tasks_completed),
                R.drawable.ic_verified_user_24dp,
                false
        );
    }

    public void showActiveFilterLabel() {
        mFilteringLabelView.setText(getResources().getString(R.string.label_active));
    }

    public void showCompletedFilterLabel() {
        mFilteringLabelView.setText(getResources().getString(R.string.label_completed));
    }

    public void showAllFilterLabel() {
        mFilteringLabelView.setText(getResources().getString(R.string.label_all));
    }

    private void showNoTasksViews(String mainText, int iconRes, boolean showAddView) {
        tasksListContainer.setVisibility(View.GONE);
        mNoTasksView.setVisibility(View.VISIBLE);

        mNoTaskMainView.setText(mainText);
        mNoTaskIcon.setImageDrawable(getResources().getDrawable(iconRes));
        mNoTaskAddView.setVisibility(showAddView ? View.VISIBLE : View.GONE);
    }


    interface TaskItemListener {

        void onTaskClick(Task clickedTask);

        void onCompleteTaskClick(boolean isChecked);
    }


}
