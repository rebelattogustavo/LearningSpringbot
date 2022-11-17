package br.senai.sc.editoralivros;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

@SpringBootTest
class EditoraLivrosApplicationTests {

	@Test
	void contextLoads() {
		try {
			Optional optional = Optional.ofNullable(null);
			System.out.println(optional.get());
		}catch (Exception e){
			System.out.println(e.getLocalizedMessage());
		}
	}

}
