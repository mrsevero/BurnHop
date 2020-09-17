package br.com.burnhop;

import java.sql.Timestamp;
import java.sql.Date;
import br.com.burnhop.model.Users;

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
	ApplicationRunner init(LoginRepository repository, UsersRepository repository2) {

		return args -> {
			try {
				Login login1 = new Login("wanderson_zika@uol.com.br", "@123");
				Login login2 = new Login("joaomaiszikaqueowanderson@outlook.com", "123@");
				repository.save(login1);
				repository.save(login2);

				Timestamp time = new Timestamp(System.currentTimeMillis());
				Date data_nasc = new Date(1999,1,28);
				Users user = new Users("Wanderson", "wandsu", data_nasc, time);	
				repository2.save(user);
				System.out.println("User ID: " + user.getId());
			}
			catch (Exception e) {
				e.printStackTrace();
			}
			repository.findAll().forEach(System.out::println);
		};
	}


}
