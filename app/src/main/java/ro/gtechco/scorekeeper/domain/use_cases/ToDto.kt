package ro.gtechco.scorekeeper.domain.use_cases

import ro.gtechco.scorekeeper.data.dto.PlayerDto
import ro.gtechco.scorekeeper.data.model.Player

class ToDto {
    fun execute(oldDtoList: List<PlayerDto>, playerList: List<Player>):List<PlayerDto>
    {
       return playerList.map { player->
            val oldPlayerDto=oldDtoList.find { dto-> dto.id==player.id }
            oldPlayerDto?.copy(
                name = player.name,
                score = player.score,
                gamesWon = player.gamesWon
                ) ?: PlayerDto(
                    id = player.id,
                    name = player.name,
                    score = player.score,
                    gamesWon = player.gamesWon
                )
        }
    }
}