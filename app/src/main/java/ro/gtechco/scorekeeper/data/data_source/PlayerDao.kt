package ro.gtechco.scorekeeper.data.data_source

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow
import ro.gtechco.scorekeeper.data.model.Player

@Dao
interface PlayerDao {
    @Upsert
    suspend fun upsertPlayer(player: Player)

    @Delete
    suspend fun deletePlayer(player: Player)

    @Query("SELECT * FROM `player-table` ORDER BY score DESC ")
    fun fetchAllPlayers():Flow<List<Player>>
}