package br.com.jarvis.property

import com.fasterxml.jackson.annotation.JsonProperty
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

@Component
@ConfigurationProperties(value = "spring.security.oauth2.client.registration.facebook")
class FacebookProperties (
    @JsonProperty("secret-app-key")
    var secretAppKey: String = "",
    @JsonProperty("app-id")
    var appId: String = ""
)