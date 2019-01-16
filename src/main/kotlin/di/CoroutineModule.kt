package di

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import org.koin.dsl.module.module

object Scopes {
    const val LongPoll = "long poll"
    const val Requests = "requests"
    const val Presentation = "scope presentation"
}

val CoroutineModule = module {
    single(name = Scopes.LongPoll) { CoroutineScope(Dispatchers.IO) }
    single(name = Scopes.Requests) { CoroutineScope(Dispatchers.IO + SupervisorJob()) }
    single(name = Scopes.Presentation) { CoroutineScope(Dispatchers.Default) }
}