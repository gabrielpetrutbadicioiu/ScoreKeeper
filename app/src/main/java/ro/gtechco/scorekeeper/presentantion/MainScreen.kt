package ro.gtechco.scorekeeper.presentantion

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import ro.gtechco.scorekeeper.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(viewModel: MainScreenViewModel)
{
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {

            TopAppBar(title = {
                Row(modifier = Modifier.fillMaxSize(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically){
                    Text(
                        stringResource(R.string.main_screen_title),
                        fontSize = 32.sp,
                        fontFamily = FontFamily.Cursive,
                        fontWeight = FontWeight.Bold)
                }
            },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Red, titleContentColor = Color.White))},
        floatingActionButton = {
            FloatingActionButton(
                modifier = Modifier.padding(8.dp),
                shape = CircleShape,
                containerColor = if(isSystemInDarkTheme()) Color.Transparent else Color.White,
                onClick = {/*todo*/}
            ) {
                val fabComposition by rememberLottieComposition(spec = LottieCompositionSpec.RawRes(R.raw.add_red))
                val fabProgress by animateLottieCompositionAsState(
                    composition = fabComposition,
                    isPlaying = true,
                    iterations = LottieConstants.IterateForever
                    )
                LottieAnimation(composition = fabComposition, progress = fabProgress, modifier = Modifier.size(70.dp))

            }
        }) { innerPadding->

        LazyColumn(modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top) {
            if (viewModel.playerDtoList.value.isEmpty())
            {
                item {
                    val emptyScreenLottieComposition by rememberLottieComposition(spec = LottieCompositionSpec.RawRes(R.raw.empty))
                    val emptyScreenLottieProgress by animateLottieCompositionAsState(
                        composition = emptyScreenLottieComposition,
                        isPlaying = true,
                        iterations = LottieConstants.IterateForever
                    )
                    LottieAnimation(composition = emptyScreenLottieComposition, progress = emptyScreenLottieProgress, modifier = Modifier.size(512.dp))
                }

            }
        }

    }
}