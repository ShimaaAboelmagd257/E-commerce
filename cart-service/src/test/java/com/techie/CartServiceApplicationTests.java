package com.techie;

import com.techie.domain.CartEntity;
import com.techie.domain.ProductResponse;
import com.techie.repository.CartRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@EmbeddedKafka(partitions = 1,topics = { "product-request", "product-response"})
class CartServiceApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private KafkaTemplate<String,Object> kafkaTemplate;

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

		Long cartId = 1L;
		Integer productId = 100;

		mockMvc.perform(post("/cart/" +cartId+"/product/")
				.contentType("application/json")
				.content(String.valueOf(productId)))
				.andExpect(status().isOk());

		verify(kafkaTemplate).send("product-request",productId);

		ProductResponse productResponse = new ProductResponse();
		productResponse.setProductId(productId);
		productResponse.setProductPrice(99.99);
		productResponse.setProductName("pijama");

		kafkaTemplate.send("product-response",productResponse);

		Thread.sleep(1000);
		verify(cartRepository).save(any(CartEntity.class));
	}

}
