package com.deloitte.url.shortening.service;

import java.util.concurrent.atomic.AtomicLong;

import com.deloitte.url.shortening.util.UrlCachingService;

public class UrlShorteningService implements IUrlShorteningService{

	// Counter for Urls to calculate key. 
	// We can start our Counter as our requirement from 1l or any required number for our min length of key requirement
	// I am starting my counter from 1000000l to get atleast min key length of 4
	private static AtomicLong atomicCounter = new AtomicLong(1000000l);
	
	// Array to store possible set of characters in our alias url
	private static char[] charMap = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz".toCharArray();
	
	private final String domain;
	private final int maxKeyLength;
	
	//Default Constructor
	public UrlShorteningService() {
		super();
		domain = "http://dtt.com";
		this.maxKeyLength = 8;
	}
	
	// Constructor for Custom domain and max key length
	public UrlShorteningService(String domain, int maxKeyLength) {
		super();
		this.domain = domain;
		this.maxKeyLength = maxKeyLength;
	}
	

	/**
	 * This Method return an alias from Cache.
	 * If it is not present in cache then creaes and stores it.
	 */
	@Override
	public String getAliasUrl(String originalUrl) {
		StringBuilder aliasUrl = new StringBuilder();
		
		//Checking if this url is already stored
		String storedAliasUrl = UrlCachingService.getAliasUrl(originalUrl);
		
		if(null != storedAliasUrl) {
			// Url is already having a alias. So just appending it.
			aliasUrl.append(storedAliasUrl);
		}else {
			// Calculating a new alias and storing it in cache.
			aliasUrl.append(domain);
			aliasUrl.append("/");
			aliasUrl.append(calculateKey());
			saveUrl(originalUrl, aliasUrl.toString());
		}
		return aliasUrl.toString();
	}

	
	/**
	 * This Method stores an alias into Cache.
	 * We can store in DB also for permanent storage.
	 */
	public void saveUrl(String originalUrl, String aliasUrl) {
		UrlCachingService.saveAliasUrl(originalUrl, aliasUrl);
	}
	
	
	/**
	 * This Method calculates short key of specified length.
	 */
	public String calculateKey() {
		long counter = atomicCounter.getAndIncrement();
		StringBuffer key = new StringBuffer();
		int base = charMap.length;
		do {
			key.append(charMap[(int) (counter % base)]);
			counter = counter / base;
		} while (counter > 0 && key.length() < maxKeyLength);
		return key.toString();
	}

}
