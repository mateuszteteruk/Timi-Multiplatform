package com.akjaw.timi.kmp.core.shared.composition

import com.akjaw.timi.kmp.core.shared.time.KlockTimestampProvider
import com.akjaw.timi.kmp.core.shared.time.TimestampMillisecondsFormatter
import com.akjaw.timi.kmp.core.shared.time.TimestampProvider
import org.koin.dsl.module

val coreSharedModule = module {
    factory<TimestampProvider> { KlockTimestampProvider() }
    factory { TimestampMillisecondsFormatter() }
    coreSharedPlatformModule()
}
