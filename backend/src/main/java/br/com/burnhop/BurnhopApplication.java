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

}
