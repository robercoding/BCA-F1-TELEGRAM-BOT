package common.utils

object StringUtils {
    fun containsRaceWord(string: String): Boolean = string.toLowerCase().contains("race")

    fun getRaceId(string: String): Long {
        val fromIndex =
            string.indexOf("e") + 1 //we use e because raceId starts after "Race" word, e.g -> /Race3278548800000@Bot
        val toIndex = string.length
        return string.substring(fromIndex, toIndex).toLong()
    }
}

//Index from first to last digit and then just filter and return only the ones who are digits.
fun String.getDigits(): String? {
    return if (any { it.isDigit() }) {
        substring(indexOfFirst { it.isDigit() }, indexOfLast { it.isDigit() } + 1)
            .filter { it.isDigit() }
    } else null
}

fun String.getCommand(): String {
    return substring(indexOfFirst { it == '/' }, indexOfLast { it.isLetter() } + 1)
        .filter { it.isLetter() || it == '/' }
}

fun String.trimStartUntilCommand(): String {
    return if (indexOf(' ') == 0) {
        substring(indexOfFirst { !it.isWhitespace() }, lastIndex + 1)
    } else {
        this
    }
}