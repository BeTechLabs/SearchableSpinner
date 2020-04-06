package `in`.galaxyofandroid.spinerdialog

import android.app.AlertDialog
import android.app.AlertDialog.Builder
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.InsetDrawable
import android.support.annotation.ColorInt
import android.support.v4.content.ContextCompat
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.TextView


@Suppress("UNCHECKED_CAST")
class SpinnerDialog<T>(
        private val context: Context,
        private val items: List<T>,
        private val onItemClicked: ((String, Int) -> (Unit))? = null,
        private val onCreationRequested: (() -> Unit)? = null,
        private val adapter: SearchableRecyclerAdapter<T> = DefaultRecyclerAdapter(
                items.toList() as List<String>,
                onItemClicked
        ) as SearchableRecyclerAdapter<T>,
        private val dialogTitle: String = "Title",
        private val style: Int = 0,
        private val creationText: String = "Create new Item",
        @ColorInt private val searchTextColor: Int = ContextCompat.getColor(context, R.color.colorForeground)
) {

    var alertDialog: AlertDialog? = null
    var isCancellable: Boolean = false
    var isShowKeyboard: Boolean = false
    var searchBoxGravity = Gravity.Start


    fun showSpinnyDialog() {

        val alertDialogBuilder = Builder(context)
        val inflatedView = LayoutInflater.from(context).inflate(R.layout.dialog_layout, null)


        alertDialogBuilder.setView(inflatedView)

        val title = inflatedView.findViewById<View>(R.id.spinner_title_tv) as TextView
        val recyclerView = inflatedView.findViewById<View>(R.id.items_rv) as RecyclerView
        val searchBox = inflatedView.findViewById<View>(R.id.search_et) as EditText
        val creationTextView = inflatedView.findViewById<View>(R.id.creation_tv) as TextView
        onCreationRequested?.let {
            creationTextView.apply {
                visibility = View.VISIBLE
                setOnClickListener { it() }
                text = creationText
            }
        }
        title.text = dialogTitle
        searchBox.gravity = searchBoxGravity.value
        searchBox.setTextColor(searchTextColor)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.addItemDecoration(
                DividerItemDecoration(
                        context,
                        DividerItemDecoration.VERTICAL
                ).apply {
                    setDrawable(
                            ContextCompat.getDrawable(
                                    context,
                                    R.drawable.item_recycler_separator
                            )!!
                    )
                }
        )
        adapter.resetItems()
        searchBox.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
            override fun afterTextChanged(editable: Editable) {
                adapter.applySearch(searchBox.text.toString())
            }
        })

        if (isShowKeyboard) {
            showKeyboard(searchBox)
        }
        setupAlertDialog(alertDialogBuilder)
    }

    private fun setupAlertDialog(alertDialogBuilder: Builder) {
        alertDialog = alertDialogBuilder.create()

        alertDialog?.window?.apply {
            setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
            setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            attributes!!.windowAnimations = style

            val back = ColorDrawable(Color.TRANSPARENT)
            val inset = InsetDrawable(back, 10)
            setBackgroundDrawable(inset)
        }

        alertDialog?.setCancelable(isCancellable)
        alertDialog?.setCanceledOnTouchOutside(isCancellable)
        alertDialog?.show()
    }

    fun close() {
        hideKeyboard()
        if (alertDialog != null) {
            alertDialog!!.cancel()
        }
    }

    private fun hideKeyboard() {
        try {
            val inputManager =
                    context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputManager.hideSoftInputFromWindow(
                    LayoutInflater.from(context).inflate(R.layout.dialog_layout, null).windowToken,
                    InputMethodManager.HIDE_NOT_ALWAYS
            )
        } catch (e: Exception) {
        }
    }

    private fun showKeyboard(ettext: EditText) {
        ettext.requestFocus()
        ettext.postDelayed({
            val keyboard =
                    context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            keyboard.showSoftInput(ettext, 0)
        }
                , 200)
    }

    enum class Gravity(val value: Int) {
        Center(android.view.Gravity.CENTER),
        Start(android.view.Gravity.START),
        End(android.view.Gravity.END)
    }

}