package br.com.api.kotlin.kotlin_api.services

import br.com.api.kotlin.kotlin_api.dtos.IpVerifyDTO
import br.com.api.kotlin.kotlin_api.dtos.IpVerifyView
import br.com.api.kotlin.kotlin_api.dtos.ResponseIpVerify
import br.com.api.kotlin.kotlin_api.enums.IpStatus
import org.springframework.stereotype.Service
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse

@Service
class IpVerifyService(val gsonService: GsonService) {

    fun verifyIp(dto: IpVerifyDTO): IpVerifyView {
        val client = HttpClient.newHttpClient()

        val request = HttpRequest.newBuilder().uri(URI.create("http://ip-api.com/json/${dto.ip}")).GET().build()

        val response = client.send(request, HttpResponse.BodyHandlers.ofString())

        val json = response.body()

        val data = gsonService.deserializer(json, ResponseIpVerify::class.java)

        println(dto)
        println(data)

        // Lógica mais flexível para demonstração
        return when {
            data.status != "success" -> IpVerifyView(IpStatus.REVIEW)
            data.country == "Brazil" -> IpVerifyView(IpStatus.ALLOW)
            data.country in listOf("United States", "Canada", "Argentina", "Chile", "Uruguay") -> IpVerifyView(IpStatus.ALLOW)
            data.country in listOf("China", "Russia", "Iran", "North Korea") -> IpVerifyView(IpStatus.DENY)
            else -> IpVerifyView(IpStatus.REVIEW)
        }
    }
}