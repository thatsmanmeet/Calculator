package com.thatsmanmeet.calculator.room

import androidx.lifecycle.LiveData

class HistoryRepository(private val historyDao: HistoryDao) {

    fun getAllHistory() : LiveData<List<History>> = historyDao.getAll()

    suspend fun insertHistory(history: History){
        historyDao.insert(history)
    }

    suspend fun deleteAllHistory(){
        historyDao.deleteAll()
    }

}