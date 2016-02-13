package ltde.pets.nlp.uimaSpring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import uima.EnableUimaInjections;

@SpringBootApplication
@EnableSwagger2
@EnableUimaInjections
public class Application {

  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }
}
