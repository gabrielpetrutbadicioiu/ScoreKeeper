package ro.gtechco.scorekeeper.data.repository

import kotlinx.coroutines.flow.Flow
import ro.gtechco.scorekeeper.data.data_source.ScoreDao
import ro.gtechco.scorekeeper.data.model.Score
import ro.gtechco.scorekeeper.domain.repository.ScoreRepository

class ScoreRepositoryImpl(private val dao: ScoreDao):ScoreRepository {
    override suspend fun upsertScore(score: Score) {
        dao.upsertScore(score)
    }

    override fun getScore(scoreId: Int): Flow<Score> {
        return dao.getScore(scoreId)
    }
}