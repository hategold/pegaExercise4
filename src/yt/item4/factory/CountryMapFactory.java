package yt.item4.factory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.EnumMap;
import java.util.Map;

import yt.item4.CountryCode;

public class CountryMapFactory {

	public Map<CountryCode, String> createCountryMap(String configFile) {//TODO check
		Map<CountryCode, String> countryCodeMap = new EnumMap<CountryCode, String>(CountryCode.class);
		BufferedReader countryCodeReader = new BufferedReader(new InputStreamReader(getClass().getClassLoader().getResourceAsStream(configFile)));

		try {
			String line = "";
			while ((line = countryCodeReader.readLine()) != null) { // can use double loop to avoid double try?
				int cammaIndex = line.indexOf(",");
				String countryCodeString = line.substring(0, cammaIndex).trim();
				String countryName = line.substring(cammaIndex + 1).replace("\"", "").trim();
				try {
					countryCodeMap.put(CountryCode.valueOf(countryCodeString), countryName);
				} catch (IllegalArgumentException e) {
					continue;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return countryCodeMap;
	}
}
