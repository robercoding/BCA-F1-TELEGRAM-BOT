package model

data class AlonsoMeme(override val captionMeme: String, override val memeUrl: String) : Meme(captionMeme, memeUrl) {

}
