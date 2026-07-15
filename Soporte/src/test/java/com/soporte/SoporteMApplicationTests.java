package com.soporte;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.ActiveProfiles;

import com.soporte.soportem.SoporteMApplication;
import com.soporte.soportem.feign.PedidoFeignClient;
import com.soporte.soportem.feign.UsuarioFeignClient;

@SpringBootTest
@ActiveProfiles("test")
class SoporteMApplicationTests {

	@MockitoBean
	private UsuarioFeignClient usuarioFeignClient;

	@MockitoBean
	private PedidoFeignClient pedidoFeignClient;

	@Test
	void contextLoads() {
	}

	@Test
	void main_ejecuta() {
		SoporteMApplication.main(new String[]{
			"--spring.profiles.active=test",
			"--spring.main.web-application-type=none"
		});
	}

}
