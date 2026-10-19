package ca.comp3074.payroll

import java.math.BigDecimal
import org.junit.Assert.assertEquals
import org.junit.Assert.assertThrows
import org.junit.Test

class PayCalculatorTest {
    private fun bd(number: String) = BigDecimal(number)
    private fun assertMoney(expected: String, actual: BigDecimal) {
        assertEquals(0, bd(expected).compareTo(actual))
    }

    @Test fun underFortyHoursHasNoOvertime() {
        val r = PayCalculator.calculate(bd("35"), bd("20"), bd("0.10"))
        assertMoney("700", r.pay)
        assertMoney("0", r.overtimePay)
        assertMoney("700", r.totalPay)
        assertMoney("70", r.tax)
    }

    @Test fun exactlyFortyHoursHasNoOvertime() {
        val r = PayCalculator.calculate(bd("40"), bd("25"), bd("0.15"))
        assertMoney("1000", r.pay)
        assertMoney("0", r.overtimePay)
        assertMoney("1000", r.totalPay)
        assertMoney("150", r.tax)
    }

    @Test fun overFortyHoursCalculatesOvertimeAndTaxOnRegularPay() {
        val r = PayCalculator.calculate(bd("45"), bd("20"), bd("0.10"))
        assertMoney("800", r.pay)
        assertMoney("150", r.overtimePay)
        assertMoney("950", r.totalPay)
        assertMoney("80", r.tax)
    }

    @Test fun supportsDecimalHoursAndRates() {
        val r = PayCalculator.calculate(bd("40.5"), bd("18.50"), bd("0.12"))
        assertMoney("740.00", r.pay)
        assertMoney("13.875", r.overtimePay)
        assertMoney("753.875", r.totalPay)
        assertMoney("88.8000", r.tax)
    }

    @Test fun zeroHoursIsAllowed() {
        val r = PayCalculator.calculate(bd("0"), bd("20"), bd("0.10"))
        assertMoney("0", r.totalPay)
        assertMoney("0", r.tax)
    }

    @Test fun rejectsInvalidInputs() {
        assertThrows(IllegalArgumentException::class.java) {
            PayCalculator.calculate(bd("-1"), bd("20"), bd("0.1"))
        }
        assertThrows(IllegalArgumentException::class.java) {
            PayCalculator.calculate(bd("1"), bd("20"), bd("1.25"))
        }
    }
}
