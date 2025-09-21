package br.com.api.kotlin.kotlin_api.services

import br.com.api.kotlin.kotlin_api.dtos.*
import br.com.api.kotlin.kotlin_api.enums.IpStatus
import org.springframework.stereotype.Service
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse
import java.security.MessageDigest
import java.util.*

@Service
class AdvancedVerificationService(
    private val gsonService: GsonService,
    private val ipVerifyService: IpVerifyService
) {

    private val client = HttpClient.newHttpClient()

    fun verifyFingerprint(request: VerificationRequestDTO): VerificationResponseDTO {
        val reasons = mutableListOf<String>()
        var riskScore = 0
        
        // Log dos dados recebidos para debug
        println("🔍 FINGERPRINT RECEIVED:")
        println("   SessionId: ${request.fingerprint.sessionId}")
        println("   Device UA: ${request.fingerprint.device.userAgent}")
        println("   Device Screen: ${request.fingerprint.device.screenResolution}")
        println("   Device Timezone: ${request.fingerprint.device.timezone}")
        println("   Network IP: ${request.fingerprint.network.ip}")
        println("   Behavior Duration: ${request.fingerprint.behavior.sessionDuration}")
        println("   Behavior Mouse: ${request.fingerprint.behavior.mouseMovements}")

        // Análise do dispositivo
        val deviceRisk = analyzeDeviceFingerprint(request.fingerprint.device)
        riskScore += deviceRisk.first
        reasons.addAll(deviceRisk.second)
        println("🖥️ Device Risk: ${deviceRisk.first} - ${deviceRisk.second}")

        // Análise do comportamento
        val behaviorRisk = analyzeBehaviorFingerprint(request.fingerprint.behavior)
        riskScore += behaviorRisk.first
        reasons.addAll(behaviorRisk.second)
        println("👤 Behavior Risk: ${behaviorRisk.first} - ${behaviorRisk.second}")

        // Análise da rede (IP)
        val networkRisk = analyzeNetworkFingerprint(request.fingerprint.network)
        riskScore += networkRisk.first
        reasons.addAll(networkRisk.second)
        println("🌐 Network Risk: ${networkRisk.first} - ${networkRisk.second}")

        // Análise de consistência
        val consistencyRisk = analyzeConsistency(request.fingerprint)
        riskScore += consistencyRisk.first
        reasons.addAll(consistencyRisk.second)
        println("🔍 Consistency Risk: ${consistencyRisk.first} - ${consistencyRisk.second}")
        
        // Adicionar pequena variação aleatória para simular análise real
        val randomVariation = (Math.random() * 10 - 5).toInt() // -5 a +5
        riskScore += randomVariation
        
        println("📊 Total Risk Score: $riskScore (com variação: $randomVariation)")

        // Determinar status final (ajustado para ser menos restritivo)
        val status = when {
            riskScore >= 120 -> IpStatus.DENY
            riskScore >= 40 -> IpStatus.REVIEW
            else -> IpStatus.ALLOW
        }

        // Gerar sessionId único para cada verificação
        val uniqueSessionId = "session_${System.currentTimeMillis()}_${(Math.random() * 1000000).toInt()}"

        return VerificationResponseDTO(
            status = status,
            riskScore = riskScore,
            reasons = reasons,
            sessionId = uniqueSessionId,
            timestamp = System.currentTimeMillis()
        )
    }

    fun verifyFingerprintAdvanced(request: VerificationRequestDTO): AdvancedVerificationResponseDTO {
        val reasons = mutableListOf<String>()
        var riskScore = 0

        // Análise do dispositivo
        val deviceRisk = analyzeDeviceFingerprint(request.fingerprint.device)
        riskScore += deviceRisk.first
        reasons.addAll(deviceRisk.second)

        // Análise do comportamento
        val behaviorRisk = analyzeBehaviorFingerprint(request.fingerprint.behavior)
        riskScore += behaviorRisk.first
        reasons.addAll(behaviorRisk.second)

        // Análise da rede (IP)
        val networkRisk = analyzeNetworkFingerprint(request.fingerprint.network)
        riskScore += networkRisk.first
        reasons.addAll(networkRisk.second)

        // Análise de consistência
        val consistencyRisk = analyzeConsistency(request.fingerprint)
        riskScore += consistencyRisk.first
        reasons.addAll(consistencyRisk.second)

        // Adicionar pequena variação aleatória para simular análise real
        val randomVariation = (Math.random() * 10 - 5).toInt() // -5 a +5
        riskScore += randomVariation
        
        println("📊 Advanced Total Risk Score: $riskScore (com variação: $randomVariation)")

        // Determinar status final (ajustado para ser menos restritivo)
        val status = when {
            riskScore >= 80 -> IpStatus.DENY
            riskScore >= 40 -> IpStatus.REVIEW
            else -> IpStatus.ALLOW
        }

        // Criar análise detalhada por categoria
        val analysis = VerificationAnalysisDTO(
            device = CategoryAnalysisDTO(
                score = deviceRisk.first,
                riskLevel = when {
                    deviceRisk.first >= 30 -> "HIGH_RISK"
                    deviceRisk.first >= 15 -> "MEDIUM_RISK"
                    else -> "LOW_RISK"
                },
                factors = deviceRisk.second
            ),
            behavior = CategoryAnalysisDTO(
                score = behaviorRisk.first,
                riskLevel = when {
                    behaviorRisk.first >= 30 -> "HIGH_RISK"
                    behaviorRisk.first >= 15 -> "MEDIUM_RISK"
                    else -> "LOW_RISK"
                },
                factors = behaviorRisk.second
            ),
            network = CategoryAnalysisDTO(
                score = networkRisk.first,
                riskLevel = when {
                    networkRisk.first >= 30 -> "HIGH_RISK"
                    networkRisk.first >= 15 -> "MEDIUM_RISK"
                    else -> "LOW_RISK"
                },
                factors = networkRisk.second
            ),
            consistency = CategoryAnalysisDTO(
                score = consistencyRisk.first,
                riskLevel = when {
                    consistencyRisk.first >= 30 -> "HIGH_RISK"
                    consistencyRisk.first >= 15 -> "MEDIUM_RISK"
                    else -> "LOW_RISK"
                },
                factors = consistencyRisk.second
            )
        )

        // Gerar sessionId único para cada verificação avançada
        val uniqueSessionId = "session_${System.currentTimeMillis()}_${(Math.random() * 1000000).toInt()}"
        
        return AdvancedVerificationResponseDTO(
            status = status,
            riskScore = riskScore,
            reasons = reasons,
            sessionId = uniqueSessionId,
            ip = request.fingerprint.network.ip,
            analysis = analysis,
            timestamp = System.currentTimeMillis()
        )
    }

    fun verifyDeviceOnly(request: VerificationRequestDTO): VerificationResponseDTO {
        val deviceRisk = analyzeDeviceFingerprint(request.fingerprint.device)
        val riskScore = deviceRisk.first
        val reasons = deviceRisk.second

        val status = when {
            riskScore >= 50 -> IpStatus.DENY
            riskScore >= 25 -> IpStatus.REVIEW
            else -> IpStatus.ALLOW
        }

        return VerificationResponseDTO(
            status = status,
            riskScore = riskScore,
            reasons = reasons,
            sessionId = "session_${System.currentTimeMillis()}_${(Math.random() * 1000000).toInt()}",
            timestamp = System.currentTimeMillis()
        )
    }

    fun verifyBehaviorOnly(request: VerificationRequestDTO): VerificationResponseDTO {
        val behaviorRisk = analyzeBehaviorFingerprint(request.fingerprint.behavior)
        val riskScore = behaviorRisk.first
        val reasons = behaviorRisk.second

        val status = when {
            riskScore >= 40 -> IpStatus.DENY
            riskScore >= 20 -> IpStatus.REVIEW
            else -> IpStatus.ALLOW
        }

        return VerificationResponseDTO(
            status = status,
            riskScore = riskScore,
            reasons = reasons,
            sessionId = "session_${System.currentTimeMillis()}_${(Math.random() * 1000000).toInt()}",
            timestamp = System.currentTimeMillis()
        )
    }

    fun verifyNetworkOnly(request: VerificationRequestDTO): VerificationResponseDTO {
        val networkRisk = analyzeNetworkFingerprint(request.fingerprint.network)
        val riskScore = networkRisk.first
        val reasons = networkRisk.second

        val status = when {
            riskScore >= 50 -> IpStatus.DENY
            riskScore >= 25 -> IpStatus.REVIEW
            else -> IpStatus.ALLOW
        }

        return VerificationResponseDTO(
            status = status,
            riskScore = riskScore,
            reasons = reasons,
            sessionId = "session_${System.currentTimeMillis()}_${(Math.random() * 1000000).toInt()}",
            timestamp = System.currentTimeMillis()
        )
    }

    private fun analyzeDeviceFingerprint(device: DeviceFingerprintDTO): Pair<Int, List<String>> {
        var riskScore = 0
        val reasons = mutableListOf<String>()

        // Verificar User Agent suspeito
        if (device.userAgent.contains("bot", ignoreCase = true) ||
            device.userAgent.contains("crawler", ignoreCase = true) ||
            device.userAgent.contains("spider", ignoreCase = true)) {
            riskScore += 30
            reasons.add("User Agent suspeito detectado")
        }

        // Análise mais diferenciada de plugins baseada no browser
        when {
            device.plugins.isEmpty() -> {
                riskScore += 15
                reasons.add("Nenhum plugin detectado")
            }
            device.plugins.size < 2 -> {
                riskScore += 8
                reasons.add("Poucos plugins detectados")
            }
            device.plugins.size > 20 -> {
                riskScore += 5
                reasons.add("Muitos plugins detectados")
            }
        }

        // Adicionar variação baseada no hash do User Agent para consistência por dispositivo
        val uaHash = device.userAgent.hashCode()
        val deviceVariation = (uaHash % 10) - 5 // -5 a +4 baseado no dispositivo
        riskScore += deviceVariation

        // Verificar resolução de tela suspeita
        val resolution = device.screenResolution.split("x")
        if (resolution.size == 2) {
            val width = resolution[0].toIntOrNull() ?: 0
            val height = resolution[1].toIntOrNull() ?: 0
            
            if (width < 800 || height < 600) {
                riskScore += 10
                reasons.add("Resolução de tela muito baixa")
            }
            
            if (width > 4000 || height > 3000) {
                riskScore += 5
                reasons.add("Resolução de tela muito alta")
            }
        }

        // Verificar timezone (mais flexível)
        if (!device.timezone.contains("America/") && !device.timezone.contains("UTC") && !device.timezone.contains("Europe/")) {
            riskScore += 5
            reasons.add("Timezone pouco comum")
        }

        // Verificar idioma (mais flexível)
        if (!device.language.startsWith("pt") && !device.language.startsWith("en") && !device.language.startsWith("es")) {
            riskScore += 3
            reasons.add("Idioma pouco comum")
        }

        // Verificar hardware
        if (device.hardwareConcurrency < 2) {
            riskScore += 10
            reasons.add("Hardware insuficiente")
        }

        return Pair(riskScore, reasons)
    }

    private fun analyzeBehaviorFingerprint(behavior: BehaviorFingerprintDTO): Pair<Int, List<String>> {
        var riskScore = 0
        val reasons = mutableListOf<String>()

        // Análise mais granular da duração da sessão
        when {
            behavior.sessionDuration < 500 -> {
                riskScore += 25
                reasons.add("Sessão extremamente curta")
            }
            behavior.sessionDuration < 2000 -> {
                riskScore += 15
                reasons.add("Sessão muito curta")
            }
            behavior.sessionDuration < 5000 -> {
                riskScore += 8
                reasons.add("Sessão curta")
            }
            behavior.sessionDuration > 300000 -> { // > 5 minutos
                riskScore += 3
                reasons.add("Sessão muito longa")
            }
        }

        // Análise mais detalhada de interações
        val totalInteractions = behavior.mouseMovements + behavior.keystrokes + 
                               behavior.scrollEvents + behavior.clickEvents
        
        when {
            totalInteractions == 0 -> {
                riskScore += 20
                reasons.add("Nenhuma interação detectada")
            }
            totalInteractions < 3 -> {
                riskScore += 12
                reasons.add("Poucas interações detectadas")
            }
            totalInteractions < 10 -> {
                riskScore += 5
                reasons.add("Interações limitadas")
            }
            totalInteractions > 1000 -> {
                riskScore += 3
                reasons.add("Interações excessivas (possível bot)")
            }
        }

        // Variação baseada no padrão de comportamento específico
        val behaviorHash = "${behavior.mouseMovements}_${behavior.keystrokes}_${behavior.sessionDuration}".hashCode()
        val behaviorVariation = (behaviorHash % 8) - 4 // -4 a +3
        riskScore += behaviorVariation

        // Verificar padrão de mouse suspeito (mais flexível para demo)
        if (behavior.mouseMovements < 1) {
            riskScore += 8
            reasons.add("Sem movimentos de mouse")
        }

        // Verificar tempo de carregamento
        if (behavior.pageLoadTime > 10000) { // Mais de 10 segundos
            riskScore += 10
            reasons.add("Tempo de carregamento muito alto")
        }

        // Verificar referrer
        if (behavior.referrer.isEmpty() || behavior.referrer == "null") {
            riskScore += 5
            reasons.add("Sem referrer")
        }

        return Pair(riskScore, reasons)
    }

    private fun analyzeNetworkFingerprint(network: NetworkFingerprintDTO): Pair<Int, List<String>> {
        var riskScore = 0
        val reasons = mutableListOf<String>()

        // Usar o serviço existente de verificação de IP
        try {
            val ipResult = ipVerifyService.verifyIp(IpVerifyDTO(network.ip))
            when (ipResult.status) {
                IpStatus.DENY -> {
                    riskScore += 40
                    reasons.add("IP bloqueado")
                }
                IpStatus.REVIEW -> {
                    riskScore += 20
                    reasons.add("IP em revisão")
                }
                IpStatus.ALLOW -> {
                    // IP aprovado, sem penalização
                }
            }
        } catch (e: Exception) {
            riskScore += 15
            reasons.add("Erro na verificação de IP")
        }

        // Verificar tipo de conexão
        if (network.connectionType == "cellular") {
            riskScore += 5
            reasons.add("Conexão móvel")
        }

        // Verificar velocidade de conexão
        network.downlink?.let { downlink ->
            if (downlink < 1.0) {
                riskScore += 10
                reasons.add("Conexão muito lenta")
            }
        }

        return Pair(riskScore, reasons)
    }

    private fun analyzeConsistency(fingerprint: CompleteFingerprintDTO): Pair<Int, List<String>> {
        var riskScore = 0
        val reasons = mutableListOf<String>()

        // Verificar consistência entre timezone e IP
        val isBrazilianTimezone = fingerprint.device.timezone.contains("America/")
        val isBrazilianLanguage = fingerprint.device.language.startsWith("pt")
        
        if (!isBrazilianTimezone && !isBrazilianLanguage) {
            riskScore += 15
            reasons.add("Inconsistência entre timezone e idioma")
        }

        // Verificar se o canvas fingerprint é muito simples
        if (fingerprint.device.canvas.length < 100) {
            riskScore += 10
            reasons.add("Canvas fingerprint suspeito")
        }

        // Verificar se WebGL está disponível mas vazio
        if (fingerprint.device.webgl.isEmpty() && fingerprint.device.userAgent.contains("Chrome")) {
            riskScore += 10
            reasons.add("WebGL não disponível em navegador compatível")
        }

        return Pair(riskScore, reasons)
    }

    private fun generateFingerprintHash(fingerprint: CompleteFingerprintDTO): String {
        val fingerprintString = "${fingerprint.device.userAgent}_${fingerprint.device.screenResolution}_${fingerprint.device.timezone}_${fingerprint.device.canvas}"
        val digest = MessageDigest.getInstance("SHA-256")
        val hash = digest.digest(fingerprintString.toByteArray())
        return Base64.getEncoder().encodeToString(hash)
    }
}
