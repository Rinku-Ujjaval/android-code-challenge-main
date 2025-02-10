package life.league.challenge.kotlin.api

import life.league.challenge.kotlin.model.Account
import life.league.challenge.kotlin.model.Posts
import life.league.challenge.kotlin.model.User
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Path

/**
 * Retrofit API interface definition using coroutines. Feel free to change this implementation to
 * suit your chosen architecture pattern and concurrency tools
 */
interface Api {

    @GET("login")
    suspend fun login(@Header("Authorization") credentials: String?): Account

    @GET("users")
    suspend fun userList(@Header("x-access-token") apiKey: String): List<User>


    @GET("posts")
    suspend fun userPostsList(@Header("x-access-token") apiKey: String): List<Posts>
}
