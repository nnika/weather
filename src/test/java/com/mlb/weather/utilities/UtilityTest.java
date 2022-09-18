package com.mlb.weather.utilities;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

@ContextConfiguration(classes = {Utility.class})
@ExtendWith(SpringExtension.class)
class UtilityTest {
	@Autowired
	private Utility utility;

	/**
	 * Method under test: {@link Utility#createHttpClient()}
	 */
	@Test
	void testCreateHttpClient() {
		utility.createHttpClient();
	}

	/**
	 * Method under test: {@link Utility#getByteArrayOutputStream(InputStream)}
	 */
	@Test
	void testGetByteArrayOutputStream() throws IOException {
		Utility.getByteArrayOutputStream(new ByteArrayInputStream("AAAAAAAA".getBytes("UTF-8")));
	}

}

