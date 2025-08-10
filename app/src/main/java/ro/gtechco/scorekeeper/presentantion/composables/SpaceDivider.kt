package ro.gtechco.scorekeeper.presentantion.composables

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp


@Composable
fun SpaceDivider(height:Int, thickness:Int)
{
    Spacer(modifier = Modifier.height(height = height.dp))
    HorizontalDivider(thickness = thickness.dp )
    Spacer(modifier = Modifier.height(height = height.dp))
}