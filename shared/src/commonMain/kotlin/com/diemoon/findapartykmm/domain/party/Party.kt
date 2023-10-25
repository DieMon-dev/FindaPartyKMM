package com.diemoon.findapartykmm.domain.party

import com.diemoon.findapartykmm.presentation.BabyBlueHex
import com.diemoon.findapartykmm.presentation.LightGreenHex
import com.diemoon.findapartykmm.presentation.RedOrangeHex
import com.diemoon.findapartykmm.presentation.RedPinkHex
import com.diemoon.findapartykmm.presentation.VioletHex
import kotlinx.datetime.LocalDateTime

data class Party(
    val id: Long?,
    val title: String,
    val content: String,
    val colorHex: Long,
    val created: LocalDateTime
){
   companion object{
       private val colors = listOf(RedOrangeHex, RedPinkHex, LightGreenHex, BabyBlueHex, VioletHex)
       fun generateRandomColor() = colors.random()

   }
}
