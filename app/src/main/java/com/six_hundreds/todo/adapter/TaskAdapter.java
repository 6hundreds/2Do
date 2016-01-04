package com.six_hundreds.todo.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.six_hundreds.todo.fragment.TasksFragment;
import com.six_hundreds.todo.model.Item;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by six_hundreds on 02.01.16.
 */
public abstract class TaskAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    List<Item> items;

    TasksFragment tasksFragment;

    public TaskAdapter(TasksFragment tasksFragment) {
        this.tasksFragment = tasksFragment;
        items = new ArrayList<>();
    }

    public Item getItem(int position) {
        return items.get(position);
    }

    public void addItem(Item item) {
        items.add(item);
        notifyItemInserted(getItemCount() - 1);
    }

    public void addItem(int location, Item item) {
        items.add(location, item);
        notifyItemInserted(location);
    }

    public void removeItem (int location) {
        if (location >=0 && location<= getItemCount() -1 ){
            items.remove(location);
            notifyItemRemoved(location);
        }
    }
    @Override
    public int getItemCount() {
        return items.size();
    }


    protected class TaskViewHolder extends RecyclerView.ViewHolder {

        protected TextView title;
        protected TextView date;
        protected CircleImageView priority;

        public TaskViewHolder(View itemView, TextView title, TextView date, CircleImageView priority) {
            super(itemView);
            this.title = title;
            this.date = date;
            this.priority = priority;
        }
    }

    public TasksFragment getTasksFragment() {
        return tasksFragment;
    }
}
