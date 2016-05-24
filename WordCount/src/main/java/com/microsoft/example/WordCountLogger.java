package com.microsoft.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.json.JSONObject;

public class WordCountLogger {
	private static final Logger LOG = LoggerFactory.getLogger(WordCountLogger.class);

	private static final String processName = "WORD-COUNT";

	public static void EVENT(String name) {
		JSONObject json = new JSONObject();
		json.put("EVENT", name);
		logInfo(json);
	} 

	public static void EVENT(String name, String text) {
		JSONObject json = new JSONObject();
		json.put("EVENT", name);
		json.put("TEXT", text);
		logInfo(json);
	}

	public static void EVENT(String name, String text, Integer value) {
		JSONObject json = new JSONObject();
		json.put("EVENT", name);
		json.put("TEXT", text);
		json.put("VALUE", value);
		logInfo(json);
	}

	public static void ERROR(String text) {
		JSONObject json = new JSONObject();
		json.put("MESSAGE", text);
		json.put("INDEX", "ERROR");
		json.put("PROCESS", processName);
		LOG.error(json.toString());
	}

	private static void logInfo(JSONObject message) {
		message.put("PROCESS", processName);
		message.put("INDEX", "EVENT");
		LOG.info(message.toString());
	}
}