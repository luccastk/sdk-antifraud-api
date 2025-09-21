package br.com.api.kotlin.kotlin_api.controllers

import br.com.api.kotlin.kotlin_api.dtos.AdvancedVerificationResponseDTO
import br.com.api.kotlin.kotlin_api.dtos.VerificationRequestDTO
import br.com.api.kotlin.kotlin_api.dtos.VerificationResponseDTO
import br.com.api.kotlin.kotlin_api.services.AdvancedVerificationService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/verify-fingerprint")
@CrossOrigin(origins = ["*"])
@Tag(name = "Advanced Verification", description = "Endpoints para verificação avançada de fingerprint e análise comportamental")
class AdvancedVerificationController(
    private val advancedVerificationService: AdvancedVerificationService
) {

    @PostMapping
    @Operation(
        summary = "Verificar Fingerprint Completo",
        description = "Realiza verificação completa baseada em fingerprint do dispositivo e comportamento do usuário"
    )
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Verificação realizada com sucesso"),
            ApiResponse(responseCode = "400", description = "Dados de entrada inválidos ou erro na verificação")
        ]
    )
    fun verifyFingerprint(@RequestBody request: VerificationRequestDTO): ResponseEntity<VerificationResponseDTO> {
        return try {
            val result = advancedVerificationService.verifyFingerprint(request)
            ResponseEntity.ok()
                .header("Cache-Control", "no-cache, no-store, must-revalidate")
                .header("Pragma", "no-cache")
                .header("Expires", "0")
                .body(result)
        } catch (e: Exception) {
            println("Erro na verificação de fingerprint: ${e.message}")
            ResponseEntity.badRequest().build()
        }
    }

    @PostMapping("/advanced")
    @Operation(
        summary = "Verificar Fingerprint Avançado",
        description = "Realiza verificação avançada com análise detalhada por categoria e retorna o IP do usuário"
    )
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Verificação avançada realizada com sucesso"),
            ApiResponse(responseCode = "400", description = "Dados de entrada inválidos ou erro na verificação")
        ]
    )
    fun verifyFingerprintAdvanced(@RequestBody request: VerificationRequestDTO): ResponseEntity<AdvancedVerificationResponseDTO> {
        return try {
            val result = advancedVerificationService.verifyFingerprintAdvanced(request)
            ResponseEntity.ok()
                .header("Cache-Control", "no-cache, no-store, must-revalidate")
                .header("Pragma", "no-cache")
                .header("Expires", "0")
                .body(result)
        } catch (e: Exception) {
            println("Erro na verificação avançada de fingerprint: ${e.message}")
            ResponseEntity.badRequest().build()
        }
    }

    @PostMapping("/device")
    @Operation(
        summary = "Verificar Apenas Dispositivo",
        description = "Realiza verificação focada apenas no fingerprint do dispositivo"
    )
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Verificação de dispositivo realizada com sucesso"),
            ApiResponse(responseCode = "400", description = "Dados de entrada inválidos")
        ]
    )
    fun verifyDevice(@RequestBody request: VerificationRequestDTO): ResponseEntity<VerificationResponseDTO> {
        return try {
            val result = advancedVerificationService.verifyDeviceOnly(request)
            ResponseEntity.ok(result)
        } catch (e: Exception) {
            println("Erro na verificação de dispositivo: ${e.message}")
            ResponseEntity.badRequest().build()
        }
    }

    @PostMapping("/behavior")
    @Operation(
        summary = "Verificar Apenas Comportamento",
        description = "Realiza verificação focada apenas no comportamento do usuário"
    )
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Verificação comportamental realizada com sucesso"),
            ApiResponse(responseCode = "400", description = "Dados de entrada inválidos")
        ]
    )
    fun verifyBehavior(@RequestBody request: VerificationRequestDTO): ResponseEntity<VerificationResponseDTO> {
        return try {
            val result = advancedVerificationService.verifyBehaviorOnly(request)
            ResponseEntity.ok(result)
        } catch (e: Exception) {
            println("Erro na verificação comportamental: ${e.message}")
            ResponseEntity.badRequest().build()
        }
    }

    @PostMapping("/network")
    @Operation(
        summary = "Verificar Apenas Rede/IP",
        description = "Realiza verificação focada apenas na rede e IP do usuário"
    )
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Verificação de rede realizada com sucesso"),
            ApiResponse(responseCode = "400", description = "Dados de entrada inválidos")
        ]
    )
    fun verifyNetwork(@RequestBody request: VerificationRequestDTO): ResponseEntity<VerificationResponseDTO> {
        return try {
            val result = advancedVerificationService.verifyNetworkOnly(request)
            ResponseEntity.ok(result)
        } catch (e: Exception) {
            println("Erro na verificação de rede: ${e.message}")
            ResponseEntity.badRequest().build()
        }
    }
}
