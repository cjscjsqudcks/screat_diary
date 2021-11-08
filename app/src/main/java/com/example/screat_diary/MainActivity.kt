package com.example.screat_diary

import android.content.Context
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.preference.Preference
import android.widget.Button
import android.widget.NumberPicker
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.AppCompatButton
import androidx.core.content.edit

class MainActivity : AppCompatActivity() {
    private val np1 : NumberPicker by lazy{
        findViewById<NumberPicker>(R.id.np1)
            .apply {
                maxValue=9
                minValue=0
            }
    }
    private val np2 : NumberPicker by lazy{
        findViewById<NumberPicker>(R.id.np2)
            .apply {
                maxValue=9
                minValue=0
            }
    }
    private val np3 : NumberPicker by lazy{
        findViewById<NumberPicker>(R.id.np3)
            .apply {
                maxValue=9
                minValue=0
            }//필요에 의해 선언 될때 괄호 안의 속성도 같이 선언 되며 괄호안에는 선언된 데이터 타입에 맞는 속성 입력 가능
    }
    private val unlockbtn : AppCompatButton by lazy{
        findViewById<AppCompatButton>(R.id.unlockbtn)
    }
    private val refreshbtn: AppCompatButton by lazy{
        findViewById<AppCompatButton>(R.id.refreshbtn)
    }

    private  var changingTime=false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        np1
        np2
        np3

        val pwPreference=getSharedPreferences("pw",Context.MODE_PRIVATE)


        refreshbtn.setOnClickListener {
            var pwsave=pwPreference.getString("pw","000")
            val pwfromuser="${np1.value}${np2.value}${np3.value}"//스트링 값으로 변환 시켜줌
            if(!changingTime){
                if(pwsave.equals(pwfromuser)){
                    changingTime=true
                    refreshbtn.setBackgroundColor(Color.RED)

                }
                else{
                    showAlert()
                }

            }
            else{
                changingTime=false
                pwPreference.edit {
                    putString("pw",pwfromuser)
                    commit()
                }
                refreshbtn.setBackgroundColor(Color.WHITE)
            }

        }// 비밀 번호 변경 버튼


        unlockbtn.setOnClickListener {
            var pwsave=pwPreference.getString("pw","000")
            if(changingTime){
                Toast.makeText(this, "비밀번호 변경중 입니다",Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val pwfromuser="${np1.value}${np2.value}${np3.value}"//스트링 값으로 변환 시켜줌
            if(pwsave.equals(pwfromuser)){
                val i=Intent(this,DiaryActivity::class.java)
                startActivity(i)
            }
            else{
                showAlert()

            }
        }

    }


    private fun showAlert(){
        AlertDialog.Builder(this)
            .setTitle("실패!!")
            .setMessage("비밀번호가 잘못되었습니다")
            .setPositiveButton("확인"){_,_->
            }
            .show()
    }


}