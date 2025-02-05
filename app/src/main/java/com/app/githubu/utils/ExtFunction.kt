package com.app.githubu.utils

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.TextView
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

fun View.invisible() {
    visibility = View.INVISIBLE
}

fun View.visible() {
    visibility = View.VISIBLE
}

fun View.gone() {
    visibility = View.GONE
}

/**
 * SNACKBAR MULTILINE
 */
fun Snackbar.allowInfiniteLines(): Snackbar {
    return apply {
        (view.findViewById<View?>(com.google.android.material.R.id.snackbar_text) as? TextView?)?.isSingleLine =
            false
    }
}

/**
 * PREVENT DOUBLE CLICK
 */
fun View.setSafeOnClickListener(onSafeClick: (View) -> Unit) {
    val safeClickListener = SafeClickListener {
        onSafeClick(it)
    }
    setOnClickListener(safeClickListener)
}

/**
 * Delay Edittext
 * Purpose :
 * To handle search query
 */
fun AppCompatEditText.afterTextChangedDebounce(delayMillis: Long, input: (String) -> Unit) {
    var lastInput = ""
    var debounceJob: Job? = null
    val uiScope = CoroutineScope(Dispatchers.Main + SupervisorJob())
    this.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(editable: Editable?) {
            if (editable != null) {
                val newtInput = editable.toString()
                debounceJob?.cancel()
                if (lastInput != newtInput) {
                    lastInput = newtInput
                    debounceJob = uiScope.launch {
                        delay(delayMillis)
                        if (lastInput == newtInput) {
                            input(newtInput)
                        }
                    }
                }
            }
        }

        override fun beforeTextChanged(cs: CharSequence?, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(cs: CharSequence?, start: Int, before: Int, count: Int) {}
    })
}

/**
 * OPEN BROWSER
 * From string into url
 */
fun String?.asUri(): Uri? {
    return try {
        Uri.parse(this)
    } catch (e: Exception) {
        null
    }
}

fun Uri?.openInBrowser(context: Context) {
    this ?: return // Do nothing if uri is null
    val browserIntent = Intent(Intent.ACTION_VIEW, this)
    ContextCompat.startActivity(context, browserIntent, null)
}

/**
 * VALIDATE STRING IF EMPTY, IT SHOULD BE "-"
 */
fun String?.orDash(): String = this ?: "-"

/**
 * VALIDATE INT IF EMPTY, IT SHOULD BE 0
 */
fun Int?.orZero(): Int = this ?: 0