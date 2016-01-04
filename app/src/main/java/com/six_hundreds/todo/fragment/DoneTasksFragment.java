package com.six_hundreds.todo.fragment;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.six_hundreds.todo.R;
import com.six_hundreds.todo.adapter.DoneTasksAdapter;
import com.six_hundreds.todo.database.DBHelper;
import com.six_hundreds.todo.model.ModelTask;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class DoneTasksFragment extends TasksFragment {



    public DoneTasksFragment() {
        // Required empty public constructor
    }

    OnTaskRestoreListener onTaskRestoreListener;

    public interface OnTaskRestoreListener {
        void onTaskRestore(ModelTask task);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {

            onTaskRestoreListener = (OnTaskRestoreListener)  getActivity();

        } catch (ClassCastException e) {
            throw new ClassCastException(getActivity().toString() + " must implement OnTaskDoneListener");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_done_task, container, false);

        recyclerView = (RecyclerView) rootView.findViewById(R.id.rvDoneTasks);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        adapter = new DoneTasksAdapter(this);
        recyclerView.setAdapter(adapter);

        return rootView;
    }

    @Override
    public void addTaskFromDB() {
        List<ModelTask> tasks = new ArrayList<>();
        tasks.addAll(activity.dbHelper.query().getTasks(DBHelper.SELECTION_STATUS,
                new String[] {Integer.toString(ModelTask.STATUS_DONE)},
                DBHelper.COLUMN_TASK_DATE));

        for (int i = 0; i<tasks.size(); i++){
            addTask(tasks.get(i), false);
        }
    }


    @Override
    public void moveTask(ModelTask task) {
        onTaskRestoreListener.onTaskRestore(task);

    }
}
