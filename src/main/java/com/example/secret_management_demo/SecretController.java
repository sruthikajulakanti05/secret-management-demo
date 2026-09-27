package com.example.secret_management_demo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.secretsmanager.SecretsManagerClient;
import software.amazon.awssdk.services.secretsmanager.model.GetSecretValueRequest;

@RestController
public class SecretController {

    @Value("${username}")
    private String username;

    @Value("${password}")
    private String password;

    @GetMapping("/secret")
    public String getSecret() {
        return "Vault Username: " + username + "<br>Vault Password: " + password;
    }

    @GetMapping("/aws-secret")
    public String getAwsSecret() {

        SecretsManagerClient client = SecretsManagerClient.builder()
                .region(Region.AP_SOUTH_1)
                .endpointOverride(java.net.URI.create("http://localhost:4566"))
                .build();

        GetSecretValueRequest request = GetSecretValueRequest.builder()
                .secretId("secret-management-demo")
                .build();

        String secret = client.getSecretValue(request).secretString();

        return "AWS Secret: " + secret;
    }
}