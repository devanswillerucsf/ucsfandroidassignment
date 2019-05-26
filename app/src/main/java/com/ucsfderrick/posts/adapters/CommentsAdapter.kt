package com.ucsfderrick.posts.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import kotlinx.android.synthetic.main.content_row.view.*

import com.ucsfderrick.posts.R
import com.ucsfderrick.posts.fragments.ItemDetailFragment
import com.ucsfderrick.posts.models.Comment

class CommentsAdapter(
    private val parentActivity: ItemDetailFragment,
    private val comments: List<Comment>
) :
    RecyclerView.Adapter<CommentsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.content_row, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val comment = comments[position]
        holder.titleView.text = "Comment:"
        holder.contentView.text = comment.body
    }

    override fun getItemCount() = comments.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val titleView: TextView = view.textView_title
        val contentView: TextView = view.textView_content
    }
}