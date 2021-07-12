package domain.usecase

import com.github.kotlintelegrambot.Bot
import common.outcome.Answer
import common.toChatId
import common.utils.ChatNotifyRaceWeekDoesntExist
import common.utils.ErrorUpdatingNotifyRaceWeek
import common.utils.FormatCaption
import config.F1Config
import data.repository.chat.ChatRepository
import data.repository.notifyraceweek.NotifyRaceWeekRepository
import data.repository.race.RaceRepository
import domain.model.*
import domain.model.dto.ChatDTO
import domain.model.dto.RaceDTO
import org.jetbrains.exposed.sql.transactions.transaction
import presentation.functionality.race.RaceHelper
import presentation.functionality.schedule.ScheduleUtils
import java.util.*

class NotifyRaceWeekUseCase(
    private val notifyRaceWeekRepository: NotifyRaceWeekRepository,
    private val raceRepository: RaceRepository,
    private val chatRepository: ChatRepository
) {
    private val raceHelper = RaceHelper()
    private val mapTimers = mapOf<Long, TimerTask>()

    fun setOnNotifyRaceWeek(bot: Bot, chat: Chat, notifyRaceWeek: NotifyRaceWeek): NotifyRaceWeekSettled {
        val chatDTO = chatRepository.findById(chat.id) ?: createChatAndNotifyRaceWeek(chat, notifyRaceWeek)

        notifyRaceWeek.id = chatDTO.notifyRaceWeek.id
        val notifyRaceWeekDb = notifyRaceWeekRepository.update(notifyRaceWeek) ?: throw ErrorUpdatingNotifyRaceWeek()

        val timerTaskAndNotifyRaceWeek = scheduleNotifyRaceWeek(bot, chatDTO, notifyRaceWeekDb)
        mapTimers.toMutableMap()[chatDTO.id] = timerTaskAndNotifyRaceWeek.timerTask
        return timerTaskAndNotifyRaceWeek.notifyRaceWeekSettled
    }

    private fun createChatAndNotifyRaceWeek(chat: Chat, notifyRaceWeek: NotifyRaceWeek): ChatDTO {
        val notifyRaceWeekEntity = notifyRaceWeekRepository.saveNotifyRaceWeek(notifyRaceWeek)
        return chatRepository.saveChat(chat, notifyRaceWeekEntity)
    }

    private fun scheduleNotifyRaceWeek(
        bot: Bot,
        chat: ChatDTO,
        notifyRaceWeek: NotifyRaceWeek
    ): TimerTaskAndNotifyRaceWeek {
        return ScheduleUtils.getTimerTask(notifyRaceWeek) {
            val answer = isRaceWeek()
            if (answer is Answer.Yes) {
                notifyRaceWeek(bot, chat, answer.data)
            }
            //TODO if answer no then check if user wants to be notified when there's no race in every week check
        }
    }

    private fun isRaceWeek(): Answer<RaceDTO> {
        val races = raceRepository.getRacesBySeason(F1Config.actualSeason)

        if (raceHelper.isThisWeekRaceWeek(races)) {
            val race = raceHelper.getNextRaceWeek(races)
            return if (race != null) Answer.Yes(race) else Answer.Not(null)
        }

        return Answer.Not(null)
    }

    private fun notifyRaceWeek(bot: Bot, chat: ChatDTO, race: RaceDTO) {
        val caption = FormatCaption.formatRaceDetails(race, chat.timeZone)
        bot.sendPhoto(
            chatId = chat.toChatId(),
            "",
            caption
        )
    }

    fun unsetOnNotifyRaceWeek(chat: Chat): NotifyRaceWeekUnsettled {
        return transaction {
            val chatDTO = chatRepository.findById(chat.id) ?: throw ChatNotifyRaceWeekDoesntExist()
            val notifyRaceWeek = chatDTO.notifyRaceWeek
            notifyRaceWeek.isActivated = false
            val notifyRaceWeekDb =
                notifyRaceWeekRepository.update(notifyRaceWeek) ?: throw ErrorUpdatingNotifyRaceWeek()
            mapTimers.toMutableMap().remove(chatDTO.id)

            return@transaction NotifyRaceWeekUnsettled(
                notifyRaceWeekDb,
                timeZone = TimeZone.getTimeZone(chatDTO.timeZone)
            )
        }
    }
}