package ro.gtechco.scorekeeper.domain.use_cases

data class UseCases(
    val toDto: ToDto,
    val saveNewPlayer: SaveNewPlayer,
    val updateUser: UpdateUser,
    val addEarnedPoints: AddEarnedPoints
)