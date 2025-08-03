package ro.gtechco.scorekeeper.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "player-table")
data class Player(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id:Int?=null,
    @ColumnInfo(name = "name")
    val name:String="",
    @ColumnInfo("score")
    val score:Int=0,
    @ColumnInfo(name = "games-won")
    val gamesWon:Int=0,
)
