package com.diemoon.findapartykmm

import database.PartyEntity

class Greeting {
    var db = PartyEntity(20, "Bro", "Never gonna give you up! Never gonna let you down.", 254345,231020)



    private val platform: Platform = getPlatform()

    fun greeting(): String {
        return "Hello, ${platform.name}!"
    }
}