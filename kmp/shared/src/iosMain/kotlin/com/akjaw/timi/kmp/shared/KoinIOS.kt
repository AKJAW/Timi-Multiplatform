package com.akjaw.timi.kmp.shared

import co.touchlab.kermit.Logger
import kotlinx.cinterop.ObjCClass
import kotlinx.cinterop.ObjCProtocol
import kotlinx.cinterop.getOriginalKotlinClass
import org.koin.core.Koin
import org.koin.core.KoinApplication
import org.koin.core.context.startKoin
import org.koin.core.parameter.parametersOf
import org.koin.core.qualifier.Qualifier

fun initKoinIos(): KoinApplication = startKoin {
    modules(
        *kmmKoinModules.toTypedArray()
    )
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

fun Koin.get(objCProtocol: ObjCProtocol, parameter: Any): Any {
    val kClazz = getOriginalKotlinClass(objCProtocol)!!
    return getOrThrow { get(kClazz) { parametersOf(parameter) } }
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
