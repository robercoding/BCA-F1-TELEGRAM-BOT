package functionality.action

import com.github.kotlintelegrambot.entities.ChatId

sealed class ManualActionEvent(open val chatId: ChatId) {
    data class GetNextRace(override val chatId: ChatId) : ManualActionEvent(chatId)

}