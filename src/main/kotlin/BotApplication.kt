import functionality.bot.*
import com.github.kotlintelegrambot.Bot
import com.github.kotlintelegrambot.bot
import com.github.kotlintelegrambot.dispatch
import com.github.kotlintelegrambot.dispatcher.text
import com.github.kotlintelegrambot.entities.ChatId
import help.IdiomUtils
import help.TelegramUtils

val botHandler = BotHandler()
fun main() {
    val bot = bot {
        token = TelegramBot.TOKEN
        dispatch {
            text {
                println(message.text)
                val chatId = TelegramUtils.convertToChatId(message.chat.id)
                val text = message.text ?: ""
                when(text.toLowerCase()){
                    "/alonso" -> botHandler.sendMessage(chatId, "EL MAGIC FIAUUUUUUM ALPEINEE! (inserte emoji de huevos gordos!)")
                    "/vettel" -> bot.sendMessage(chatId, "Ferrarí le destrozó la vidas")
                    "/hamilton" -> bot.sendMessage(chatId, "BLM Y MAFIA A TU LADO SIEMPRE")
                    "/nextrace" -> botHandler.getNextRace(chatId)
                    "/nr" -> botHandler.getNextRace(chatId)
                    "/rw" -> botHandler.sendPhotoByUrl(chatId, "https://e00-marca.uecdn.es/assets/multimedia/imagenes/2020/05/07/15888780078997.jpg", "RACE WEEK!")
                    "/raceweek" -> botHandler.sendPhotoByUrl(chatId, "https://e00-marca.uecdn.es/assets/multimedia/imagenes/2020/05/07/15888780078997.jpg")
                    "/remind" -> botHandler.activateScheduleThursday(chatId)
                    "/activarrecordatoriocuandohaycarrera" -> botHandler.activateScheduleThursday(chatId)
                    "/activatereminderraceweek" -> botHandler.activateScheduleThursday(chatId)
                    "/disablereminder" -> botHandler.stopTimer(chatId)
                    "/desactivarrecordatorio" -> botHandler.stopTimer(chatId)
                    "/reddit" -> botHandler.getHotPost(chatId)
                    "/help" -> bot.sendHelp(message.chat.id, Idiom.ES)
                    "/helpES" -> bot.sendHelp(message.chat.id, Idiom.ES)
                    "/helpEN" -> bot.sendHelp(message.chat.id, Idiom.EN)
                }
            }
        }
    }
    bot.startPolling()
    botHandler.setBot(bot)
}

fun Bot.sendHelp(chatId: Long, idiom : Idiom){
    val help = when (idiom){
        Idiom.ES -> IdiomUtils.getHelpSpanish()
        Idiom.EN -> IdiomUtils.getHelpEnglish()
    }
    this.sendMessage(ChatId.fromId(chatId), text = help)
}

enum class Idiom {
    ES, EN
}