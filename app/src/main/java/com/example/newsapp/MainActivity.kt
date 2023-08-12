package com.example.newsapp

import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.browser.customtabs.CustomTabsIntent
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.newsapp.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity(), Adapter.NewsItemClicked {

    lateinit var binding: ActivityMainBinding
    private lateinit var madapter:Adapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.recyclerView.layoutManager=LinearLayoutManager(this)
        val items=fetchData()
        madapter=Adapter(this)

        binding.recyclerView.adapter=madapter

        fetchData()

    }





    override fun onItemClicked(item: News) {

        val intent = CustomTabsIntent.Builder()
            .build()
        intent.launchUrl(this@MainActivity, Uri.parse(item.url))

    }


    fun fetchData(){

        var newsArray=ArrayList<News>()
        val apiSample:String= "https://saurav.tech/NewsAPI/everything/cnn.json"
        val reqQueue:RequestQueue=Volley.newRequestQueue(this)
        val request=JsonObjectRequest(Request.Method.GET,apiSample, null  ,{res->



            val jsonArray=res.getJSONArray("articles")


            for (i in 0 until jsonArray.length()){
                val jsonObj=jsonArray.getJSONObject(i)


                val news=News(
                    jsonObj.getString("title"),
                    jsonObj.getString("author"),
                    jsonObj.getString("url"),
                    jsonObj.getString("urlToImage")
                )
                newsArray.add(news)
                //Log.d("volley",newsArray.toString())
            }
            madapter.updatedNews(newsArray)
    }, { err ->

        }


        )

        reqQueue.add(request)









}}