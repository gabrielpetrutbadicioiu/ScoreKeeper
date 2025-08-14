package ro.gtechco.scorekeeper.presentantion

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import ro.gtechco.scorekeeper.data.dto.PlayerDto
import ro.gtechco.scorekeeper.data.model.Score
import ro.gtechco.scorekeeper.domain.repository.PlayerRepository
import ro.gtechco.scorekeeper.domain.repository.ScoreRepository
import ro.gtechco.scorekeeper.domain.use_cases.UseCases

class MainScreenViewModel(
    private val playerRepository: PlayerRepository,
    private val scoreRepository: ScoreRepository,
    private val useCases: UseCases
):ViewModel() {

    //states
    private val _playerDtoList= mutableStateOf<List<PlayerDto>>(emptyList())
    val playerDtoList:State<List<PlayerDto>> = _playerDtoList

    private val _deletedPlayer= mutableStateOf(PlayerDto())
    val deletedPlayer:State<PlayerDto> = _deletedPlayer

    private val _score = mutableStateOf(Score(id = 1))
    val score:State<Score> = _score

    private val _screenState= mutableStateOf(MainScreenState())
    val screenState:State<MainScreenState> =_screenState

    private val _createPlayerSheet= mutableStateOf(CreatePlayerSheetState(targetScore = _score.value.maximumScore.toString()))
    val createPlayerSheet:State<CreatePlayerSheetState> = _createPlayerSheet

    private val _editedPlayerDto= mutableStateOf(PlayerDto())
    val editedPlayerDto:State<PlayerDto> =_editedPlayerDto

    private val _earnedPoints= mutableStateOf("")
    val earnedPoints:State<String> = _earnedPoints

    private val _playerScoreEditedDto= mutableStateOf(PlayerDto())
    val playerScoreEditedDto:State<PlayerDto> = _playerScoreEditedDto

    private val _editedMaximumScore= mutableStateOf("")
    val editedMaximumScore:State<String> = _editedMaximumScore

    private val _winner= mutableStateOf(PlayerDto())
    val winner:State<PlayerDto> = _winner

    //one time events
    private val _eventFlow= MutableSharedFlow<UiEvent>()
    val eventFlow=_eventFlow.asSharedFlow()
    sealed class UiEvent{
        data class ShowToast(val message:String):UiEvent()
    }
    init {
        playerRepository.fetchAllPlayers()
            .onEach { playerList->
               _playerDtoList.value= useCases.toDto.execute(
                    oldDtoList = _playerDtoList.value,
                    playerList = playerList
                )
            }.launchIn(viewModelScope)
        scoreRepository.getScore(1).onEach { score-> _score.value=score
        _createPlayerSheet.value=_createPlayerSheet.value.copy(targetScore = score.maximumScore.toString())}.launchIn(viewModelScope)
        _editedMaximumScore.value=score.value.maximumScore.toString()
    }

    fun onEvent(event: MainScreenEvent)
    {
        when(event)
        {

            is MainScreenEvent.OnAddPlayerDismiss -> {
                _screenState.value=_screenState.value.copy(showAddPlayerBottomSheet = false)
                _createPlayerSheet.value=CreatePlayerSheetState(targetScore = _score.value.maximumScore.toString())
            }

            is MainScreenEvent.OnPlayerAvatarUriResult -> {_createPlayerSheet.value=_createPlayerSheet.value.copy(playerAvatarUri = event.uri)}

            is MainScreenEvent.OnPlayerNameChange -> { _createPlayerSheet.value=_createPlayerSheet.value.copy(playerName = event.name)}

            is MainScreenEvent.OnScoreChange -> {_createPlayerSheet.value=_createPlayerSheet.value.copy(targetScore = event.score)}

            is MainScreenEvent.OnSaveNewPlayer -> {
                viewModelScope.launch {
                    useCases.saveNewPlayer.execute(
                        createPlayerSheetState = _createPlayerSheet.value.copy(),
                        onNameErr = {e->
                            _createPlayerSheet.value=_createPlayerSheet.value.copy(isNameErr = true, isScoreErr = false)
                            launch { _eventFlow.emit(UiEvent.ShowToast(e)) }
                            },
                        onScoreErr = {e->
                            _createPlayerSheet.value=_createPlayerSheet.value.copy(isNameErr = false, isScoreErr = true)
                            launch { _eventFlow.emit(UiEvent.ShowToast(e)) }
                        },
                        onSuccess = {lastPlayer->
                            _screenState.value=_screenState.value.copy(showAddPlayerBottomSheet = false)
                            _playerDtoList.value=_playerDtoList.value.map { dto->
                                if (lastPlayer?.id==dto.id) dto.copy(profilePicture = _createPlayerSheet.value.playerAvatarUri) else dto
                            }
                            _createPlayerSheet.value=CreatePlayerSheetState(targetScore = _score.value.maximumScore.toString())                        },
                        playerRepository = playerRepository,
                        scoreRepository = scoreRepository,
                    )
                }
            }
            MainScreenEvent.OnFabClick -> {_screenState.value=_screenState.value.copy(showAddPlayerBottomSheet = true)}
            is MainScreenEvent.OnEditPlayer -> {
                if (_editedPlayerDto.value.isEditing) {
                        viewModelScope.launch { _eventFlow.emit(UiEvent.ShowToast(message = "You can only edit one player at a time."))}
                }
                else{
                    _playerDtoList.value=_playerDtoList.value.map { player-> if (player.id==event.id) player.copy(isEditing = true) else player }
                    _editedPlayerDto.value=_playerDtoList.value.find { player-> player.id==event.id }?:PlayerDto()
                }
            }

            MainScreenEvent.OnCancelEdit -> {
                _editedPlayerDto.value= PlayerDto()
                _playerDtoList.value=playerDtoList.value.map { player-> if (player.isEditing) player.copy(isEditing = false) else player }
            }

            is MainScreenEvent.OnEditedPlayerAvatarUriResult ->{
                _editedPlayerDto.value=_editedPlayerDto.value.copy(profilePicture = event.uri)
            }

            is MainScreenEvent.OnEditedPlayerNameChange -> {
                _editedPlayerDto.value=_editedPlayerDto.value.copy(name = event.name)
            }

            MainScreenEvent.OnSaveEdit ->{
                viewModelScope.launch {
                    useCases.updateUser.execute(
                        playerDto = _editedPlayerDto.value,
                        playerRepository = playerRepository,
                        onFailure = {e->launch { _eventFlow.emit(UiEvent.ShowToast(e)) }},
                        onSuccess = {
                            _playerDtoList.value=_playerDtoList.value.map {
                                dto-> if(dto.id==_editedPlayerDto.value.id) _editedPlayerDto.value.copy(isEditing = false) else dto}
                            _editedPlayerDto.value=PlayerDto()
                        }
                        )
                }
            }

            is MainScreenEvent.OnDeletePlayerClick -> {
                _deletedPlayer.value=_playerDtoList.value.find { dto-> dto.id==event.id }?:PlayerDto()
                _screenState.value=_screenState.value.copy(showDeletePlayerAlertDialog = true)
            }

            MainScreenEvent.OnDeleteDismiss -> {
                _deletedPlayer.value= PlayerDto()
                _screenState.value=_screenState.value.copy(showDeletePlayerAlertDialog = false)
            }

            MainScreenEvent.OnDeleteConfirm -> {
                viewModelScope.launch { playerRepository.deletePlayer(_deletedPlayer.value.toPlayer())}
               onEvent(MainScreenEvent.OnDeleteDismiss)
            }

            is MainScreenEvent.OnScoreSettingsClick -> {
                _playerScoreEditedDto.value=_playerDtoList.value.find { dto-> dto.id== event.id}?:PlayerDto()
                _playerDtoList.value=_playerDtoList.value.map { dto-> if (dto.id==event.id) dto.copy(isScoreDropdownMenuExpanded = true) else dto }
            }

            MainScreenEvent.OnDismissScoreDropdownMenu -> {
                _playerDtoList.value=_playerDtoList.value.map { dto-> if (dto.isScoreDropdownMenuExpanded) dto.copy(isScoreDropdownMenuExpanded = false) else dto }
            }

            MainScreenEvent.OnAddPointsClick ->{
                _screenState.value=_screenState.value.copy(showAddPointsAlertDialog = true)
                onEvent(MainScreenEvent.OnDismissScoreDropdownMenu)
            }

            is MainScreenEvent.OnAddPointsValueChange -> {
                _earnedPoints.value=event.points
            }

            MainScreenEvent.OnAddPointsAdDismiss -> {
                _screenState.value=_screenState.value.copy(showAddPointsAlertDialog = false)
                _earnedPoints.value=""
                _playerScoreEditedDto.value= PlayerDto()

            }

            MainScreenEvent.OnAddPointsAdConfirm -> {
                viewModelScope.launch {useCases.addEarnedPoints.execute(
                    editedPlayer = _playerScoreEditedDto.value,
                    earnedPoints = _earnedPoints.value,
                    onSuccess = {onEvent(MainScreenEvent.OnAddPointsAdDismiss)}
                ) }
            }

            MainScreenEvent.OnDismissResetPlayerScoreAd -> {
                _screenState.value=_screenState.value.copy(showResetPlayerScoreAlertDialog = false)
                _playerScoreEditedDto.value=PlayerDto()
            }

            is MainScreenEvent.OnResetPlayerScore -> {
                _playerDtoList.value=_playerDtoList.value.map { dto-> if(dto.id==_playerScoreEditedDto.value.id) dto.copy(isScoreDropdownMenuExpanded = false) else dto }
                _screenState.value=_screenState.value.copy(showResetPlayerScoreAlertDialog = true)
            }

            MainScreenEvent.OnConfirmResetPlayerScore -> {
                viewModelScope.launch { playerRepository.updatePlayer(_playerScoreEditedDto.value.copy(score = 0, gamesWon = 0).toPlayer()) }
                onEvent(MainScreenEvent.OnDismissResetPlayerScoreAd)
            }

            is MainScreenEvent.OnFinishGameClick -> {
                _screenState.value=_screenState.value.copy(showFinishGameAlertDialog = true)
                 _winner.value=_playerDtoList.value.find { dto-> dto.id==event.winnerId }?:PlayerDto()

            }

            is MainScreenEvent.OnDismissSettingsDropdownMenu -> {
                _screenState.value=_screenState.value.copy(showSettingsDropdownMenu = false)
            }
           is MainScreenEvent.OnExpandSettingsDropdownMenu -> {
                _screenState.value=_screenState.value.copy(showSettingsDropdownMenu = true)
            }

            is MainScreenEvent.OnTotalResetClick -> {
                _screenState.value=_screenState.value.copy(showTotalResetAlertDialog = true, showSettingsDropdownMenu = false)
            }

            is MainScreenEvent.OnTotalResetAdDismiss -> {
                _screenState.value=_screenState.value.copy(showTotalResetAlertDialog = false)
            }

            is MainScreenEvent.OnTotalResetConfirm -> {
                viewModelScope.launch {
                    _playerDtoList.value.forEach { dto->
                        playerRepository.updatePlayer(dto.copy(gamesWon = 0, score = 0).toPlayer())
                    }
                }
                onEvent(MainScreenEvent.OnTotalResetAdDismiss)
            }

            is MainScreenEvent.OnConfirmMaximumScoreEdit -> {
                if (_editedMaximumScore.value.isEmpty())
                {
                    viewModelScope.launch { _eventFlow.emit(UiEvent.ShowToast("Maximum score cannot be empty!")) }
                }
                else{
                    viewModelScope.launch { scoreRepository.upsertScore(_score.value.copy(maximumScore = _editedMaximumScore.value.toInt())) }
                    onEvent(MainScreenEvent.OnDismissEditMaximumScoreAd)
                }
            }
           is  MainScreenEvent.OnDismissEditMaximumScoreAd -> {
               _editedMaximumScore.value=_score.value.maximumScore.toString()
               _screenState.value=_screenState.value.copy(showEditWinningScoreAlertDialog = false)
           }
            is MainScreenEvent.OnMaximumScoreEditValueChange -> {
                _editedMaximumScore.value=event.newScore
            }

            is MainScreenEvent.OnShowMaximumScoreAd -> {
                _editedMaximumScore.value=_score.value.maximumScore.toString()
                _screenState.value=_screenState.value.copy(showEditWinningScoreAlertDialog = true, showSettingsDropdownMenu = false)

            }

            is MainScreenEvent.OnConfirmFinishGameAd -> {
                val champ=_winner.value
                                viewModelScope.launch {
                 playerDtoList.value.forEach { dto-> playerRepository.updatePlayer(dto.copy(score = 0).toPlayer())
                }
                   playerRepository.updatePlayer(champ.copy(gamesWon = champ.gamesWon+1, score = 0).toPlayer())
               }
                onEvent(MainScreenEvent.OnDismissFinishGameAd)
            }
            MainScreenEvent.OnDismissFinishGameAd -> {
                _screenState.value=_screenState.value.copy(showFinishGameAlertDialog = false)
                _winner.value= PlayerDto()
            }
        }
    }
}