package servlet;

import core.ForexData;
import core.TriangularArbitrage;
import model.Currency;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class ForexArbitrage
 */
@WebServlet("/ForexArbitrage")
public class ForexArbitrageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * Default constructor.
	 */
	public ForexArbitrageServlet() {

	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */

	/*
	 * Servlet takes input from the user form and interacts with other functions
	 * to determine the ROI
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String currency1 = request.getParameter("curr1").toUpperCase();
		String currency2 = request.getParameter("curr2").toUpperCase();
		String currency3 = request.getParameter("curr3").toUpperCase();
		String currency4 = request.getParameter("curr4").toUpperCase();
		String currency5 = request.getParameter("curr5").toUpperCase();
		String currency6 = request.getParameter("curr6").toUpperCase();

		if (!currency1.equals(currency6)) {
			System.out.println("Invalid triad");
		}
		if (!currency2.equals(currency3)) {
			System.out.println("Invalid triad");
		}
		if (!currency4.equals(currency5)) {
			System.out.println("Invalid triad");
		}
		ForexData fxd = new ForexData();
		Currency currency = new Currency();
		String flag = null;
		if (currency1.equals("USD")) {
			double conversion = fxd.usdForexData(currency2);
			currency.setRates(currency1 + currency2, conversion);
			flag = "currency1";
		} else if (currency3.equals("USD")) {
			double conversion = fxd.usdForexData(currency4);
			currency.setRates(currency3 + currency4, conversion);
			flag = "currency3";

		} else if (currency5.equals("USD")) {
			double conversion = fxd.usdForexData(currency6);
			currency.setRates(currency5 + currency6, conversion);
			flag = "currency5";

		}
		if (!flag.equals("currency1")) {
			double conversion = fxd.getOtherCurrecyData(currency1, currency2);
			currency.setRates(currency1 + currency2, conversion);
		}
		if (!flag.equals("currency3")) {
			double conversion = fxd.getOtherCurrecyData(currency3, currency4);
			currency.setRates(currency3 + currency4, conversion);
		}
		if (!flag.equals("currency5")) {
			double conversion = fxd.getOtherCurrecyData(currency5, currency6);
			currency.setRates(currency5 + currency6, conversion);
		}

		System.out.println(currency.rates);

		TriangularArbitrage arbitrage = new TriangularArbitrage();
		double initialInvestment = Double.parseDouble(request.getParameter("amount"));
		double amountArbitrage = arbitrage.calcArbitrage(initialInvestment, currency.getRates(currency1 + currency2),
				currency.getRates(currency3 + currency4), currency.getRates(currency5 + currency6));

		double value = arbitrage.calcReturns(initialInvestment, amountArbitrage);
		response.setContentType("text/html");
		PrintWriter printWriter = response.getWriter();
		if (arbitrage.isProfitable(value)) {
			String result = "Arbitrage is currently profitable for these currency pairs. You will earn a profit of "
					+ value + " for you investment";
			printWriter.append("<h3>" + result + "</h3>");

		} else {
			String result = "Arbitrage is currently not profitable for these currency pairs. You will lose " + value
					+ " for you investment.";
			printWriter.append("<h3>" + result + "</h3>");
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
