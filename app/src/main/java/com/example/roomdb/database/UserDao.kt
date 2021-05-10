package com.example.roomdb.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.roomdb.models.User

@Dao
interface UserDao {
    @Insert
    suspend fun insertUser(user: User)

    @Insert
    suspend fun insertAll(userList: List<User>)

    @Delete
    suspend fun deleteUser(user: User)

    @Query("Select * from User order by name asc")
    fun getAllUsers(): LiveData<List<User>>

    @Query("SELECT * from User where age>=:age order by name asc")
    fun getUsersAboveAge(age: Int): LiveData<List<User>>
}