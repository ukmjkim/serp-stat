package com.serpstat.rest.utils;

import java.util.List;
import java.util.Map;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

public final class JsonConverter {
	public static String convertObjectToJson(Object obj) {
		return new Gson().toJson(obj);
	}

	public static List<Map<String, Object>> convertJsonToList(String jsonString) {
		return new Gson().fromJson(jsonString, new TypeToken<List<Map<String, Object>>>() {}.getType());
	}

	public static Map<String, Object> convertJsonToMap(String jsonString) {
		CustomizedObjectTypeAdapter adapter = new CustomizedObjectTypeAdapter();
		Gson gson = new GsonBuilder()
		        .registerTypeAdapter(Map.class, adapter)
		        .registerTypeAdapter(List.class, adapter)
		        .create();

		@SuppressWarnings("unchecked")
		Map<String, Object> map = (Map<String, Object>) gson.fromJson(jsonString, Map.class);

		return map;
	}
}
