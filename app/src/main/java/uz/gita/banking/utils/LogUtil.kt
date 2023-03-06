package uz.gita.banking.utils

import android.util.Log
import uz.gita.banking.BuildConfig

fun l(string: String){
    if(BuildConfig.DEBUG){
        Log.d("TTT",string)
    }
}