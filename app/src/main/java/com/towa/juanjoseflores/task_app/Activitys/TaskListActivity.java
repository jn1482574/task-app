package com.towa.juanjoseflores.task_app.Activitys;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.towa.juanjoseflores.task_app.Adapters.TaskListAdapter;
import com.towa.juanjoseflores.task_app.DAO.DBUtil;
import com.towa.juanjoseflores.task_app.DAO.TaskDB;
import com.towa.juanjoseflores.task_app.Models.Task;
import com.towa.juanjoseflores.task_app.R;

import java.util.List;

public class TaskListActivity extends BaseActivity {

    final static String TAG = "TaskListActivity";
    private List<Task> listTask;
    BroadcastReceiver showTaskReciever;
    RecyclerView rvTaskList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_list);
        rvTaskList = findViewById(R.id.rvTaskList);

        showTaskReciever = new TaskListActivity.ShowTaskReceiver(rvTaskList);

    }

    @Override
    protected void onStart() {
        super.onStart();

        IntentFilter intentFilter = new IntentFilter("com.JJFM.CUSTOM_INTENT.TasksReady");
        this.registerReceiver(showTaskReciever, intentFilter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        TaskDB taskDBInstance = TaskDB.getTaskDB(getApplicationContext());
        DBUtil.DBGetAllTask(taskDBInstance, getApplicationContext());
    }

    @Override
    protected void onPause() {
        super.onPause();
        this.unregisterReceiver(this.showTaskReciever);
    }

    public void getDoneTask(View view){
        TaskDB taskDBInstance = TaskDB.getTaskDB(getApplicationContext());
        DBUtil.DBGetDoneTask(taskDBInstance, getApplicationContext());

    }

    public void getToDoTask(View view){
        TaskDB taskDBInstance = TaskDB.getTaskDB(getApplicationContext());
        DBUtil.DBGetToDoTask(taskDBInstance, getApplicationContext());

    }

    public void getDoingTask(View view){
        TaskDB taskDBInstance = TaskDB.getTaskDB(getApplicationContext());
        DBUtil.DBGetDoingTask(taskDBInstance, getApplicationContext());

    }

    public void getAllTask(View view){
        TaskDB taskDBInstance = TaskDB.getTaskDB(getApplicationContext());
        DBUtil.DBGetAllTask(taskDBInstance, getApplicationContext());

    }


    private class ShowTaskReceiver extends BroadcastReceiver {

        RecyclerView rvTaskList;

        public ShowTaskReceiver(RecyclerView rvTaskList) {
            this.rvTaskList = rvTaskList;
        }

        @Override
        public void onReceive(Context context, Intent intent) {
            List<Task> taskList = DBUtil.getTasks();

            TaskListAdapter adapterTaskList = new TaskListAdapter(taskList, getApplicationContext(), TaskListActivity.this);
            rvTaskList.setAdapter(adapterTaskList);

            LinearLayoutManager manager = new LinearLayoutManager(getApplicationContext(),
                    LinearLayoutManager.VERTICAL, false);
            rvTaskList.setLayoutManager(manager);

            for (Task task: taskList){
                Log.d(TAG, "*******************************");
                Log.w(TAG, task.getStrShortDesc() + ", " + String.valueOf(task.getIntPercentage()));
            }
        }
    }


}
