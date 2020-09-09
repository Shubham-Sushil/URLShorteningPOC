package com.deloitte.url.shortening.util;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class UrlCachingService {

	private static Map<String, String> urlCacheMap = new ConcurrentHashMap<>();
	
	public static String saveAliasUrl(String originalUrl, String aliasUrl) {
		return urlCacheMap.put(originalUrl, aliasUrl);
	}
	
	public static String getAliasUrl(String originalUrl) {
		return urlCacheMap.get(originalUrl);
	}
	
}
