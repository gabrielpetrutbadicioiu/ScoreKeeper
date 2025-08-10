package ro.gtechco.scorekeeper.data.dto

import android.net.Uri
import ro.gtechco.scorekeeper.data.model.Player


data class PlayerDto(
    val id:Int?=null,
    val name:String="",
    val score:Int=0,
    val gamesWon:Int=0,
    val profilePicture: Uri?=null,
    val isEditing:Boolean=false,
    val isScoreDropdownMenuExpanded:Boolean=false
)
{
    fun toPlayer():Player
    {
        return Player(
            id = this.id,
            name=this.name,
            score = this.score,
            gamesWon = this.gamesWon)
    }
}
