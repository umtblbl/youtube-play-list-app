package com.example.youtubeplaylist

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    val API_KEY = "1Ad23t4gdsIzaJ6WUO2"
    val CHANNEL_ID = "UCXebEXcYgA"

    var gelenVeri:PlayListData? = null
    var oynatmaListeleri:List<Item>? = null

    var myAdapter:PlaylistAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        var apiInterface = ApiClient.client?.create(ApiInterface::class.java)
        var apiCall = apiInterface?.getChannelPlayList(CHANNEL_ID, API_KEY, 25)
        apiCall?.enqueue(object: Callback<PlayListData> {
            @SuppressLint("WrongConstant")
            override fun onResponse(call: Call<PlayListData>, response: Response<PlayListData>) {

                gelenVeri = response.body()
                oynatmaListeleri = gelenVeri?.items
                supportActionBar?.subtitle = "Toplam Liste:${oynatmaListeleri?.size}"


                myAdapter = PlaylistAdapter(oynatmaListeleri)
                var layoutManager = LinearLayoutManager(this@MainActivity, LinearLayoutManager.VERTICAL, false)
                recyclerViewPlayList.layoutManager = layoutManager

                recyclerViewPlayList.adapter = myAdapter





            }

            override fun onFailure(call: Call<PlayListData>, t: Throwable) {
                Log.e("HATA", ""+t.printStackTrace())
            }


        })






    }
}
