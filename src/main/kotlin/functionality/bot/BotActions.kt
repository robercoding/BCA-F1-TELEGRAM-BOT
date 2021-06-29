package functionality.bot

import com.github.kotlintelegrambot.Bot
import com.github.kotlintelegrambot.entities.ChatId

class BotActions(private val bot: Bot) {

    fun sendMessage(chatId: ChatId, message: String) {
        bot.sendMessage(chatId, message)
    }

    fun sendPhotoByUrl(chatId: ChatId, urlPhoto: String, caption: String = "") {
        bot.sendPhoto(
                chatId,
                urlPhoto,
                caption
        )
    }
}