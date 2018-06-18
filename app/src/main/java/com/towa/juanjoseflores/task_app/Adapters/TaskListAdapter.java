package com.towa.juanjoseflores.task_app.Adapters;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.towa.juanjoseflores.task_app.DAO.DBUtil;
import com.towa.juanjoseflores.task_app.DAO.TaskDB;
import com.towa.juanjoseflores.task_app.Fragments.DetailDialogFragment;
import com.towa.juanjoseflores.task_app.Models.Task;
import com.towa.juanjoseflores.task_app.R;

import java.util.List;

public class TaskListAdapter extends RecyclerView.Adapter<TaskListAdapter.TaskHolder> {


    private List<Task> listTasks;
    private Context context;
    private Activity activity;

    public TaskListAdapter(List<Task> listTasks, Context context, Activity activity) {
        this.listTasks = listTasks;
        this.context = context;
        this.activity = activity;
    }


    @NonNull
    @Override
    public TaskHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        View view = inflater.inflate(R.layout.item_task, parent,false);

        TaskHolder taskHolder = new TaskHolder(view);

        return taskHolder;
    }

    class TaskHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView tvShortDesciption;
        TextView tvPercentage;
        ImageButton imgBtnDelete;

        String strLongDesc;
        String strShortDesc;

        public TaskHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            tvShortDesciption = itemView.findViewById(R.id.tv_short_description);
            tvPercentage = itemView.findViewById(R.id.tv_percentage);
            imgBtnDelete = itemView.findViewById(R.id.img_btn_delete);
        }

        @Override
        public void onClick(View v) {
            DialogFragment newFragment = DetailDialogFragment.newInstance(strLongDesc, strShortDesc);

            newFragment.show(activity.getFragmentManager(), "dialog");
        }
    }


    @Override
    public void onBindViewHolder(@NonNull TaskHolder holder, int position) {

        final Task task = listTasks.get(position);

        String strPercentage = String.valueOf(task.getIntPercentage())+ "%";

        holder.strLongDesc = task.getStrLongDesc();
        holder.strShortDesc = task.getStrShortDesc();

        holder.tvShortDesciption.setText(holder.strShortDesc);
        holder.tvPercentage.setText(strPercentage);


        holder.imgBtnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TaskDB taskDBInstance = TaskDB.getTaskDB(context);
                DBUtil.DBDeleteTask(taskDBInstance, task, TaskListAdapter.this);
                TaskDB.destroyInstance();


            }
        });
    }

    public void removeItem(Task task){

        listTasks.remove(task);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return this.listTasks.size();
    }



}
