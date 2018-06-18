package com.towa.juanjoseflores.task_app.Activitys;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;

import com.towa.juanjoseflores.task_app.DAO.DBUtil;
import com.towa.juanjoseflores.task_app.DAO.TaskDB;
import com.towa.juanjoseflores.task_app.Models.Task;
import com.towa.juanjoseflores.task_app.R;

import java.util.List;

public class NewTaskActivity extends BaseActivity {

    final static String TAG = "NewTaskActivity";

    TextView textViewPercentage;
    SeekBar seekBar;
    BroadcastReceiver showTaskReciever = new ShowTaskReceiver();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_task);

        textViewPercentage = findViewById(R.id.int_percentage);
        seekBar = findViewById(R.id.sb_percentage);

        seekBar.setProgress(0);

        addListenerToTheSwitchDone();
        addListenerToTheSeekBar();
        addListenerToSaveButton();
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

    public void back(View view){
        finish();
    }

    public void addListenerToTheSwitchDone(){
        Switch swicth = findViewById(R.id.switch1);

        swicth.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    textViewPercentage.setText("100");
                    seekBar.setEnabled(false);
                }
                else {
                    textViewPercentage.setText(""+seekBar.getProgress());
                    seekBar.setEnabled(true);
                }
            }
        });

    }

    public void addListenerToTheSeekBar(){

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                textViewPercentage.setText("" + progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }

    private void addListenerToSaveButton(){

        Button btnSave = findViewById(R.id.btn_save);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditText etShortDesc = findViewById(R.id.et_short_desc);
                EditText etLongDesc = findViewById(R.id.et_long_desc);
                Switch swicth = findViewById(R.id.switch1);


                Task newTask = new Task();

                newTask.setStrShortDesc(etShortDesc.getText().toString());
                newTask.setStrLongDesc(etLongDesc.getText().toString());
                newTask.setIntPercentage(Integer.parseInt(textViewPercentage.getText().toString()));

                Log.d(TAG, "LongDesc: " + newTask.getStrLongDesc() );
                Log.d(TAG, "shortDesc: " + newTask.getStrShortDesc() );
                Log.d(TAG, "Percentage: " + newTask.getIntPercentage() );

                TaskDB taskDBInstance = TaskDB.getTaskDB(getApplicationContext());
                DBUtil.DBSaveNewTask(taskDBInstance, newTask);
                TaskDB.destroyInstance();

                etLongDesc.setText("");
                etShortDesc.setText("");
                textViewPercentage.setText("0");
                swicth.setChecked(false);
                seekBar.setProgress(0);

            }
        });

    }

    private class ShowTaskReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            List<Task> taskList = DBUtil.getTasks();
            for (Task task: taskList){
                Log.d(TAG, "*******************************");
                Log.w(TAG, task.getStrShortDesc() + ", " + String.valueOf(task.getIntPercentage()));
            }
        }
    }
}
