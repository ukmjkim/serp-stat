package com.serpstat.rest.utils;

import static org.junit.Assert.assertEquals;


import org.json.JSONException;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;

import static com.serpstat.rest.utils.JsonConverter.convertObjectToJson;
import static com.serpstat.rest.utils.JsonConverter.convertJsonToList;
import static com.serpstat.rest.utils.JsonConverter.convertJsonToMap;

public final class JsonConverterTest {
	@Test
	public void testConvertObjectToJson() throws JSONException {
		User user = new User(1, "john", "von");
		String expected =  "{id:1,name:john,password:von,email:invalid@invalid.com}";
		JSONAssert.assertEquals(expected, convertObjectToJson((Object) user), true);
	}

	@Test
	public void testConvertJsonToList() {
		String jsonString = "[" + "{user:{id:1,name:'john',password:'von'}},"
				+ "{user:{id:2,name:'bob',password:'pwd'}}" + "]";

		assertEquals(2, convertJsonToList(jsonString).size());
	}

	@Test
	public void testConvertJsonToMap() {
		String jsonString = "{id:1,name:'john',password:'von',email:'invalid@invalid.com'}";

		assertEquals("john", convertJsonToMap(jsonString).get("name"));
		assertEquals(1, convertJsonToMap(jsonString).get("id"));
	}

	private static final class User {
		static final String DEFAULT_EMAIL = "invalid@invalid.com";
		int id;
		String name;
		String password;
		String email = DEFAULT_EMAIL;

		public User(int id, String name, String password) {
			this.id = id;
			this.name = name;
			this.password = password;
		}
	}
}
