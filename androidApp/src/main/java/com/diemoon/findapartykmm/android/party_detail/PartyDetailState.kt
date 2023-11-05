package com.diemoon.findapartykmm.android.party_detail

data class PartyDetailState(
    val partyTitle: String = "",
    val isPartyTitleHintVisible: Boolean = false,
    val partyContent: String = "",
    val isPartyContentHintVisible: Boolean = false,
    val partyColor: Long = 0xFFFFFF
)
