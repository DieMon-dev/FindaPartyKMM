package com.diemoon.findapartykmm.android.party_list

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.diemoon.findapartykmm.domain.party.Party
import com.diemoon.findapartykmm.domain.party.PartyDataSource
import com.diemoon.findapartykmm.domain.party.SearchParty
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class PartyListViewModel @Inject constructor(
    private val partyDataSource: PartyDataSource,
    private val savedStateHandle: SavedStateHandle
): ViewModel() {

    private val searchParty = SearchParty()

    private val parties = savedStateHandle.getStateFlow("parties", emptyList<Party>())
    private val searchText = savedStateHandle.getStateFlow("searchText", "")
    private val isSearchActive = savedStateHandle.getStateFlow("isSearchActive", false)

    val state = combine(parties, searchText, isSearchActive) {parties, searchText, isSearchActive ->
        PartyListState(
            parties =  searchParty.execute(parties, searchText),
            searchText = searchText,
            isSearchActive = isSearchActive
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), PartyListState())

    fun loadParties(){
        viewModelScope.launch{
            savedStateHandle["parties"] = partyDataSource.getAllParties()
        }
    }

    fun onSearchTextChange(text: String) {
        savedStateHandle["searchText"] = text
    }

    fun onToggleSearch() {
        savedStateHandle["isSearchActive"] = !isSearchActive.value
        if(!isSearchActive.value) {
            savedStateHandle["searchText"] = ""
        }
    }

    fun deletePartyById(id: Long) {
        viewModelScope.launch {
            partyDataSource.deletePartyById(id)
            loadParties()
        }
    }
}