package br.com.burnhop;

import br.com.burnhop.model.Login;
import br.com.burnhop.repository.LoginRepository;
import br.com.burnhop.repository.UsersRepository;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class BurnhopApplication {

	public static void main(String[] args) {
		SpringApplication.run(BurnhopApplication.class, args);
	}

	@Bean
	ApplicationRunner init(LoginRepository repository) {

		return args -> {
			try {
				Login login1 = new Login("wanderson_zika@uol.com.br", "@123");
				Login login2 = new Login("joaomaiszikaqueowanderson@outlook.com", "123@");
				repository.save(login1);
				repository.save(login2);
			}
			catch (Exception e) {
				e.printStackTrace();
			}
			repository.findAll().forEach(System.out::println);
		};
	}


}
