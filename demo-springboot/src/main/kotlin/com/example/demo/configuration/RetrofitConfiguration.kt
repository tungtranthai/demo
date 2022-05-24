package com.example.demo.configuration

import com.example.demo.retrofit.httpclient.VidaApiKeyServiceHttpClient
import com.example.demo.retrofit.httpclient.VidaVerificationServiceHttpClient
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.PropertyNamingStrategy
import com.fasterxml.jackson.module.kotlin.KotlinModule
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory
import java.util.concurrent.TimeUnit
import org.springframework.cloud.sleuth.Tracer

@Configuration
class RetrofitConfiguration {

  @Bean
  fun retrofitOkHttpClient(
    tracer: Tracer
  ): OkHttpClient {
    val logging = HttpLoggingInterceptor()
    logging.setLevel(HttpLoggingInterceptor.Level.BASIC)

    val okBraveInterceptor = OkHttpClientBraveInstrumentInterceptor(tracer)
    return OkHttpClient
      .Builder()
      .addInterceptor(logging)
      .addInterceptor(okBraveInterceptor)
      .readTimeout(10L, TimeUnit.SECONDS)
      .build()
  }

  @Bean
  fun vidaRetrofitOkHttpClient(
    tracer: Tracer,
    vidaApiKeyServiceHttpClient: VidaApiKeyServiceHttpClient,
    vidaJacksonObjectMapper: ObjectMapper
  ): OkHttpClient {
    val logging = HttpLoggingInterceptor()
    logging.setLevel(HttpLoggingInterceptor.Level.BASIC)

    val okBraveInterceptor = OkHttpClientBraveInstrumentInterceptor(tracer)
    val vidaAuthInterceptor = VidaAuthInterceptor(
      vidaApiKeyServiceHttpClient,
      vidaJacksonObjectMapper
    )
    return OkHttpClient
      .Builder()
      .addInterceptor(logging)
      .addInterceptor(okBraveInterceptor)
      .addInterceptor(vidaAuthInterceptor)
      .readTimeout(10L, TimeUnit.SECONDS)
      .build()
  }

  @Bean
  fun vidaApiKeyHttpClient(
    retrofitOkHttpClient: OkHttpClient,
    @Qualifier("vidaJacksonObjectMapper")
    vidaJacksonObjectMapper: ObjectMapper
  ): VidaApiKeyServiceHttpClient {
    val vidaTokenBaseUrl = "http://localhost:3000/"

    return Retrofit
      .Builder()
      .baseUrl(vidaTokenBaseUrl)
      .addConverterFactory(JacksonConverterFactory.create(vidaJacksonObjectMapper))
      .client(retrofitOkHttpClient)
      .build()
      .create(VidaApiKeyServiceHttpClient::class.java)
  }

  @Bean
  fun vidaMotherNameVerificationServiceHttpClient(
    vidaRetrofitOkHttpClient: OkHttpClient,
    @Qualifier("vidaJacksonObjectMapper")
    vidaJacksonObjectMapper: ObjectMapper
  ): VidaVerificationServiceHttpClient {
    val vidaMotherNameVerificationUrl = "http://localhost:3000/"
    return Retrofit
      .Builder()
      .baseUrl(vidaMotherNameVerificationUrl)
      .addConverterFactory(JacksonConverterFactory.create(vidaJacksonObjectMapper))
      .client(vidaRetrofitOkHttpClient)
      .build()
      .create(VidaVerificationServiceHttpClient::class.java)
  }

  @Bean("vidaJacksonObjectMapper")
  fun vidaJacksonObjectMapper(builder: Jackson2ObjectMapperBuilder): ObjectMapper {
    builder.propertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE)
    builder.modules(KotlinModule())
    return builder.createXmlMapper(false).build()
  }
}
