package ro.gtechco.scorekeeper.domain.use_cases

import ro.gtechco.scorekeeper.data.model.Player
import ro.gtechco.scorekeeper.data.model.Score
import ro.gtechco.scorekeeper.domain.repository.PlayerRepository
import ro.gtechco.scorekeeper.domain.repository.ScoreRepository
import ro.gtechco.scorekeeper.presentantion.CreatePlayerSheetState

class SaveNewPlayer {
   suspend fun execute(
       createPlayerSheetState: CreatePlayerSheetState,
       onNameErr:(String)->Unit,
       onScoreErr:(String)->Unit,
       onSuccess:(Player?)->Unit,
       playerRepository: PlayerRepository,
       scoreRepository: ScoreRepository,
                )
    {
        if (createPlayerSheetState.targetScore.isEmpty())
        {
            onScoreErr("Enter target score!")
            return
        }
        if (createPlayerSheetState.playerName.isEmpty())
        {
            onNameErr("Enter player name!")
            return
        }
        playerRepository.insertPlayer(Player(name = createPlayerSheetState.playerName))
        scoreRepository.upsertScore(Score(id = 1, maximumScore = createPlayerSheetState.targetScore.toInt()))
        val lastInsertedPlayer=playerRepository.getLastPlayer()
        onSuccess(lastInsertedPlayer)
    }
}