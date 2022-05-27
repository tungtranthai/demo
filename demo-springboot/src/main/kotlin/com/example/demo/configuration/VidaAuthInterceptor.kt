package com.example.demo.configuration

import com.example.demo.identifyverification.dto.VidaAuthenticationTokenModel
import com.example.demo.identifyverification.response.VidaAuthenticationTokenResponse
import com.example.demo.retrofit.httpclient.VidaApiKeyServiceHttpClient
import com.fasterxml.jackson.databind.ObjectMapper
import com.github.benmanes.caffeine.cache.Caffeine
import com.github.benmanes.caffeine.cache.LoadingCache
import retrofit2.Call
import okhttp3.Interceptor
import okhttp3.Response
import okhttp3.ResponseBody
import org.slf4j.LoggerFactory


import java.util.concurrent.TimeUnit
import javax.annotation.PostConstruct

class VidaAuthInterceptor(
  private val vidaApiKeyServiceHttpClient: VidaApiKeyServiceHttpClient,
  private val vidaJacksonObjectMapper: ObjectMapper
) : Interceptor {
  companion object {
    private val LOGGER = LoggerFactory.getLogger(VidaAuthInterceptor::class.java)
    private const val AUTHORIZATION = "Authorization"
    private const val BEARER = "Bearer"
    private const val VIDA_SERVICE_API_KEY = "VIDA_SERVICE_API_KEY"
    private const val VIDA_SERVICE_CLIENT_ID = "VIDA_SERVICE_CLIENT_ID"
    private const val VIDA_SERVICE_CLIENT_SECRET  = "VIDA_SERVICE_CLIENT_SECRET"
  }

  // Auth0 token is valid for 5 minutes, we set expiry of 3 minutes to make sure the key is valid in every request.
  private val vidaApiKeyCache: LoadingCache<String, String> = Caffeine
    .newBuilder()
    .refreshAfterWrite(3, TimeUnit.MINUTES)
    .build {
      this.requestAuth0Token(
        VIDA_SERVICE_CLIENT_ID,
        VIDA_SERVICE_CLIENT_SECRET,
      )
    }

  override fun intercept(chain: Interceptor.Chain): Response {
    val chainRequest = chain.request()
    val token = vidaApiKeyCache.get(VIDA_SERVICE_API_KEY)!!
    val request = chainRequest
      .newBuilder()
      .header(AUTHORIZATION, "$BEARER $token")

    return chain.proceed(request.build())
  }

  private fun requestAuth0Token(clientId: String, clientSecret: String): String {
    val callBody = VidaAuthenticationTokenModel(
      clientId = clientId,
      clientSecret = clientSecret,
      grantType = "grantType",
      scope = "scope"
    )
    val call: Call<ResponseBody> = vidaApiKeyServiceHttpClient.getAuthorizationToken(
      body = callBody
    )
    with(call.execute()) {
      return if (isSuccessful) {
        val body = body()?.string()
        LOGGER.debug("Retrieved new token for clientId=${clientId}")
        val response = vidaJacksonObjectMapper.readValue(body, VidaAuthenticationTokenResponse::class.java)
        response.accessToken
      } else {
        val error = errorBody()?.string()
        LOGGER.error("code=${code()}, message=${message()}, error=${error}")
        throw RuntimeException(error)
      }
    }
  }


  @PostConstruct
  private fun prepareCache() {
    this.vidaApiKeyCache.get(VIDA_SERVICE_API_KEY)
  }

}
