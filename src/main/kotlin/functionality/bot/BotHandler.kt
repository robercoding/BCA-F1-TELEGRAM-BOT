package functionality.bot

import Idiom
import TelegramBot
import com.github.kotlintelegrambot.Bot
import com.github.kotlintelegrambot.bot
import com.github.kotlintelegrambot.dispatch
import com.github.kotlintelegrambot.dispatcher.text
import com.github.kotlintelegrambot.entities.ChatId
import functionality.action.AutomaticAction
import functionality.action.AutomaticActionEvent
import functionality.action.ManualAction
import functionality.action.ManualActionEvent
import functionality.race.RaceManager
import help.DateUtils
import help.TelegramUtils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import model.Race
import repository.ActionRepository
import repository.service.RedditService
import sendHelp
import java.util.*

class BotHandler {
    private var timers: MutableList<Pair<Long, TimerTask>> = mutableListOf()
    private lateinit var bot: Bot
    private lateinit var botActions: BotActions
    private val raceManager = RaceManager()
    val actionRepository = ActionRepository()
    private val automaticAction = AutomaticAction(actionRepository)
    private val manualAction = ManualAction(actionRepository)

    fun startListening() {
        bot = bot {
            token = TelegramBot.TOKEN
            dispatch {
                text {
                    println(message.text)
                    val chatId = TelegramUtils.convertToChatId(message.chat.id)
                    val text = message.text ?: ""
                    when (text.toLowerCase()) {
                        "/alonso" -> botActions.sendMessage(chatId, "EL MAGIC FIAUUUUUUM ALPINEE!")
                        "/vettel" -> botActions.sendMessage(chatId, "Ferrarí le destrozó la vidas")
                        "/hamilton" -> botActions.sendMessage(chatId, "HAMILTON PRESSED THE MAGIC BUTTON")
                        "/nextrace" -> handleManualActions(ManualActionEvent.GetNextRace(chatId))
                        "/nr" -> handleManualActions(ManualActionEvent.GetNextRace(chatId))
//                        "/rw" -> botHandler.sendPhotoByUrl(chatId, "https://e00-marca.uecdn.es/assets/multimedia/imagenes/2020/05/07/15888780078997.jpg", "RACE WEEK!")
//                        "/raceweek" -> botHandler.sendPhotoByUrl(chatId, "https://e00-marca.uecdn.es/assets/multimedia/imagenes/2020/05/07/15888780078997.jpg")
                        "/autRemindRaceWeek" -> handleAutomaticActions(AutomaticActionEvent.RemindRaceWeek(chatId))
                        "/autRecordarSemanaCarrera" -> handleAutomaticActions(AutomaticActionEvent.RemindRaceWeek(chatId))
                        "/autDisableReminderRaceWeek" -> handleAutomaticActions(AutomaticActionEvent.DisableRemindRaceWeek(chatId))
                        "/autDesactivarRecordatorioSemanaCarrera" -> handleAutomaticActions(AutomaticActionEvent.DisableRemindRaceWeek(chatId))
                        "/reddit" -> getHotPost(chatId)
                        "/help" -> bot.sendHelp(message.chat.id, Idiom.ES)
                        "/helpES" -> bot.sendHelp(message.chat.id, Idiom.ES)
                        "/helpEN" -> bot.sendHelp(message.chat.id, Idiom.EN)
                    }
                }
            }
        }
        botActions = BotActions(bot)
        bot.startPolling()
    }

    private fun handleManualActions(manualActionEvent: ManualActionEvent) {
        when (manualActionEvent) {
            is ManualActionEvent.GetNextRace -> {
                getNextRace(chatId = manualActionEvent.chatId)
            }
        }
    }

    private fun handleAutomaticActions(automaticActionEvent: AutomaticActionEvent) {
        when (automaticActionEvent) {
            is AutomaticActionEvent.RemindRaceWeek -> {
                activateScheduleThursday(automaticActionEvent.chatId)
            }

            is AutomaticActionEvent.DisableRemindRaceWeek -> {
                stopTimer(chatId = automaticActionEvent.chatId)
            }
        }
    }

