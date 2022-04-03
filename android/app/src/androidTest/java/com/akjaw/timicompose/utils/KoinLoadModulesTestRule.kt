package com.akjaw.timicompose.utils

import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runners.model.Statement
import org.koin.core.context.GlobalContext.loadKoinModules
import org.koin.core.context.GlobalContext.unloadKoinModules
import org.koin.core.module.Module

class KoinLoadModulesTestRule(vararg modules: Module) : TestRule {

    private val modules: List<Module> = listOf(*modules)

    override fun apply(base: Statement, description: Description): Statement {
        return object : Statement() {
            override fun evaluate() {
                unloadKoinModules(modules)
                loadKoinModules(modules)
                base.evaluate()
            }
        }
    }
}
