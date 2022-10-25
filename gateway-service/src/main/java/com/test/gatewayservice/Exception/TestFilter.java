package com.test.gatewayservice.Exception;

import java.util.Base64;

import javax.crypto.spec.SecretKeySpec;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.crypto.DefaultJwtSignatureValidator;
import reactor.core.publisher.Mono;


@Component
public class TestFilter extends AbstractGatewayFilterFactory<TestFilter.Config> {

//	@Autowired
//	private Environment env;
	
//	@Autowired
//	private TestController handleException;
	
	public static class Config {
	    
	}

	public TestFilter() {
		super(Config.class);
	}
	
	@SuppressWarnings("unused")
	private Mono<Void> onError(ServerWebExchange exchange, String err, HttpStatus httpStatus){
		ServerHttpResponse response = exchange.getResponse();
        ErrorDetails res = new ErrorDetails(httpStatus.value(), err);
        HttpHeaders httpHeaders = response.getHeaders();
//        httpHeaders.add("Content-Type", "application/json; charset=UTF-8");
        
        String json = "";
		try {
			json = new ObjectMapper().writeValueAsString(res);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
        byte[] bytes = json.getBytes();
        DataBuffer buffer = response.bufferFactory().wrap(bytes);
        response.setStatusCode(httpStatus);
        System.out.println(json);
        
        return response.writeWith(Mono.just(buffer));
    }

	final Logger logger = LoggerFactory.getLogger(TestFilter.class);
	final SignatureAlgorithm sa = SignatureAlgorithm.HS256;
	final SecretKeySpec secretKeySpec = new SecretKeySpec("daycaidaynaychinhlachukycuabandungdelorangoaidaynhenguyhiemchetnguoidayhihihi".getBytes(), sa.getJcaName());

	@Override
	public GatewayFilter apply(Config config) {
		return (exchange, chain) -> {
			ServerHttpRequest request = exchange.getRequest();
			
			if(request.getHeaders().get("Authorization") == null) {
				return this.onError(exchange, "No Authorization header", HttpStatus.FORBIDDEN);
			}
			
			final String authorizationHeader = request.getHeaders().get("Authorization").get(0);
			
			if(authorizationHeader == "") {
				return this.onError(exchange, "No Token", HttpStatus.FORBIDDEN);
			}
			final String token = authorizationHeader.substring(6);
			String[] chunks = token.split("\\.");
			
			Base64.Decoder decoder = Base64.getUrlDecoder();

			String tokenWithoutSignature = chunks[0] + "." + chunks[1];
			String signature = chunks[2];
			
//			String header = new String(decoder.decode(chunks[0]));
			String payload = new String(decoder.decode(chunks[1]));
			
			DefaultJwtSignatureValidator validator = new DefaultJwtSignatureValidator(sa, secretKeySpec);
			
			
			if(!validator.isValid(tokenWithoutSignature, signature)) {
				return this.onError(exchange, "Invalid Authorization header", HttpStatus.FORBIDDEN);
			}
			
			JSONParser parser = new JSONParser();
			JSONObject json = null;
			try {
				json = (JSONObject) parser.parse(payload);
			} catch (ParseException e) {
				e.printStackTrace();
			} 
			JSONObject user = (JSONObject) json.get("user");
			JSONArray authorities = (JSONArray) user.get("authorities");
			if(authorities.isEmpty()) return this.onError(exchange, "not authorized", HttpStatus.UNAUTHORIZED);
			
			
			if(!authorities.get(authorities.size()-1).toString().contains("ADMIN")) {
				return this.onError(exchange, "not authorized by admin", HttpStatus.UNAUTHORIZED);
			}
			
			return chain.filter(exchange);
		};
	}
}
