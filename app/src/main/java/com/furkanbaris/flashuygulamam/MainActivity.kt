package com.furkanbaris.flashuygulamam

import android.content.Context
import android.hardware.camera2.CameraAccessException
import android.hardware.camera2.CameraManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import android.widget.ToggleButton
import androidx.annotation.RequiresApi
import kotlin.coroutines.coroutineContext


private var toggleFlashbuton: ToggleButton? = null
private var kameraAsistani :CameraManager?= null
private var camera_kimlik: String? = null

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Toast.makeText(applicationContext, "hoşgeldin", Toast.LENGTH_SHORT).show()

        toggleFlashbuton =
            findViewById(R.id.toggleFlashLight) //toggle flash butona bir ıd tanımladık.

        kameraAsistani =
            getSystemService(Context.CAMERA_SERVICE) as CameraManager //arka planda kamera ya erişim istedik.

        try {
            camera_kimlik =
                kameraAsistani!!.cameraIdList[0] //"0" arka kamera anlamına gelir. "1" ise ön kamera anlamına gelir.
        } catch (e: CameraAccessException) {
            e.printStackTrace()
        }

    }

/* RequiresApi ayarlamamız gerek çünkü
API seviyesi 10'un altındaki cihazlarda kamera flash ünitesi bulunmamakta.*/

    fun Flash_fonksiyonu_1(view: View?) {
        if (toggleFlashbuton!!.isChecked) {
            /* Kamera kaynağının başka bir hizmet tarafından kullanılıp kullanılmadığını kontrol etmek için istisna işlenir.*/
            try {
                kameraAsistani!!.setTorchMode(
                    camera_kimlik!!,
                    true
                )//toggle açık olduğunda flash'ı aç dedik.
                //şimdi kullanıcıya altta açıldığına dair yazı çıkartalım.(Bu ksımı erkanın
                Toast.makeText(this@MainActivity,"Doğdu güneşim",Toast.LENGTH_SHORT).show()
            } catch (e: CameraAccessException) {
                e.printStackTrace()
                Toast.makeText(this,e.message,Toast.LENGTH_LONG).show()
            }
        } else {
            kameraAsistani!!.setTorchMode(
                camera_kimlik!!,
                false
            )//bu kod "true" modda olan feneri kapalı moda alır.
            //aynı açarken yaptığımız gibi yine altta bir yazı gösterelim.
            Toast.makeText(this@MainActivity, "Işık söndü  ", Toast.LENGTH_SHORT).show()        }
    } //inene kadar telefa at deneyelim tamam telefon görünmüyor burada  ben seni duyabiliyom kütüphanedeyim konuşamıyom :D

    /*şimdi yazacağımız kod sen flash ı açtıktan kapatmanı sağlıyacak */
    @RequiresApi(api = Build.VERSION_CODES.M)
    override fun finish() {
        super.finish()
        try {
            kameraAsistani!!.setTorchMode(
                camera_kimlik!!,
                false
            )//bu kod "true" modda olan feneri kapalı moda alır.
            //aynı açarken yaptığımız gibi yine altta bir yazı gösterelim.
            Toast.makeText(this@MainActivity, "Işık söndü  ", Toast.LENGTH_SHORT).show()
        } catch (e: CameraAccessException) {
            e.printStackTrace()
        }


    }
}
