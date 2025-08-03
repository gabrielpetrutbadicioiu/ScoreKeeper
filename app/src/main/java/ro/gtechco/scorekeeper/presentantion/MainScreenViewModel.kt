package ro.gtechco.scorekeeper.presentantion

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import ro.gtechco.scorekeeper.data.data_source.PlayerDao
import ro.gtechco.scorekeeper.data.data_source.ScoreDao
import ro.gtechco.scorekeeper.data.dto.PlayerDto
import ro.gtechco.scorekeeper.domain.use_cases.UseCases

class MainScreenViewModel(
    private val playerDao: PlayerDao,
    private val scoreDao: ScoreDao,
    private val useCases: UseCases
):ViewModel() {

    //states
    private val _playerDtoList= mutableStateOf<List<PlayerDto>>(emptyList())
    val playerDtoList:State<List<PlayerDto>> = _playerDtoList

    init {
        playerDao.fetchAllPlayers()
            .onEach { playerList->
               _playerDtoList.value= useCases.toDto.execute(
                    oldDtoList = _playerDtoList.value,
                    playerList = playerList
                )
            }.launchIn(viewModelScope)
    }
}