package com.ucsfderrick.posts.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.gson.GsonBuilder
import com.ucsfderrick.posts.R
import com.ucsfderrick.posts.adapters.CommentsAdapter
import com.ucsfderrick.posts.interfaces.PostAPIService
import com.ucsfderrick.posts.models.Comment
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.internal.schedulers.IoScheduler
import kotlinx.android.synthetic.main.activity_item_detail.*
import kotlinx.android.synthetic.main.item_detail.*
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/**
 * A fragment representing a single Item detail screen.
 * This fragment is either contained in a [ItemListActivity]
 * in two-pane mode (on tablets) or a [ItemDetailActivity]
 * on handsets.
 */
class ItemDetailFragment : Fragment() {

    /**
     * The dummy content this fragment is presenting.
     */
    private var id: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            if (it.containsKey(ARG_ITEM_ID)) {
                id = it.getString(ARG_ITEM_ID)
               // activity?.toolbar_layout?.title = "Comments"
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.item_detail, container, false)

        id?.let {
            loadComments(id)
        }

        return rootView
    }

    private fun loadComments(id: String?) {
        val retrofit = Retrofit.Builder().addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .baseUrl(PostAPIService.BASE_URL).build()

        val postsApi = retrofit.create(PostAPIService::class.java)

        var response = postsApi.getComments(id)

        response.observeOn(AndroidSchedulers.mainThread()).subscribeOn(IoScheduler()).subscribe {
            // There is a bug with the api where all comments are returned
            // regardless of which postId is passed as a parameter
            // so in order to get this sample app working we need to filter ourselves
            // https://github.com/typicode/jsonplaceholder/issues/91
            var comments: List<Comment> = it.filter { s -> s.postId == id?.toInt() }
            recyclerView_comments.adapter = CommentsAdapter(this, comments)
        }
    }

    companion object {
        const val ARG_ITEM_ID = "item_id"
    }
}
