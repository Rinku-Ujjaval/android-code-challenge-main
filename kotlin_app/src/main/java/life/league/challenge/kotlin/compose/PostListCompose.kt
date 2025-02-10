package life.league.challenge.kotlin.compose

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.ui.draw.clip
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import kotlin.collections.toList
import kotlin.toString
import life.league.challenge.kotlin.model.UserPost
import life.league.challenge.kotlin.viewmodel.UserViewModel

@Composable
fun PostsView(
    userViewModel: UserViewModel,
    modifier: Modifier = Modifier
) {
    // collect data from userViewmodel
    val postList = userViewModel.uiState.collectAsState()

    Column(
        verticalArrangement = Arrangement.Center,
        modifier = modifier
            .fillMaxHeight()
    ) {
        when (postList.value) {
            // inti state load the data
            is UiState.Loading -> {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            // getting success response from viewmodel
            is UiState.Success -> {
                val data = (postList.value as UiState.Success<List<UserPost>>).data.toList()
                LazyColumn {
                    items(data) {
                        Column(modifier = Modifier.padding(start = 16.dp)) {
                            Column(modifier = Modifier.padding(8.dp)) {
                                Row {
                                    Image(
                                        painter = rememberAsyncImagePainter(it.avatar),
                                        contentDescription = null,
                                        modifier = Modifier
                                            .size(80.dp)
                                            .clip(CircleShape)
                                    )
                                    Box(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .height(80.dp)
                                            .padding(start = 8.dp),
                                        contentAlignment = Alignment.CenterStart
                                    ) {
                                        Text(
                                            it.name.toString(),
                                            style = TextStyle(
                                                fontSize = 22.sp,
                                            ),
                                        )
                                    }
                                }
                                Text(
                                    it.title.toString(),
                                    style = TextStyle(fontSize = 20.sp),
                                    modifier = Modifier.padding(8.dp)
                                )
                                Text(
                                    it.body.toString(),
                                    style = TextStyle(fontSize = 18.sp),
                                    modifier = Modifier.padding(8.dp)
                                )
                            }
                            HorizontalDivider(color = Color.LightGray, thickness = 0.8.dp)
                        }
                    }
                }
            }

            // if not data found in response
            is UiState.Error -> {
                Text("No Data Found")
            }
        }
    }
}
