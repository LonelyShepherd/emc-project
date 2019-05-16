package mk.finki.ukim.emt.lab;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.RequestParam;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
public class LabApplication {
	public static void main(String[] args) {
		SpringApplication.run(LabApplication.class, args);
	}
}
