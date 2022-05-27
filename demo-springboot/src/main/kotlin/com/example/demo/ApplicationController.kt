package com.example.demo

import com.example.demo.identifyverification.dto.MotherNameRequest
import com.example.demo.identifyverification.model.VerificationNoResultException
import com.example.demo.identifyverification.service.VidaApiClient
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.jooq.tools.json.JSONObject
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.time.OffsetDateTime
import javax.annotation.PostConstruct

@RestController
@RequestMapping("/demo")
class ApplicationController(
  private val vidaApiClient: VidaApiClient,
  private val objectMapper: ObjectMapper
) {

  companion object {
    private val LOGGER = LoggerFactory.getLogger(ApplicationController::class.java)
    const val EMPTY = ""
  }

  @GetMapping("/healthcheck")
  fun healthCheck() = "Healthy"

  @GetMapping("/verify")
  fun getToken() {
    val request = MotherNameRequest(
      trxId = "",
      idCardNumber = "3512000101806300",
      motherName = "tung"
    )
    val resquestString = objectMapper.convertValue(request, JSONObject::class.java).toString()

    val verify = vidaApiClient.verifyMotherName(
      "", "3512000101806300", "tung"
    )
  }

  //  @PostConstruct
  fun test() {
    kotlin.runCatching {

      kotlin.runCatching {

        throw VerificationNoResultException()

      }.getOrElse {

        if (it is VerificationNoResultException) {
          LOGGER.error("VerificationNoResultException")
          return@getOrElse
        }

        LOGGER.info("123")
      }
    }.getOrElse {
      LOGGER.error("Message for verification is null")
    }
  }

  @PostConstruct
  fun test2() {
    val request = VerificationServiceSpec(
      applicationDraftId = 1L,
      lastVerificationModuleType = VerificationModuleType.VIDA_PROFESSIONAL_MODULE,
      verificationModuleName = VerificationModuleType.VIDA_PROFESSIONAL_MODULE.name
    )
    val verificationModules = listOf(
      VerificationModuleDto(1, "VIDA_MOTHER_NAME_MODULE", 1),
      VerificationModuleDto(2, "VIDA_PROFESSIONAL_MODULE", 2)
    )

    val moduleByName = verificationModules.associateBy { it.providerName }
    val indexToDropFrom = moduleByName[request.verificationModuleName]?.moduleOrder
    val verificationResults = mutableListOf<VerificationResultDto>()
    val verificationModuleGroups = verificationModules
      .sortedBy { it.moduleOrder }
      .groupBy { it.moduleOrder }
      .let {
        // skip modules already called
        if (indexToDropFrom == null) return@let it
        it.filterKeys { it >= indexToDropFrom }
      }.toSortedMap()

    for ((_, modules) in verificationModuleGroups) {
      val verificationGroupResult = listOf(VerificationResultDto(modules[0].providerName))
      val module = modules
    }

  }

  data class VerificationServiceSpec(
    val applicationDraftId: Long,
    val lastVerificationModuleType: VerificationModuleType? = null,
    val verificationModuleName: String = EMPTY
  )

  enum class VerificationModuleType {
    SAFETY_NET_MODULE,
    ASLI_PROFESSIONAL_MODULE,
    ASLI_MOTHER_NAME_MODULE,
    VIDA_PROFESSIONAL_MODULE,
    VIDA_MOTHER_NAME_MODULE,
    KTP_OCR_MODULE,
    NOT_IN_USE,
    REFINITIV_MODULE
  }

  data class VerificationModuleDto(
    val id: Long,
    val providerName: String,
    val moduleOrder: Int = 1
  )

  data class VerificationResultDto(
    val providerName: String,
  )

  fun testreset (){
    //1
    //2
    //3
    //4
  }
}
