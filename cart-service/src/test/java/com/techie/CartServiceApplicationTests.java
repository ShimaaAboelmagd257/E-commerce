package com.techie;

import com.techie.domain.CartEntity;
import com.techie.domain.ProductResponse;
import com.techie.repository.CartRepository;
import com.techie.service.CartService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.test.EmbeddedKafkaBroker;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = CartServiceApplication.class)
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@EmbeddedKafka(partitions = 1,topics = { "product-request", "product-response"})
class CartServiceApplicationTests {
	private final Map<Integer, CompletableFuture<ProductResponse>> productFutureMap = new ConcurrentHashMap<>();

	private static final String PRODUCT_REQUEST_TOPIC = "product-request";
	private static final String PRODUCT_RESPONSE_TOPIC = "product-response";

	private static final Logger logger = LoggerFactory.getLogger(CartServiceApplicationTests.class);
	@Autowired
	private EmbeddedKafkaBroker embeddedKafkaBroker;
	@Autowired
	private MockMvc mockMvc;
    @MockBean
	private KafkaTemplate<String,Object> kafkaTemplate;
	@MockBean
	private CartService cartService;
	@MockBean
	private CartRepository cartRepository;

	@BeforeEach
	public  void setUp(){
		CartEntity cart =new CartEntity();
		cart.setCartId(1L);
		when(cartRepository.findById(1l)).thenReturn(Optional.of(cart));
	}
	@Test
	void addToCartTest() throws Exception {


	}

	@AfterEach
	void tearDown(){
		logger.info("Breaking down -------------------- embeddedKafkaBroker.destroy() ");

		embeddedKafkaBroker.destroy();
	}

}
