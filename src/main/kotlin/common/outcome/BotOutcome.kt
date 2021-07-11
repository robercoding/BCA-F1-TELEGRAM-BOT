package common.outcome

sealed class BotOutcome {
    data class SendMessage(val message: String) : BotOutcome()
    data class SendPhotoByUrl(val message: String, val photoUrl: String) : BotOutcome()
}