package br.com.test.mercadobitcoinapitesting;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.anything;
import static org.hamcrest.CoreMatchers.is;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import br.com.test.mercadobitcoinapitesting.domain.TestReport;
import br.com.test.mercadobitcoinapitesting.repository.TestReportRepository;
import io.restassured.http.ContentType;
/**
 * Classe para testes de enpoints da API publica
 * do Mercado Bitcoin.
 * 
 * A documentação referência utilizada para construção destes testes
 * está disponível em:  https://www.mercadobitcoin.com.br/api-doc/
 * @author Emiliano Fagundes
 *
 */
@SpringBootTest
public class MercadoBiticoinApiTesting {
	
	@Autowired
	private TestReportRepository repository;

	
	private final String BASE_URL = "https://www.mercadobitcoin.net/api/";
	
	/**
	 * Teste que verifica se o método Get do endpoint /BTC/ticker retorna HttpStatus 200 OK
	 * e ResponseBody válido.
	 * 
	 * curl --request GET \ url https://www.mercadobitcoin.net/api/BTC/ticker/
	 * @author Emiliano Fagundes 
	 * @Since 2019
	 */
	@Test
	public void whenGetInBtcTickerThenExpect200OkAndValidResponseBody() {
		TestReport report  = new TestReport();
		report.setTestName("whenGetInBtcTickerThenExpect200OkAndValidResponseBody");

		report.setStartedAt(LocalDateTime.now());
		String btcPath = "BTC/ticker/";
		given()
			.relaxedHTTPSValidation()
			.when()
			.get(BASE_URL + btcPath)
			.then()
			.statusCode(is(200))
			.contentType(ContentType.JSON)
			.body("ticker.high", is(anything()))
			.body("ticker.low", is(anything()))
			.body("ticker.vol", is(anything()))
			.body("ticker.last", is(anything()))
			.body("ticker.buy", is(anything()))
			.body("ticker.sell", is(anything()))
			.body("ticker.date", is(anything()));
		report.setStatus("OK");
		report.setFinishedAt(LocalDateTime.now());
		this.repository.save(report);
	}
	
	/**
	 * Teste que verifica se o método Get do endpoint /BTC/ticker retorna HttpStatus 
	 * 405Method Not Allowed
	 */
	@Test
	void whenPostInBtcTickerThenExpect405MethodNotAllowed() {
		TestReport report  = new TestReport();
		report.setTestName("whenPostInBtcTickerThenExpect405MethodNotAllowed");

		report.setStartedAt(LocalDateTime.now());
		String btcPath = "BTC/ticker/";
		given()
			.relaxedHTTPSValidation()
			.when()
			.post(BASE_URL + btcPath)
			.then()
			.statusCode(is(405));
		report.setStatus("OK");
		report.setFinishedAt(LocalDateTime.now());
		this.repository.save(report);
	}
	
	/**
	 * Teste que verifica o método GET do endpoint do livro de ofertas de Bitcoin (/BTC/orderbook/)
	 * espera-se que HttpStatus 200 Ok  
	 */

	@Test
	void whenGetBtcOrderBookThenExpect200OkAndValidResponseBody() {
		TestReport report  = new TestReport();
		report.setTestName("whenGetBtcOrderBookThenExpect200OkAndValidResponseBody");

		report.setStartedAt(LocalDateTime.now());
		String btcPath = "BTC/orderbook/";
		given()
			.relaxedHTTPSValidation()
			.when()
			.get(BASE_URL + btcPath)
			.then()
			.statusCode(is(200))
			.contentType(ContentType.JSON)
			.body("asks", is(anything()));
		
		report.setStatus("OK");
		report.setFinishedAt(LocalDateTime.now());
		this.repository.save(report);
	}
	
	/**
	 * Teste que verifica o método POST do endpoint do livro de ofertas de Bitcoin (/BTC/orderbook/)
	 * espera-se que HttpStatus 405 Method Not Allowed  
	 */
	@Test
	void whenPostInBtcOrderBookThenExpect405MethodNotAllowed() {
		
		
		TestReport report  = new TestReport();
		report.setTestName("whenPostInBtcOrderBookThenExpect405MethodNotAllowed");

		report.setStartedAt(LocalDateTime.now());
		String btcPath = "BTC/orderbook/";
		given()
			.relaxedHTTPSValidation()
			.when()
			.post(BASE_URL + btcPath)
			.then()
			.statusCode(is(405));
		
		report.setStatus("OK");
		report.setFinishedAt(LocalDateTime.now());
		this.repository.save(report);
	}
}
