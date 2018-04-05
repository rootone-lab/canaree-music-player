package dev.olog.msc.music.service.equalizer

import android.media.audiofx.Virtualizer
import dev.olog.msc.domain.interactor.prefs.EqualizerPrefsUseCase
import dev.olog.msc.interfaces.equalizer.IVirtualizer
import dev.olog.msc.utils.k.extension.printStackTraceOnDebug
import javax.inject.Inject

class VirtualizerImpl @Inject constructor(
        private val equalizerPrefsUseCase: EqualizerPrefsUseCase

) : IVirtualizer {

    private var virtualizer : Virtualizer? = null

    override fun getStrength(): Int {
        return useOrDefault({ virtualizer!!.roundedStrength.toInt() }, 0)
    }

    override fun setStrength(value: Int) {
        use {
            virtualizer!!.setStrength(value.toShort())
        }
    }

    override fun setEnabled(enabled: Boolean) {
        use {
            virtualizer!!.enabled = enabled
        }
    }

    override fun onAudioSessionIdChanged(audioSessionId: Int) {
        release()

        use {
            virtualizer = Virtualizer(0, audioSessionId)
            virtualizer!!.enabled = equalizerPrefsUseCase.isEqualizerEnabled()
        }

        use {
            val properties = equalizerPrefsUseCase.getVirtualizerSettings()
            if (properties.isNotBlank()){
                virtualizer!!.properties = Virtualizer.Settings(properties)
            }
        }
    }

    override fun release() {
        virtualizer?.let {
            try {
                equalizerPrefsUseCase.saveVirtualizerSettings(it.properties.toString())
            } catch (ex: Exception){}
            use {
                it.release()
            }
        }
    }

    private fun use(action: () -> Unit){
        try {
            action()
        } catch (ex: Exception){
            ex.printStackTraceOnDebug()
        }
    }

    private fun <T> useOrDefault(action: () -> T, default: T): T {
        return try {
            action()
        } catch (ex: Exception){
            ex.printStackTraceOnDebug()
            default
        }
    }

}