package com.thatsmanmeet.calculator

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.thatsmanmeet.calculator.databinding.ActivityHistoryBinding
import com.thatsmanmeet.calculator.room.HistoryViewModel
import kotlinx.coroutines.launch

class HistoryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHistoryBinding
    private lateinit var viewModel:HistoryViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // RecyclerView Settings starts here...
        val recyclerView = binding.rvHistory
        recyclerView.layoutManager = LinearLayoutManager(this)
        // RecyclerView Settings ends here...
        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(this.application)
        )[HistoryViewModel::class.java]

        viewModel.allHistory.observe(this){list->
            recyclerView.adapter = RecyclerViewAdapter(list.toMutableList())
            if (list.isEmpty()){
                binding.tvNoHistory.visibility = View.VISIBLE
            }else{
                binding.tvNoHistory.visibility = View.INVISIBLE
            }
        }

        // Delete icon button
        binding.ivDelete.setOnClickListener {
            MaterialAlertDialogBuilder(this)
                .setTitle("Delete History")
                .setMessage("This action will delete all the calculator history.")
                .setPositiveButton("Delete"){_,_->
                    deleteData()
//                    finish()
                }
                .setNegativeButton("Cancel",null)
                .show()
        }
    }
    private fun deleteData() {
        lifecycleScope.launch {
            viewModel.deleteAllHistory()
        }
    }
}


