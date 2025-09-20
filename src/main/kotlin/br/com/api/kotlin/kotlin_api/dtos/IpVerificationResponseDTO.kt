package br.com.api.kotlin.kotlin_api.dtos

import br.com.api.kotlin.kotlin_api.enums.IpStatus
import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "Resposta da verificação de IP")
data class IpVerificationResponseDTO(
    @Schema(description = "Status da verificação do IP", example = "ALLOW")
    val status: IpStatus,
    
    @Schema(description = "Score de risco do IP (0-100)", example = "15")
    val riskScore: Int,
    
    @Schema(description = "Informações sobre o IP verificado")
    val ipInfo: IpInfoDTO?,
    
    @Schema(description = "Timestamp da verificação", example = "1695839472000")
    val timestamp: Long
)

@Schema(description = "Informações detalhadas sobre o IP")
data class IpInfoDTO(
    @Schema(description = "IP verificado", example = "8.8.8.8")
    val ip: String,
    
    @Schema(description = "País do IP", example = "United States")
    val country: String?,
    
    @Schema(description = "Região/Estado", example = "California")
    val region: String?,
    
    @Schema(description = "Cidade", example = "Mountain View")
    val city: String?,
    
    @Schema(description = "Provedor de internet", example = "Google LLC")
    val isp: String?,
    
    @Schema(description = "Motivo da classificação", example = "IP de país permitido")
    val reason: String
)
