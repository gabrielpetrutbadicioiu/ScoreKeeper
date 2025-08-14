package ro.gtechco.scorekeeper.presentantion.composables

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AddAPhoto
import androidx.compose.material.icons.outlined.AddCircle
import androidx.compose.material.icons.outlined.Cancel
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.Save
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import ro.gtechco.scorekeeper.R
import ro.gtechco.scorekeeper.data.dto.PlayerDto
import ro.gtechco.scorekeeper.data.model.Score

@Composable
fun PlayerItem(
    modifier: Modifier,
    firstPlayerId:Int?,
    player:PlayerDto,
    editedPlayer:PlayerDto,
    score: Score,
    onEditPlayerClick:()->Unit,
    onDeletePlayer:()->Unit,
    onPlayerNameChange:(String)->Unit,
    onDiscardEdit:()->Unit,
    onSaveEdit:()->Unit,
    onUriResult:(Uri?)->Unit,
    onScoreSettingsClick:()->Unit,
    onAddPointsClick:()->Unit,
    onResetScoreClick:()->Unit,
    onDismissDropdownMenu:()->Unit,
    onFinishGameClick:()->Unit

)
{
    val avatarPhotoPicker= rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = {uri-> onUriResult(uri)})

    Spacer(modifier = Modifier.height(16.dp))
    Surface (modifier = modifier
        .fillMaxWidth()
        .padding(start = 6.dp, end = 6.dp)
        .border(border = BorderStroke(
            width = 4.dp,
            brush = SolidColor(if (player.isEditing) colorResource(R.color.orange) else if (isSystemInDarkTheme()) Color.Green else Color.Red)
        ),
            shape = RoundedCornerShape(16.dp)
        )
        )
    {
        Column(modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally) {
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth().padding(8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically)
            {
                        BadgedBox(
                            badge ={
                                if (player.isEditing)
                                {
                                    IconButton(onClick = {
                                        avatarPhotoPicker.launch(
                                            PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
                                    }) { Icon(imageVector = Icons.Outlined.AddAPhoto, contentDescription = stringResource(R.string.icon_description))}
                                }
                            } )
                        {
                            if (player.isEditing)
                            {
                                if (editedPlayer.profilePicture!=null)
                                {

                                        AsyncImage(
                                            model =editedPlayer.profilePicture.toString(),
                                            contentDescription = stringResource(R.string.avatar_img_description),
                                            modifier = Modifier.size(64.dp)
                                                .clip(shape = CircleShape),
                                            contentScale = ContentScale.Crop
                                        )
                                }
                                else{
                                    val dogComposition by rememberLottieComposition(spec = LottieCompositionSpec.RawRes(R.raw.dog_avatar))
                                    val progress by animateLottieCompositionAsState(
                                        composition = dogComposition,
                                        isPlaying = true,
                                        iterations = LottieConstants.IterateForever
                                    )
                                    LottieAnimation(composition = dogComposition, progress = progress, modifier = Modifier.size(64.dp). padding(start = 16.dp))
                                }
                            }
                            else{
                                if (player.profilePicture!=null)
                                {
                                    AsyncImage(
                                        model =player.profilePicture,
                                        contentDescription = stringResource(R.string.avatar_img_description),
                                        modifier = Modifier.size(64.dp)
                                            .clip(shape = CircleShape),
                                        contentScale = ContentScale.Crop
                                    )
                                }
                                else{
                                    val dogComposition by rememberLottieComposition(spec = LottieCompositionSpec.RawRes(R.raw.dog_avatar))
                                    val progress by animateLottieCompositionAsState(
                                        composition = dogComposition,
                                        isPlaying = true,
                                        iterations = LottieConstants.IterateForever
                                    )
                                    LottieAnimation(composition = dogComposition, progress = progress, modifier = Modifier.size(64.dp). padding(start = 16.dp))
                                }
                            }

                        }
                if (player.isEditing)
                {
                    TextField(value = editedPlayer.name,
                        onValueChange = {name->onPlayerNameChange(name.replaceFirstChar { char-> char.uppercase() })},
                        colors = TextFieldDefaults.colors(focusedIndicatorColor = colorResource(R.color.orange)),
                        textStyle = TextStyle(fontFamily = FontFamily.Cursive, fontWeight = FontWeight.SemiBold),
                        modifier = Modifier.weight(0.1f).padding(start = 32.dp)
                    )
                }
                else{
                    Text(
                        text = player.name,
                        fontFamily = FontFamily.Cursive,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 28.sp
                    )
                }

                Row {
                    if (player.isEditing)
                    {
                        IconButton(onClick = {onDiscardEdit()} ) {
                            Icon(imageVector = Icons.Outlined.Cancel, contentDescription = stringResource(R.string.icon_description), tint = Color.Red)
                        }
                        IconButton(onClick = {onSaveEdit()}) {
                            Icon(imageVector = Icons.Outlined.Save, contentDescription = stringResource(R.string.icon_description), tint = Color.Green)
                        }
                    }
                    else{
                        IconButton(onClick = {onEditPlayerClick()}) {
                            Icon(imageVector = Icons.Outlined.Edit,
                                contentDescription = stringResource(R.string.icon_description)
                            )
                        }
                        IconButton(onClick ={onDeletePlayer()} ) {
                            Icon(imageVector = Icons.Outlined.Delete,
                                contentDescription = stringResource(R.string.icon_description)
                            )
                    }

                    }

                }

            }

            val snailComposition by rememberLottieComposition(
                spec = LottieCompositionSpec.RawRes(R.raw.snail_loader)
            )

            val targetProgress by remember(player.score, score.maximumScore) {
                mutableFloatStateOf(
                    (player.score.toFloat() / maxOf(1, score.maximumScore).toFloat())
                        .coerceIn(0f, 1f)
                )
            }

            val animatedProgress by animateFloatAsState(
                targetValue = targetProgress,
                animationSpec = tween(durationMillis = 5000, easing = FastOutSlowInEasing),
                label = "snailFill"
            )

            LottieAnimation(
                composition = snailComposition,
                progress = { animatedProgress },
                modifier = Modifier.size(150.dp)
            )


            BadgedBox(badge = {
                IconButton(onClick = {onScoreSettingsClick()}) {
                    Icon(imageVector = Icons.Outlined.AddCircle,
                        contentDescription = stringResource(R.string.icon_description),
                        tint = if (isSystemInDarkTheme()) Color.Green else Color.Red)
                    ScoreDropdownMenu(
                        isExpanded = player.isScoreDropdownMenuExpanded,
                        onDismissRequest = {onDismissDropdownMenu()},
                        onAddPointsClick = {onAddPointsClick()},
                        onResetScoreClick = {onResetScoreClick()})
                }
            }) {
                Text(text = "${player.score}/${score.maximumScore}",
                    fontFamily = FontFamily.Cursive,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 20.sp,
                    modifier = Modifier.border(
                        width = 2.dp,
                        color = if (isSystemInDarkTheme()) Color.Green else Color.Red,
                        shape = RoundedCornerShape(16.dp)
                    ).padding(8.dp)
                )
            }
            Spacer(modifier = Modifier.height(6.dp))
            Text(text = "${stringResource(R.string.games_won)} ${player.gamesWon}",
                fontFamily = FontFamily.Cursive,
                fontWeight = FontWeight.Bold,
                fontSize = 28.sp,
                )
            Spacer(modifier = Modifier.height(12.dp))
            if (player.score>= score.maximumScore && firstPlayerId==player.id)
            {
                val champComposition by  rememberLottieComposition(spec = LottieCompositionSpec.RawRes(R.raw.champion))
                val progress by animateLottieCompositionAsState(
                    composition = champComposition,
                    iterations = LottieConstants.IterateForever,
                    isPlaying = true
                )
                Row(horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()) {
                    LottieAnimation(
                        composition = champComposition,
                        progress = progress,
                        modifier=Modifier.size(128.dp)
                    )
                    Button(
                        modifier = Modifier.padding(end = 16.dp),
                        onClick = {onFinishGameClick()},
                        colors = ButtonDefaults.buttonColors(containerColor = colorResource(R.color.gold))
                    ) { Text(text = stringResource(R.string.finish_game)) }
                }
                Spacer(modifier = Modifier.height(12.dp))

            }
        }//column

    }
}