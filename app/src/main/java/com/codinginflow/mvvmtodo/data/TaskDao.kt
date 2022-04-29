package com.codinginflow.mvvmtodo.data

import androidx.room.*
import com.codinginflow.mvvmtodo.ui.tasks.SortOrder
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {


    fun getTasks(query: String, sortOrder: SortOrder,hideCompleted: Boolean) :Flow<List<Task>> =
        when (sortOrder) {
            SortOrder.BY_DATE -> getTaskSortedByDateCreated(query, hideCompleted)
            SortOrder.BY_NAME -> getTaskSortedByName(query, hideCompleted)
        }


    @Query("SELECT * FROM task_table WHERE (completed != :hideCompleted OR completed=0) AND name LIKE  '%'|| :searQuery || '%'  ORDER BY important DESC, name")
    fun getTaskSortedByName(searQuery: String, hideCompleted: Boolean): Flow<List<Task>>


    @Query("SELECT * FROM task_table WHERE (completed != :hideCompleted OR completed=0) AND name LIKE  '%'|| :searQuery || '%'  ORDER BY important DESC, created")
    fun getTaskSortedByDateCreated(searQuery: String, hideCompleted: Boolean): Flow<List<Task>>


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