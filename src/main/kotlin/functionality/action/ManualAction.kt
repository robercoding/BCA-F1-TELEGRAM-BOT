package functionality.action

import repository.ActionRepository

//TODO rename to RequestAction or UserAction? Like it's 1 time call and return
class ManualAction(actionRepository: ActionRepository) : Action(actionRepository)