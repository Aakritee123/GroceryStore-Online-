package com.egroc.config;

import com.stripe.Stripe;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StripeConfig {

    @Value("${stripe.secret.key}")
    private String secretKey;

    // Initialize the Stripe API key without autowiring or unnecessary beans
    public void initializeStripe() {
        Stripe.apiKey = secretKey;  // Set the secret key for the Stripe SDK
    }
}
