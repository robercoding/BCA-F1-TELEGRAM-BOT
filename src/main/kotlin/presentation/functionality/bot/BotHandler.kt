package presentation.functionality.bot

import Idiom
import com.github.kotlintelegrambot.Bot
import com.github.kotlintelegrambot.bot
import com.github.kotlintelegrambot.dispatch
import com.github.kotlintelegrambot.dispatcher.text
import com.github.kotlintelegrambot.entities.ChatId
import com.github.kotlintelegrambot.entities.ParseMode
import common.*
import common.outcome.BotOutcome
import common.utils.*
import config.F1Config
import config.TelegramBot
import data.repository.calendarraceyear.CalendarRaceYearRepositoryImpl
import data.repository.chat.ChatRepositoryImpl
import data.repository.notifyraceweek.NotifyRaceWeekRepositoryImpl
import data.repository.race.RaceRepositoryImpl
import data.repository.service.RedditService
import domain.usecase.CalendarRaceYearUseCase
import domain.usecase.NotifyRaceWeekUseCase
import domain.usecase.RaceUseCase
import kotlinx.coroutines.*
import presentation.functionality.action.automatic.AutomaticAction
import presentation.functionality.action.automatic.AutomaticActionEvent
import presentation.functionality.action.manual.ManualAction
import presentation.functionality.action.manual.ManualActionEvent
import sendHelp
import java.util.*

class BotHandler {
    private var timers: MutableList<Pair<Long, TimerTask>> = mutableListOf()
    private lateinit var bot: Bot
    private lateinit var botActions: BotActions
    private val raceRepository = RaceRepositoryImpl()
    private val automaticAction = AutomaticAction(
        NotifyRaceWeekUseCase(
            NotifyRaceWeekRepositoryImpl(),
            raceRepository,
            ChatRepositoryImpl()
        )
    )
    private val manualAction =
        ManualAction(CalendarRaceYearUseCase(CalendarRaceYearRepositoryImpl()), RaceUseCase(raceRepository))
    val scope = CoroutineScope(SupervisorJob())

    fun startListening() {
        bot = bot {
            token = TelegramBot.TOKEN
            dispatch {
                text {
                    val chatId = message.chat.toChatId()
                    val text = message.text?.trimStartUntilCommand() ?: return@text

                    if (CommandUtils.requestRaceDetails(text)) {
                        val raceId = StringUtils.getRaceId(text)
                        handleManualActions(ManualActionEvent.GetRaceDetails(raceId = raceId, chatId = chatId))
                        return@text
                    }

                    val command = text.getCommandUntilWhiteSpace()

                    println("command: $command")
                    // TODO CONVERT THIS TO AN ENUM OR SEALED CLASS
                    when (command.toLowerCase()) {
                        "/alonso" -> botActions.sendMessage(chatId, "EL MAGIC FIAUUUUUUM ALPINEE!")
                        "/vettel" -> botActions.sendMessage(chatId, "Ferrar?? le destroz?? la vidas")
                        "/hamilton" -> botActions.sendMessage(chatId, "HAMILTON PRESSED THE MAGIC BUTTON")
                        "/nextrace" -> handleManualActions(ManualActionEvent.GetNextRace(chatId))
                        "/nr" -> handleManualActions(ManualActionEvent.GetNextRace(chatId))
                        "/autDisableReminderRaceWeek" -> handleAutomaticActions(
                            AutomaticActionEvent.DisableRemindRaceWeek(
                                chatId
                            )
                        )
                        "/autDesactivarRecordatorioSemanaCarrera" -> handleAutomaticActions(
                            AutomaticActionEvent.DisableRemindRaceWeek(
                                chatId
                            )
                        )
                        "/reddit" -> getHotPost(chatId)
                        "/calendar" -> handleManualActions(
                            ManualActionEvent.GetCalendar(
                                chatId,
                                text.getDigits()?.toInt()
                            )
                        )
                        "/help" -> bot.sendHelp(message.chat.id, Idiom.ES)
                        "/helpES" -> bot.sendHelp(message.chat.id, Idiom.ES)
                        "/helpEN" -> bot.sendHelp(message.chat.id, Idiom.EN)
                        "/chat" -> println(message.chat)
                        "/setnotifyraceweek" -> {
                            handleAutomaticActions(
                                AutomaticActionEvent.SetNotifyRaceWeek(
                                    chatId,
                                    message.chat.toChat(),
                                    text.trimStartUntilCommand().removeCommand()
                                )
                            )
                        }
                        "/unsetnotifyraceweek" -> {
                            println("entrar unset")
                            handleAutomaticActions(
                                AutomaticActionEvent.UnsetNotifyRaceWeek(
                                    chatId,
                                    message.chat.toChat()
                                )
                            )
                        }
                    }
                }
            }
        }
        botActions = BotActions(bot)
        bot.startPolling()
    }

