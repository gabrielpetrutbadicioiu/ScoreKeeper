package ro.gtechco.scorekeeper.domain.repository

import kotlinx.coroutines.flow.Flow
import ro.gtechco.scorekeeper.data.model.Score

interface ScoreRepository {

    suspend fun upsertScore(score: Score)
    fun getScore(scoreId:Int): Flow<Score>

}