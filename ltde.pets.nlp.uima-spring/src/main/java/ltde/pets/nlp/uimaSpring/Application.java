package ltde.pets.nlp.uimaSpring;

import org.apache.uima.CompositeResourceFactory;
import org.apache.uima.UIMAFramework;
import org.apache.uima.analysis_engine.AnalysisEngineDescription;
import org.apache.uima.collection.CasConsumerDescription;
import org.apache.uima.collection.CollectionReaderDescription;
import org.apache.uima.fit.spring.factory.AnalysisEngineFactory_impl;
import org.apache.uima.fit.spring.factory.CasConsumerFactory_impl;
import org.apache.uima.fit.spring.factory.CollectionReaderFactory_impl;
import org.apache.uima.fit.spring.factory.CustomResourceFactory_impl;
import org.apache.uima.resource.CustomResourceSpecifier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.test.SpringApplicationContextLoader;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.AbstractContextLoaderInitializer;
import org.springframework.web.context.WebApplicationContext;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

@SpringBootApplication
@EnableSwagger2
public class Application {

	public static void main(String[] args) {
		SpringApplicationBuilder builder = new SpringApplicationBuilder(Application.class);
//		builder.initializers(new UimaInitialization())
		builder.listeners(new UimalListener())
				.run(args);
//		SpringApplication app = new SpringApplication(Application.class);
//
//		app.addInitializers(new UimaInitialization());
//		app.run(args);
	}
}
