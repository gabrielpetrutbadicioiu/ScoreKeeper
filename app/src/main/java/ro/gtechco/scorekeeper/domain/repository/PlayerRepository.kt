package ro.gtechco.scorekeeper.domain.repository

import kotlinx.coroutines.flow.Flow
import ro.gtechco.scorekeeper.data.model.Player

interface PlayerRepository {
    suspend fun insertPlayer(player: Player)

    suspend fun updatePlayer(player: Player)

    suspend fun deletePlayer(player: Player)

    fun fetchAllPlayers(): Flow<List<Player>>
    suspend fun getLastPlayer(): Player?
}