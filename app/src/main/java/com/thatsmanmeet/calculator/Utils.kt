package com.thatsmanmeet.calculator

import android.content.Context
import android.content.Intent

class Utils (private val context: Context? = null) {
     fun launchErrorActivity(error:String){
        val intent = Intent(context,ErrorActivity::class.java).also {
            it.putExtra("error",error)
        }
         context?.startActivity(intent)
    }
}