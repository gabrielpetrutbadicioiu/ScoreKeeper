package ro.gtechco.scorekeeper.data.data_source

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import ro.gtechco.scorekeeper.data.model.Player

@Dao
interface PlayerDao {
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertPlayer(player: Player)

    @Update
    suspend fun updatePlayer(player: Player)

    @Delete
    suspend fun deletePlayer(player: Player)

    @Query("SELECT * FROM `player-table` ORDER BY score DESC ")
    fun fetchAllPlayers():Flow<List<Player>>

    @Query("SELECT * FROM `player-table` ORDER BY id DESC LIMIT 1")
    suspend fun getLastPlayer(): Player?
}