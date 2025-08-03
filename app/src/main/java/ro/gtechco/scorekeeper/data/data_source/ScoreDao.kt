package ro.gtechco.scorekeeper.data.data_source

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow
import ro.gtechco.scorekeeper.data.model.Score

@Dao
interface ScoreDao {

    @Upsert
    suspend fun upsertScore(score: Score)

    @Query("SELECT * FROM `score-table` WHERE id = :scoreId")
    fun getScore(scoreId:Int):Flow<Score>

}