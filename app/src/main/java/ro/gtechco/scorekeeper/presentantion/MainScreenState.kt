package ro.gtechco.scorekeeper.presentantion

data class MainScreenState (
    val showAddPlayerBottomSheet:Boolean=false,
    val showDeletePlayerAlertDialog:Boolean=false,
    val showAddPointsAlertDialog:Boolean=false,
    val showResetPlayerScoreAlertDialog:Boolean=false,
    val showSettingsDropdownMenu:Boolean=false,
    val showTotalResetAlertDialog:Boolean=false,
    val showEditWinningScoreAlertDialog:Boolean=false
)