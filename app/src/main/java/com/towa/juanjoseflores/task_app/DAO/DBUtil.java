package com.towa.juanjoseflores.task_app.DAO;

import com.towa.juanjoseflores.task_app.Adapters.TaskListAdapter;
import com.towa.juanjoseflores.task_app.Models.Task;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;

import android.content.Intent;

import android.os.AsyncTask;

import android.util.Log;

import java.util.ArrayList;

import java.util.List;



public class DBUtil {

    final static String TAG = "DBUtil";

    static int intTaskTodo = 0;
    static int intTaskDoing = 0;
    static int intTaskDone = 0;

    static List<Task> tasks = new ArrayList<>();

    public static List<Task> getTasks() {

        return tasks;

    }

    public static List<Task> DBGetAllTask(TaskDB taskDBInstance, Context context){

        GetAllTask getAllTask = new GetAllTask(taskDBInstance, context);

        getAllTask.execute();

        return tasks;

    }

    public static List<Task> DBGetSummaryTasks(TaskDB taskDBInstance, Context context){

        GetSummaryTasks getSummaryTasks = new GetSummaryTasks(taskDBInstance, context);

        getSummaryTasks.execute();

        return tasks;

    }

    public static List<Task> DBGetDoneTask(TaskDB taskDBInstance, Context context){

        GetDoneTask getDoneTask = new GetDoneTask(taskDBInstance, context);

        getDoneTask.execute();

        return tasks;

    }

    public static List<Task> DBGetToDoTask(TaskDB taskDBInstance, Context context){

        GetToDoTask getToDoTask = new GetToDoTask(taskDBInstance, context);

        getToDoTask.execute();

        return tasks;

    }

    public static List<Task> DBGetDoingTask(TaskDB taskDBInstance, Context context){

        GetDoingTask getDoingTask = new GetDoingTask(taskDBInstance, context);

        getDoingTask.execute();

        return tasks;

    }


    public static void DBSaveNewTask(TaskDB taskDBInstance, Task task){

        SaveNewTask saveNewTask = new SaveNewTask(taskDBInstance, task);

        saveNewTask.execute();

    }

    public static void DBDeleteTask(TaskDB taskDBInstance, Task task, TaskListAdapter adapter){

        DeleteTask deleteTask = new DeleteTask(taskDBInstance, task, adapter);

        deleteTask.execute();

    }

    public static int getToDoTaskCount(){
        return intTaskTodo;
    }

    public static int getDoingTaskCount(){
        return intTaskDoing;
    }

    public static int getDoneTaskCount(){
        return intTaskDone;
    }


    //-----------------------------------------------------------------------------------------------------------------

    private static class GetAllTask extends AsyncTask<Void, Void, Void> {

        TaskDB taskDBInstance;

        Context context;



        public GetAllTask(TaskDB taskDBInstance, Context context) {

            this.taskDBInstance = taskDBInstance;

            this.context = context;

        }



        @Override

        protected Void doInBackground(final Void... params) {

            tasks = taskDBInstance.taskDAO().getAll();

            Log.d(TAG, tasks.size() + " Tasks in DB");

            return null;

        }



        @Override

        protected void onPostExecute(Void v)

        {

            Log.d(TAG, "TasksReady");

            Intent intent = new Intent();

            intent.setAction("com.JJFM.CUSTOM_INTENT.TasksReady");

            context.sendBroadcast(intent);

        }

    }

    //-----------------------------------------------------------------------------------------------------------------

    private static class GetSummaryTasks extends AsyncTask<Void, Void, Void> {

        TaskDB taskDBInstance;

        Context context;



        public GetSummaryTasks(TaskDB taskDBInstance, Context context) {

            this.taskDBInstance = taskDBInstance;

            this.context = context;

        }



        @Override

        protected Void doInBackground(final Void... params) {

            intTaskTodo = taskDBInstance.taskDAO().getToDo().size();
            intTaskDoing = taskDBInstance.taskDAO().getDoing().size();
            intTaskDone = taskDBInstance.taskDAO().getDone().size();

            Log.d(TAG, tasks.size() + " Tasks in DB");

            return null;

        }



