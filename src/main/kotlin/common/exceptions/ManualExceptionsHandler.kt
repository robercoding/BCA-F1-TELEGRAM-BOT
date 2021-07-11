package common.utils

class CalendarRaceYearNotFound(val season: Int) : Exception()
class RaceDetailsNotFound : Exception()

class ChatShouldExist : Exception()
class ChatAlreadyExist : Exception()
class NotifyRaceWeekShouldExist : Exception()