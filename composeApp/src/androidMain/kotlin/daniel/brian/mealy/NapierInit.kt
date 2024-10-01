package daniel.brian.mealy

import android.app.Application
import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier

class NapierInit : Application() {
    override fun onCreate() {
        super.onCreate()
        Napier.base(DebugAntilog())
    }
}