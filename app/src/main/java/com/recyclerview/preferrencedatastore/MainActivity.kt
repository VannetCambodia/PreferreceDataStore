package com.recyclerview.preferrencedatastore

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.asLiveData
import androidx.lifecycle.observe
import com.recyclerview.preferrencedatastore.databinding.ActivityMainBinding
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlin.system.measureTimeMillis

class MainActivity : AppCompatActivity() {

    private lateinit var activityMainActivity: ActivityMainBinding;
    private lateinit var nameEt: EditText;
    private lateinit var emailEt: EditText;
    private lateinit var submitRetrieveBtn: Button;
    private lateinit var userManager:UserManagement;
    private var name = ""
    private var email = ""
    private lateinit var tv:TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar!!.hide()
        activityMainActivity = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainActivity.root)

        userManager = UserManagement(this)

        nameEt = activityMainActivity.nameEt;
        emailEt = activityMainActivity.emailEt;
        submitRetrieveBtn = activityMainActivity.retrieveBtn;
        tv = activityMainActivity.tv;

        submitRetrieveBtn.setOnClickListener {
            if (submitRetrieveBtn.text.equals(getString(R.string.retrieve))){
                retrieveData()
            }else{
                submitData()
            }
        }
    }

    private fun retrieveData(){
        submitRetrieveBtn.text = getText(R.string.submit)
        userManager.nameFlow.asLiveData().observe(this,{
            name = it
            nameEt.setText(it)
        })

        userManager.emailFlow.asLiveData().observe(this,{
            email = it
            emailEt.setText(it)
        })
    }

    private fun submitData(){
        submitRetrieveBtn.text = getText(R.string.retrieve)

        name = nameEt.text.toString()
        email = emailEt.text.toString()

        GlobalScope.launch {
            userManager.storeUserInfo(name,email)
        }
    }
}