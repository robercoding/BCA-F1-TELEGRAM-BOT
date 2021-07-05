package functionality.notify

import com.github.kotlintelegrambot.Bot
import com.github.kotlintelegrambot.entities.ChatId
import help.DateUtils
import model.dto.RaceDTO

/**
 * TODO maybe we should convert this in the future to a class and then pass languageHelper as a parameter?
 * Don't want to get 4 parameters in every function :D
 */
object NotifyRace {

    fun notifyRaceWeek(bot: Bot, chatId: ChatId, race: RaceDTO) {
        var captionSprintQualifying = ""
        if (race.isSprintQualifying) {
            captionSprintQualifying = "\n Sprint Qualifying: ${DateUtils.formatToTimezoneGMT(race.dateQualifying)}"
        }
        val caption = ""
//        val caption = "RACE WEEK!!" +
//                "\n\nA continuación detalles de la siguiente carrera:" +
//                "\n País: ${race.country}" +
//                "\n Circuito: ${race.nameCircuit}" +
//                captionSprintQualifying +
//                "\n Clasificación: ${DateUtils.formatToTimezoneGMT(DateUtils.convertToDateFromLocalDateTime(race.dateQualifying))}" +
//                "\n Carrera: ${DateUtils.formatToTimezoneGMT(DateUtils.convertToDateFromLocalDateTime(race.dateRace))}"

//        bot.sendPhoto(
//                chatId = chatId,
//                race.layoutCircuitUrl,
//                caption
//        )
        bot.sendPhoto(
            chatId = chatId,
            "",
            caption
        )
    }
}