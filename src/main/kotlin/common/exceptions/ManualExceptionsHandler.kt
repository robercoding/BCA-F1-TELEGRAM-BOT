package common.utils

class CalendarRaceYearNotFound(val season: Int) : Exception()
class RaceDetailsNotFound : Exception()

class ChatNotifyRaceWeekDoesntExist : Exception()

class DayOfWeekUnsupportedNumber(val number: Int) : Exception()