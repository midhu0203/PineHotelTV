package com.example.pinehoteltv

import android.graphics.Bitmap
import android.graphics.Color
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.google.zxing.common.BitMatrix

class WifiInfoActivity : FragmentActivity() {

    companion object {
        // TODO: replace with your hotel's real Wi-Fi network name and password
        const val WIFI_SSID = "Pine_Hotel_Guest"
        const val WIFI_PASSWORD = "welcome2024"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wifi_info)

        findViewById<TextView>(R.id.wifi_ssid_text).text =
            getString(R.string.wifi_ssid_label, WIFI_SSID)
        findViewById<TextView>(R.id.wifi_password_text).text =
            getString(R.string.wifi_password_label, WIFI_PASSWORD)

        val qrContent = "WIFI:T:WPA;S:$WIFI_SSID;P:$WIFI_PASSWORD;;"
        val qrBitmap = generateQrCode(qrContent, 400, 400)
        findViewById<ImageView>(R.id.wifi_qr_code).setImageBitmap(qrBitmap)
    }

    private fun generateQrCode(content: String, width: Int, height: Int): Bitmap {
        val bitMatrix: BitMatrix = MultiFormatWriter().encode(
            content, BarcodeFormat.QR_CODE, width, height
        )
        val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565)
        for (x in 0 until width) {
            for (y in 0 until height) {
                bitmap.setPixel(x, y, if (bitMatrix.get(x, y)) Color.BLACK else Color.WHITE)
            }
        }
        return bitmap
    }
}