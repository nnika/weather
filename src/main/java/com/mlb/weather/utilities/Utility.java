package com.mlb.weather.utilities;

import org.apache.hc.client5.http.config.RequestConfig;
import org.apache.hc.client5.http.cookie.StandardCookieSpec;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManager;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManagerBuilder;
import org.apache.hc.core5.http.io.SocketConfig;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.io.CloseMode;
import org.apache.hc.core5.pool.PoolConcurrencyPolicy;
import org.apache.hc.core5.util.TimeValue;
import org.apache.hc.core5.util.Timeout;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

@Component
public class Utility {
	private static final int chunkSize = 1024; 
	
	public static CloseableHttpClient client;

	@PostConstruct
	public void configureHttpClient() {
		client = createHttpClient();
	}

	@PreDestroy
	public void clean() {
		client.close(CloseMode.GRACEFUL);
	}

	final PoolingHttpClientConnectionManager connectionManager  = PoolingHttpClientConnectionManagerBuilder.create()
			.setPoolConcurrencyPolicy(PoolConcurrencyPolicy.STRICT)
			.setMaxConnPerRoute(100)
			.setMaxConnTotal(100)
			.setValidateAfterInactivity(TimeValue.ofSeconds(30))
			.setDefaultSocketConfig(SocketConfig.custom()
					.setSoTimeout(Timeout.ofSeconds(30))
					.build())
			.build();

	protected CloseableHttpClient createHttpClient() {
		return HttpClients.custom()
				.setConnectionManager(connectionManager)
				.setDefaultRequestConfig(RequestConfig.custom()
						.setCookieSpec(StandardCookieSpec.STRICT)
						.setConnectTimeout(Timeout.ofSeconds(30))
						.setResponseTimeout(Timeout.ofSeconds(30))
						.build())
				.build();
	}

	public static ResponseEntity<String> getResponseEntity(CloseableHttpResponse response) throws IOException {
		InputStream is = response.getEntity().getContent();
		ByteArrayOutputStream buffer = getByteArrayOutputStream(is);
		String reply = buffer.toString();
		int statusCode = response.getCode();
		ResponseEntity<String> responseEntity = new ResponseEntity<>(reply, HttpStatus.valueOf(statusCode));
		EntityUtils.consume(response.getEntity());
		response.close();
		return responseEntity;
	}

	static ByteArrayOutputStream getByteArrayOutputStream(InputStream is) throws IOException {
		ByteArrayOutputStream buffer = new ByteArrayOutputStream();
		int len;
		byte[] data = new byte[chunkSize];
		while ((len = is.read(data, 0, data.length)) != -1) {
			buffer.write(data, 0, len);
		}
		buffer.flush();
		return buffer;
	}


	public static boolean isTheResponseSuccessful(ResponseEntity<String> responseEntity) {
		return (responseEntity.getStatusCode().value() >= 200 && responseEntity.getStatusCode().value() < 300);
	} 
}
