package com.ns.pu.users.repository.network

import com.ns.pu.users.repository.dto.*
import kotlinx.coroutines.runBlocking
import okhttp3.MediaType
import okhttp3.ResponseBody
import okio.BufferedSource
import org.junit.Assert
import org.junit.Test
import retrofit2.Response
import kotlin.random.Random

class UserProviderImplTest {

    private class UserServiceMock(
        private val response: Response<Users> = Response.success(null),
    ) : UserService {

        private val usersByPagesMutable: MutableList<Int> = mutableListOf()
        val usersByPages: List<Int> = usersByPagesMutable


        private val pageNumbersMutable: MutableList<Int> = mutableListOf()
        val pageNumbers: List<Int> = pageNumbersMutable


        override suspend fun users(usersByPage: Int, pageNumber: Int): Response<Users> {
            usersByPagesMutable.add(usersByPage)
            pageNumbersMutable.add(pageNumber)
            return response
        }
    }

    private fun makeDummyUser() = User(
        cell = Random.nextLong().toString(),
        dob = Random.nextInt(),
        email = Random.nextLong().toString(),
        gender = Random.nextLong().toString(),
        id = Id(
            name = Random.nextLong().toString(),
            value = Random.nextLong().toString()
        ),
        location = Location(
            city = Random.nextLong().toString(),
            postcode = Random.nextLong().toString(),
            state = Random.nextLong().toString(),
            street = Random.nextLong().toString()
        ),
        login = Login(
            md5 = Random.nextLong().toString(),
            password = Random.nextLong().toString(),
            salt = Random.nextLong().toString(),
            sha1 = Random.nextLong().toString(),
            sha256 = Random.nextLong().toString(),
            username = Random.nextLong().toString()
        ),
        name = Name(
            first = Random.nextLong().toString(),
            last = Random.nextLong().toString(),
            title = Random.nextLong().toString()
        ),
        nat = Random.nextLong().toString(),
        phone = Random.nextLong().toString(),
        picture = Picture(
            large = Random.nextLong().toString(),
            medium = Random.nextLong().toString(),
            thumbnail = Random.nextLong().toString()
        ),
        registered = Random.nextInt()
    )


    private fun makeDummyResponseBody() = object : ResponseBody() {
        override fun contentLength() = 0L
        override fun contentType(): MediaType? = null
        override fun source(): BufferedSource = throw NotImplementedError()
    }

    @Test
    fun `GIVEN userByPage WHEN getUsers THEN userByPage passed to the service`() = runBlocking {
        val userService = UserServiceMock()
        val sut = UserProviderImpl(userService)
        val userByPageGiven = Random.nextInt()
        sut.getUsers(userByPageGiven, -1)
        Assert.assertEquals(userByPageGiven, userService.usersByPages.last())
    }

    @Test
    fun `GIVEN pageNumber WHEN getUsers THEN pageNumber passed to the service`() = runBlocking {
        val userService = UserServiceMock()
        val sut = UserProviderImpl(userService)
        val pageNumberGiven = Random.nextInt()
        sut.getUsers(-1, pageNumberGiven)
        Assert.assertEquals(pageNumberGiven, userService.pageNumbers.last())
    }

    @Test
    fun `GIVEN users to userService WHEN getUsers THEN users forward`() = runBlocking {
        val usersGiven: List<User> = List(Random.nextInt(1, 100)) { makeDummyUser() }
        val userService = UserServiceMock(
            response = Response.success(Users(null, usersGiven))
        )
        val sut = UserProviderImpl(userService)
        val response = sut.getUsers(-1, -1)
        Assert.assertEquals(usersGiven, response.users)
    }

    @Test
    fun `GIVEN pageNumber to userService WHEN getUsers THEN pageNumber forward`() = runBlocking {
        val pageNumberGiven = Random.nextInt()
        val userService = UserServiceMock(
            response = Response.success(Users(info = Info(page = pageNumberGiven, results = null, seed = null, version = null), results = null))
        )
        val sut = UserProviderImpl(userService)
        val response = sut.getUsers(-1, -1)
        Assert.assertEquals(pageNumberGiven, response.currentPage)
    }

    @Test(expected = Exception::class)
    fun `GIVEN error WHEN getUsers THEN exception`(): Unit = runBlocking {
        val userService = UserServiceMock(
            response = Response.error(400, makeDummyResponseBody())
        )
        val sut = UserProviderImpl(userService)
        sut.getUsers(-1, -1)
    }
}