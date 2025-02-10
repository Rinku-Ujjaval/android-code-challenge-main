package life.league.challenge.kotlin

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking
import life.league.challenge.kotlin.api.Service
import life.league.challenge.kotlin.base.MyPreferences
import life.league.challenge.kotlin.model.Account
import life.league.challenge.kotlin.model.Error
import life.league.challenge.kotlin.model.User
import life.league.challenge.kotlin.repository.UserRepository
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

@ExperimentalCoroutinesApi
class UserPostRepositoryTest {
    @Mock
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    lateinit var userRepository: UserRepository

    @Mock
    lateinit var myPreferences: MyPreferences

    @Mock
    var mockAPI = Service.api


    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        userRepository = UserRepository(myPreferences)
    }

    // same as write test case for login and post list API
    @Test
    fun userPostRepository() {
        runBlocking {
            //mock model prepend
            var userList =
                mutableListOf<User>(User(id = 0, name = "Rinku", avatar = "http://Rinku",))
            val account = Account(apiKey = "APIKEY")

            // mock Datastore object
            Mockito.`when`(myPreferences.apiKeyFlow).thenReturn(flowOf("APIKEY"))

            // mock get user list from api
            Mockito.`when`(mockAPI.userList("APIKEY")).thenReturn(userList)

            //return result from API
            var result = userRepository.userList()

        }
    }


}