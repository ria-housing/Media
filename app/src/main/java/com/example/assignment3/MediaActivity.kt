package com.example.assignment3

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.assignment3.ui.theme.Assignment3Theme
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.ui.StyledPlayerView

class MediaActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Assignment3Theme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MediaPlayer(applicationContext, rememberNavController())
//                    Greeting2("Android")
                }
            }
        }
    }
}

@Composable
fun MediaPlayer(applicationContext: Context, navController: NavHostController) {
    var mp = android.media.MediaPlayer.create(applicationContext, R.raw.saudebaazi)
    Column() {
        Button(onClick = {
            mp.start()
        }) {
            Text(text = "Play")
        }
        Button(onClick = {
            mp.pause()
        }) {
            Text(text = "Pause")
        }
        Button(onClick = {
            mp.stop()
            mp = android.media.MediaPlayer.create(applicationContext, R.raw.saudebaazi)

        }) {
            Text(text = "Stop")
        }
        Button(onClick = {
            navController.navigate("home")
        }) {
            Icon(painter = painterResource(id = R.drawable.baseline_arrow_back_24), contentDescription = "")
            Text(text = "Back")
        }
        PlayVideo(videoUri = "https://www.learningcontainer.com/wp-content/uploads/2020/05/sample-mp4-file.mp4")
    }
}
@Composable
fun PlayVideo(videoUri: String){
    val context = LocalContext.current

    val exoPlayer = ExoPlayer.Builder(LocalContext.current)
        .build()
        .also { exoPlayer ->
            val mediaItem = MediaItem.Builder()
                .setUri(videoUri)
                .build()
            exoPlayer.setMediaItem(mediaItem)
            exoPlayer.prepare()
        }

    DisposableEffect(
        AndroidView(factory = {
            StyledPlayerView(context).apply {
                player = exoPlayer
            }
        })
    ) {
        onDispose { exoPlayer.release() }
    }
}
@Composable
fun Greeting2(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview2() {
    Assignment3Theme {
        Greeting2("Android")
    }
}