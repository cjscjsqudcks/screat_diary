package com.example.screat_diary

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.edit
import androidx.core.widget.addTextChangedListener

class DiaryActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_diary)

        val handler=Handler(Looper.getMainLooper())
        val textPreference=getSharedPreferences("diary", MODE_PRIVATE)
        val dtext=findViewById<EditText>(R.id.dtext)

        dtext.setText(textPreference.getString("detail",""))

        val savethread= Runnable {
            textPreference.edit{
                putString("detail",dtext.text.toString())

            }
        }//계속 저장하다가 스레드가 꼬이지 않게 하기 위해

        dtext.addTextChangedListener{
            handler.removeCallbacks(savethread)//이전에 돌아가고 있던 스레드를 종료 시킴
            handler.postDelayed(savethread,500)
        }
    }

}