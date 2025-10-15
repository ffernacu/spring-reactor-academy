package pe.ffernacu.spring_reactor_academy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class SpringReactorAcademyApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringReactorAcademyApplication.class, args);
	}

}
