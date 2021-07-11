package domain.model.dto

import java.util.*

data class ChatTimerTask(
    val chatId: Long,
    var timerTask: TimerTask
)