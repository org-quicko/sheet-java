package in.org.quicko.sheet.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;

import java.io.IOException;

import in.org.quicko.sheet.beans.Item;

public class CustomItemSerializer extends JsonSerializer<Item>
{

	@Override
	public void serialize(final Item item, final JsonGenerator gen, final SerializerProvider serializers)
	        throws IOException
	{

		if (item.getKey() == null)
		{
			throw new IOException("Cannot serialize Item with null key: " + item.toString());
		}

		gen.writeStartObject(); // Start a new JSON object

		// Dynamically write the key-value pair based on the item's key
		gen.writeObjectField(item.getKey(), item.getValue());

		gen.writeEndObject(); // End the JSON object
	}

	@Override
	public void serializeWithType(final Item item, final JsonGenerator gen, final SerializerProvider serializers,
	        final TypeSerializer typeSer) throws IOException
	{
	}

}