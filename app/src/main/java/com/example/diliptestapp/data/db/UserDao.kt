package com.example.diliptestapp.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.diliptestapp.data.db.entities.User

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveAllUsers(records: List<User?>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveUser(records: User?)

    @Query("Delete FROM User where id = :record_id ")
    fun deleteUser(record_id: Int?)

    @Query("SELECT * FROM User ")
    fun getUser(): LiveData<List<User>>
}