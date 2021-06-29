package functionality.notify

import com.github.kotlintelegrambot.Bot
import com.github.kotlintelegrambot.entities.ChatId
import help.DateUtils
import model.Race

/**
 * TODO maybe we should convert this in the future to a class and then pass languageHelper as a parameter?
 * Don't want to get 4 parameters in every function :D
 */
object NotifyRace {

    fun notifyRaceWeek(bot: Bot, chatId: ChatId, race: Race) {
        var captionSprintQualifying = ""
        if (race.isSprintQualifying) {
            captionSprintQualifying = "\n Sprint Qualifying: ${DateUtils.formatToTimezoneGMT(race.dateSprintQualifying)}"
        }
        val caption = "RACE WEEK!!" +
                "\n\nA continuación detalles de la siguiente carrera:" +
                "\n País: ${race.country}" +
                "\n Circuito: ${race.nameCircuit}" +
                captionSprintQualifying +
                "\n Clasificación: ${DateUtils.formatToTimezoneGMT(race.dateQualifying)}" +
                "\n Carrera: ${DateUtils.formatToTimezoneGMT(race.dateRace)}"

        bot.sendPhoto(
                chatId = chatId,
                race.layoutCircuitUrl,
                caption
        )
    }
}