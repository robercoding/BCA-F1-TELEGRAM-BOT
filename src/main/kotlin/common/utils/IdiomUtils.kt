package common.utils

object IdiomUtils {
    fun getHelpSpanish() : String {
      return "" +
              "Aquí estan los comandos disponibles:\n" +
              "/Alonso\n" +
              "/Hamilton\n" +
              "/Vettel\n" +
              "/SiguienteCarrera y /sc\n" +
              "/ActivarRecordatorioCuandoHayCarrera (Recuerda cada jueves)\n" +
              "/DesactivarRecordatorio"
    }

    fun getHelpEnglish() : String {
        return "" +
                "Here are the available commands:\n" +
                "/Alonso\n" +
                "/Hamilton\n" +
                "/Vettel\n" +
                "/NextRace y /nr\n" +
                "/ActivateReminderRaceWeek (Remind every thursday)\n" +
                "/DisableReminder"
    }
}