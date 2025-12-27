package com.commondnd.data.user

import okhttp3.Interceptor
import okhttp3.Response

class UserAuthTokenInterceptor(
    private val tokenProvider: () -> String?
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()

        tokenProvider()?.let { token ->
            request.addHeader("Authorization", "Bearer $token")
        }

        return chain.proceed(request.build())
    }
}
