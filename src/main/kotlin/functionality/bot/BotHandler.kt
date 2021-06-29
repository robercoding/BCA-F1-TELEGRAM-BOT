package functionality.bot

import com.github.kotlintelegrambot.Bot
import com.github.kotlintelegrambot.entities.ChatId
import help.DateUtils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import model.Race
import functionality.race.RaceManager
import functionality.schedule.ScheduleUtils
import repository.service.RedditService
import java.util.*

class BotHandler {
    private var timers : MutableList<Pair<Long, TimerTask>> = mutableListOf()
    private val raceManager = RaceManager()
//    private val alonsoMemes = listOf(AlonsoMeme("", "https://i.redd.it/oc0b3h8w7x951.png"), AlonsoMeme("", "https://www.mallorcadiario.com/fotos/1/Sinttulo-5.jpg"))
//    private val memes : List<List<Meme>> = listOf(alonsoMemes)

    private lateinit var bot: Bot

    fun setBot(bot: Bot){
        this.bot = bot
    }

    fun sendMessage(chatId: ChatId, message: String){
        bot.sendMessage(chatId, message)
    }

    fun sendPhotoByUrl(chatId: ChatId, urlPhoto: String, caption : String = "") {
        bot.sendPhoto(
                chatId,
                urlPhoto,
                caption
        )
    }

    fun activateScheduleThursday(chatId: ChatId){
        if(ScheduleUtils.isTodayThursdayOrGreater()){
            println("Yes is greater")
            notifyChatIfThisWeekIsRaceWeek(chatId)
        }

        val timer = ScheduleUtils.getTimerTask {
            notifyChatIfThisWeekIsRaceWeek(chatId)
        }

        val chatIdLong = (chatId as ChatId.Id).id
        timers.add(Pair(chatIdLong, timer))
        bot.sendMessage(chatId, "Perfecto, cada jueves comprobaré si hay carrera esa misma semana.")
    }

    private fun notifyChatIfThisWeekIsRaceWeek(chatId: ChatId){
        if(raceManager.isThisWeekRaceWeek()){
            val raceWeek = raceManager.getNextRaceWeek()
            if(raceWeek.country == "Empty") return

            notifyRaceWeek(chatId, raceWeek)
        }
    }

    private fun notifyRaceWeek(chatId: ChatId, race: Race) {
        var captionSprintQualifying = ""
        if(race.isSprintQualifying){
            captionSprintQualifying = "\n Sprint Qualifying: ${formatToTimezoneGMT(race.dateSprintQualifying)}"
        }
        val caption = "RACE WEEK!!" +
                "\n\nA continuación detalles de la siguiente carrera:" +
                "\n País: ${race.country}" +
                "\n Circuito: ${race.nameCircuit}" +
                captionSprintQualifying +
                "\n Clasificación: ${formatToTimezoneGMT(race.dateQualifying)}" +
                "\n Carrera: ${formatToTimezoneGMT(race.dateRace)}"

        bot.sendPhoto(
                chatId = chatId,
                race.layoutCircuitUrl,
                caption
        )
    }

    fun getNextRace(chatId: ChatId) {
        val race = raceManager.getNextRaceWeek()

        if(race.country == "Empty"){
            bot.sendMessage(chatId, "No hay más carreras esta temporada :(")
        }else{
            bot.sendPhoto(chatId,race.layoutCircuitUrl, formatTextNextRace(race))
        }
    }

    private fun formatTextNextRace(race: Race) : String{
        if(race.country == "Empty"){
            return "No hay más carreras esta temporada... :("
        }

        var captionSprintQualifying = ""
        if(race.isSprintQualifying){
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

    fun stopTimer(chatId: ChatId){
        var timerTask : TimerTask? = null
        var timerIndex = -1
        val chatIdLong = (chatId as ChatId.Id).id
        timers.forEachIndexed { index, it ->
            if(it.first == chatIdLong){
                timerIndex = index
                timerTask = it.second
                return@forEachIndexed
            }
        }

        if(timerTask != null && timerIndex != -1){
            timerTask?.cancel()
            timers.removeAt(timerIndex)
            bot.sendMessage(chatId, "Se ha parado el recordatorio")
        }else{
            bot.sendMessage(chatId, "No tienes ningún recordatorio")
        }
    }

    private fun formatToTimezoneGMT(date: Date) : String {
        return DateUtils.formatToTimezoneGMT(date)
    }

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
            sendPhotoByUrl(chatId, post.data.url, post.data.title)
        }
    }
}