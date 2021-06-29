package repository

import com.github.kotlintelegrambot.entities.ChatId
import model.RaceCalendar
import java.util.*

class ActionRepository {
    fun saveRemindRaceWeek(timerTask: TimerTask, chatId: ChatId): Boolean {
        //TODO implement db to save Timer with reminder race week and return if it was saved or not
        //return db.saveReminderRaceWeek(timerTask, chatId)
        return true
    }

    fun getRaceCalendar(): RaceCalendar {
        //TODO implement db to get raceCalendar saved in database
        //return db.getRaceCalendar()
        return RaceCalendar(listOf())
    }
}