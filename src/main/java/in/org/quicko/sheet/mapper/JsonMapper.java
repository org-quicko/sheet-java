package in.org.quicko.sheet.mapper;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

import tools.jackson.databind.DeserializationFeature;
import tools.jackson.databind.json.JsonMapper.Builder;
import tools.jackson.datatype.jsonorg.JsonOrgModule;


public class JsonMapper extends tools.jackson.databind.json.JsonMapper
{

	private static final long serialVersionUID = -5476932598287013800L;

	/**
	 * Constructs a new ObjectMapper with specific feature configurations: - Uses BigDecimal for floating-point numbers
	 * during deserialization. - Uses BigInteger for integer numbers during deserialization. - Disables automatic
	 * detection of getter methods, is-getter methods and setter methods as properties.
	 */
	public JsonMapper()
	{
		super(builder());
	}

	/**
	 * Creates the opinionated builder for this library.
	 *
	 * @return Builder initialized with Jackson 2 style defaults and the sheet library's custom settings.
	 */
	public static Builder builder()
	{
		return configure(tools.jackson.databind.json.JsonMapper.builderWithJackson2Defaults());
	}

	private static Builder configure(Builder builder)
	{
		return builder.addModule(new JsonOrgModule()).enable(DeserializationFeature.USE_BIG_DECIMAL_FOR_FLOATS)
		        .enable(DeserializationFeature.USE_BIG_INTEGER_FOR_INTS)
		        .changeDefaultVisibility(vc -> vc.withGetterVisibility(JsonAutoDetect.Visibility.NONE)
		                .withIsGetterVisibility(JsonAutoDetect.Visibility.NONE)
		                .withSetterVisibility(JsonAutoDetect.Visibility.NONE));
	}

}