    private fun handleManualActions(manualActionEvent: ManualActionEvent) {
        val chatId: ChatId = manualActionEvent.chatId
        val handler = CoroutineExceptionHandler { coroutineContext, throwable ->
            when (throwable) {
                is CalendarRaceYearNotFound -> bot.sendMessage(
                    chatId,
                    "Sorry we couldn't find a calendar for the season ${throwable.season}."
                )
                is RaceDetailsNotFound -> bot.sendMessage(chatId, "Sorry, race details are not available")
                else -> bot.sendMessage(
                    chatId,
                    "Sorry, something went wrong, if problem still persist contact me through command /error 'explain here what is the error' "
                )
            }
        }

        scope.launch(handler) {
            when (manualActionEvent) {
                is ManualActionEvent.GetNextRace -> {

                }

                is ManualActionEvent.GetCalendar -> {
                    val botSendMessage = manualAction.getCalendarRace(
                        manualActionEvent.season ?: F1Config.actualSeason
                    ) as BotOutcome.SendMessage
                    bot.sendMessage(chatId, botSendMessage.message, ParseMode.MARKDOWN_V2)

                }

                is ManualActionEvent.GetRaceDetails -> {
                    val botPhotoByUrl =
                        manualAction.getRaceDetails(manualActionEvent.raceId) as BotOutcome.SendPhotoByUrl
                    bot.sendPhoto(chatId, botPhotoByUrl.photoUrl, botPhotoByUrl.message, ParseMode.MARKDOWN_V2)
                }
            }
        }
    }

    private fun handleAutomaticActions(automaticActionEvent: AutomaticActionEvent) {
        val chatId: ChatId = automaticActionEvent.chatId


        scope.launch {
            when (automaticActionEvent) {
                is AutomaticActionEvent.SetNotifyRaceWeek -> {
                    val alarmRaceWeek =
                        NotifyRaceWeekUtils.getNotifyRaceWeekTime(automaticActionEvent.alarmValues, true)

                    val botPhotoByUrl =
                        automaticAction.setAlarmRaceWeek(
                            bot,
                            automaticActionEvent.chat,
                            alarmRaceWeek
                        ) as BotOutcome.SendPhotoByUrl
                    bot.sendPhoto(
                        chatId,
                        "https://external-preview.redd.it/kp_TGuhjEmWpxeJlBd9I5ErR75ZE6hC3O9sx4VbN2s8.jpg?width=960&crop=smart&auto=webp&s=383c68d4310c3c563a6662714cd1fe93b0769d39",
                        botPhotoByUrl.message,
                        ParseMode.MARKDOWN_V2
                    )
                }

                is AutomaticActionEvent.UnsetNotifyRaceWeek -> {
                    val botPhoto =
                        automaticAction.unsetNotifyRaceWeek(automaticActionEvent.chat) as BotOutcome.SendPhotoByUrl
                    bot.sendPhoto(
                        chatId,
                        botPhoto.photoUrl,
                        botPhoto.message,
                        ParseMode.MARKDOWN_V2
                    )
                }
            }
        }
    }

    //TODO THIS SHOULD BE BOTH, AUTOMATIC AND MANUAL Maybe create a gwclass speciailized for this?
    fun getHotPost(chatId: ChatId) {
        val scope = CoroutineScope(Dispatchers.IO)
        scope.launch {
            println("fetching")
            val hotPosts = RedditService.service.fetchNewPosts()
            println("hotPosts list size ${hotPosts.data.children.size}")
//            println("url ${hotPosts.data.children.get(1).data.url}")
            val randomNumber = Random().nextInt(hotPosts.data.children.size)
            println("Random number: $randomNumber")
            val post = hotPosts.data.children.get(randomNumber)
            botActions.sendPhotoByUrl(chatId, post.data.url, post.data.title)
        }
    }
}