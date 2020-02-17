package tech.orlov.filmsapp.di

import dagger.Module
import dagger.android.ContributesAndroidInjector
import tech.orlov.ui.MainActivity

@Module
abstract class ApplicationModule {

    @ContributesAndroidInjector
    abstract fun contributeMainActivity(): MainActivity

}