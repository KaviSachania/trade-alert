package com.cryptoalert.cryptoalert.email;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.ses.SesClient;

@Configuration
public class AwsSesConfiguration {

    public AwsSesConfiguration() {
    }

    /**
     * Build the AWS ses client
     *
     * @return SesClient
     */
    @Bean
    public SesClient amazonSimpleEmailService() {
        return SesClient.builder()
                .region(Region.US_WEST_2)
                .build();
    }
}