    fun activateScheduleThursday(chatId: ChatId) {
        automaticAction.activateScheduleThursday(bot, chatId)
        automaticAction.isRaceWeek()
//        val timer = ScheduleUtils.getTimerTask {
//            notifyChatIfThisWeekIsRaceWeek(chatId)
//        }
//
//        val chatIdLong = (chatId as ChatId.Id).id
//        timers.add(Pair(chatIdLong, timer))
//        bot.sendMessage(chatId, "Perfecto, cada jueves comprobaré si hay carrera esa misma semana.")
    }


//    private fun notifyChatIfThisWeekIsRaceWeek(chatId: ChatId){
//        val races = actionRepository.getRaceCalendar().races
//        if(raceManager.isThisWeekRaceWeek(races)){
//            val raceWeek = raceManager.getNextRaceWeek(races)
//            if(raceWeek.country == "Empty") return //Why the heck is this here, investigate later
//
//            notifyRaceWeek(chatId, raceWeek)
//        }
//    }

//    private fun notifyRaceWeek(chatId: ChatId, race: Race) {
//        var captionSprintQualifying = ""
//        if(race.isSprintQualifying){
//            captionSprintQualifying = "\n Sprint Qualifying: ${formatToTimezoneGMT(race.dateSprintQualifying)}"
//        }
//        val caption = "RACE WEEK!!" +
//                "\n\nA continuación detalles de la siguiente carrera:" +
//                "\n País: ${race.country}" +
//                "\n Circuito: ${race.nameCircuit}" +
//                captionSprintQualifying +
//                "\n Clasificación: ${formatToTimezoneGMT(race.dateQualifying)}" +
//                "\n Carrera: ${formatToTimezoneGMT(race.dateRace)}"
//
//        bot.sendPhoto(
//                chatId = chatId,
//                race.layoutCircuitUrl,
//                caption
//        )
//    }

    private fun getNextRace(chatId: ChatId) {
        val races = actionRepository.getRaceCalendar().races
        val race = raceManager.getNextRaceWeek(races) // RaceManager should be called RaceHelper instead

        if (race.country == "Empty") {
            bot.sendMessage(chatId, "No hay más carreras esta temporada :(")
        } else {
            bot.sendPhoto(chatId, race.layoutCircuitUrl, formatTextNextRace(race))
        }
    }

    //TODO FORMATTEXTNEXTRACE SHOULD BE IN A LanguageHelper class to translate it according to the selected language
    private fun formatTextNextRace(race: Race): String {
        if (race.country == "Empty") {
            return "No hay más carreras esta temporada... :("
        }

        var captionSprintQualifying = ""
        if (race.isSprintQualifying) {
            captionSprintQualifying = "\n Sprint Qualifying: ${formatToTimezoneGMT(race.dateSprintQualifying)}"
        }

        return "La siguiente carrera será el día ${DateUtils.formatDayAndMonthToTimezoneGMT(race.dateRace)} en el ${race.nameGrandPrix}" +
                "\n\n Detalles importantes:" +
                "\n País: ${race.country}" +
                "\n Circuito: ${race.nameCircuit}" +
                captionSprintQualifying +
                "\n Clasificación: ${DateUtils.formatToTimezoneGMT(race.dateQualifying)}" +
                "\n Carrera: ${DateUtils.formatToTimezoneGMT(race.dateRace)}"
    }

    //TODO STOPTIMER SHOULD BE REMOVE FROM DB AND CANCEL IT
    fun stopTimer(chatId: ChatId) {
        var timerTask: TimerTask? = null
        var timerIndex = -1
        val chatIdLong = (chatId as ChatId.Id).id
        timers.forEachIndexed { index, it ->
            if (it.first == chatIdLong) {
                timerIndex = index
                timerTask = it.second
                return@forEachIndexed
            }
        }

        if (timerTask != null && timerIndex != -1) {
            timerTask?.cancel()
            timers.removeAt(timerIndex)
            bot.sendMessage(chatId, "Se ha parado el recordatorio")
        } else {
            bot.sendMessage(chatId, "No tienes ningún recordatorio")
        }
    }

    private fun formatToTimezoneGMT(date: Date): String {
        return DateUtils.formatToTimezoneGMT(date)
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