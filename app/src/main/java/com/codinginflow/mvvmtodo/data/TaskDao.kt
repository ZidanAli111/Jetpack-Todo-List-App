package com.codinginflow.mvvmtodo.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {


    @Query("SELECT * FROM task_table WHERE name LIKE  :searQuery || '%'  ORDER BY important DESC ")
    fun getTask(searQuery:String): Flow<List<Task>>

    /**
    Here the onConflict = OnConflictStrategy.REPLACE is just a meta data
    that will replace if we try to insert a row with a pre-existing id
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(task: Task)

    @Update
    suspend fun update(task: Task)

    @Delete
    suspend fun delete(task: Task)

}