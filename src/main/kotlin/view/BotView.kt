package view

import java.io.File

interface BotView {

    fun respond(recipient: Int, file: File)

}