package tech.orlov.ui.filmdetail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import tech.orlov.domain.usecases.GetFilmByIdCommand
import tech.orlov.ui.formatters.FilmVoFormatter
import tech.orlov.ui.vo.FilmVo
import tech.orlov.ui.vo.ScreenStateVo
import javax.inject.Inject

class FilmDetailViewModel @Inject constructor(
    private val getFilmByIdCommand: GetFilmByIdCommand,
    private val filmVoFormatter: FilmVoFormatter
) : ViewModel() {

    val film = MutableLiveData<FilmVo>()
    val screenState = MutableLiveData<ScreenStateVo>()
    private var filmId: Long? = null

    fun onStart(filmId: Long?) {
        screenState.value = ScreenStateVo.LOADING
        this.filmId = filmId
        refreshFilm()
    }

    fun refreshFilm() {
        if (screenState.value != ScreenStateVo.CONTENT) {
            screenState.value = ScreenStateVo.LOADING
        }
        filmId?.let{
            getFilmByIdCommand.getFilmById(it)
                .map(filmVoFormatter::formatFilm)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ currentFilm ->
                    film.value = currentFilm
                    screenState.value = ScreenStateVo.CONTENT
                }, {
                    screenState.value = ScreenStateVo.ERROR
                })
        }?: run { screenState.value = ScreenStateVo.ERROR }
    }

}
