package co.touchlab.kampkit

import co.touchlab.kampkit.timer.Timer
import co.touchlab.kampkit.timer.TimerViewModel
import co.touchlab.kermit.Logger
import co.touchlab.kermit.StaticConfig
import co.touchlab.kermit.platformLogWriter
import com.akjaw.timi.kmp.core.shared.composition.coreSharedModule
import com.akjaw.timi.kmp.feature.stopwatch.composition.stopwatchModule
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import org.koin.core.KoinApplication
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.core.parameter.parametersOf
import org.koin.core.scope.Scope
import org.koin.dsl.module

fun initKoin(appModules: List<Module>): KoinApplication {
    val koinApplication = startKoin {
        modules(
            *appModules.toTypedArray(),
            coreModule,
            coreSharedModule,
            stopwatchModule,
        )
    }
    return koinApplication
}

// TODO rename
private val coreModule = module {
    factory { Timer(scope = CoroutineScope(Dispatchers.Default + SupervisorJob())) }
    factory { TimerViewModel(timer = get()) }

    // platformLogWriter() is a relatively simple config option, useful for local debugging. For production
    // uses you *may* want to have a more robust configuration from the native platform. In KaMP Kit,
    // that would likely go into platformModule expect/actual.
    // See https://github.com/touchlab/Kermit
    val baseLogger = Logger(config = StaticConfig(logWriterList = listOf(platformLogWriter())), "KampKit")
    factory { (tag: String?) -> if (tag != null) baseLogger.withTag(tag) else baseLogger }
}

internal inline fun <reified T> Scope.getWith(vararg params: Any?): T {
    return get(parameters = { parametersOf(*params) })
}

// Simple function to clean up the syntax a bit
fun KoinComponent.injectLogger(tag: String): Lazy<Logger> = inject { parametersOf(tag) }