        @Override

        protected void onPostExecute(Void v)

        {

            Log.d(TAG, "TasksReady");

            Intent intent = new Intent();

            intent.setAction("com.JJFM.CUSTOM_INTENT.SummaryReady");

            context.sendBroadcast(intent);

        }

    }

    //-----------------------------------------------------------------------------------------------------------------

    private static class GetDoneTask extends AsyncTask<Void, Void, Void> {

        TaskDB taskDBInstance;

        Context context;



        public GetDoneTask(TaskDB taskDBInstance, Context context) {

            this.taskDBInstance = taskDBInstance;

            this.context = context;

        }



        @Override

        protected Void doInBackground(final Void... params) {

            tasks = taskDBInstance.taskDAO().getDone();

            Log.d(TAG, tasks.size() + " Tasks in DB");

            return null;

        }



        @Override

        protected void onPostExecute(Void v)

        {

            Log.d(TAG, "TasksReady");

            Intent intent = new Intent();

            intent.setAction("com.JJFM.CUSTOM_INTENT.TasksReady");

            context.sendBroadcast(intent);

        }

    }

    //-----------------------------------------------------------------------------------------------------------------

    private static class GetToDoTask extends AsyncTask<Void, Void, Void> {

        TaskDB taskDBInstance;

        Context context;



        public GetToDoTask(TaskDB taskDBInstance, Context context) {

            this.taskDBInstance = taskDBInstance;

            this.context = context;

        }



        @Override

        protected Void doInBackground(final Void... params) {

            tasks = taskDBInstance.taskDAO().getToDo();

            Log.d(TAG, tasks.size() + " Tasks in DB");

            return null;

        }



        @Override

        protected void onPostExecute(Void v)

        {

            Log.d(TAG, "TasksReady");

            Intent intent = new Intent();

            intent.setAction("com.JJFM.CUSTOM_INTENT.TasksReady");

            context.sendBroadcast(intent);

        }

    }

    //-----------------------------------------------------------------------------------------------------------------

    private static class GetDoingTask extends AsyncTask<Void, Void, Void> {

        TaskDB taskDBInstance;

        Context context;



        public GetDoingTask(TaskDB taskDBInstance, Context context) {

            this.taskDBInstance = taskDBInstance;

            this.context = context;

        }



        @Override

        protected Void doInBackground(final Void... params) {

            tasks = taskDBInstance.taskDAO().getDoing();

            Log.d(TAG, tasks.size() + " Tasks in DB");

            return null;

        }



        @Override

        protected void onPostExecute(Void v)

        {

            Log.d(TAG, "TasksReady");

            Intent intent = new Intent();

            intent.setAction("com.JJFM.CUSTOM_INTENT.TasksReady");

            context.sendBroadcast(intent);

        }

    }



    //-----------------------------------------------------------------------------------------------------------------

    private static class SaveNewTask extends AsyncTask<Void, Void, Void> {

        TaskDB taskDBInstance;

        Task task;



        public SaveNewTask(TaskDB taskDBInstance, Task task) {

            this.taskDBInstance = taskDBInstance;

            this.task = task;

        }



        @Override

        protected Void doInBackground(final Void... params) {

            taskDBInstance.taskDAO().insertTask(task);

            Log.d(TAG, "Saving new task ");

            return null;

        }

    }

    //-----------------------------------------------------------------------------------------------------------------

    private static class DeleteTask extends AsyncTask<Void, Void, Void> {

        TaskDB taskDBInstance;

        Task task;

        TaskListAdapter adapter;



        public DeleteTask(TaskDB taskDBInstance, Task task, TaskListAdapter adapter) {

            this.taskDBInstance = taskDBInstance;

            this.task = task;

            this.adapter = adapter;

        }



        @Override

        protected Void doInBackground(final Void... params) {

            taskDBInstance.taskDAO().deleteTask(task);

            Log.d(TAG, "delete new task ");

            return null;

        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            adapter.removeItem(task);

        }
    }


    //-----------------------------------------------------------------------------------------------------------------



}