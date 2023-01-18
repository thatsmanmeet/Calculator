package com.thatsmanmeet.calculator

import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.thatsmanmeet.calculator.R
import com.thatsmanmeet.calculator.databinding.ActivityHistoryBinding
import com.thatsmanmeet.calculator.room.History
import com.thatsmanmeet.calculator.room.HistoryDatabase
import kotlinx.coroutines.Runnable
import kotlinx.coroutines.launch

class HistoryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHistoryBinding
    private lateinit var appDb: HistoryDatabase
    private var myList: MutableList<History> = mutableListOf()
    private lateinit var sharedPref: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // Make instance of the History Database
        appDb = HistoryDatabase.getDatabase(this)
        // Initialize shared preferences and get the theme data from it
        sharedPref = getSharedPreferences("themePref", MODE_PRIVATE)
        loadThemeData()
        getResults()
        // RecyclerView Settings starts here...
        val recyclerView = binding.rvHistory
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = RecyclerViewAdapter(myList)
        // RecyclerView Settings ends here...

        // Delete icon button
        binding.ivDelete.setOnClickListener {
            MaterialAlertDialogBuilder(this)
                .setTitle("Delete History")
                .setMessage("This action will delete all the calculator history.")
                .setPositiveButton("Delete"){_,_->
                    deleteData()
                    finish()
                }
                .setNegativeButton("Cancel",null)
                .show()
        }
    }

    private fun deleteData() {
        lifecycleScope.launch {
            appDb.historyDao().deleteAll()
        }
        Toast.makeText(this, "History Deleted", Toast.LENGTH_SHORT).show()
    }

    private fun getResults() {
        Thread(Runnable {
            val list = appDb.historyDao().getAll()
            for (history in list) {
                myList.add(history)
            }
        }).start()
    }

    private fun loadThemeData() {
        when (sharedPref.getString("theme", null)) {
            "dark" -> {
                binding.parent.setBackgroundColor(ContextCompat.getColor(this, R.color.black))
                binding.tvHistoryText.setTextColor(ContextCompat.getColor(this, R.color.white))
                window.navigationBarColor = getColor(R.color.black)
                window.statusBarColor = getColor(R.color.black)
            }
            "light" -> {
                binding.parent.setBackgroundColor(ContextCompat.getColor(this, R.color.white))
                binding.tvHistoryText.setTextColor(ContextCompat.getColor(this, R.color.black))
                window.navigationBarColor = getColor(R.color.white)
                window.statusBarColor = getColor(R.color.white)
            }
            else -> {
                binding.parent.setBackgroundColor(ContextCompat.getColor(this, R.color.black))
                binding.tvHistoryText.setTextColor(ContextCompat.getColor(this, R.color.white))
                window.navigationBarColor = getColor(R.color.black)
                window.statusBarColor = getColor(R.color.black)
            }
        }
    }
}


