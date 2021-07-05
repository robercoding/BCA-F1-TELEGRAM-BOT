package functionality.bot

//fun Bot.sendMessage(chatId: Long, message: String){
//    println("Send MESSAGE and chatid is $chatId")
//    this.sendMessage(ChatId.fromId(chatId), text = "$message")
//}
//
//fun Bot.sendPhotoToChat(chatId: Long, isTesting : Boolean = false){
//    println("Send PHOTO and chatid is $chatId")
//    val urlPhoto = if(isTesting) "https://i.gyazo.com/e2fb8f5208a5a3c159225c2fef8eff58.png" else "http://e00-marca.uecdn.es/assets/multimedia/imagenes/2020/05/07/15888780078997.jpg"
//    this.sendPhoto(
//            ChatId.Id(chatId),
//            "http://e00-marca.uecdn.es/assets/multimedia/imagenes/2020/05/07/15888780078997.jpg",
//            "RACE WEEK"
//    )
//}


//fun Bot.notifyRaceWeek(chatId: Long, race: RaceOutdated) {
//    val chat = ChatId.Id(chatId)
//
////    http://e00-marca.uecdn.es/assets/multimedia/imagenes/2020/05/07/15888780078997.jpg
//    this.sendPhoto(
//            chat,
//            race.layoutCircuitUrl,
//            "RACE WEEK!!" +
//                    "A continuación detalles de la carrera:\" +\n" +
//                    "            \"\\n País: ${race.country}\" +\n" +
//                    "            \"\\n Circuito: ${race.nameCircuit}\" +\n" +
//                    "            \"\\n Clasificación: ${race.dateQualifying}\" +\n" +
//                    "            \"\\n Carrera: ${race.dateRace}"
//    )

//    this.sendMessage(chat, )

//    this.sendMessage(chat, "ESTA SEMANA HAY DIVERSIÓN, VAMOS A VER EL MEJOR DEPORTE DE AUTOMOVILISMO QUE SE HAYA PARIDO EN ESTE PUTO PAIS, EN EFECTO, LA F1! A CONTINUACIÓN NOMBRAREMOS LAS ESTRELLAS:" +
//            "\nEL DOS VECES CAMPEÓN DEL MUNDO FERNANDO ALONSO DIAZ CON SU ALPINE! Y decían que estaba oxidado..." +
//            "\nTAMBIÉN EL COMENTARISTA ESPAÑOL MAS LAUREADO DE LA HISTORIA, ANTONIO LOBATO! Apoyando incondicionalmente a su amigo Fernando Alonso" +
//            "\nÚLTIMO Y NO POR ELLO MENOS, EL GRAN PEDRO DE LA ROSA QUE 16 AÑOS DESPUÉS SIGUE MANTIENIENDO ESE RECORD EN BAHREIN, GRANDE PEDRITO" +
//            "\n\n A continuación detalles de la carrera:" +
//            "\n Circuito: ${functionality.race.layoutCircuitUrl}" +
//            "\n Clasificación: ${functionality.race.dateQualifying}" +
//            "\n Carrera: ${functionality.race.dateRace}")
//}