package ro.gtechco.scorekeeper.presentantion

import android.net.Uri

sealed class MainScreenEvent {
    data object OnAddPlayerDismiss:MainScreenEvent()
    data object OnSaveNewPlayer:MainScreenEvent()
    data object OnFabClick:MainScreenEvent()
    data object OnCancelEdit:MainScreenEvent()
    data object OnSaveEdit:MainScreenEvent()
    data object OnDeleteDismiss:MainScreenEvent()
    data object OnDeleteConfirm:MainScreenEvent()
    data object OnDismissScoreDropdownMenu:MainScreenEvent()
    data object OnAddPointsClick:MainScreenEvent()
    data object OnAddPointsAdDismiss:MainScreenEvent()
    data object OnAddPointsAdConfirm:MainScreenEvent()
    data object OnResetPlayerScore:MainScreenEvent()
    data object OnDismissResetPlayerScoreAd:MainScreenEvent()
    data object OnConfirmResetPlayerScore:MainScreenEvent()
    data object OnExpandSettingsDropdownMenu:MainScreenEvent()
    data object OnDismissSettingsDropdownMenu:MainScreenEvent()
    data object OnTotalResetClick:MainScreenEvent()
    data object OnTotalResetAdDismiss:MainScreenEvent()
    data object OnTotalResetConfirm:MainScreenEvent()
    data object OnDismissEditMaximumScoreAd:MainScreenEvent()
    data object OnConfirmMaximumScoreEdit:MainScreenEvent()
    data object OnShowMaximumScoreAd:MainScreenEvent()

    data class OnPlayerAvatarUriResult(val uri: Uri?):MainScreenEvent()
    data class OnPlayerNameChange(val name:String):MainScreenEvent()
    data class OnScoreChange(val score:String):MainScreenEvent()
    data class OnEditPlayer(val id:Int?):MainScreenEvent()
    data class OnEditedPlayerAvatarUriResult(val uri:Uri?):MainScreenEvent()
    data class OnEditedPlayerNameChange(val name:String):MainScreenEvent()
    data class OnDeletePlayerClick(val id:Int?):MainScreenEvent()
    data class OnScoreSettingsClick(val id: Int?):MainScreenEvent()
    data class OnAddPointsValueChange(val points:String):MainScreenEvent()
    data class OnFinishGameClick(val winnerId:Int?):MainScreenEvent()
    data class OnMaximumScoreEditValueChange(val newScore:String):MainScreenEvent()



}