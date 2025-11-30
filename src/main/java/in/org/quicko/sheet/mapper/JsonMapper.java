package in.org.quicko.sheet.mapper;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;

public class JsonMapper extends com.fasterxml.jackson.databind.json.JsonMapper
{

	private static final long serialVersionUID = -5476932598287013800L;

	/**
	 * Constructs a new ObjectMapper with specific feature configurations: - Uses BigDecimal for floating-point numbers
	 * during deserialization. - Uses BigInteger for integer numbers during deserialization. - Disables automatic
	 * detection of getter methods as properties. - Disables automatic detection of setter methods as properties.
	 */
	public JsonMapper()
	{
		super();

		this.enable(DeserializationFeature.USE_BIG_DECIMAL_FOR_FLOATS);
		this.enable(DeserializationFeature.USE_BIG_INTEGER_FOR_INTS);

		this.disable(MapperFeature.AUTO_DETECT_GETTERS);
		this.disable(MapperFeature.AUTO_DETECT_SETTERS);
	}

}
