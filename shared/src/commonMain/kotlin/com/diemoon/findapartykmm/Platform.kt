package com.diemoon.findapartykmm

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform