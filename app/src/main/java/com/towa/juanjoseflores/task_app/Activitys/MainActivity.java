package com.towa.juanjoseflores.task_app.Activitys;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.towa.juanjoseflores.task_app.DAO.DBUtil;
import com.towa.juanjoseflores.task_app.DAO.TaskDB;
import com.towa.juanjoseflores.task_app.R;

public class MainActivity extends BaseActivity {

    final static String TAG = "MainActivity";
    BroadcastReceiver summaryReciver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "The onCreate() event");

        summaryReciver = new MainActivity.UpdateTaskCountReceiver();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "The onStart() event");

        IntentFilter intentFilter = new IntentFilter("com.JJFM.CUSTOM_INTENT.SummaryReady");
        this.registerReceiver(summaryReciver, intentFilter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "The onPause() event");
        this.unregisterReceiver(this.summaryReciver);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "The onResume() event");
        TaskDB taskDBInstance = TaskDB.getTaskDB(getApplicationContext());
        DBUtil.DBGetSummaryTasks(taskDBInstance, getApplicationContext());
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "The onDestroy() event");
    }

    public void ShowNewTask(View view)
    {
        Intent intent = new Intent(getApplicationContext(), NewTaskActivity.class);
        startActivity(intent);

    }

    public void ShowAllTasks(View view)
    {
        Intent intent = new Intent(getApplicationContext(), TaskListActivity.class);
        startActivity(intent);

    }

    private class UpdateTaskCountReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {

            int intToDoTask = DBUtil.getToDoTaskCount();
            TextView tvTaskToDo = findViewById(R.id.tv_task_to_do);
            tvTaskToDo.setText(String.valueOf(intToDoTask) + " Task To Do");

            int intDoingTask = DBUtil.getDoingTaskCount();
            TextView tvTaskDoing = findViewById(R.id.tv_task_doing);
            tvTaskDoing.setText(String.valueOf(intDoingTask) + " Task Doing");

            int intDoneTask = DBUtil.getDoneTaskCount();
            TextView tvTaskDone = findViewById(R.id.tv_task_done);
            tvTaskDone.setText(String.valueOf(intDoneTask) + " Task Done");
        }
    }
}


