package io.github.bloodnighttw.whateverBot.utils.callback

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private class CallExample<T>(
	val r: ((Call<T>, Response<T>) -> Unit),
	val f: ((Call<T>, Throwable) -> Unit)
) : Callback<T> {
	override fun onResponse(call: Call<T>, response: Response<T>) {
		r.invoke(call, response)
	}

	override fun onFailure(call: Call<T>, t: Throwable) {
		f.invoke(call, t)
	}

}

class CallBackExtension<T>(
	private val call: Call<T>,
	private var response: ((Call<T>, Response<T>) -> Unit)? = null,
	private var fail: ((Call<T>, Throwable) -> Unit)? = null
) {
	fun onResponse(response: (call: Call<T>, Response<T>) -> Unit): CallBackExtension<T> {
		this.response = response
		return this
	}

	fun onFailure(failure: (call: Call<T>, t: Throwable) -> Unit): CallBackExtension<T> {
		this.fail = failure
		return this
	}

	fun run() {
		val callExample = CallExample<T>(
			response ?: throw IllegalArgumentException("your callbcak response must be non-null"),
			fail ?: throw IllegalArgumentException("your callback fail must be non-null")
		)
		call.enqueue(callExample)
	}

}

fun <T> Call<T>.onResponse(response: (call: Call<T>, Response<T>) -> Unit): CallBackExtension<T> {
	return CallBackExtension<T>(this, response = response)
}

fun <T> Call<T>.onFailure(failure: (call: Call<T>, t: Throwable) -> Unit): CallBackExtension<T> {
	return CallBackExtension<T>(this, fail = failure)
}