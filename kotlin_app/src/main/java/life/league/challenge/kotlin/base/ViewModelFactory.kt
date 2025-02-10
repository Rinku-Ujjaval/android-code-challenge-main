package life.league.challenge.kotlin.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import life.league.challenge.kotlin.repository.UserRepository
import life.league.challenge.kotlin.viewmodel.UserViewModel
import kotlin.jvm.java

class ViewModelFactory(val userRepository: UserRepository) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
        if (modelClass.isAssignableFrom(UserViewModel::class.java)) {
            return UserViewModel(userRepository) as T
        }
        return super.create(modelClass, extras)
    }
}

