package co.touchlab.kampkit

import co.touchlab.kermit.Logger
import com.akjaw.timi.kmp.feature.task.domain.GetTasks
import com.akjaw.timi.kmp.feature.task.domain.model.Task
import com.akjaw.timi.kmp.feature.task.domain.model.TaskColor
import kotlinx.cinterop.ObjCClass
import kotlinx.cinterop.ObjCProtocol
import kotlinx.cinterop.getOriginalKotlinClass
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import org.koin.core.Koin
import org.koin.core.KoinApplication
import org.koin.core.context.startKoin
import org.koin.core.parameter.parametersOf
import org.koin.core.qualifier.Qualifier
import org.koin.dsl.module

fun initKoinIos(): KoinApplication = startKoin {
    modules(
        *kmmKoinModules.toTypedArray(),
        module {
            factory<GetTasks> { FakeGetTasks() }
        }
    )
}

class FakeGetTasks : GetTasks {

    private val defaultTasks = listOf(
        Task(
            id = 1,
            name = "First task",
            backgroundColor = TaskColor(132 / 255f, 212 / 255f, 240 / 255f)
        ),
        Task(
            id = 2,
            name = "Second task",
            backgroundColor = TaskColor(230 / 255f, 240 / 255f, 132 / 255f)
        ),
        Task(
            id = 3,
            name = "Third task",
            backgroundColor = TaskColor(132 / 255f, 240 / 255f, 161 / 255f)
        ),
    )
    val tasks: MutableStateFlow<List<Task>> = MutableStateFlow(defaultTasks)

    override fun execute(): Flow<List<Task>> = tasks
}

// Access from Swift to create a logger
@Suppress("unused")
fun Koin.loggerWithTag(tag: String) =
    get<Logger>(qualifier = null) { parametersOf(tag) }

fun Koin.get(objCClass: ObjCClass): Any {
    val kClazz = getOriginalKotlinClass(objCClass)!!
    return getOrThrow { get(kClazz) }
}

fun Koin.get(objCProtocol: ObjCProtocol): Any {
    val kClazz = getOriginalKotlinClass(objCProtocol)!!
    return getOrThrow { get(kClazz) }
}

fun Koin.get(objCClass: ObjCClass, qualifier: Qualifier?, parameter: Any): Any {
    val kClazz = getOriginalKotlinClass(objCClass)!!
    return getOrThrow { get(kClazz, qualifier) { parametersOf(parameter) } }
}

fun Koin.get(objCClass: ObjCClass, parameter: Any): Any {
    val kClazz = getOriginalKotlinClass(objCClass)!!
    return getOrThrow { get(kClazz, null) { parametersOf(parameter) } }
}

fun Koin.get(objCClass: ObjCClass, qualifier: Qualifier?): Any {
    val kClazz = getOriginalKotlinClass(objCClass)!!
    return getOrThrow { get(kClazz, qualifier, null) }
}

private val kermit = Logger.withTag("Koin")

private fun getOrThrow(get: () -> Any): Any {
    try {
        return get()
    } catch (error: Throwable) {
        kermit.i { "error: $error" }
        throw error
    }
}
