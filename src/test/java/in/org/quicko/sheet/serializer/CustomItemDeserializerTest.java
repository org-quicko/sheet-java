package in.org.quicko.sheet.serializer;

import static org.junit.jupiter.api.Assertions.assertEquals;

import tools.jackson.core.JacksonException;
import tools.jackson.databind.module.SimpleModule;

import org.junit.jupiter.api.Test;

import in.org.quicko.sheet.beans.Item;
import in.org.quicko.sheet.mapper.JsonMapper;

class CustomItemDeserializerTest
{

	@Test
	void testDeserializeItem() throws JacksonException
	{
		// Arrange
		SimpleModule module = new SimpleModule();
		module.addDeserializer(Item.class, new CustomItemDeserializer());
		JsonMapper objectMapper = new JsonMapper(
		        tools.jackson.databind.json.JsonMapper.builderWithJackson2Defaults().addModule(module));

		String json = "{\"id\":\"123\"}";

		// Act
		Item item = objectMapper.readValue(json, Item.class);

		// Assert
		assertEquals("id", item.getKey());
		assertEquals("123", item.getValue());
	}
}
