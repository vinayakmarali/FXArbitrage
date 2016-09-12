package core;

/**
 * This class contains the formula computation for deriving the triangluar
 * arbitrage and determining whether we can gain profit from it
 * 
 * @author vinayakmarali
 *
 */
public class TriangularArbitrage {

	// calculates triangular arbitrage
	public double calcArbitrage(double initialInvestment, double currencyRate1, double currencyRate2,
			double currencyRate3) {
		double forexArbitrage, sellCurrency1, sellCurrency2;
		sellCurrency1 = initialInvestment * currencyRate1;
		sellCurrency2 = sellCurrency1 / currencyRate2;
		forexArbitrage = sellCurrency2 * currencyRate3;
		return forexArbitrage;
	}

	// calculates return on investment
	public double calcReturns(double initialInvestment, double forexArbitrage) {
		return forexArbitrage - initialInvestment;
	}

	// checks whether the deal is profitable or not
	public boolean isProfitable(double finalArbitrage) {
		if (finalArbitrage > 0) {
			return true;
		} else {
			return false;
		}
	}

}
