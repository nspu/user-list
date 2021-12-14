package com.ns.pu.users.repository.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.ns.pu.users.repository.network.UserProvider
import com.ns.pu.users.repository.database.UserDao
import com.ns.pu.users.repository.dto.User
import com.ns.pu.users.repository.network.Response

// Try to reproduce the behavior of RemoteMediator which is in release candidate
class UserPagingSource(
    private val backend: UserProvider,
    private val userDao: UserDao,
) : PagingSource<Int, User>() {

    override suspend fun load(
        params: LoadParams<Int>,
    ): LoadResult<Int, User> {
        val currentPage = params.key ?: 1
        return try {
            val response = backend.getUsers(params.loadSize, currentPage)
            val ids = registerUsersInDb(currentPage, response)
            LoadResult.Page(
                data = userDao.findByIds(ids),
                prevKey = null,
                nextKey = response.currentPage?.plus(1)
            )
        } catch (e: Exception) {
            if (currentPage == 1) userDao.pagingSource().load(params)
            else LoadResult.Error(e)
        }
    }

    private fun registerUsersInDb(currentPage: Int, response: Response): List<Long> {
        if (currentPage == 1) userDao.clearAll()
        val ids = userDao.insertAll(users = response.users)
        return ids
    }

    override fun getRefreshKey(state: PagingState<Int, User>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}
