package com.ucsfderrick.posts.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.google.gson.GsonBuilder
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.internal.schedulers.IoScheduler
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import kotlinx.android.synthetic.main.activity_item_list.*
import kotlinx.android.synthetic.main.item_list.*

import com.ucsfderrick.posts.R
import com.ucsfderrick.posts.adapters.PostsAdapter
import com.ucsfderrick.posts.interfaces.PostAPIService

class ItemListActivity : AppCompatActivity() {

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private var twoPane: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item_list)

        setupToolbar()
        loadPosts()
    }

    private fun setupToolbar() {
        toolbar.title = "UCSF Posts"
        setSupportActionBar(toolbar)
    }

    private fun loadPosts() {
        if (item_detail_container != null) {
            twoPane = true
        }

        val retrofit = Retrofit.Builder().addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .baseUrl(PostAPIService.BASE_URL).build()

        val postsApi = retrofit.create(PostAPIService::class.java)

        var response = postsApi.getAllPosts()

        response.observeOn(AndroidSchedulers.mainThread()).subscribeOn(IoScheduler()).subscribe {
            recyclerView_posts.adapter = PostsAdapter(this, it, twoPane)
        }
    }
}
