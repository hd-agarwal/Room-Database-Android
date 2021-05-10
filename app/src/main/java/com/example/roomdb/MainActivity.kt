package com.example.roomdb

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.example.roomdb.adapters.UserAdapter
import com.example.roomdb.database.AppDatabase
import com.example.roomdb.models.User
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {


    companion object {
        const val CALL_PERMISSION_CODE = 1234
    }
    private val db by lazy {
        Room.databaseBuilder(
            this,
            AppDatabase::class.java,
            "User.db"
        ).fallbackToDestructiveMigration().build()
    }
    lateinit var callIntent:Intent
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
         val userList = ArrayList<User>()
        rvUsers.layoutManager = LinearLayoutManager(this)
        val adapter = UserAdapter(userList,object: OnRvClick{
            override fun onClick(pos: Int) {
                var phone = userList[pos].phone
                if (!phone.startsWith("+91"))
                    phone = "+91$phone"
                if (phone.length != 13) {
                    Toast.makeText(applicationContext, "Phone number provided is incorrect", Toast.LENGTH_SHORT)
                        .show()
                    return
                }

                callIntent=Intent( Intent.ACTION_CALL,Uri.parse("tel:$phone"))
                if (ContextCompat.checkSelfPermission(
                        applicationContext,
                        Manifest.permission.CALL_PHONE
                    ) == PackageManager.PERMISSION_GRANTED
                ) {
                    startActivity( callIntent)
                } else {
                    ActivityCompat.requestPermissions(
                        this@MainActivity,
                        arrayOf(Manifest.permission.CALL_PHONE),
                        CALL_PERMISSION_CODE
                    )
                }

            }

        })
        rvUsers.adapter = adapter
        btnAdd.setOnClickListener {
            if (etName.text.toString().isEmpty()) {
                etName.error = "Please enter name"
                return@setOnClickListener
            }
            if (etPhone.text.toString().isEmpty()) {
                etPhone.error = "Please Enter Phone number"
                return@setOnClickListener
            }
            GlobalScope.launch(Dispatchers.IO) {
                db.userDao().insertUser(
                    User(
                        name=etName.text.toString(),
                        age= if(etAge.text.toString().isEmpty())null else etAge.text.toString(),
                        address = if(etAddress.text.toString().isEmpty())null else etAddress.text.toString(),
                        phone = etPhone.text.toString()
                    )
                )
            }
        }
        db.userDao().getAllUsers().observe(this, Observer {
            userList.clear()
            userList.addAll(it)
            Log.d("TAG","$it")
            adapter.notifyDataSetChanged()
        })
    }
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == CALL_PERMISSION_CODE && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            startActivity( callIntent)
        } else {
            Toast.makeText(applicationContext, "Permission denied", Toast.LENGTH_SHORT).show()
        }
    }
}