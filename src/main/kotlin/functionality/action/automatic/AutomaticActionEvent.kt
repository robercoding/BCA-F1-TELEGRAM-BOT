package functionality.action

import com.github.kotlintelegrambot.entities.ChatId

sealed class AutomaticActionEvent(open val chatId: ChatId) {
    data class RemindRaceWeek(override val chatId: ChatId) : AutomaticActionEvent(chatId)
    data class DisableRemindRaceWeek(override val chatId: ChatId) : AutomaticActionEvent(chatId)
}
