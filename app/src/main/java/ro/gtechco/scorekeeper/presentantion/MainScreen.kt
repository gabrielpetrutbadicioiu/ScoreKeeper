package ro.gtechco.scorekeeper.presentantion

import android.widget.Toast
import androidx.compose.animation.core.EaseInOut
import androidx.compose.animation.core.tween
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
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
import kotlinx.coroutines.flow.collectLatest
import ro.gtechco.scorekeeper.R
import ro.gtechco.scorekeeper.presentantion.composables.AddPlayerBottomSheet
import ro.gtechco.scorekeeper.presentantion.composables.AddPointsAlertDialog
import ro.gtechco.scorekeeper.presentantion.composables.DeletePlayerAlertDialog
import ro.gtechco.scorekeeper.presentantion.composables.EditWinningScoreAlertDialog
import ro.gtechco.scorekeeper.presentantion.composables.FinishGameAlertDialog
import ro.gtechco.scorekeeper.presentantion.composables.GameSettingsDropdownMenu
import ro.gtechco.scorekeeper.presentantion.composables.PlayerItem
import ro.gtechco.scorekeeper.presentantion.composables.ResetPlayerScoreAlertDialog
import ro.gtechco.scorekeeper.presentantion.composables.TotalResetAlertDialog


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(viewModel: MainScreenViewModel)
{val context = LocalContext.current
    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event->
            when(event)
            {
                is MainScreenViewModel.UiEvent.ShowToast->{
                    Toast.makeText(context, event.message, Toast.LENGTH_LONG).show()
                }
            }
        }
    }
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
                actions = {
                    IconButton(onClick = {viewModel.onEvent(MainScreenEvent.OnExpandSettingsDropdownMenu)})
                    {
                        Icon(imageVector = Icons.Outlined.Settings, contentDescription = stringResource(R.string.icon_description))
                        GameSettingsDropdownMenu(
                            isExpanded = viewModel.screenState.value.showSettingsDropdownMenu,
                            onDismiss ={viewModel.onEvent(MainScreenEvent.OnDismissSettingsDropdownMenu)} ,
                            onEditClick = {viewModel.onEvent(MainScreenEvent.OnShowMaximumScoreAd)},
                            onResetClick ={viewModel.onEvent(MainScreenEvent.OnTotalResetClick)} )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Red, titleContentColor = Color.White))},
        floatingActionButton = {
            FloatingActionButton(
                modifier = Modifier.padding(8.dp),
                shape = CircleShape,
                containerColor = if(isSystemInDarkTheme()) Color.Transparent else Color.White,
                onClick = {viewModel.onEvent(MainScreenEvent.OnFabClick)}
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
            else{
                items(viewModel.playerDtoList.value, key = {it.id?:hashCode()}){ player->
                    PlayerItem(
                        modifier = Modifier.animateItem(placementSpec = tween(durationMillis = 1000, easing = EaseInOut)),
                        player = player,
                        score = viewModel.score.value,
                        onDeletePlayer = {viewModel.onEvent(MainScreenEvent.OnDeletePlayerClick(player.id))},
                        onEditPlayerClick = {viewModel.onEvent(MainScreenEvent.OnEditPlayer(player.id))},
                        editedPlayer = viewModel.editedPlayerDto.value,
                        onUriResult = {uri->viewModel.onEvent(MainScreenEvent.OnEditedPlayerAvatarUriResult(uri))},
                        onDiscardEdit = {viewModel.onEvent(MainScreenEvent.OnCancelEdit)},
                        onPlayerNameChange = {name->viewModel.onEvent(MainScreenEvent.OnEditedPlayerNameChange(name))},
                        onSaveEdit = {viewModel.onEvent(MainScreenEvent.OnSaveEdit)},
                        onScoreSettingsClick = {viewModel.onEvent(MainScreenEvent.OnScoreSettingsClick(player.id))},
                        onDismissDropdownMenu = {viewModel.onEvent(MainScreenEvent.OnDismissScoreDropdownMenu)},
                        onAddPointsClick = {viewModel.onEvent(MainScreenEvent.OnAddPointsClick)},
                        onResetScoreClick = {viewModel.onEvent(MainScreenEvent.OnResetPlayerScore)},
                        firstPlayerId = viewModel.playerDtoList.value.first().id,
                        onFinishGameClick = {viewModel.onEvent(MainScreenEvent.OnFinishGameClick(player.id))}
                    )
                }
            }
        }
        AddPlayerBottomSheet(
            isExpanded = viewModel.screenState.value.showAddPlayerBottomSheet,
            scoreValue = viewModel.createPlayerSheet.value.targetScore,
            isScoreError = viewModel.createPlayerSheet.value.isScoreErr,
            isPlayerNameErr = viewModel.createPlayerSheet.value.isNameErr,
            playerName = viewModel.createPlayerSheet.value.playerName,
            avatarImageUri = viewModel.createPlayerSheet.value.playerAvatarUri,
            avatarSize =128,
            onDismiss = {viewModel.onEvent(MainScreenEvent.OnAddPlayerDismiss)},
            onUriResult = {uri->viewModel.onEvent(MainScreenEvent.OnPlayerAvatarUriResult(uri))},
            onPlayerNameChange = {name-> viewModel.onEvent(MainScreenEvent.OnPlayerNameChange(name))},
            onScoreValueChange = {score->viewModel.onEvent(MainScreenEvent.OnScoreChange(score))},
            onCancelBtnClick = {viewModel.onEvent(MainScreenEvent.OnAddPlayerDismiss)},
            onSaveBtnClick = {viewModel.onEvent(MainScreenEvent.OnSaveNewPlayer)}
        )
    }
