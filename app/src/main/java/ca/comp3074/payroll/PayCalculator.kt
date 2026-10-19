package ca.comp3074.payroll

import java.math.BigDecimal

/**
 * Payroll calculations follow the COMP3074 Assignment 1 specification.
 * In particular, tax is computed on regular pay, NOT total pay.
 * Tax rate is a fraction: use 0.20 for 20 percent.
 */
object PayCalculator {
    private val STANDARD_HOURS = BigDecimal("40")
    private val OVERTIME_MULTIPLIER = BigDecimal("1.5")
    private val MAX_TAX_RATE = BigDecimal.ONE

    data class PayrollResult(
        val pay: BigDecimal,
        val overtimePay: BigDecimal,
        val totalPay: BigDecimal,
        val tax: BigDecimal
    )

    fun calculate(hours: BigDecimal, hourlyRate: BigDecimal, taxRate: BigDecimal): PayrollResult {
        require(hours >= BigDecimal.ZERO) { "Hours cannot be negative" }
        require(hourlyRate >= BigDecimal.ZERO) { "Hourly rate cannot be negative" }
        require(taxRate >= BigDecimal.ZERO && taxRate <= MAX_TAX_RATE) {
            "Tax rate must be between 0 and 1"
        }

        val regularPay: BigDecimal
        val overtimePay: BigDecimal
        if (hours <= STANDARD_HOURS) {
            regularPay = hours.multiply(hourlyRate)
            overtimePay = BigDecimal.ZERO
        } else {
            regularPay = STANDARD_HOURS.multiply(hourlyRate)
            overtimePay = hours.subtract(STANDARD_HOURS)
                .multiply(hourlyRate)
                .multiply(OVERTIME_MULTIPLIER)
        }

        return PayrollResult(
            pay = regularPay,
            overtimePay = overtimePay,
            totalPay = regularPay.add(overtimePay),
            tax = regularPay.multiply(taxRate)
        )
    }
}
