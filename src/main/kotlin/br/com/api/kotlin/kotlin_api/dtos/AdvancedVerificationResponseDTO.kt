package br.com.api.kotlin.kotlin_api.dtos

import br.com.api.kotlin.kotlin_api.enums.IpStatus
import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "Resposta da verificação avançada de fingerprint")
data class AdvancedVerificationResponseDTO(
    @Schema(description = "Status final da verificação", example = "ALLOW")
    val status: IpStatus,
    
    @Schema(description = "Score total de risco (0-100+)", example = "25")
    val riskScore: Int,
    
    @Schema(description = "Lista de razões que contribuíram para o score")
    val reasons: List<String>,
    
    @Schema(description = "ID da sessão verificada", example = "sess_123456789")
    val sessionId: String,
    
    @Schema(description = "IP do usuário verificado", example = "192.168.1.1")
    val ip: String,
    
    @Schema(description = "Análise detalhada por categoria")
    val analysis: VerificationAnalysisDTO,
    
    @Schema(description = "Timestamp da verificação", example = "1695839472000")
    val timestamp: Long
)

@Schema(description = "Análise detalhada por categoria de verificação")
data class VerificationAnalysisDTO(
    @Schema(description = "Análise do dispositivo")
    val device: CategoryAnalysisDTO,
    
    @Schema(description = "Análise comportamental")
    val behavior: CategoryAnalysisDTO,
    
    @Schema(description = "Análise de rede")
    val network: CategoryAnalysisDTO,
    
    @Schema(description = "Análise de consistência")
    val consistency: CategoryAnalysisDTO
)

@Schema(description = "Análise de uma categoria específica")
data class CategoryAnalysisDTO(
    @Schema(description = "Score da categoria", example = "15")
    val score: Int,
    
    @Schema(description = "Status da categoria", example = "LOW_RISK")
    val riskLevel: String,
    
    @Schema(description = "Fatores identificados nesta categoria")
    val factors: List<String>
)
