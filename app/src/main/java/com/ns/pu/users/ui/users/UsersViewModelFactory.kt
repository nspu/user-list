package com.ns.pu.users.ui.users

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import com.ns.pu.users.repository.network.NetworkStateImpl
import com.ns.pu.users.repository.database.UserDatabase
import com.ns.pu.users.repository.paging.PagingProvider

class UsersViewModelFactory(
    private val application: Application,
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UsersViewModel::class.java)) {

            val userDao = Room.databaseBuilder(
                application,
                UserDatabase::class.java,
                "database-user")
                .allowMainThreadQueries()
                .build()
                .getUserDao()

            val paging = PagingProvider(
                networkState = NetworkStateImpl(application),
                userDao = userDao
            )

            @Suppress("UNCHECKED_CAST")
            return UsersViewModel(pagingSource = paging.provide()) as T
        }

        throw IllegalArgumentException("Unknown ViewModel class")
    }
}