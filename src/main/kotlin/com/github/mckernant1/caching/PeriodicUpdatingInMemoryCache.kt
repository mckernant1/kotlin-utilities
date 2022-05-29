package com.github.mckernant1.caching

import com.github.mckernant1.extensions.executor.scheduleAtFixedRate
import kotlin.reflect.KProperty
import java.time.Duration
import java.util.concurrent.ScheduledFuture
import java.util.concurrent.ScheduledThreadPoolExecutor

class PeriodicUpdatingInMemoryCache<T>(
    period: Duration,
    threadPool: ScheduledThreadPoolExecutor = ScheduledThreadPoolExecutor(1),
    private val updateFunc: () -> T
) {

    @get:Synchronized
    @set:Synchronized
    @Volatile
    private var internalItem: T? = null

    private val future: ScheduledFuture<*> = threadPool.scheduleAtFixedRate(period) {
        internalItem = updateFunc()
    }

    val item: T
        get() = internalItem ?: updateFunc()

    /**
     * See **[ScheduledFuture.cancel]**
     */
    fun cancel(mayInterruptIfRunning: Boolean = false): Boolean = future.cancel(mayInterruptIfRunning)
}
