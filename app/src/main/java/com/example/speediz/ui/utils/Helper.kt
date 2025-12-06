package com.example.speediz.ui.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale
import androidx.core.graphics.scale
import java.time.LocalDate

fun Context.toastHelper(message: String){
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}
@RequiresApi(Build.VERSION_CODES.O)
fun dateFormat(date: String): String{
    // Handle variable fractional seconds (0 to 9 digits) and 'Z' timezone
    val inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss[.SSSSSS][.SSS][.S]'Z'")
        .withZone(ZoneId.of("UTC"))

    val timeFormatter = DateTimeFormatter.ofPattern("hh:mm a", Locale.getDefault())
    val fullFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy", Locale.getDefault())

    try {
        //parse the input date string
        val dateTime = ZonedDateTime.parse(date, inputFormatter).withZoneSameInstant(ZoneId.systemDefault())
        //get the current date and time
        val currentDateTime = ZonedDateTime.now(ZoneId.systemDefault())
        //determine if the date is today, yesterday, or earlier
        val isToday = dateTime.toLocalDate().isEqual(currentDateTime.toLocalDate())
        val isYesterday = dateTime.toLocalDate().isEqual(currentDateTime.minusDays(1).toLocalDate())
        //format the date accordingly
        val findFormattedDate = when{
            isYesterday -> "Yesterday at ${dateTime.format(timeFormatter)}"
            isToday -> "Today at ${dateTime.format(timeFormatter)}"
            else -> dateTime.format(fullFormatter)
        }
        return findFormattedDate

    }catch (e: Exception) {
        e.printStackTrace()
        return date
    }
}
@RequiresApi(Build.VERSION_CODES.O)
fun convertDateCalendar(
    date: String?,
    inputPattern: String,
    outputPattern: String
): String {
    return try {
        if (date.isNullOrEmpty()) return ""  // handle null/empty
        val inputFormatter = DateTimeFormatter.ofPattern(inputPattern, Locale.getDefault())
        val outputFormatter = DateTimeFormatter.ofPattern(outputPattern, Locale.getDefault())
        val localDate = LocalDate.parse(date, inputFormatter)
        localDate.format(outputFormatter)
    } catch (e: Exception) {
        e.printStackTrace()
        date ?: ""
    }
}

fun resizeImage(context: Context, uri: Uri, maxWidth: Int, maxHeight: Int): Bitmap? {
    val inputStream =  context.contentResolver.openInputStream(uri) ?: return null
    val originalBitmap = BitmapFactory.decodeStream(inputStream)

    val scale = minOf(
        maxWidth.toFloat() / originalBitmap.width,
        maxHeight.toFloat() / originalBitmap.height
    )

    val newWidth = (originalBitmap.width * scale).toInt()
    val newHeight = (originalBitmap.height * scale).toInt()

    return originalBitmap.scale(newWidth, newHeight)
}