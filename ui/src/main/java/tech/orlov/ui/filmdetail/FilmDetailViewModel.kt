package tech.orlov.ui.filmdetail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import tech.orlov.domain.usecases.GetFilmByIdCommand
import tech.orlov.ui.formatters.FilmVoFormatter
import tech.orlov.ui.vo.FilmVo
import tech.orlov.ui.vo.ScreenStateVo
import javax.inject.Inject

class FilmDetailViewModel @Inject constructor(
    private val getFilmByIdCommand: GetFilmByIdCommand,
    private val filmVoFormatter: FilmVoFormatter
) : ViewModel() {

    private val disposables = CompositeDisposable()
    private var filmId: Long? = null

    val film = MutableLiveData<FilmVo>()
    val screenState = MutableLiveData<ScreenStateVo>()

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
            disposables.add(getFilmByIdCommand.getFilmById(it)
                .map(filmVoFormatter::formatFilm)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ currentFilm ->
                    film.value = currentFilm
                    screenState.value = ScreenStateVo.CONTENT
                }, {
                    screenState.value = ScreenStateVo.ERROR
                }))
        }?: run { screenState.value = ScreenStateVo.ERROR }
    }

    override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }
}
