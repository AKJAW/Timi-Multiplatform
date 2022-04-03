package co.touchlab.kampkit

import co.touchlab.kermit.Logger
import co.touchlab.kermit.StaticConfig
import co.touchlab.kermit.platformLogWriter
import com.akjaw.timi.kmp.core.shared.composition.coreSharedModule
import com.akjaw.timi.kmp.feature.stopwatch.composition.stopwatchModule
import com.akjaw.timi.kmp.feature.task.dependency.composition.databaseModule
import com.akjaw.timi.kmp.feature.task.dependency.list.composition.taskListModule
import org.koin.core.module.Module
import org.koin.dsl.module

val kmmKoinModules: List<Module> = listOf(
    module {
        // platformLogWriter() is a relatively simple config option, useful for local debugging. For production
        // uses you *may* want to have a more robust configuration from the native platform. In KaMP Kit,
        // that would likely go into platformModule expect/actual.
        // See https://github.com/touchlab/Kermit
        // TODO create DebugLogger abstraction for Kermit
        val baseLogger = Logger(
            config = StaticConfig(logWriterList = listOf(platformLogWriter())),
            "KampKit"
        )
        factory { (tag: String?) -> if (tag != null) baseLogger.withTag(tag) else baseLogger }
    },
    coreSharedModule,
    stopwatchModule,
    databaseModule,
    taskListModule,
)
