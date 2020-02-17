package tech.orlov.ui.filmdetail

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.film_detail_fragment.*
import tech.orlov.ui.R
import tech.orlov.ui.utils.loadImage
import tech.orlov.ui.vo.ScreenStateVo
import javax.inject.Inject

class FilmDetailFragment : Fragment() {

    companion object {
        private const val FILM_ID = "film_id"

        fun newInstance(filmId: Long) = FilmDetailFragment().apply {
            arguments = Bundle().apply {
                putLong(FILM_ID, filmId)
            }
        }
    }

    @Inject
    lateinit var viewModel: FilmDetailViewModel

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.film_detail_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel.onStart(arguments?.getLong(FILM_ID))

        swipeRefreshLayout.setOnRefreshListener {
            swipeRefreshLayout.isRefreshing = false
            viewModel.refreshFilm()
        }

        observeFilm()
        observeScreenState()
    }

    private fun observeFilm(){
        viewModel.film.observe(viewLifecycleOwner, Observer {film ->
            posterImageView.loadImage(film.postersPath)
            titleTextView.text = film.title
            overviewTextView.text = film.overview
        })
    }

    private fun observeScreenState() {
        viewModel.screenState.observe(viewLifecycleOwner, Observer { state ->
            when(state){
                ScreenStateVo.CONTENT -> showContent()
                ScreenStateVo.ERROR -> showError()
                ScreenStateVo.LOADING -> showProgress()
                else -> showError()
            }
        })
    }

    private fun showError(){
        errorTextView.visibility = View.VISIBLE
        progressBar.visibility = View.GONE
        contentLayout.visibility = View.GONE
    }

    private fun showContent(){
        errorTextView.visibility = View.GONE
        progressBar.visibility = View.GONE
        contentLayout.visibility = View.VISIBLE
    }

    private fun showProgress(){
        errorTextView.visibility = View.GONE
        progressBar.visibility = View.VISIBLE
        contentLayout.visibility = View.GONE
    }

}
