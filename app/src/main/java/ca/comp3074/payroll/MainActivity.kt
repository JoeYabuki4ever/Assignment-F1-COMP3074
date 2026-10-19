package ca.comp3074.payroll

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import java.math.BigDecimal
import java.math.RoundingMode
import java.text.NumberFormat
import java.util.Locale

/** Main screen: obtains three inputs and displays the four required outputs. */
class MainActivity : Activity() {

    private lateinit var hoursInput: EditText
    private lateinit var hourlyRateInput: EditText
    private lateinit var taxRateInput: EditText
    private lateinit var payValue: TextView
    private lateinit var overtimeValue: TextView
    private lateinit var totalValue: TextView
    private lateinit var taxValue: TextView

    private val currencyFormat: NumberFormat by lazy {
        NumberFormat.getCurrencyInstance(Locale.CANADA).apply {
            minimumFractionDigits = 2
            maximumFractionDigits = 2
            roundingMode = RoundingMode.HALF_UP
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        hoursInput = findViewById(R.id.hoursInput)
        hourlyRateInput = findViewById(R.id.hourlyRateInput)
        taxRateInput = findViewById(R.id.taxRateInput)
        payValue = findViewById(R.id.payValue)
        overtimeValue = findViewById(R.id.overtimeValue)
        totalValue = findViewById(R.id.totalValue)
        taxValue = findViewById(R.id.taxValue)

        findViewById<Button>(R.id.calculateButton).setOnClickListener { calculatePay() }
        findViewById<Button>(R.id.resetButton).setOnClickListener { resetScreen() }
        findViewById<Button>(R.id.aboutButton).setOnClickListener {
            startActivity(Intent(this, AboutActivity::class.java))
        }

        if (savedInstanceState != null) {
            payValue.text = savedInstanceState.getString(KEY_PAY, "--")
            overtimeValue.text = savedInstanceState.getString(KEY_OVERTIME, "--")
            totalValue.text = savedInstanceState.getString(KEY_TOTAL, "--")
            taxValue.text = savedInstanceState.getString(KEY_TAX, "--")
        }
    }

    private fun calculatePay() {
        val hours = readNonNegative(hoursInput, getString(R.string.hours_label)) ?: return
        val hourlyRate = readNonNegative(hourlyRateInput, getString(R.string.hourly_rate_label)) ?: return
        val taxRate = readNonNegative(taxRateInput, getString(R.string.tax_rate_label)) ?: return

        if (taxRate > BigDecimal.ONE) {
            taxRateInput.error = getString(R.string.tax_rate_error)
            taxRateInput.requestFocus()
            return
        }

        val result = PayCalculator.calculate(hours, hourlyRate, taxRate)
        payValue.text = currencyFormat.format(result.pay)
        overtimeValue.text = currencyFormat.format(result.overtimePay)
        totalValue.text = currencyFormat.format(result.totalPay)
        taxValue.text = currencyFormat.format(result.tax)
    }

    private fun readNonNegative(field: EditText, label: String): BigDecimal? {
        val value = field.text.toString().trim().toBigDecimalOrNull()
        if (value == null || value < BigDecimal.ZERO) {
            field.error = getString(R.string.validation_error, label)
            field.requestFocus()
            return null
        }
        return value
    }

    private fun resetScreen() {
        hoursInput.text.clear()
        hourlyRateInput.text.clear()
        taxRateInput.text.clear()
        payValue.text = "--"
        overtimeValue.text = "--"
        totalValue.text = "--"
        taxValue.text = "--"
        hoursInput.requestFocus()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(KEY_PAY, payValue.text.toString())
        outState.putString(KEY_OVERTIME, overtimeValue.text.toString())
        outState.putString(KEY_TOTAL, totalValue.text.toString())
        outState.putString(KEY_TAX, taxValue.text.toString())
    }

    private companion object {
        const val KEY_PAY = "pay"
        const val KEY_OVERTIME = "overtime"
        const val KEY_TOTAL = "total"
        const val KEY_TAX = "tax"
    }
}
