package core;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Extracts forex data from various APIs
 * 
 * @author vinayakmarali
 *
 */
public class ForexData {

	// extracts forex data from fixer.io API
	public double getOtherCurrecyData(String baseCurrency, String toCurrency) throws JSONException, IOException {
		JSONObject jsonObj = readJsonFromUrl(
				"http://api.fixer.io/latest?base=" + baseCurrency + "&symbols=" + toCurrency);
		String rates = jsonObj.get("rates").toString();
		double exchangeRate = Double.parseDouble(rates.substring(rates.indexOf(':') + 1, rates.length() - 1));
		return exchangeRate;
	}

	// extracts the conversion rates for USD from currency layer API
	public double usdForexData(String toCurrency) throws JSONException, IOException {
		JSONObject jsonObj = readJsonFromUrl(
				"http://apilayer.net/api/live?access_key=fe6a06037456df21bea88ca36c111f97&currencies=" + toCurrency);
		String rates = jsonObj.get("quotes").toString();
		double exchangeRate = Double.parseDouble(rates.substring(rates.indexOf(':') + 1, rates.length() - 1));
		return exchangeRate;
	}

	// uses org.json API to parse the JSON market data received
	public static JSONObject readJsonFromUrl(String url) throws IOException, JSONException {
		InputStream is = new URL(url).openStream();
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
			JSONObject js = new JSONObject(readAll(reader));
			return js;
		} finally {
			is.close();
		}
	}

	// reads and appends data to string builder
	private static String readAll(Reader reader) throws IOException {
		int i;
		StringBuilder stringBuilder = new StringBuilder();
		while ((i = reader.read()) != -1) {
			stringBuilder.append((char) i);
		}
		return stringBuilder.toString();
	}

}
