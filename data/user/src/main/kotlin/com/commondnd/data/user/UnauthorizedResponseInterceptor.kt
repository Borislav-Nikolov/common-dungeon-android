package com.commondnd.data.user

import okhttp3.Interceptor
import okhttp3.Response

class UnauthorizedResponseInterceptor(
    private val onUnauthorizedResponse: () -> Unit
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val response = chain.proceed(request)

        if (response.code == 401) {
            onUnauthorizedResponse()
        }

        return response
    }
}
