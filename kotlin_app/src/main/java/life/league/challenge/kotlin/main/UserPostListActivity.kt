package life.league.challenge.kotlin.main

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import life.league.challenge.kotlin.base.MyPreferences
import life.league.challenge.kotlin.base.ViewModelFactory
import life.league.challenge.kotlin.compose.PostsView
import life.league.challenge.kotlin.repository.UserRepository
import life.league.challenge.kotlin.ui.theme.AndroidcodechallengemainTheme
import life.league.challenge.kotlin.viewmodel.UserViewModel
import kotlin.jvm.java
import kotlin.text.isEmpty

// Used this below functionality in this assessment
//1. MVVM caricature used
//2. Kotlin
//3. Jetpack Compose
//4. Datastore
//5. Retrofit
//6. Write unit test cases


class UserPostListActivity : ComponentActivity() {

    private var myPreferences = MyPreferences(this)
    private var userRepository = UserRepository(myPreferences)

    @SuppressLint("CoroutineCreationDuringComposition")
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // performed viewmodel
        val viewModelFactory = ViewModelFactory(userRepository)
        val userViewModel =
            ViewModelProvider(this, viewModelFactory)[UserViewModel::class.java]

        setContent {
            // checking is already user login or not
            lifecycleScope.launch {
                // checking with datastore if APIKEY available
                myPreferences.apiKeyFlow.collect {
                    if (it.toString().isEmpty()) {
                        userViewModel.userLogin("hello", "world")
                    } else {
                        userViewModel.userListAndPostLIst()
                    }
                }
            }
            AndroidcodechallengemainTheme {
                Scaffold(topBar = {
                    Surface(shadowElevation = 3.dp) {
                        TopAppBar(title = {
                            Text(
                                "Posts",
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .wrapContentSize(Alignment.Center),
                                style = TextStyle(
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 22.sp,
                                    color = Color.Black
                                )
                            )
                        })
                    }
                }, modifier = Modifier.fillMaxSize()) { innerPadding ->
                    PostsView(
                        userViewModel,
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }


    @Preview(showBackground = true)
    @Composable
    fun GreetingPreview() {
        AndroidcodechallengemainTheme {

        }
    }
}