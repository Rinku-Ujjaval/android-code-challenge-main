import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import life.league.challenge.kotlin.base.MyPreferences
import life.league.challenge.kotlin.model.Account
import life.league.challenge.kotlin.model.User
import life.league.challenge.kotlin.repository.UserRepository
import life.league.challenge.kotlin.viewmodel.UserViewModel
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

@ExperimentalCoroutinesApi
class UserPostViewModel {

    @Mock
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    lateinit var  userRepository : UserRepository

    lateinit var userPostViewModel: UserViewModel

    @Before
    fun setup(){
        MockitoAnnotations.openMocks(this)
        userPostViewModel = UserViewModel(userRepository)
    }


    // write test case same as UserList and Post List for viewmodel
    @Test
    fun userViewModel()   {
        runBlocking {

            //mock account mock
            val mockAccount = Account("APIKEY")

            // mock user login repository
            Mockito.`when`(userRepository.userLogin("USERNAME","PASSWORD")).thenReturn(flowOf(mockAccount))


            // get data from viewmodel
            userPostViewModel = UserViewModel(userRepository)
            userPostViewModel.userLogin("UserName", "UserPassword")

            val user = userPostViewModel.apiKey.value.toString()
            assertEquals("", user)
        }
    }
    @After
    fun tearDown(){
    }
}