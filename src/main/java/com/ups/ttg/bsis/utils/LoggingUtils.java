package com.ups.ttg.bsis.utils;

import java.util.UUID;

import org.springframework.stereotype.Component;

@Component
public class LoggingUtils {
	
	public static String createMessageOID() {
		return UUID.randomUUID().toString();
	}
}
