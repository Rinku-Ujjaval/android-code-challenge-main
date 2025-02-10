package life.league.challenge.kotlin.viewmodel

import UiState
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.zip
import kotlinx.coroutines.launch
import life.league.challenge.kotlin.model.UserPost
import life.league.challenge.kotlin.repository.UserRepository

class UserViewModel(val userRepository: UserRepository) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState<List<UserPost>>>(UiState.Loading)

    val uiState: StateFlow<UiState<List<UserPost>>> = _uiState
    var apiKey = MutableStateFlow<String>("")

    fun userLogin(username: String, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            userRepository.userLogin(username, password).catch { e ->
                Log.v("User Login", e.message.toString())
                userListAndPostLIst()
            }.collect {
                Log.v("User Login", it.apiKey.toString())
                apiKey.value = it.apiKey.toString()
            }
        }
    }

    // parallel call Post List API and User list API map data in userPost model
    // using flow zip functionality
    fun userListAndPostLIst() {
        viewModelScope.launch(Dispatchers.Main) {
            userRepository.userPostsList().zip(userRepository.userList()) { postList, userList ->
                val userPost = mutableListOf<UserPost>()
                postList.map { postList ->
                    userList.filter { postList.userId == it.id }.map {
                        userPost.add(
                            UserPost(
                                id = postList.userId,
                                title = postList.title,
                                body = postList.body,
                                avatar = it.avatar,
                                name = it.name
                            )
                        )
                    }
                }
                return@zip userPost
            }.catch { e ->
                Log.v("UserPost", e.toString())
            }.flowOn(Dispatchers.IO)
                .collect {
                    _uiState.value = UiState.Success(it)
                    Log.v("UserPost", Gson().toJson(it))
                }
        }
    }
}