package com.example.apidatafetchusingretrofitlibrary

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


const val BASE_URL = "https://jsonplaceholder.typicode.com/"
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        getMyData()
    }

    private fun getMyData() {
        val retrofitBuilder = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(ApiInterface::class.java)

        val retrofitData = retrofitBuilder.getData()

        retrofitData.enqueue(object : Callback<List<MyDataItem>?> {
            override fun onResponse(
                call: Call<List<MyDataItem>?>,
                response: Response<List<MyDataItem>?>

            ) {
                val myStringBuilder = StringBuilder()
                val responseBody = response.body()!!
                for(myData in responseBody){
                    myStringBuilder.append(myData.id)
                    myStringBuilder.append("\n")
                }

                val display = findViewById<TextView>(R.id.txtId)
                display.text = myStringBuilder
            }

            override fun onFailure(call: Call<List<MyDataItem>?>, t: Throwable) {
                Log.d("MainActivity", "onFailure: "+ t.message)
            }
        })

    }
}