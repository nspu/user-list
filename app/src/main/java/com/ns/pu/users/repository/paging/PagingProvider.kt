package com.ns.pu.users.repository.paging

import androidx.paging.PagingSource
import com.ns.pu.users.repository.network.NetworkState
import com.ns.pu.users.repository.network.UserProviderImpl
import com.ns.pu.users.repository.database.UserDao
import com.ns.pu.users.repository.dto.User
import com.ns.pu.users.repository.network.serviceRetrofit


class PagingProvider(private val networkState: NetworkState, private val userDao: UserDao) {

    fun provide(): PagingSource<Int, User> {

        return if (networkState.isConnected()) {
            UserPagingSource(
                backend = UserProviderImpl(userService = serviceRetrofit),
                userDao = userDao
            )
        } else {
            userDao.pagingSource()
        }
    }
}