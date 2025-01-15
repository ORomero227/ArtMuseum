package com.example.artmuseum

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.StringRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.artmuseum.ui.theme.ArtMuseumTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ArtMuseumTheme {
                Surface {
                    ArtMuseumLayout()
                }
            }
        }
    }
}

@Composable
fun ArtMuseumLayout() {
    var currentArtWork by remember { mutableIntStateOf(1) }

    val (artTitle, artArtist, artWorkImage) = when (currentArtWork) {
        1 -> Triple(R.string.artwork_1_title, R.string.artwork_1_artist, R.drawable.artwork_1)
        2 -> Triple(R.string.artwork_2_title, R.string.artwork_2_artist, R.drawable.artwork_2)
        3 -> Triple(R.string.artwork_3_title, R.string.artwork_3_artist, R.drawable.artwork_3)
        else -> Triple(R.string.app_name,R.string.app_name,R.drawable.ic_launcher_foreground)
    }

    fun moveToNextArtwork() { currentArtWork++ }
    fun moveToPreviousArtwork() { currentArtWork-- }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .padding(horizontal = 30.dp)
            .verticalScroll(rememberScrollState())
            .safeDrawingPadding(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ArtWork(
            modifier = Modifier
                .size(300.dp)
                .border(BorderStroke(15.dp, Color.White))
                .shadow(10.dp),
            artWorkImage = artWorkImage
        )
        Spacer(Modifier.padding(top = 100.dp))
        ArtWorkDetails(
            modifier = Modifier.fillMaxWidth(),
            artworkArtist = artArtist,
            artworkTitle = artTitle
        )
        Spacer(Modifier.padding(110.dp))
        ControlButtons(
            moveToNextArtwork = ::moveToNextArtwork,
            moveToPreviousArtwork = ::moveToPreviousArtwork,
            currentArtwork = currentArtWork,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
fun ArtWork(
    artWorkImage: Int,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(artWorkImage),
            contentDescription = "",
            contentScale = ContentScale.Fit,
            modifier = Modifier.matchParentSize().background(Color.White)
        )
    }
}


@Composable
fun ArtWorkDetails(
    @StringRes artworkTitle: Int,
    @StringRes artworkArtist: Int,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(artworkTitle),
            fontSize = 35.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.padding(5.dp))
        Text(
            text = stringResource(artworkArtist),
            fontSize = 25.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
fun ControlButtons(
    moveToNextArtwork: () -> Unit,
    moveToPreviousArtwork: () -> Unit,
    currentArtwork: Int,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Button(
            onClick = { moveToPreviousArtwork() },
            enabled = currentArtwork > 1
        ) {
            Text(stringResource(R.string.button_previous))
        }
        Button(
            onClick = { moveToNextArtwork() },
            enabled = currentArtwork < 3
        ) {
            Text(stringResource(R.string.button_next))
        }
    }
}


@Preview(showBackground = true)
@Composable
fun ArtMuseumPreview() {
    ArtMuseumTheme {
        Surface {
            ArtMuseumLayout()
        }
    }
}