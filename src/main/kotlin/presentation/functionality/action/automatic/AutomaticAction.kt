package presentation.functionality.action.automatic

import com.github.kotlintelegrambot.Bot
import com.github.kotlintelegrambot.entities.ChatId
import common.utils.Answer
import common.utils.BotOutcome
import common.utils.FormatCaption
import data.repository.ActionRepository
import domain.model.NotifyRaceWeek
import domain.model.dto.ChatTimerTask
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import presentation.functionality.action.Action
import presentation.functionality.notify.NotifyRace
import presentation.functionality.schedule.ScheduleUtils

//TODO rename to ScheduleActions to make it more clear?
class AutomaticAction(
    actionRepository: ActionRepository
) : Action(actionRepository) {

    private val listTimers = mutableListOf<ChatTimerTask>()

    suspend fun setAlarmRaceWeek(bot: Bot, chatId: ChatId, notifyRaceWeek: NotifyRaceWeek): BotOutcome {
        return withContext(Dispatchers.IO) {

            actionRepository.saveRemindRaceWeek(notifyRaceWeek, chatId) //This should save into the DB, day, hour, min

            val timerTaskAndAlarmSettled = ScheduleUtils.getTimerTask(notifyRaceWeek) {
                val answer = isRaceWeek()

                if (answer is Answer.Yes) {
                    NotifyRace.notifyRaceWeek(bot, chatId, answer.data!!)
                }
            }

            val chatTimerTask = ChatTimerTask((chatId as ChatId.Id).id, timerTaskAndAlarmSettled.timerTask)
            listTimers.add(chatTimerTask)

            val timerCaption =
                FormatCaption.formatSetAlarm(timerTaskAndAlarmSettled.notifyRaceWeekSettled, "Europe/Madrid")
            BotOutcome.SendPhotoByUrl(
                timerCaption,
                "https://as.com/tikitakas/imagenes/2017/09/04/portada/1504556804_289726_1504557895_sumario_grande.jpg"
            )
        }
    }
}