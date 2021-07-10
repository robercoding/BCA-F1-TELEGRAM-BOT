package model.dto

import java.util.*

data class ChatTimerTaskDTO(
    val chatId: Long,
    var timerTask: TimerTask
)