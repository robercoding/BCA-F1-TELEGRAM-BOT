package common.utils

import com.github.kotlintelegrambot.entities.ChatId

object TelegramUtils {

    fun convertToChatId(chatId: Long) : ChatId {
        return ChatId.fromId(chatId)
    }
}