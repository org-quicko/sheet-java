package in.org.quicko.sheet.serializer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.module.SimpleModule;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import in.org.quicko.sheet.beans.Item;
import in.org.quicko.sheet.beans.List;
import in.org.quicko.sheet.mapper.JsonMapper;

class ListSerializerTest
{

	private JsonMapper mapper;

	@BeforeEach
	void setUp()
	{
		mapper = new JsonMapper();
		SimpleModule module = new SimpleModule();
		module.addSerializer(Item.class, new CustomItemSerializer());
		module.addDeserializer(Item.class, new CustomItemDeserializer());
		mapper.registerModule(module);
	}

	@Test
	void testSerializeEmptyList() throws JsonProcessingException
	{
		// Arrange
		List list = new List();

		// Act
		String json = mapper.writeValueAsString(list);

		// Assert
		assertEquals("{\"metadata\":null,\"@entity\":\"list\",\"items\":null,\"name\":\"list\"}", json);
	}

	@Test
	void testSerializeListWithSingleItem() throws JsonProcessingException
	{
		// Arrange
		List list = new List();
		list.addItem(new Item("id", "123"));

		// Act
		String json = mapper.writeValueAsString(list);

		// Assert
		assertEquals("{\"metadata\":null,\"@entity\":\"list\",\"items\":[{\"id\":\"123\"}],\"name\":\"list\"}", json);
	}

	@Test
	void testSerializeListWithMultipleItems() throws JsonProcessingException
	{
		// Arrange
		List list = new List();
		list.addItem(new Item("id", "123"));
		list.addItem(new Item("name", "John Doe"));
		list.addItem(new Item("active", true));

		// Act
		String json = mapper.writeValueAsString(list);

		// Assert
		assertEquals(
		        "{\"metadata\":null,\"@entity\":\"list\",\"items\":[{\"id\":\"123\"},{\"name\":\"John Doe\"},{\"active\":true}],\"name\":\"list\"}",
		        json);
	}

	@Test
	void testSerializeListWithNestedItems() throws JsonProcessingException
	{
		// Arrange
		List list = new List();
		Item nestedItem = new Item("nested", "value");
		list.addItem(new Item("simple", "data"));
		list.addItem(new Item("complex", nestedItem));

		// Act
		String json = mapper.writeValueAsString(list);

		// Assert
		assertEquals(
		        "{\"metadata\":null,\"@entity\":\"list\",\"items\":[{\"simple\":\"data\"},{\"complex\":{\"nested\":\"value\"}}],\"name\":\"list\"}",
		        json);
	}

	@Test
	void testSerializeListWithDifferentValueTypes() throws JsonProcessingException
	{
		// Arrange
		List list = new List();
		list.addItem(new Item("string", "text"));
		list.addItem(new Item("number", 42));
		list.addItem(new Item("boolean", true));

		Object value = null;

		list.addItem(new Item("null", value));

		// Act
		String json = mapper.writeValueAsString(list);

		// Assert
		assertEquals(
		        "{\"metadata\":null,\"@entity\":\"list\",\"items\":[{\"string\":\"text\"},{\"number\":42},{\"boolean\":true},{\"null\":null}],\"name\":\"list\"}",
		        json);
	}

	@Test
	void testSerializeAndDeserializeList() throws JsonProcessingException
	{
		// Arrange
		List originalList = new List();
		originalList.addItem(new Item("id", "123"));
		originalList.addItem(new Item("name", "John Doe"));
		originalList.addItem(new Item("age", 30));

		// Act
		String json = mapper.writeValueAsString(originalList);
		List deserializedList = mapper.readValue(json, List.class);

		// Assert
		assertNotNull(deserializedList);
		assertEquals(3, deserializedList.size());

		Item idItem = deserializedList.getItem("id");
		assertNotNull(idItem);
		assertEquals("id", idItem.getKey());
		assertEquals("123", idItem.getValue());

		Item nameItem = deserializedList.getItem("name");
		assertNotNull(nameItem);
		assertEquals("name", nameItem.getKey());
		assertEquals("John Doe", nameItem.getValue());

		Item ageItem = deserializedList.getItem("age");
		assertNotNull(ageItem);
		assertEquals("age", ageItem.getKey());
		assertEquals(30, ageItem.getIntegerValue());
	}

	@Test
	void testSerializeListWithCustomMetadata() throws JsonProcessingException
	{
		// Arrange
		List list = new List();
		list.addItem(new Item("key", "value"));
		list.getMetadata().put("author", "Test User");
		list.getMetadata().put("version", 1);

		// Act
		String json = mapper.writeValueAsString(list);

		// Assert
		assertTrue(json.contains("\"items\":[{\"key\":\"value\"}]"));
	}

	@Test
	void testRoundTripWithItemManipulation() throws JsonProcessingException
	{
		// Arrange
		List originalList = new List();
		originalList.addItem(new Item("item1", "value1"));
		originalList.addItem(new Item("item2", "value2"));

		// Serialize
		String json = mapper.writeValueAsString(originalList);

		// Deserialize
		List deserializedList = mapper.readValue(json, List.class);

		// Manipulate
		deserializedList.removeItem("item1");
		deserializedList.addItem(new Item("item3", "value3"));
		deserializedList.replaceItem(new Item("item2", "updated"));

		// Re-serialize
		String updatedJson = mapper.writeValueAsString(deserializedList);

		// Assert
		assertTrue(updatedJson.contains("\"item3\":\"value3\""));
		assertTrue(updatedJson.contains("\"item2\":\"updated\""));
		assertTrue(!updatedJson.contains("\"item1\":\"value1\""));
	}
}
