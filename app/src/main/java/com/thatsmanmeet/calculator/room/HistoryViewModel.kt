package com.thatsmanmeet.calculator.room

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HistoryViewModel(application: Application) : AndroidViewModel(application){

    private val repository : HistoryRepository
    val allHistory : LiveData<List<History>>

    init {
        val dao = HistoryDatabase.getDatabase(application).historyDao()
        repository = HistoryRepository(dao)
        allHistory = repository.getAllHistory()
    }

    fun insertHistory(history: History){
        viewModelScope.launch (Dispatchers.IO){
            repository.insertHistory(history)
        }
    }

    fun deleteAllHistory(){
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteAllHistory()
        }
    }

}