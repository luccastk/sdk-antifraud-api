# 🛡️ SDK Anti-Fraud API

Uma API robusta de detecção de fraudes desenvolvida em **Kotlin** com **Spring Boot**, projetada para analisar comportamentos suspeitos através de múltiplas camadas de verificação.

## 🚀 Demonstração ao Vivo

**🌐 API:** [https://conventional-klarika-pulsarantifraud-4620d4a3.koyeb.app](https://conventional-klarika-pulsarantifraud-4620d4a3.koyeb.app)  
**📚 Swagger UI:** [https://conventional-klarika-pulsarantifraud-4620d4a3.koyeb.app/swagger-ui/index.html](https://conventional-klarika-pulsarantifraud-4620d4a3.koyeb.app/swagger-ui/index.html)  
**🏥 Health Check:** [https://conventional-klarika-pulsarantifraud-4620d4a3.koyeb.app/actuator/health](https://conventional-klarika-pulsarantifraud-4620d4a3.koyeb.app/actuator/health)

## 📋 Índice

- [Características](#-características)
- [Tecnologias](#️-tecnologias)
- [Arquitetura](#-arquitetura)
- [Instalação](#-instalação)
- [Configuração](#️-configuração)
- [Uso da API](#-uso-da-api)
- [Endpoints](#-endpoints)
- [Sistema de Scoring](#-sistema-de-scoring)
- [Exemplos](#-exemplos)
- [Deploy](#-deploy)
- [Monitoramento](#-monitoramento)
- [Contribuição](#-contribuição)

## ✨ Características

### 🔍 **Análise Multicamada**

- **Device Fingerprinting**: Análise de hardware, navegador e características do dispositivo
- **Behavioral Analysis**: Padrões de interação do usuário (mouse, teclado, scroll)
- **Network Analysis**: Verificação de IP, geolocalização e tipo de conexão
- **Consistency Checks**: Validação de consistência entre diferentes dados

### 🎯 **Sistema de Scoring Inteligente**

- **Pontuação de Risco**: 0-100+ (quanto maior, mais suspeito)
- **Categorização Automática**: ALLOW, REVIEW, DENY
- **Explicabilidade**: Razões detalhadas para cada decisão
- **Calibrado para Demo**: Thresholds otimizados para demonstrações

### 📊 **Funcionalidades Avançadas**

- **Dashboard Analytics**: Métricas e estatísticas em tempo real
- **Histórico de Verificações**: Rastreamento completo de todas as análises
- **API RESTful**: Interface padronizada e documentada
- **Health Checks**: Monitoramento de saúde da aplicação

## 🛠️ Tecnologias

### **Backend**

- ![Kotlin](https://img.shields.io/badge/Kotlin-7F52FF?style=flat&logo=kotlin&logoColor=white) **Kotlin 1.9.25**
- ![Spring Boot](https://img.shields.io/badge/Spring_Boot-6DB33F?style=flat&logo=spring-boot&logoColor=white) **Spring Boot 3.5.0**
- ![Gradle](https://img.shields.io/badge/Gradle-02303A?style=flat&logo=gradle&logoColor=white) **Gradle 8.x**

### **Documentação**

- ![Swagger](https://img.shields.io/badge/Swagger-85EA2D?style=flat&logo=swagger&logoColor=black) **SpringDoc OpenAPI 2.2.0**

### **Infraestrutura**

- ![Docker](https://img.shields.io/badge/Docker-2496ED?style=flat&logo=docker&logoColor=white) **Docker**
- ![Koyeb](https://img.shields.io/badge/Koyeb-121212?style=flat&logo=koyeb&logoColor=white) **Koyeb Cloud**

### **Ferramentas**

- ![Java](https://img.shields.io/badge/Java-17-ED8B00?style=flat&logo=openjdk&logoColor=white) **OpenJDK 17**
- ![Gson](https://img.shields.io/badge/Gson-4285F4?style=flat&logo=google&logoColor=white) **Google Gson**

## 🏗️ Arquitetura

```
┌─────────────────┐    ┌─────────────────┐    ┌─────────────────┐
│   Controllers   │────│    Services     │────│   External APIs │
│                 │    │                 │    │                 │
│ • IP Verify     │    │ • Advanced      │    │ • IP-API.com    │
│ • Dashboard     │    │   Verification  │    │ • Geolocation   │
│ • Advanced      │    │ • IP Verify     │    │                 │
│   Verification  │    │ • Gson Service  │    │                 │
└─────────────────┘    └─────────────────┘    └─────────────────┘
         │                       │                       │
         └───────────────────────┼───────────────────────┘
                                 │
                    ┌─────────────────┐
                    │      DTOs       │
                    │                 │
                    │ • Request/      │
                    │   Response      │
                    │ • Fingerprint   │
                    │ • Verification  │
                    └─────────────────┘
```

## 📦 Instalação

### **Pré-requisitos**

- Java 17+
- Gradle 8.x
- Docker (opcional)

### **1. Clone o Repositório**

```bash
git clone https://github.com/luccastk/sdk-antifraud-api.git
cd sdk-antifraud-api
```

### **2. Build Local**

```bash
./gradlew clean build
```

### **3. Executar**

```bash
./gradlew bootRun
```

### **4. Usando Docker**

```bash
docker build -t sdk-antifraud-api .
docker run -p 8080:8080 sdk-antifraud-api
```

## ⚙️ Configuração

### **application.properties**

```properties
server.port=8080
spring.application.name=kotlin-api
management.endpoints.web.exposure.include=health,info
```

### **Profiles**

- **`default`**: Desenvolvimento local
- **`production`**: Deploy em produção

### **Variáveis de Ambiente**

```bash
SPRING_PROFILES_ACTIVE=production
SERVER_PORT=8080
```

## 📡 Uso da API

### **Base URL**

```
https://conventional-klarika-pulsarantifraud-4620d4a3.koyeb.app
```

### **Headers Obrigatórios**

```http
Content-Type: application/json
Accept: application/json
```

## 🛣️ Endpoints

### **1. 🔍 Verificação de IP**

```http
POST /ip-verify
```

**Request:**

```json
{
  "ip": "8.8.8.8"
}
```

**Response:**

```json
{
  "status": "ALLOW"
}
```

### **2. 🛡️ Verificação de Fingerprint**

```http
POST /verify-fingerprint
```

**Request:**

```json
{
  "fingerprint": {
    "sessionId": "sess_123456789",
    "device": {
      "userAgent": "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36",
      "screenResolution": "1920x1080",
      "timezone": "America/Sao_Paulo",
      "language": "pt-BR",
      "plugins": ["Chrome PDF Plugin", "Widevine Content Decryption Module"],
      "canvas": "data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAASwAAA...",
      "webgl": "ANGLE (Intel, Intel(R) HD Graphics 620 Direct3D11 vs_5_0 ps_5_0)",
      "hardwareConcurrency": 4
    },
    "behavior": {
      "sessionDuration": 45000,
      "mouseMovements": 127,
      "keystrokes": 23,
      "scrollEvents": 8,
      "clickEvents": 5,
      "pageLoadTime": 2340,
      "referrer": "https://google.com"
    },
    "network": {
      "ip": "191.123.45.67",
      "connectionType": "wifi",
      "downlink": 10.5
    }
  }
}
```

**Response:**

```json
{
  "status": "ALLOW",
  "riskScore": 25,
  "reasons": ["Resolução de tela muito alta"],
  "sessionId": "sess_123456789",
  "timestamp": 1695839472000
}
```

### **3. 🛡️ Verificação Avançada com IP**

```http
POST /verify-fingerprint/advanced
```

**Request:**

```json
{
  "fingerprint": {
    "sessionId": "sess_123456789",
    "device": {
      "userAgent": "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36",
      "screenResolution": "1920x1080",
      "timezone": "America/Sao_Paulo",
      "language": "pt-BR",
      "plugins": ["Chrome PDF Plugin", "Widevine Content Decryption Module"],
      "canvas": "data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAASwAAA...",
      "webgl": "ANGLE (Intel, Intel(R) HD Graphics 620 Direct3D11 vs_5_0 ps_5_0)",
      "hardwareConcurrency": 4
    },
    "behavior": {
      "sessionDuration": 45000,
      "mouseMovements": 127,
      "keystrokes": 23,
      "scrollEvents": 8,
      "clickEvents": 5,
      "pageLoadTime": 2340,
      "referrer": "https://google.com"
    },
    "network": {
      "ip": "191.123.45.67",
      "connectionType": "wifi",
      "downlink": 10.5
    }
  }
}
```

**Response:**

```json
{
  "status": "ALLOW",
  "riskScore": 25,
  "reasons": ["Resolução de tela muito alta"],
  "sessionId": "sess_123456789",
  "ip": "191.123.45.67",
  "analysis": {
    "device": {
      "score": 5,
      "riskLevel": "LOW_RISK",
      "factors": ["Resolução de tela muito alta"]
    },
    "behavior": {
      "score": 0,
      "riskLevel": "LOW_RISK",
      "factors": []
    },
    "network": {
      "score": 0,
      "riskLevel": "LOW_RISK",
      "factors": []
    },
    "consistency": {
      "score": 0,
      "riskLevel": "LOW_RISK",
      "factors": []
    }
  },
  "timestamp": 1695839472000
}
```

### **4. 📊 Dashboard**

```http
GET /dashboard
```

**Response:**

```json
{
  "totalVerifications": 1250,
  "allowedCount": 875,
  "reviewCount": 250,
  "deniedCount": 125,
  "allowedPercentage": 70.0,
  "reviewPercentage": 20.0,
  "deniedPercentage": 10.0,
  "averageRiskScore": 32.5,
  "topRiskFactors": [
    "IP não brasileiro",
    "Sessão muito curta",
    "Poucos movimentos de mouse"
  ],
  "verificationsByHour": [
    { "hour": 0, "count": 45 },
    { "hour": 1, "count": 32 }
  ]
}
```

### **4. 🏥 Health Check**

```http
GET /actuator/health
```

**Response:**

```json
{
  "status": "UP"
}
```

## 🎯 Sistema de Scoring

### **Categorias de Risco**

| Score | Status        | Ação                      |
| ----- | ------------- | ------------------------- |
| 0-59  | 🟢 **ALLOW**  | Aprovado automaticamente  |
| 60-99 | 🟡 **REVIEW** | Revisão manual necessária |
| 100+  | 🔴 **DENY**   | Bloqueado automaticamente |

### **Fatores de Risco**

#### **🖥️ Device Analysis**

- **User Agent Suspeito**: +40 pontos
- **Plugins Insuficientes**: +15 pontos
- **Resolução Anormal**: +5-10 pontos
- **Timezone Suspeito**: +10 pontos
- **Idioma Não Comum**: +8 pontos
- **Hardware Insuficiente**: +10 pontos

#### **👤 Behavioral Analysis**

- **Sessão Muito Curta** (<2s): +15 pontos
- **Poucas Interações** (<2): +12 pontos
- **Sem Mouse**: +8 pontos
- **Loading Lento** (>10s): +10 pontos
- **Sem Referrer**: +5 pontos

#### **🌐 Network Analysis**

- **IP Bloqueado**: +40 pontos
- **IP em Revisão**: +20 pontos
- **Conexão Móvel**: +5 pontos
- **Conexão Lenta**: +10 pontos

#### **🔍 Consistency Checks**

- **Timezone/Idioma Inconsistente**: +15 pontos
- **Canvas Suspeito**: +10 pontos
- **WebGL Ausente**: +10 pontos

## 💡 Exemplos de Uso

### **Teste Rápido - IP Verification**

```bash
curl -X POST "https://conventional-klarika-pulsarantifraud-4620d4a3.koyeb.app/ip-verify" \
  -H "Content-Type: application/json" \
  -d '{"ip": "8.8.8.8"}'
```

### **Cenário 1: Usuário Legítimo**

```bash
curl -X POST "https://conventional-klarika-pulsarantifraud-4620d4a3.koyeb.app/advanced-verification" \
  -H "Content-Type: application/json" \
  -d '{
    "fingerprint": {
      "sessionId": "user_normal_001",
      "device": {
        "userAgent": "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36",
        "screenResolution": "1920x1080",
        "timezone": "America/Sao_Paulo",
        "language": "pt-BR",
        "plugins": ["Chrome PDF Plugin", "Flash Player"],
        "canvas": "data:image/png;base64,validcanvasdata...",
        "webgl": "WebGL 2.0 (OpenGL ES 3.0 Chromium)",
        "hardwareConcurrency": 8
      },
      "behavior": {
        "sessionDuration": 30000,
        "mouseMovements": 50,
        "keystrokes": 15,
        "scrollEvents": 10,
        "clickEvents": 8,
        "pageLoadTime": 1200,
        "referrer": "https://google.com"
      },
      "network": {
        "ip": "191.123.45.67",
        "connectionType": "wifi",
        "downlink": 25.0
      }
    }
  }'
```

**Resultado Esperado:** `ALLOW` (Score: ~5-15)

### **Cenário 2: Bot Suspeito**

```bash
curl -X POST "https://conventional-klarika-pulsarantifraud-4620d4a3.koyeb.app/advanced-verification" \
  -H "Content-Type: application/json" \
  -d '{
    "fingerprint": {
      "sessionId": "bot_suspicious_001",
      "device": {
        "userAgent": "Mozilla/5.0 (compatible; Googlebot/2.1)",
        "screenResolution": "800x600",
        "timezone": "UTC",
        "language": "en-US",
        "plugins": [],
        "canvas": "simple",
        "webgl": "",
        "hardwareConcurrency": 1
      },
      "behavior": {
        "sessionDuration": 500,
        "mouseMovements": 0,
        "keystrokes": 0,
        "scrollEvents": 0,
        "clickEvents": 1,
        "pageLoadTime": 100,
        "referrer": ""
      },
      "network": {
        "ip": "142.250.191.14",
        "connectionType": "unknown",
        "downlink": null
      }
    }
  }'
```

**Resultado Esperado:** `DENY` (Score: ~100+)

### **Cenário 3: Verificação Avançada com IP**

```bash
curl -X POST "https://conventional-klarika-pulsarantifraud-4620d4a3.koyeb.app/verify-fingerprint/advanced" \
  -H "Content-Type: application/json" \
  -d '{
    "fingerprint": {
      "sessionId": "user_advanced_001",
      "device": {
        "userAgent": "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36",
        "screenResolution": "1920x1080",
        "timezone": "America/Sao_Paulo",
        "language": "pt-BR",
        "plugins": ["Chrome PDF Plugin", "Flash Player"],
        "canvas": "data:image/png;base64,validcanvasdata...",
        "webgl": "WebGL 2.0 (OpenGL ES 3.0 Chromium)",
        "hardwareConcurrency": 8
      },
      "behavior": {
        "sessionDuration": 30000,
        "mouseMovements": 50,
        "keystrokes": 15,
        "scrollEvents": 10,
        "clickEvents": 8,
        "pageLoadTime": 1200,
        "referrer": "https://google.com"
      },
      "network": {
        "ip": "191.123.45.67",
        "connectionType": "wifi",
        "downlink": 25.0
      }
    }
  }'
```

**Resposta Esperada:**

```json
{
  "status": "ALLOW",
  "riskScore": 10,
  "reasons": ["Timezone fora das Américas"],
  "sessionId": "user_advanced_001",
  "ip": "191.123.45.67",
  "analysis": {
    "device": {
      "score": 10,
      "riskLevel": "LOW_RISK",
      "factors": ["Timezone fora das Américas"]
    },
    "behavior": {
      "score": 0,
      "riskLevel": "LOW_RISK",
      "factors": []
    },
    "network": {
      "score": 0,
      "riskLevel": "LOW_RISK",
      "factors": []
    },
    "consistency": {
      "score": 0,
      "riskLevel": "LOW_RISK",
      "factors": []
    }
  },
  "timestamp": 1695839472000
}
```

**Características da Resposta Avançada:**

- ✅ **IP do usuário**: Incluído na resposta para tracking
- 📊 **Análise detalhada**: Score e fatores por categoria
- 🎯 **Níveis de risco**: LOW_RISK, MEDIUM_RISK, HIGH_RISK
- 📝 **Fatores específicos**: Lista de razões por categoria

## 🚀 Deploy

### **Koyeb (Atual)**

✅ **Status**: Deployado e funcionando  
🌐 **URL**: https://conventional-klarika-pulsarantifraud-4620d4a3.koyeb.app  
🏥 **Health**: Todos os health checks passando

### **Docker Local**

```bash
docker build -t sdk-antifraud-api .
docker run -p 8080:8080 -e SPRING_PROFILES_ACTIVE=production sdk-antifraud-api
```

### **Outros Provedores**

```bash
# Heroku
heroku create sua-app-antifraud
heroku config:set SPRING_PROFILES_ACTIVE=production
git push heroku main

# Railway
railway login
railway init
railway up
```

## 📊 Monitoramento

### **Health Checks**

- **Endpoint**: `/actuator/health`
- **Status**: ✅ UP
- **Timeout**: 5s

### **Métricas em Tempo Real**

- **Total de Verificações**
- **Taxa de Aprovação/Rejeição**
- **Score de Risco Médio**
- **Principais Fatores de Risco**

### **Logs de Aplicação**

```bash
# Visualizar logs no Koyeb
# Disponível no dashboard do Koyeb

# Docker local
docker logs -f container-id
```

## 🤝 Contribuição

### **Como Contribuir**

1. **Fork o projeto**
2. **Crie uma branch** (`git checkout -b feature/nova-funcionalidade`)
3. **Commit suas mudanças** (`git commit -am 'Adiciona nova funcionalidade'`)
4. **Push para a branch** (`git push origin feature/nova-funcionalidade`)
5. **Abra um Pull Request**

### **Padrões de Código**

- **Kotlin Style Guide**
- **Clean Code Principles**
- **SOLID Principles**
- **Documentação obrigatória**

### **Testes**

```bash
./gradlew test
```

## 📄 Licença

Este projeto está sob a licença **MIT**. Veja o arquivo [LICENSE](LICENSE) para mais detalhes.

## 👥 Equipe

- **Desenvolvedor Principal**: [Lucca](https://github.com/luccastk)
- **Email**: dev@antifraud.com

## 🔗 Links Úteis

- **📚 Documentação da API**: [Swagger UI](https://conventional-klarika-pulsarantifraud-4620d4a3.koyeb.app/swagger-ui/index.html)
- **🌐 API Demo**: [Koyeb Deploy](https://conventional-klarika-pulsarantifraud-4620d4a3.koyeb.app)
- **📊 OpenAPI Spec**: [JSON](https://conventional-klarika-pulsarantifraud-4620d4a3.koyeb.app/v3/api-docs)
- **🏥 Health Check**: [Status](https://conventional-klarika-pulsarantifraud-4620d4a3.koyeb.app/actuator/health)
- **🐛 Issues**: [GitHub Issues](https://github.com/luccastk/sdk-antifraud-api/issues)

---

<div align="center">

**🛡️ Desenvolvido com ❤️ para detecção inteligente de fraudes**

[![Kotlin](https://img.shields.io/badge/Kotlin-7F52FF?style=for-the-badge&logo=kotlin&logoColor=white)](https://kotlinlang.org/)
[![Spring Boot](https://img.shields.io/badge/Spring_Boot-6DB33F?style=for-the-badge&logo=spring-boot&logoColor=white)](https://spring.io/projects/spring-boot)
[![Docker](https://img.shields.io/badge/Docker-2496ED?style=for-the-badge&logo=docker&logoColor=white)](https://www.docker.com/)

**🎯 Status: ✅ FUNCIONANDO PERFEITAMENTE**

</div>
