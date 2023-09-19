package br.com.jarvis.component.security

import org.springframework.security.oauth2.server.resource.introspection.OpaqueTokenIntrospector
import org.springframework.security.oauth2.server.resource.introspection.OpaqueTokenIntrospectionException
import org.springframework.security.oauth2.server.resource.introspection.OpaqueTokenMetadata

class CustomRemoteTokenIntrospector : OpaqueTokenIntrospector {

    override fun introspect(token: String?): OpaqueTokenMetadata {
        // Implemente a lógica de introspecção aqui
        // Faça a chamada para o servidor de autorização OAuth 2.0 para verificar a validade do token

        // Se o token for válido, retorne informações sobre o token
        // Caso contrário, lance uma exceção OpaqueTokenIntrospectionException

        // Exemplo de implementação fictícia:
        if (isTokenValid(token)) {
            val metadata = OpaqueTokenMetadata()
            // Preencha as informações do token, como os escopos e as autorizações aqui
            metadata.scope = setOf("read")
            return metadata
        } else {
            throw OpaqueTokenIntrospectionException("Token inválido")
        }
    }

    private fun isTokenValid(token: String?): Boolean {
        // Implemente a lógica de validação do token aqui
        // Faça a chamada para o servidor de autorização para verificar a validade
        // Retorne true se o token for válido, caso contrário, retorne false

        // Exemplo de implementação fictícia:
        // Verificando se o token não está vencido
        return !isTokenExpired(token)
    }

    private fun isTokenExpired(token: String?): Boolean {
        // Implemente a lógica de verificação de validade do token aqui
        // Por exemplo, verifique a data de expiração do token

        // Exemplo de implementação fictícia:
        val expirationTimestamp = getTokenExpirationTimestamp(token)
        val currentTimestamp = System.currentTimeMillis() / 1000
        return expirationTimestamp < currentTimestamp
    }

    private fun getTokenExpirationTimestamp(token: String?): Long {
        // Implemente a lógica para extrair a data de expiração do token aqui
        // Esta implementação depende da estrutura do token OAuth 2.0

        // Exemplo de implementação fictícia:
        // Suponha que o token seja um JWT e a data de expiração esteja no campo 'exp'
        // Para fins de exemplo, estamos assumindo que o token é um JWT com decodificação simplificada.
        val payload = token?.split(".")?.get(1) // Pegue a parte do payload do JWT
        val decodedPayload = java.util.Base64.getDecoder().decode(payload)
        // Extraia a data de expiração (exp) do JSON decodificado
        val jsonObject = com.google.gson.JsonParser().parse(String(decodedPayload, Charsets.UTF_8)).asJsonObject
        val expirationTimestamp = jsonObject["exp"].asLong
        return expirationTimestamp
    }
}
