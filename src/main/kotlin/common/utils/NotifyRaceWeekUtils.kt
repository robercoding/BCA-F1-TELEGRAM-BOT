package common.utils

import domain.model.NotifyRaceWeek

object NotifyRaceWeekUtils {

    fun getNotifyRaceWeekTime(text: String): NotifyRaceWeek {
        val alarmRaceWeekValues = text.toLowerCase()
        val alarmRaceWeek = NotifyRaceWeek()
        val indexDay = alarmRaceWeekValues.indexOfFirst { it == 'd' }
        val indexHour = alarmRaceWeekValues.indexOfFirst { it == 'h' }
        val indexMinute = alarmRaceWeekValues.indexOfFirst { it == 'm' }


        if (alarmRaceWeekValues.contains('d')) {
            if (alarmRaceWeekValues.contains('h') && indexDay < indexHour) {
                val subSequenceOne = alarmRaceWeekValues.indexOfFirst { it == 'd' }
                val subSequenceTwo = alarmRaceWeekValues.indexOfFirst { it == 'h' }
                val textDay = alarmRaceWeekValues.substring(subSequenceOne, subSequenceTwo)
                val hasAnyDigit = textDay.any { it.isDigit() }
                if (hasAnyDigit) {
                    alarmRaceWeek.day = textDay.getDigits()!!.toInt()
                }
            } else if (alarmRaceWeekValues.contains('m') && indexDay < indexMinute) {
                val subSequenceOne = alarmRaceWeekValues.indexOfFirst { it == 'd' }

                val subSequenceTwo = alarmRaceWeekValues.indexOfFirst { it == 'm' }
                val textDay = alarmRaceWeekValues.substring(subSequenceOne, subSequenceTwo)
                val hasAnyDigit = textDay.any { it.isDigit() }
                if (hasAnyDigit) {
                    alarmRaceWeek.day = textDay.getDigits()!!.toInt()
                }
            } else {
                val subSequenceOne = alarmRaceWeekValues.indexOfFirst { it == 'd' }
                val subSequenceTwo = alarmRaceWeekValues.lastIndex + 1
                val textDay = alarmRaceWeekValues.substring(subSequenceOne, subSequenceTwo)
                val hasAnyDigit = textDay.any { it.isDigit() }
                if (hasAnyDigit) {
                    alarmRaceWeek.day = textDay.getDigits()!!.toInt()
                }
            }

        }

        if (alarmRaceWeekValues.contains('h')) {
            if (alarmRaceWeekValues.contains('d') && indexHour < indexDay) {
                val subSequenceOne = alarmRaceWeekValues.indexOfFirst { it == 'h' }
                val subSequenceTwo = alarmRaceWeekValues.indexOfFirst { it == 'd' }
                val textDay = alarmRaceWeekValues.substring(subSequenceOne, subSequenceTwo)
                val hasAnyDigit = textDay.any { it.isDigit() }
                if (hasAnyDigit) {
                    alarmRaceWeek.hour = textDay.getDigits()!!.toInt()
                }
            } else if (alarmRaceWeekValues.contains('m') && indexHour < indexMinute) {
                val subSequenceOne = alarmRaceWeekValues.indexOfFirst { it == 'h' }
                val subSequenceTwo = alarmRaceWeekValues.indexOfFirst { it == 'm' }
                val textDay = alarmRaceWeekValues.substring(subSequenceOne, subSequenceTwo)
                val hasAnyDigit = textDay.any { it.isDigit() }
                if (hasAnyDigit) {
                    alarmRaceWeek.hour = textDay.getDigits()!!.toInt()
                }
            } else {
                val subSequenceOne = alarmRaceWeekValues.indexOfFirst { it == 'h' }
                val subSequenceTwo = alarmRaceWeekValues.lastIndex + 1
                val textHour = alarmRaceWeekValues.substring(subSequenceOne, subSequenceTwo)
                val hasAnyDigit = textHour.any { it.isDigit() }
                if (hasAnyDigit) {
                    alarmRaceWeek.hour = textHour.getDigits()!!.toInt()
                }
            }
        }


        if (alarmRaceWeekValues.contains('m')) {
            if (alarmRaceWeekValues.contains('d') && indexMinute < indexDay) {
                val subSequenceOne = alarmRaceWeekValues.indexOfFirst { it == 'm' }
                val subSequenceTwo = alarmRaceWeekValues.indexOfFirst { it == 'd' }
                val textDay = alarmRaceWeekValues.substring(subSequenceOne, subSequenceTwo)
                val hasAnyDigit = textDay.any { it.isDigit() }
                if (hasAnyDigit) {
                    alarmRaceWeek.minute = textDay.getDigits()!!.toInt()
                }
            } else if (alarmRaceWeekValues.contains('h') && indexMinute < indexHour) {
                val subSequenceOne = alarmRaceWeekValues.indexOfFirst { it == 'm' }
                val subSequenceTwo = alarmRaceWeekValues.indexOfFirst { it == 'h' }
                val textDay = alarmRaceWeekValues.substring(subSequenceOne, subSequenceTwo)
                val hasAnyDigit = textDay.any { it.isDigit() }
                if (hasAnyDigit) {
                    alarmRaceWeek.minute = textDay.getDigits()!!.toInt()
                }
            } else {
                val subSequenceOne = alarmRaceWeekValues.indexOfFirst { it == 'm' }
                val subSequenceTwo = alarmRaceWeekValues.lastIndex + 1
                val textHour = alarmRaceWeekValues.substring(subSequenceOne, subSequenceTwo)
                val hasAnyDigit = textHour.any { it.isDigit() }
                if (hasAnyDigit) {
                    alarmRaceWeek.minute = textHour.getDigits()!!.toInt()
                }
            }
        }


        println(alarmRaceWeek)
        return alarmRaceWeek
    }
}