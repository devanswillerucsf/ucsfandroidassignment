package com.ucsfderrick.posts.adapters

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import kotlinx.android.synthetic.main.content_row.view.*
import com.ucsfderrick.posts.R
import com.ucsfderrick.posts.activities.ItemDetailActivity
import com.ucsfderrick.posts.activities.ItemListActivity
import com.ucsfderrick.posts.fragments.ItemDetailFragment
import com.ucsfderrick.posts.models.Post

class PostsAdapter(
    private val parentActivity: ItemListActivity,
    private val posts: List<Post>,
    private val twoPane: Boolean
) :
    RecyclerView.Adapter<PostsAdapter.ViewHolder>() {

    private val onClickListener: View.OnClickListener

    init {
        onClickListener = View.OnClickListener { v ->
            val item = v.tag as Post
            if (twoPane) {
                val fragment = ItemDetailFragment().apply {
                    arguments = Bundle().apply {
                        putString(ItemDetailFragment.ARG_ITEM_ID, item.id.toString())
                    }
                }
                parentActivity.supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.item_detail_container, fragment)
                    .commit()
            } else {
                val intent = Intent(v.context, ItemDetailActivity::class.java).apply {
                    putExtra(ItemDetailFragment.ARG_ITEM_ID, item.id.toString())
                }
                v.context.startActivity(intent)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        //val view = LayoutInflater.from(parent.context)
           // .inflate(R.layout.item_list_content, parent, false)
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.content_row, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val post = posts[position]
        holder.titleView.text = "Post Title:"
        holder.contentView.text = post.title

        with(holder.itemView) {
            tag = post
            setOnClickListener(onClickListener)
        }
    }

    override fun getItemCount() = posts.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val titleView: TextView = view.textView_title
        val contentView: TextView = view.textView_content
    }
}