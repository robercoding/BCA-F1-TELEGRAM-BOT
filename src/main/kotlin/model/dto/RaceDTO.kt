package model.dto

import java.util.*

data class RaceDTO(
    var id: Int = -1,
    val season: Int,
    val grandPrix: GrandPrixDTO,
    val weekRace: Int,
    val dateQualifying: Date,
    val dateRace: Date,
    val isSprintQualifying: Boolean,
    val dateSprintQuailifying: Date
)