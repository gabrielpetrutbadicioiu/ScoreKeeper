package ro.gtechco.scorekeeper.presentantion.composables

import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import ro.gtechco.scorekeeper.R

@Composable
fun GameSettingsDropdownMenu(
    isExpanded:Boolean,
    onDismiss:()->Unit,
    onResetClick:()->Unit,
    onEditClick:()->Unit
)
{
    DropdownMenu(
        expanded =isExpanded,
        onDismissRequest = {onDismiss()}) {
        DropdownMenuItem(
            text = { Text(text = stringResource(R.string.reset_total)) },
            onClick = {onResetClick()},
            )
        DropdownMenuItem(
            text = { Text(text = stringResource(R.string.edit_winning_score)) },
            onClick = {onEditClick()},
        )
    }
}