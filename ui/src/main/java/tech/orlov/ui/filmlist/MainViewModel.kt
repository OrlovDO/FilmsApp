package tech.orlov.ui.filmlist

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import tech.orlov.domain.usecases.GetAllFilmsCommand
import tech.orlov.ui.formatters.FilmVoFormatter
import tech.orlov.ui.vo.FilmVo
import tech.orlov.ui.vo.ScreenStateVo
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val getAllFilmsCommand: GetAllFilmsCommand,
    private val filmVoFormatter: FilmVoFormatter
) : ViewModel() {

    val films = MutableLiveData<List<FilmVo>>()
    val screenState = MutableLiveData<ScreenStateVo>()

    fun onStart(){
        screenState.value = ScreenStateVo.LOADING
        refreshFilmsList()
    }


    fun refreshFilmsList(){
        if(screenState.value != ScreenStateVo.CONTENT){
            screenState.value = ScreenStateVo.LOADING
        }
        getAllFilmsCommand.getAllFilms()
            .map(filmVoFormatter::formatFilmsList)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ filmList ->
                films.value = filmList
                screenState.value = ScreenStateVo.CONTENT
            },{
                films.value = emptyList()
                screenState.value = ScreenStateVo.ERROR
            })
    }
}
