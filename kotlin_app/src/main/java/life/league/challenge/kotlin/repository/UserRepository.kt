package life.league.challenge.kotlin.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import life.league.challenge.kotlin.api.Service
import life.league.challenge.kotlin.base.MyPreferences
import life.league.challenge.kotlin.model.Account
import life.league.challenge.kotlin.model.Posts
import life.league.challenge.kotlin.model.User

open class UserRepository(val myPreferences: MyPreferences) {

    // login api call and get APIKEY
    open fun userLogin(username: String, password: String): Flow<Account> = flow {
        emit(
            Service.api.login(
                "Basic " + android.util.Base64.encodeToString(
                    "$username:$password".toByteArray(), android.util.Base64.NO_WRAP
                )
            )
        )
    }.map {
        // store APIKEY in datastore
        myPreferences.storeApiKey(it.apiKey.toString())
        return@map it
    }

    // get user list form user api with using flow
    fun userList(): Flow<List<User>> {
        return flow {
            // get APIKEY from datastore and pass in user api header
            myPreferences.apiKeyFlow.collect {
                emit(Service.api.userList(it.toString()))
            }
        }.map { return@map it }
    }

    // get post list from post api with using flow
    fun userPostsList(): Flow<List<Posts>> {
        return flow {
            // get APIKEY from datastore and pass in user api header
            myPreferences.apiKeyFlow.collect {
                emit(Service.api.userPostsList(it.toString()))
            }
        }.map { return@map it }
    }
}