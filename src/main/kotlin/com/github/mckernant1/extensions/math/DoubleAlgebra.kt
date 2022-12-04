package com.github.mckernant1.extensions.math

import kotlin.math.floor
import java.math.BigDecimal
import java.math.RoundingMode

object DoubleAlgebra  {

    fun Double.pow(exponent: Int): BigDecimal = this.toBigDecimal().pow(exponent)

    fun Double.round(digits: Int, roundingMode: RoundingMode = RoundingMode.HALF_EVEN): Double =
        BigDecimal(this).setScale(digits, roundingMode).toDouble()

    fun Double.isInt(): Boolean = floor(this) == this

}
