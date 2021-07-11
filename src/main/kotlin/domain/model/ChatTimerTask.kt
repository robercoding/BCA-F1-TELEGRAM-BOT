package domain.model

import java.util.*

data class ChatTimerTask(
    val chatId: Long,
    var timerTask: TimerTask
)