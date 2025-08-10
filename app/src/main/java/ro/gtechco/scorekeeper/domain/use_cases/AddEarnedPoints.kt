package ro.gtechco.scorekeeper.domain.use_cases

import ro.gtechco.scorekeeper.data.dto.PlayerDto
import ro.gtechco.scorekeeper.domain.repository.PlayerRepository

class AddEarnedPoints(private val playerRepository: PlayerRepository) {

    suspend fun execute(
        editedPlayer:PlayerDto,
        earnedPoints:String,
        onSuccess:()->Unit)
    {
        if (earnedPoints.isEmpty())
        {
            val newPlayer=editedPlayer.copy(score = editedPlayer.score).toPlayer()
            playerRepository.updatePlayer(newPlayer)
            onSuccess()
        }
        else{
            val newPlayer=editedPlayer.copy(score = editedPlayer.score+earnedPoints.toInt()).toPlayer()
            playerRepository.updatePlayer(newPlayer)
            onSuccess()
        }

    }
}