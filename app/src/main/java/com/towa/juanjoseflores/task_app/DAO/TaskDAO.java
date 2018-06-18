package com.towa.juanjoseflores.task_app.DAO;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.towa.juanjoseflores.task_app.Models.Task;

import java.util.List;

@Dao
public interface TaskDAO {
    @Query("SELECT * FROM Task ORDER BY percentage")
    List<Task> getAll();
    @Query("SELECT * FROM Task WHERE percentage = 0")
    List<Task> getToDo();
    @Query("SELECT * FROM Task WHERE percentage > 0 AND percentage < 100 ORDER BY percentage")
    List<Task> getDoing();
    @Query("SELECT * FROM Task WHERE percentage = 100")
    List<Task> getDone();

    @Insert
    void insertTask(Task task);

    @Delete
    void deleteTask(Task task);
}
