package com.ramsoft.mydog.utils

import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Handler
import android.os.Looper
import android.provider.MediaStore
import android.widget.Toast
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.net.URL
import java.util.Random

/**
 * @author Priyesh Bhargava
 * Utility class for common functionality
 */

object Utility {
    /**
     * To share image on social media
     * @param context imgUrl
     */
    fun shareIntent(context: Context, imgUrl: String, title: String) {
        val scope = CoroutineScope(Dispatchers.IO)
        // Launch a new coroutine in the scope
        scope.launch {
            val url = URL(imgUrl)
            val input = url.openStream()
            val imgBitmap = BitmapFactory.decodeStream(input)
            val rand = Random()
            val randNo: Int = rand.nextInt(100000)
            val imgBitmapPath = MediaStore.Images.Media.insertImage(
                context.contentResolver, imgBitmap,
                "IMG:$randNo", null
            )
            val imgBitmapUri = Uri.parse(imgBitmapPath)

            val shareIntent = Intent(Intent.ACTION_SEND)
            shareIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            shareIntent.putExtra(Intent.EXTRA_STREAM, imgBitmapUri)
            shareIntent.setType("image/png")
            shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            shareIntent.putExtra(Intent.EXTRA_TEXT, title)
            context.startActivity(Intent.createChooser(shareIntent, "Share with"))
        }
    }

    /**
     * To save image on device
     * @param context imgUrl
     */
    fun savePhoto(context: Context, imgUrl: String) {
        val scope = CoroutineScope(Dispatchers.IO)
        scope.launch {
            val url = URL(imgUrl)
            val input = url.openStream()
            val imgBitmap = BitmapFactory.decodeStream(input)
            val rand = Random()
            val randNo: Int = rand.nextInt(100000)
            val imgBitmapPath = MediaStore.Images.Media.insertImage(
                context.contentResolver, imgBitmap,
                "IMG:$randNo", null
            )
            Handler(Looper.getMainLooper()).post {
                Toast.makeText(context, "Images saved!", Toast.LENGTH_LONG).show()
            }

        }
    }
}