package com.ns.pu.users.repository.database

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ns.pu.users.repository.dto.User
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(users: List<User>): List<Long>

    @Query("SELECT * FROM user")
    fun pagingSource(): PagingSource<Int, User>

    @Query("DELETE FROM user")
    fun clearAll()

    @Query("SELECT * FROM user WHERE id_user=:id")
    fun findById(id: Int): Flow<User>

    @Query("SELECT id_user, first, last, email FROM user WHERE id_user in (:ids)")
    fun findByIds(ids: List<Long>): List<User>
}
