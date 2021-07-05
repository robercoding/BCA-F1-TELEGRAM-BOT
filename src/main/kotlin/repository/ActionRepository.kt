package repository

import com.github.kotlintelegrambot.entities.ChatId
import model.dto.CalendarRaceYearDTO
import java.util.*

class ActionRepository {
    fun saveRemindRaceWeek(timerTask: TimerTask, chatId: ChatId): Boolean {
        //TODO implement db to save Timer with reminder race week and return if it was saved or not
        //return db.saveReminderRaceWeek(timerTask, chatId)
        return true
    }

    fun getRaceCalendar(): CalendarRaceYearDTO {
        //TODO implement db to get raceCalendar saved in database
        //return db.getRaceCalendar()
        return CalendarRaceYearDTO(1, 2021, listOf(), 0)
    }
}