package ro.gtechco.scorekeeper.domain.use_cases

import ro.gtechco.scorekeeper.data.dto.PlayerDto
import ro.gtechco.scorekeeper.domain.repository.PlayerRepository

class UpdateUser {

    suspend fun execute(
        playerRepository: PlayerRepository,
        playerDto: PlayerDto,
        onFailure:(String)->Unit,
        onSuccess:()->Unit)
    {
        if (playerDto.name.isEmpty())
        {
            onFailure("Enter player name!")
            return
        }
        playerRepository.updatePlayer(playerDto.toPlayer())
        onSuccess()
    }
}