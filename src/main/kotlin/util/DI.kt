package util

/**
 * Serves solely to identify constructor params that come from DI
 */
annotation class Injected

/**
 * Identifies constructor params that were acquired through
 * some computations, not from DI (although, they are still acquired through DI)
 */
annotation class Computed