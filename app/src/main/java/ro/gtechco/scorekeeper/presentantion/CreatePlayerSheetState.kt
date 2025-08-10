package ro.gtechco.scorekeeper.presentantion

import android.net.Uri

data class CreatePlayerSheetState (
    val targetScore:String="",
    val playerName:String="",
    val playerAvatarUri: Uri?=null,
    val isNameErr:Boolean=false,
    val isScoreErr:Boolean=false
)

