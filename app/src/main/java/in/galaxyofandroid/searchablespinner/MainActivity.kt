package `in`.galaxyofandroid.searchablespinner

import `in`.galaxyofandroid.spinerdialog.OnSpinerItemClick
import `in`.galaxyofandroid.spinerdialog.SpinnerDialog
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.TextView
import android.widget.Toast
import java.util.*

class MainActivity : AppCompatActivity() {
    var items = ArrayList<String>()
    lateinit var spinnerDialog: SpinnerDialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val selectedItems = findViewById<View>(R.id.txt) as TextView
        items.add("Mumbai")
        items.add("Delhi")
        items.add("Bengaluru")
        items.add("Hyderabad")
        items.add("Ahmedabad")
        items.add("Chennai")
        items.add("Kolkata")
        items.add("Surat")
        items.add("Pune")
        items.add("Jaipur")
        items.add("Lucknow")
        items.add("Kanpur")
        spinnerDialog = SpinnerDialog(this@MainActivity, items,
                "Select or Search City")
        spinnerDialog.setCancellable(true)
        spinnerDialog.setShowKeyboard(false)
        spinnerDialog.bindOnSpinerListener(object : OnSpinerItemClick {
            override fun onClick(item: String, position: Int) {
                Toast.makeText(this@MainActivity, "$item  $position", Toast.LENGTH_SHORT).show()
                selectedItems.text = "$item Position: $position"
            }
        })
        findViewById<View>(R.id.show).setOnClickListener { spinnerDialog.showSpinerDialog() }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        spinnerDialog.closeSpinerDialog()
    }
}