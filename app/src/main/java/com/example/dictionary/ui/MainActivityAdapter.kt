package com.example.dictionary.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.dictionary.R
import com.example.dictionary.domain.words.Words

class MainActivityAdapter:RecyclerView.Adapter<MainActivityAdapter.MainActivityViewHolder>() {
    private var listWords = listOf<Words>()

    fun refreshList(){
 //       this.listWords = newListWords
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = 10

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainActivityViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_translate_word,parent,false)
        return MainActivityViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MainActivityViewHolder, position: Int) {
//        holder.translation.text = " Перевод"
//        holder.transcription.text = "transcription"
    }

    class MainActivityViewHolder(itemView: View) :RecyclerView.ViewHolder(itemView){
        val translation:TextView = itemView.findViewById(R.id.translation_tv)
        val transcription:TextView = itemView.findViewById(R.id.transcription_tv)
    }
}