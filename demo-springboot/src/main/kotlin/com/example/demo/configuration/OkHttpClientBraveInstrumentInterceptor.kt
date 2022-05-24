package com.example.demo.configuration

import brave.sampler.Sampler
import okhttp3.Interceptor
import okhttp3.Response
import org.slf4j.LoggerFactory
import org.springframework.cloud.sleuth.Span
import org.springframework.cloud.sleuth.Tracer

class OkHttpClientBraveInstrumentInterceptor(val tracer: Tracer) : Interceptor {

  companion object {
    private val LOGGER = LoggerFactory.getLogger(OkHttpClientBraveInstrumentInterceptor::class.java)
  }

  override fun intercept(chain: Interceptor.Chain): Response {
    val orgReq = chain.request()
    val body = orgReq.body
    val spanName = "${orgReq.method}-${orgReq.url}"
    val parent = tracer.currentSpan()
    val current: Span = if (null != parent) {
      tracer.nextSpan(parent).name(spanName)
    } else {
      tracer.nextSpan().name(spanName)
    }

    return try {
      tracer.withSpan(current.start())
      val request = orgReq
        .newBuilder()
        .headers(orgReq.headers)
        .header("X-b3-sampled", Sampler.NEVER_SAMPLE.toString())
        .header("X-b3-traceid", current.context()!!.traceId())
        .header("X-b3-spanid", current.context()!!.spanId())
        .method(orgReq.method, body)
        .build();

      LOGGER.debug("sending a ${request.method} request to url [${request.url}] with headers [${request.headers}]")
      chain.proceed(request)
    } finally {
      current.end()
    }
  }
}
