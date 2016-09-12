package model;

import java.util.HashMap;

/**
 * This class acts as a POJO and it stores the conversion rates for different
 * currency pairs
 * 
 * @author vinayakmarali
 *
 */
public class Currency {

	public HashMap<String, Double> rates = new HashMap<>();

	// returns the rate for a currency pair
	public double getRates(String symbol) {
		return rates.get(symbol);
	}

	// sets the rate for a currency pair
	public void setRates(String symbol, double value) {
		rates.put(symbol, value);
	}

}
