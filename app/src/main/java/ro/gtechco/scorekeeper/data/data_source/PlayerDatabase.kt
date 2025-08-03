package ro.gtechco.scorekeeper.data.data_source

import androidx.room.Database
import androidx.room.RoomDatabase
import ro.gtechco.scorekeeper.data.model.Player
import ro.gtechco.scorekeeper.data.model.Score

@Database(
    entities = [Player::class, Score::class],
    version = 1
)
abstract class PlayerDatabase:RoomDatabase() {

    abstract val playerDao:PlayerDao
    abstract val scoreDao:ScoreDao
}