package tech.orlov.filmsapp.di

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import tech.orlov.data.DataModule
import tech.orlov.filmsapp.App
import tech.orlov.ui.PresentationModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        ApplicationModule::class,
        PresentationModule::class,
        DataModule::class
    ]
)
interface ApplicationComponent {

    fun inject(application: App)

    @Component.Builder
    interface Builder {

        fun build(): ApplicationComponent

        @BindsInstance
        fun applicationBind(application: Application): Builder

    }
}