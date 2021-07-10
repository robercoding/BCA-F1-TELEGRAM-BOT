package repository

import common.utils.AlarmShouldExist
import common.utils.ChatShouldExist
import model.AlarmEntity
import model.AlarmTable
import model.ChatEntity
import org.jetbrains.exposed.sql.update

const val ALARM_RW_DEFAULT_DAY = 4
const val ALARM_RW_DEFAULT_HOUR = 12
const val ALARM_RW_DEFAULT_MINUTE = 0

class AlarmRepository {

    /***
     * Set alarm for the first time, setting the provided values or defaults ones.
     ***/
    fun setAlarmFirstTime(chatId: Long, day : Int? = null, hour: Int? = null, minute: Int? = null){
        val chat = ChatEntity.findById(chatId) ?: throw ChatShouldExist()

        val alarm = AlarmEntity.findById(chat.alarm.id) ?: throw AlarmShouldExist()
        alarm.isActivated = false
        alarm.day = ALARM_RW_DEFAULT_DAY
        alarm.hour = ALARM_RW_DEFAULT_HOUR
        alarm.minute = ALARM_RW_DEFAULT_MINUTE

        update(alarm)
    }

    /***
     * Update alarm with provided values or if null then the already settled values.
     ***/
    private fun updateAlarm(chatId: Long, isActivated: Boolean, day : Int? = null, hour: Int? = null, minute: Int? = null){
        val chat = ChatEntity.findById(chatId) ?: throw ChatShouldExist()

        val alarm = AlarmEntity.findById(chat.alarm.id) ?: throw AlarmShouldExist()
        alarm.isActivated = isActivated
        alarm.day = day ?: alarm.day
        alarm.hour = hour ?: alarm.hour
        alarm.minute = minute ?: alarm.minute

        update(alarm)
    }

    private fun update(alarm: AlarmEntity){
        AlarmTable.update({AlarmTable.id eq alarm.id}) {
            it[isActivated] = alarm.isActivated
            it[day] = alarm.day
            it[hour] = alarm.hour
            it[minute] = alarm.minute
        }
    }
}