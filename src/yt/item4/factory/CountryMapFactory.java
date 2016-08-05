package yt.item4.factory;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.AbstractMap;
import java.util.AbstractMap.SimpleEntry;
import java.util.EnumMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import yt.item4.CountryCode;

public class CountryMapFactory {

	private <K extends Enum<K>, V> SimpleEntry<K, V> lineToEnumMapTuple(Class<K> keyType, Class<V> valueType, String line) {

		String[] lineArray = line.split(",", 2);
		try {
			return new AbstractMap.SimpleEntry<K, V>(Enum.valueOf(keyType, lineArray[0].trim()), valueType.cast(lineArray[1].replace("\"", "").trim()));
		} catch (IllegalArgumentException e) {
			return null;
		}
	}

	public Map<CountryCode, String> createCountryMap(String configFile) {//TODO check
		Map<CountryCode, String> countryCodeMap = null;

		try (Stream<String> stream = Files.lines(Paths.get(getClass().getClassLoader().getResource(configFile).toURI()))) {
			countryCodeMap = new EnumMap<CountryCode, String>(stream.map(line -> lineToEnumMapTuple(CountryCode.class, String.class, line))
					.filter(m -> null != m).collect(Collectors.toMap(m -> m.getKey(), m -> m.getValue())));
		} catch (IOException e) {
			e.printStackTrace();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		return countryCodeMap;
	}

}
