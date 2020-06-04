package com.shivaraj.tilaapp.data.local

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface RepoDAO {

    @Query("SELECT * FROM  songs_table WHERE id = :id")
    public fun get(id: Int): LiveData<RepoModel>

    @Query("SELECT * FROM  songs_table WHERE name LIKE :quesry")
    public fun search(quesry: String): List<RepoModel>

    @Query("SELECT * FROM  songs_table")
    fun getAll(): List<RepoModel>


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg songs: RepoModel): LongArray

    @Delete
    fun delete(jobModel: RepoModel)

    @Query("DELETE FROM songs_table")
    fun deleteAll()
}