if (viewModel.screenState.value.showDeletePlayerAlertDialog)
{
    DeletePlayerAlertDialog(
        playerDto = viewModel.deletedPlayer.value,
        onDismissRequest = {viewModel.onEvent(MainScreenEvent.OnDeleteDismiss)},
        onConfirmClick = {viewModel.onEvent(MainScreenEvent.OnDeleteConfirm)}
    )
}
    if (viewModel.screenState.value.showAddPointsAlertDialog)
    {
        AddPointsAlertDialog(
            playerDto = viewModel.playerScoreEditedDto.value,
            earnedPoints = viewModel.earnedPoints.value,
            onDismissRequest = {viewModel.onEvent(MainScreenEvent.OnAddPointsAdDismiss)},
            onConfirmClick = {viewModel.onEvent(MainScreenEvent.OnAddPointsAdConfirm)},
            onValueChange = {newValue->viewModel.onEvent(MainScreenEvent.OnAddPointsValueChange(newValue))}
        )
    }
    if (viewModel.screenState.value.showResetPlayerScoreAlertDialog)
    {
        ResetPlayerScoreAlertDialog(
            playerDto = viewModel.playerScoreEditedDto.value,
            onConfirmClick = {viewModel.onEvent(MainScreenEvent.OnConfirmResetPlayerScore)},
            onDismissRequest = {viewModel.onEvent(MainScreenEvent.OnDismissResetPlayerScoreAd)}
            )
    }
    if (viewModel.screenState.value.showTotalResetAlertDialog)
    {
        TotalResetAlertDialog(
            onConfirmClick = {viewModel.onEvent(MainScreenEvent.OnTotalResetConfirm)},
            onDismissRequest = {viewModel.onEvent(MainScreenEvent.OnTotalResetAdDismiss)}
        )
    }
    if (viewModel.screenState.value.showEditWinningScoreAlertDialog)
    {
        EditWinningScoreAlertDialog(
            score = viewModel.editedMaximumScore.value,
            onValueChange = {score->viewModel.onEvent(MainScreenEvent.OnMaximumScoreEditValueChange(score)) },
            onConfirmClick = {viewModel.onEvent(MainScreenEvent.OnConfirmMaximumScoreEdit)},
            onDismissRequest = {viewModel.onEvent(MainScreenEvent.OnDismissEditMaximumScoreAd)}
        )
    }
    if (viewModel.screenState.value.showFinishGameAlertDialog)
    {
        FinishGameAlertDialog(
            playerDto = viewModel.winner.value,
            onConfirmClick = {viewModel.onEvent(MainScreenEvent.OnConfirmFinishGameAd)},
            onDismissRequest = {viewModel.onEvent(MainScreenEvent.OnDismissFinishGameAd)}
        )
    }
}