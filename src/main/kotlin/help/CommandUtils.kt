package help

object CommandUtils {
    /**
     * Check if contains 'race' word and 13 digits or more
     * e.g = /race1234567890123
     */
    fun requestRaceDetails(string: String): Boolean {
        val hasDigit = string.any { it.isDigit() }
        if (!hasDigit) return false

        val isThirteenDigitsOrMore = string.filter { it.isDigit() }.length >= 13
        val containsRaceWord = string.toLowerCase().contains("race")

        return isThirteenDigitsOrMore && containsRaceWord
    }

//    fun requestSetAlarmRaceWeek(string: String): Boolean = string.toLowerCase().contains("setalarmraceweek")
}

fun String.getCommand(): String {
    return substring(indexOfFirst { it == '/' }, indexOfLast { it.isLetter() } + 1)
        .filter { it.isLetter() || it == '/' }
}

fun String.getCommandUntilWhiteSpace(): String {
    return substring(indexOfFirst { it == '/' }, indexOfFirst { it.isWhitespace() } + 1)
        .filter { it.isLetter() || it == '/' }
}

fun String.removeCommand(): String {
    val strRemove = removeRange(indexOfFirst { it == '/' }, indexOfFirst { it.isWhitespace() })
    return strRemove
}

