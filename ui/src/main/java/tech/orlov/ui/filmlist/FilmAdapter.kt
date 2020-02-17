package tech.orlov.ui.filmlist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_film.view.*
import tech.orlov.ui.R
import tech.orlov.ui.utils.loadImage
import tech.orlov.ui.vo.FilmVo

class FilmAdapter(
    private var films: MutableList<FilmVo>,
    private var onFilmClickListener: (filmId: Long) -> Unit
) : RecyclerView.Adapter<FilmAdapter.FilmViewHolder>() {

    fun updateFilms(newFilms: List<FilmVo>) {
        films.clear()
        films.addAll(newFilms)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): FilmViewHolder {
        return FilmViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_film, parent, false)
        )
    }

    override fun getItemCount() = films.size

    override fun onBindViewHolder(holder: FilmViewHolder, position: Int) {
        holder.bind(films[position])
    }

    inner class FilmViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        private val posterImageView = view.posterImageView
        private val titleTextView = view.titleTextView

        fun bind(film: FilmVo) {
            titleTextView.text = film.title
            posterImageView.loadImage(film.postersPath)
            view.setOnClickListener {
                onFilmClickListener.invoke(film.id)
            }
        }
    }

}