package presentation.functionality.action.automatic

import com.github.kotlintelegrambot.Bot
import common.outcome.BotOutcome
import common.utils.FormatCaption
import domain.model.Chat
import domain.model.NotifyRaceWeek
import domain.usecase.NotifyRaceWeekUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import presentation.functionality.action.Action

//TODO Add Another for RemoteScheduleAction so it doesn't mix with this?
//TODO rename to LocalScheduleActions to make it more clear?
class AutomaticAction(
    val notifyRaceWeekUseCase: NotifyRaceWeekUseCase
) : Action() {

    suspend fun setAlarmRaceWeek(bot: Bot, chat: Chat, notifyRaceWeek: NotifyRaceWeek): BotOutcome {
        return withContext(Dispatchers.IO) {
            val notifyRaceWeekSettled = notifyRaceWeekUseCase.setOnNotifyRaceWeek(bot, chat, notifyRaceWeek)

            val timerCaption =
                FormatCaption.formatSetNotifyRaceWeek(notifyRaceWeekSettled)
            BotOutcome.SendPhotoByUrl(
                timerCaption,
                "https://as.com/tikitakas/imagenes/2017/09/04/portada/1504556804_289726_1504557895_sumario_grande.jpg"
            )
        }
    }

    suspend fun unsetNotifyRaceWeek(chat: Chat): BotOutcome {
        return withContext(Dispatchers.IO) {
            println("enter unsetonnotifyraceweek use case")
            val notifyRaceWeek = notifyRaceWeekUseCase.unsetOnNotifyRaceWeek(chat)
            println("after return")

            val timerCaption =
                FormatCaption.formatUnsetNotifyRaceWeek(notifyRaceWeek)
            BotOutcome.SendPhotoByUrl(
                timerCaption,
                "https://as.com/tikitakas/imagenes/2017/09/04/portada/1504556804_289726_1504557895_sumario_grande.jpg"
            )
        }
    }
}