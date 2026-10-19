package ca.comp3074.payroll

import android.app.Activity
import android.os.Bundle
import android.view.MenuItem
import android.widget.TextView

/** About screen; ActionBar's Up arrow returns to the EXISTING MainActivity. */
class AboutActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)

        actionBar?.setDisplayHomeAsUpEnabled(true)
        actionBar?.setHomeButtonEnabled(true)

        findViewById<TextView>(R.id.studentName).text = getString(R.string.student_name)
        findViewById<TextView>(R.id.studentId).text = getString(R.string.student_id)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            // Do NOT use startActivity(): finish() reveals the existing MainActivity.
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}
