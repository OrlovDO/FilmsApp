package tech.orlov.ui

import dagger.Module
import dagger.android.ContributesAndroidInjector
import tech.orlov.ui.filmdetail.FilmDetailFragment
import tech.orlov.ui.filmlist.MainFragment

@Module
abstract class PresentationModule {

    @ContributesAndroidInjector
    abstract fun contributeMainFragment(): MainFragment

    @ContributesAndroidInjector
    abstract fun contributeFilmDetailFragment(): FilmDetailFragment

}