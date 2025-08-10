package ro.gtechco.scorekeeper.presentantion.composables

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import ro.gtechco.scorekeeper.R
import ro.gtechco.scorekeeper.data.dto.PlayerDto

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DeletePlayerAlertDialog(
    playerDto: PlayerDto,
    onDismissRequest:()->Unit,
    onConfirmClick:()->Unit)
{
    BasicAlertDialog(onDismissRequest = {onDismissRequest()}) {
    Surface(shape = MaterialTheme.shapes.large,
        tonalElevation = 6.dp) {
        Column(modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start) {
            Spacer(modifier = Modifier.height(8.dp))
            //title
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center)
            {
                Text(
                    text = "${stringResource(R.string.delete_player_ad)} ${playerDto.name} ?",
                    fontFamily = FontFamily.Cursive,
                    style = MaterialTheme.typography.titleLarge)
            }
            Spacer(modifier = Modifier.height(8.dp))
            //body
            Row(
                modifier = Modifier.fillMaxWidth().padding(start = 8.dp),
                horizontalArrangement = Arrangement.Start)
            {
                Text(
                    text = stringResource(R.string.cannot_be_undone),
                    fontFamily = FontFamily.Cursive,
                    style = MaterialTheme.typography.bodyLarge
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Absolute.SpaceAround)
            {
                OutlinedButton(
                    border = BorderStroke(width = 2.dp, color = Color.Red),
                    colors = ButtonDefaults.buttonColors(
                        contentColor = Color.Red,
                        containerColor = Color.Transparent),
                    onClick = {onDismissRequest()}) {
                    Text(text = stringResource(R.string.cancel))
                }
                Button(onClick = {onConfirmClick()},
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Green,
                        ),
                    ) {
                    Text(text = stringResource(R.string.confirm))
            }
            }
            Spacer(modifier = Modifier.height(8.dp))
        }

    }

    }
}