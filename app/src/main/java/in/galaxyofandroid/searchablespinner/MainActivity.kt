package `in`.galaxyofandroid.searchablespinner

import `in`.galaxyofandroid.spinerdialog.SpinnerDialog
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.github.beTechLabs.spinnydialogapp.CustomModel
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {
    lateinit var basicSpinnerDialog: SpinnerDialog<String>
    lateinit var customSpinnyDialog: SpinnerDialog<CustomModel>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val items = ArrayList<String>()
        items.add("Test")
        items.add("Test1")
        items.add("Test2")
        items.add("Test3")
        items.add("Test4")
        items.add("Test5")
        items.add("Test6")
        items.add("Test7")
        items.add("Test8")
        items.add("Test9")
        items.add("Test10")
        items.add("Test11")
        items.add("Test12")
        items.add("Test13")

        val customModels = mutableListOf<CustomModel>()
        customModels.add(CustomModel("First", "Second"))
        customModels.add(CustomModel("First1", "Second1"))
        customModels.add(CustomModel("First2", "Second2"))
        customModels.add(CustomModel("First3", "Second3"))
        customModels.add(CustomModel("First4", "Second4"))
        customModels.add(CustomModel("First5", "Second5"))
        customSpinnyDialog = SpinnerDialog(
                activity = this@MainActivity,
                items = customModels,
                searchTextColor = ContextCompat.getColor(this, R.color.colorAccent),
                adapter = DefaultRecyclerAdapter(customModels)
                { item, position ->
                    customSpinnyDialog.close()
                    Toast.makeText(this@MainActivity, "$item  $position", Toast.LENGTH_SHORT).show()
                    txt.text = "$item Position: $position"
                }

        )

        customSpinnyDialog.isCancellable = false
        customSpinnyDialog.isShowKeyboard = false
        customSpinnyDialog.searchBoxGravity = SpinnerDialog.Gravity.Center
        show_second.setOnClickListener { customSpinnyDialog.showSpinnyDialog() }
        basicSpinnerDialog = SpinnerDialog(
                activity = this@MainActivity,
                items = items,
                onItemClicked = { item, position ->
                    basicSpinnerDialog.close()
                    Toast.makeText(this@MainActivity, "$item  $position", Toast.LENGTH_SHORT).show()
                    txt.text = "$item Position: $position"
                }, onCreationRequested = {

        }
        )

        basicSpinnerDialog.isCancellable = true
        basicSpinnerDialog.isShowKeyboard = false
        basicSpinnerDialog.searchBoxGravity = SpinnerDialog.Gravity.Center
        show.setOnClickListener { basicSpinnerDialog.showSpinnyDialog() }
    }


    override fun onBackPressed() {
        super.onBackPressed()
        basicSpinnerDialog.close()
    }
}