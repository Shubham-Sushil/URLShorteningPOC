package com.deloitte.url.shortening.service;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class UrlShorteningServiceTest {

	IUrlShorteningService defaultService;
	IUrlShorteningService customService;
	
	private String customDomain = "www.dtt.com";
	private int customLength = 3;

	@Before
	public void setUp() throws Exception {
		defaultService = new UrlShorteningService();
		customService = new UrlShorteningService(customDomain, customLength);
	}

	@Test
	public void getAliasUrlDefault() {
		String url = "https://www2.deloitte.com/in/en/careers/life-at-deloitte.html";
		String actualAliasUrl = defaultService.getAliasUrl(url);
		String expected = "http://dtt.com/29C4";
		assertEquals(expected, actualAliasUrl);
	}
	
	@Test
	public void getAliasUrlCustom() {
		String url = "https://www2.deloitte.com/in/en/industries/consumer.html?icid=top_consumer";
		String aliasUrl = customService.getAliasUrl(url);
		int actualAliasKeyLength = aliasUrl.substring(customDomain.length()+1, aliasUrl.length()).length();
		assertEquals(customLength, actualAliasKeyLength);
	}
	
	@Test
	public void getAliasUrlForDuplicateValue() {
		String url = "https://www2.deloitte.com/in/en/careers/life-at-deloitte.html";
		String actualAliasUrl = defaultService.getAliasUrl(url);
		String expected = "http://dtt.com/29C4";
		assertEquals(expected, actualAliasUrl);
	}

}
