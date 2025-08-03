package ro.gtechco.scorekeeper.di

import androidx.room.Room
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import ro.gtechco.scorekeeper.data.data_source.PlayerDatabase
import ro.gtechco.scorekeeper.domain.use_cases.ToDto
import ro.gtechco.scorekeeper.domain.use_cases.UseCases
import ro.gtechco.scorekeeper.presentantion.MainScreenViewModel

val module= module {
    single {
        Room.databaseBuilder(
            get(),
            PlayerDatabase::class.java,
            "PLAYER_DB",
            ).build().playerDao
    }
    single {
        Room.databaseBuilder(
            get(),
            PlayerDatabase::class.java,
            "SCORE_DB"
        ).build().scoreDao
    }
    single {
        UseCases(
            toDto = ToDto()
        )
    }
viewModel {
    MainScreenViewModel(
        playerDao = get(),
        scoreDao = get(),
        useCases = get())
}
}