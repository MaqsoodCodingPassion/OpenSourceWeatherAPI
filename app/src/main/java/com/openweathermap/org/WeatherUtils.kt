package com.openweathermap.org

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.os.Handler
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat

val API_KEY = "5ad7218f2e11df834b0eaf3a33a39d2a"

//for showing error message
fun showErrorDialog(activity: Activity, msg: String) {
    val builder = AlertDialog.Builder(activity)
    builder.setMessage(msg)
    val alertDialog = builder.create()
    alertDialog.show()
    Handler().postDelayed({
        if(alertDialog.isShowing) alertDialog.dismiss()
    },2000)
}

//Hide keyboard
fun hideKeyboard(activity: Activity) {
    val view = activity.currentFocus
    if (view != null) {
        val imm = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }
}

//Extension function for visibility view
fun View.showView(show: Boolean) {
    visibility = if (show) {
        View.VISIBLE
    } else {
        View.GONE
    }
}

fun isPermissionsGranted(context: Context) =
    ActivityCompat.checkSelfPermission(
        context, Manifest.permission.ACCESS_FINE_LOCATION
    ) == PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
