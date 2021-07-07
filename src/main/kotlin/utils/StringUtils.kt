package utils

object StringUtils {
    fun containsRaceWord(string: String): Boolean = string.toLowerCase().contains("race")

    fun getRaceId(string: String): Long {
        val fromIndex =
            string.indexOf("e") + 1 //we use e because raceId starts after "Race" word, e.g -> /Race3278548800000@Bot
        val toIndex = string.indexOf("@")
        return string.substring(fromIndex, toIndex).toLong()
    }
}
