package ro.gtechco.scorekeeper.presentantion.composables

import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import ro.gtechco.scorekeeper.R

@Composable
fun ScoreDropdownMenu(
    isExpanded:Boolean,
    onDismissRequest:()->Unit,
    onAddPointsClick:()->Unit,
    onResetScoreClick:()->Unit
)
{
    DropdownMenu(
        expanded = isExpanded,
        onDismissRequest = {onDismissRequest()}
    ) {
        DropdownMenuItem(
            text = { Text(text = stringResource(R.string.add_points)) },
            onClick = {onAddPointsClick()})

        DropdownMenuItem(
            text = { Text(text = stringResource(R.string.reset_score)) },
            onClick = {onResetScoreClick()}
        )

    }
}