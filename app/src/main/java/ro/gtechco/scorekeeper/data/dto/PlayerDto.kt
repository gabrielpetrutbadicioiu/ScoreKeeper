package ro.gtechco.scorekeeper.data.dto


data class PlayerDto(
    val id:Int?=null,
    val name:String="",
    val score:Int=0,
    val gamesWon:Int=0,
)
