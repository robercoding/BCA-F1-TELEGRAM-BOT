import com.github.kotlintelegrambot.Bot
import com.github.kotlintelegrambot.entities.ChatId
import functionality.bot.BotHandler
import help.IdiomUtils

fun main() {
    val botHandler = BotHandler()
    botHandler.startListening()
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