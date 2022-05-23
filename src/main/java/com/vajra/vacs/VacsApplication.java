package com.vajra.vacs;

import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.RestTemplate;

import com.vajra.vacs.config.HttpComponentsClientHttpRequestFactoryDigestAuth;

@SpringBootApplication(scanBasePackages = "com.vajra.vacs")
@EnableScheduling
@EnableJpaAuditing
public class VacsApplication {

	@Value("${anpr.ip}")
	private String[] anprs;

	@Value("${anpr.user}")
	private String anprUser;

	@Value("${anpr.password}")
	private String anprPassword;

	public static void main(String[] args) {
		SpringApplication.run(VacsApplication.class, args);
	}

	@Bean
	@Primary
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}

	@Bean(name = "restAnprTemplate")
	public RestTemplate restAnprTemplate() {
		HttpHost host = new HttpHost("localhost", 8080, "http");
		CloseableHttpClient client = HttpClientBuilder.create().setDefaultCredentialsProvider(provider())
				.useSystemProperties().build();
		HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactoryDigestAuth(
				host, client);

		return new RestTemplate(requestFactory);
	}

	private CredentialsProvider provider() {
		CredentialsProvider provider = new BasicCredentialsProvider();
		UsernamePasswordCredentials credentials = new UsernamePasswordCredentials("user1", "user1Pass");
		provider.setCredentials(AuthScope.ANY, credentials);
		return provider;
	}

}
