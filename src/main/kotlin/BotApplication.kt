import com.github.kotlintelegrambot.Bot
import com.github.kotlintelegrambot.entities.ChatId
import config.DBConfig
import functionality.bot.BotHandler
import help.IdiomUtils
import org.jetbrains.exposed.sql.transactions.transaction

fun main() {
    DBConfig.setup(true)

    transaction {
//        SchemaUtils.drop(CalendarRaceYearTable, RaceTable, GrandPrixTable, CircuitTable)
//        SchemaUtils.createMissingTablesAndColumns(CalendarRaceYearTable, RaceTable, GrandPrixTable, CircuitTable)
    }
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