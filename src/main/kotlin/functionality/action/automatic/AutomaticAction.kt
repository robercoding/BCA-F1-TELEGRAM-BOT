package functionality.action.automatic

import com.github.kotlintelegrambot.Bot
import com.github.kotlintelegrambot.entities.ChatId
import common.utils.Answer
import common.utils.BotOutcome
import common.utils.FormatCaption
import functionality.action.Action
import functionality.notify.NotifyRace
import functionality.schedule.ScheduleUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import model.AlarmRaceWeek
import model.dto.ChatTimerTaskDTO
import repository.ActionRepository

//TODO rename to ScheduleActions to make it more clear?
class AutomaticAction(
    actionRepository: ActionRepository
) : Action(actionRepository) {

    private val listTimers = mutableListOf<ChatTimerTaskDTO>()

    suspend fun setAlarmRaceWeek(bot: Bot, chatId: ChatId, alarmRaceWeek: AlarmRaceWeek): BotOutcome {
        return withContext(Dispatchers.IO) {
            val timerTaskAndAlarmSettled = ScheduleUtils.getTimerTask(alarmRaceWeek) {
                val answer = isRaceWeek()

                if (answer is Answer.Yes) {
                    NotifyRace.notifyRaceWeek(bot, chatId, answer.data!!)
                }
            }

            val chatTimerTask = ChatTimerTaskDTO((chatId as ChatId.Id).id, timerTaskAndAlarmSettled.timerTask)
            listTimers.add(chatTimerTask)
//            actionRepository.saveRemindRaceWeek(timerTask, chatId) //This should save into the DB, day, hour, min

            val timerCaption = FormatCaption.formatSetAlarm(timerTaskAndAlarmSettled.alarmSettled, "Europe/Madrid")
            BotOutcome.SendPhotoByUrl(
                timerCaption,
                "https://as.com/tikitakas/imagenes/2017/09/04/portada/1504556804_289726_1504557895_sumario_grande.jpg"
            )
        }
    }
}