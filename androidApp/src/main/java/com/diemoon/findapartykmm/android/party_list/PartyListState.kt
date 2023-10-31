package com.diemoon.findapartykmm.android.party_list

import com.diemoon.findapartykmm.domain.party.Party

data class PartyListState(
    val parties: List<Party> = emptyList(),
    val searchText: String = "",
    val isSearchActive: Boolean = false
)
