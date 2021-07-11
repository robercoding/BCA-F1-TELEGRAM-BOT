package functionality.notify

import com.github.kotlintelegrambot.Bot
import com.github.kotlintelegrambot.entities.ChatId
import common.utils.FormatCaption
import model.dto.RaceDTO

/**
 * TODO maybe we should convert this in the future to a class and then pass languageHelper as a parameter?
 * Don't want to get 4 parameters in every function :D
 */
object NotifyRace {

    fun notifyRaceWeek(bot: Bot, chatId: ChatId, race: RaceDTO) {
        val caption = FormatCaption.formatRaceDetails(race, "Europe/Madrid")
        bot.sendPhoto(
            chatId = chatId,
            "",
            caption
        )
    }
}