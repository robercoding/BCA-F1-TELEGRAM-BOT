package functionality.action

import com.github.kotlintelegrambot.Bot
import com.github.kotlintelegrambot.entities.ChatId
import functionality.notify.NotifyRace
import functionality.schedule.ScheduleUtils
import repository.ActionRepository
import utils.Answer

//TODO rename to ScheduleActions to make it more clear?
class AutomaticAction(
    actionRepository: ActionRepository
) : Action(actionRepository) {

    fun activateScheduleThursday(bot: Bot, chatId: ChatId) {
        val timerTask = ScheduleUtils.getTimerTask {
            val answer = isRaceWeek()
            if (answer is Answer.Yes) {
                NotifyRace.notifyRaceWeek(bot, chatId, answer.data!!)
            }
        }

//        val chatIdLong = (chatId as ChatId.Id).id

        actionRepository.saveRemindRaceWeek(timerTask, chatId) //This should save into the DB
//        timers.add(Pair(chatIdLong, timer))
//        bot.sendMessage(chatId, "Perfecto, cada jueves comprobaré si hay carrera esa misma semana.")
    }
}