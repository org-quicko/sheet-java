package in.org.quicko.sheet.serializer;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

import org.junit.jupiter.api.Test;

import in.org.quicko.sheet.beans.Item;

class CustomItemDeserializerTest
{

	@Test
	void testDeserializeItem() throws JsonProcessingException
	{
		// Arrange
		ObjectMapper objectMapper = new ObjectMapper();
		SimpleModule module = new SimpleModule();
		module.addDeserializer(Item.class, new CustomItemDeserializer());
		objectMapper.registerModule(module);

		String json = "{\"id\":\"123\"}";

		// Act
		Item item = objectMapper.readValue(json, Item.class);

		// Assert
		assertEquals("id", item.getKey());
		assertEquals("123", item.getValue());
	}
}
