package com.example.copycrunchyroll
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import com.google.android.material.card.MaterialCardView


class CardAdapter(
    private val items: List<String>,
    private val onClick: (View) -> Unit
) : RecyclerView.Adapter<CardAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.title)
        val card: MaterialCardView = itemView.findViewById(R.id.card)
        val btnTerms: MaterialButton = itemView.findViewById(R.id.btnTerms)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.card_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.title.text = items[position]
        holder.card.transitionName = "card_transition_$position"

        holder.card.setOnClickListener {
            onClick(holder.card)
        }

        holder.btnTerms.setOnClickListener {
            val context = holder.itemView.context
            val intent = Intent(context, webview::class.java)
            context.startActivity(intent)
        }
    }
}
