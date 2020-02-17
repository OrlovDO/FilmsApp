package tech.orlov.ui.filmlist

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.main_fragment.*
import tech.orlov.ui.R
import tech.orlov.ui.filmdetail.FilmDetailFragment
import tech.orlov.ui.vo.ScreenStateVo
import javax.inject.Inject


class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    @Inject
    lateinit var viewModel: MainViewModel

    private val filmAdapter =
        FilmAdapter(mutableListOf()){
            navigateToDetail(it)
        }

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.onStart()

        swipeRefreshLayout.setOnRefreshListener {
            swipeRefreshLayout.isRefreshing = false
            viewModel.refreshFilmsList()
        }

        filmRecycler.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = filmAdapter
        }
        observeFilms()
        observeScreenState()
    }

    private fun navigateToDetail(filmId: Long){
        parentFragmentManager.beginTransaction()
            .addToBackStack(null)
            .add(R.id.container, FilmDetailFragment.newInstance(filmId))
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            .commit()
    }

    private fun observeFilms() {
        viewModel.films.observe(viewLifecycleOwner, Observer { films ->
            filmAdapter.updateFilms(films)
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
        filmRecycler.visibility = View.GONE
    }

    private fun showContent(){
        errorTextView.visibility = View.GONE
        progressBar.visibility = View.GONE
        filmRecycler.visibility = View.VISIBLE
    }

    private fun showProgress(){
        errorTextView.visibility = View.GONE
        progressBar.visibility = View.VISIBLE
        filmRecycler.visibility = View.GONE
    }

}
