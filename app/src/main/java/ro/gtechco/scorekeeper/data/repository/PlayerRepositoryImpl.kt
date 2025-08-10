package ro.gtechco.scorekeeper.data.repository

import kotlinx.coroutines.flow.Flow
import ro.gtechco.scorekeeper.data.data_source.PlayerDao
import ro.gtechco.scorekeeper.data.model.Player
import ro.gtechco.scorekeeper.domain.repository.PlayerRepository

class PlayerRepositoryImpl(private val dao: PlayerDao):PlayerRepository  {
    override suspend fun insertPlayer(player: Player) {
        dao.insertPlayer(player)
    }

    override suspend fun updatePlayer(player: Player) {
        dao.updatePlayer(player)
    }


    override suspend fun deletePlayer(player: Player) {
        dao.deletePlayer(player)
    }

    override fun fetchAllPlayers(): Flow<List<Player>> {
        return dao.fetchAllPlayers()
    }

    override suspend fun getLastPlayer(): Player? {
        return dao.getLastPlayer()
    }


}