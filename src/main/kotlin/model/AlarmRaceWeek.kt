package model

data class AlarmRaceWeek(
    var isActivated : Boolean = false,
    var day : Int? = null,
    var hour : Int? = null,
    var minute : Int? = null
)
