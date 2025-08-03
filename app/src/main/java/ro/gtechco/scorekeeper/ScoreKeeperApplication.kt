package ro.gtechco.scorekeeper

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import ro.gtechco.scorekeeper.di.module

class ScoreKeeperApplication:Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@ScoreKeeperApplication)
            modules(module)
        }
    }
}