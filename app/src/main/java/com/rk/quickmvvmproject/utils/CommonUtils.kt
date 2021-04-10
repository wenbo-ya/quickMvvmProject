package com.rk.quickmvvmproject.utils

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rk.quickmvvmproject.MyApplication


import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType
import okhttp3.RequestBody
import org.json.JSONObject


//扩展函数
fun ViewModel.toast(string: String?) {
    val mToast = Toast.makeText(MyApplication.mApplicationContext, string, Toast.LENGTH_LONG)

    mToast.show()
}


//延迟
fun ViewModel.clickDelay(block: suspend () -> Unit, error: suspend (Throwable) -> Unit) =
    viewModelScope.launch(Dispatchers.Main) {
        try {
            block()
        } catch (error: Throwable) {
            error(error)
        }

    }

infix fun <T> Collection<T>.has(element: T) = contains(element)

//泛型实例化
inline fun <reified T> startActivityT(context: Context) {
    val intent = Intent(context, T::class.java)
    context.startActivity(intent)
}

inline fun <reified T> startActivityT(context: Context, block: Intent.() -> Unit) {
    val intent = Intent(context, T::class.java)
    intent.block()
    context.startActivity(intent)
}


fun JSONObject.addParameter(json: JSONObject.() -> Unit): JSONObject {
    json.apply {
        json.invoke(this@addParameter)

    }
    return this
}


fun <T> Observable<T>.simplifySubscribe(): Observable<T> {
    return this.subscribeOn(Schedulers.io())
        .unsubscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
}


fun Context.shareData(block: SharedPreferences.Editor.() -> Unit) {
    val edit = this.getSharedPreferences("data", Context.MODE_PRIVATE).edit()
    edit.block()
    edit.apply()
}





/**
 * EditText文字监听
 */
open class SimpleTextWatcher : TextWatcher {
    override fun afterTextChanged(s: Editable?) {}
    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
}


/**
 * 软键盘消失
 */
fun dissmissSoft(isDismiss: Boolean, view: View) {
    if (isDismiss) {
        val systemService = MyApplication.mApplicationContext
            .getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        systemService.hideSoftInputFromWindow(
            view.windowToken,
            InputMethodManager.HIDE_NOT_ALWAYS
        )
    }
}


fun getRequestBody(string: String?): RequestBody {
    return RequestBody.create(MediaType.parse("application/json; charset=utf-8"), string)
}


suspend fun  getA(){
    withContext(Dispatchers.IO){
       Log.i("wwb","aaaa")
        return@withContext
    }
}