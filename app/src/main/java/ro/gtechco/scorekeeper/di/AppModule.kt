package ro.gtechco.scorekeeper.di

import androidx.room.Room
//import org.koin.core.module.dsl.viewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ro.gtechco.scorekeeper.data.data_source.PlayerDatabase
import ro.gtechco.scorekeeper.data.repository.PlayerRepositoryImpl
import ro.gtechco.scorekeeper.data.repository.ScoreRepositoryImpl
import ro.gtechco.scorekeeper.domain.repository.PlayerRepository
import ro.gtechco.scorekeeper.domain.repository.ScoreRepository
import ro.gtechco.scorekeeper.domain.use_cases.AddEarnedPoints
import ro.gtechco.scorekeeper.domain.use_cases.SaveNewPlayer
import ro.gtechco.scorekeeper.domain.use_cases.ToDto
import ro.gtechco.scorekeeper.domain.use_cases.UpdateUser
import ro.gtechco.scorekeeper.domain.use_cases.UseCases
import ro.gtechco.scorekeeper.presentantion.MainScreenViewModel

val module= module {
    single {
        Room.databaseBuilder(
            get(),
            PlayerDatabase::class.java,
            "PLAYER_DB",
            ).fallbackToDestructiveMigration().build()
    }
    single { get<PlayerDatabase>().playerDao}
    single {get<PlayerDatabase>().scoreDao}

    single {
        UseCases(
            toDto = ToDto(),
            saveNewPlayer = SaveNewPlayer(),
            updateUser = UpdateUser(),
            addEarnedPoints = AddEarnedPoints(playerRepository=get())
        )
    }
    single <PlayerRepository>{
        PlayerRepositoryImpl(dao = get())
    }
    single <ScoreRepository>{
        ScoreRepositoryImpl(dao = get())
    }
viewModel {
    MainScreenViewModel(
        playerRepository = get(),
        scoreRepository = get(),
        useCases = get())
}
}