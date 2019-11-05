package br.com.test.mercadobitcoinapitesting;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.anything;
import static org.hamcrest.CoreMatchers.is;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

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
	void whenGetInBtcTickerThenExpect200OkAndValidResponseBody() {
		
		String btcPath = "BTC/ticker/";
		given()
			.relaxedHTTPSValidation()
			.when()
			.get(BASE_URL + btcPath)
			.then()
			.statusCode(200)
			.contentType(ContentType.JSON)
			.body("ticker.high", is(anything()))
			.body("ticker.low", is(anything()))
			.body("ticker.vol", is(anything()))
			.body("ticker.last", is(anything()))
			.body("ticker.buy", is(anything()))
			.body("ticker.sell", is(anything()))
			.body("ticker.date", is(anything()));
			
	}
	
	/**
	 * Teste que verifica se o método Get do endpoint /BTC/ticker retorna HttpStatus 
	 * 405Method Not Allowed
	 */
	@Test
	void whenPostInBtcTickerThenExpect405MethodNotAllowed() {
		
		String btcPath = "BTC/ticker/";
		given()
			.relaxedHTTPSValidation()
			.when()
			.post(BASE_URL + btcPath)
			.then()
			.statusCode(405);
	}
	
	/**
	 * Teste que verifica o método GET do endpoint do livro de ofertas de Bitcoin (/BTC/orderbook/)
	 * espera-se que HttpStatus 200 Ok  
	 */
	void whenGetBtcOrderBookThenExpect200OkAndValidResponseBody() {
		
		String btcPath = "BTC/orderbook/";
		given()
			.relaxedHTTPSValidation()
			.when()
			.get(BASE_URL + btcPath)
			.then()
			.statusCode(200)
			.contentType(ContentType.JSON)
			.body("asks", is(anything()));
	}
	
	/**
	 * Teste que verifica o método POST do endpoint do livro de ofertas de Bitcoin (/BTC/orderbook/)
	 * espera-se que HttpStatus 405 Method Not Allowed  
	 */
	@Test
	void whenPostInBtcOrderBookThenExpect405MethodNotAllowed() {
		
		String btcPath = "BTC/orderbook/";
		given()
			.relaxedHTTPSValidation()
			.when()
			.post(BASE_URL + btcPath)
			.then()
			.statusCode(405);
	}
}
