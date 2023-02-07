package com.thatsmanmeet.calculator

import android.annotation.SuppressLint
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.thatsmanmeet.calculator.databinding.ActivityMainBinding
import com.thatsmanmeet.calculator.room.History
import com.thatsmanmeet.calculator.room.HistoryDatabase
import kotlinx.coroutines.launch
import org.mariuszgromada.math.mxparser.Expression


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var sharedPref: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor
    private lateinit var appDb: HistoryDatabase
    private var operator = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        sharedPref = getSharedPreferences("themePref", MODE_PRIVATE)
        editor = sharedPref.edit()
        window.statusBarColor = getColor(R.color.black)
        window.navigationBarColor = getColor(R.color.black)
        appDb = HistoryDatabase.getDatabase(this)

        binding.btnHistory.setOnClickListener {
            Intent(this, HistoryActivity::class.java).also {
                startActivity(it)
            }
        }

        allButtonClicks()

        binding.btnDot.setOnClickListener {
            addToScreen(".", isSpecialCharacter = false, isDot = true)
        }

        binding.btnClear.setOnClickListener {
            deleteLastChar()
        }

        binding.btnEquals.setOnClickListener {
            calculate()
        }
    }

    private fun allButtonClicks() {
        binding.btnOne.setOnClickListener {
            addToScreen("1", false)
        }
        binding.btnTwo.setOnClickListener {
            addToScreen("2", false)
        }
        binding.btnThree.setOnClickListener {
            addToScreen("3", false)
        }
        binding.btnFour.setOnClickListener {
            addToScreen("4", false)
        }
        binding.btnFive.setOnClickListener {
            addToScreen("5", false)
        }
        binding.btnSix.setOnClickListener {
            addToScreen("6", false)
        }
        binding.btnSeven.setOnClickListener {
            addToScreen("7", false)
        }
        binding.btnEight.setOnClickListener {
            addToScreen("8", false)
        }
        binding.btnNine.setOnClickListener {
            addToScreen("9", false)
        }

        binding.btnZero.setOnClickListener {
            addToScreen("0", false)
        }

        binding.btnAllCLear.setOnClickListener {
            binding.tvEnter.text = "0"
        }

        binding.btnMultiply.setOnClickListener {
            addToScreen("x", true)
            operator = true
        }

        binding.btnMinus.setOnClickListener {
            addToScreen("-", true)
            operator = true

        }
        binding.btnAdd.setOnClickListener {
            addToScreen("+", true)
            operator = true

        }
        binding.btnDivide.setOnClickListener {
            addToScreen("÷", true)
            operator = true

        }

        binding.btnPow.setOnClickListener {
            addToScreen("^", true)
            operator = true

        }
        binding.parenthesisLeft?.setOnClickListener {
            addToScreen("(", false)
        }
        binding.parenthesisRight?.setOnClickListener {
            addToScreen(")", false)
        }
        binding.btnSin?.setOnClickListener {
            addToScreen("sin(", false)
        }
        binding.btnLog?.setOnClickListener {
            addToScreen("ln(", false)
        }
    }


    private fun deleteLastChar() {
        val enterText = binding.tvEnter.text
        if (enterText == "0" || enterText.isEmpty()) {
            binding.tvEnter.text = "0"
        } else if (enterText.isNotEmpty() && enterText != "0") {
            if (enterText.length == 1) {
                binding.tvEnter.text = enterText.toString()
                    .subSequence(0, enterText.toString().length - 1)
                binding.tvEnter.text = "0"
            } else {
                binding.tvEnter.text = enterText.toString()
                    .subSequence(0, enterText.toString().length - 1)
            }
        } else {
            binding.tvEnter.text = "0"
        }
    }


    private fun writeData(data: String) {
        if (data.isNotEmpty()) {
            val history = History(null, data)
            lifecycleScope.launch {
                appDb.historyDao().insert(history)
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun calculate() {
        try {
            if (binding.tvEnter.text.contains("sin(") && !binding.tvEnter.text.contains(")")) {
                binding.tvEnter.text = "${binding.tvEnter.text})"
            }

            if (binding.tvEnter.text.contains("ln(")&& !binding.tvEnter.text.contains(")")) {
                binding.tvEnter.text = "${binding.tvEnter.text})"
            }

            if(binding.tvEnter.text.contains("(") && !binding.tvEnter.text.contains(")")){
                binding.tvEnter.text = "${binding.tvEnter.text})"
            }


            val parser = Expression(
                binding.tvEnter.text.toString().replace("÷", "/").replace("x", "*")
            ).calculate()
            val calculatorHistory = "${binding.tvEnter.text} = $parser"
            binding.tvHistory.text = binding.tvEnter.text
            binding.tvEnter.text = parser.toString()
            operator = false
            writeData(calculatorHistory)
        } catch (e: Exception) {
            val utils = Utils(this)
            utils.launchErrorActivity(e.toString())
            binding.tvEnter.text = "0"
        }
    }

    @SuppressLint("SetTextI18n")
    private fun addToScreen(data: String, isSpecialCharacter: Boolean, isDot: Boolean = false) {
        if (binding.tvEnter.text == "0") {
            if (isSpecialCharacter || isDot) {
                binding.tvEnter.text = "0$data"
            } else {
                binding.tvEnter.text = ""
                binding.tvEnter.text = data
            }
        } else {
            binding.tvEnter.text = binding.tvEnter.text.toString() + data
        }
    }

}