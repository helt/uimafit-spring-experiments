package ltde.pets.nlp.uimaSpring.autowired;

import org.apache.uima.CompositeResourceFactory;
import org.apache.uima.UIMAFramework;
import org.apache.uima.analysis_engine.AnalysisEngineDescription;
import org.apache.uima.collection.CasConsumerDescription;
import org.apache.uima.collection.CasInitializerDescription;
import org.apache.uima.collection.CollectionReaderDescription;
import org.apache.uima.fit.spring.factory.*;
import org.apache.uima.impl.CompositeResourceFactory_impl;
import org.apache.uima.impl.UIMAFramework_impl;
import org.apache.uima.resource.CustomResourceSpecifier;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextException;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * This component makes sure that Apache UIMA is capable to handle @Autowired injection of services
 * and alike within AnalysisEngines etc.
 *
 * Created by helt on 13.02.2016.
 */
@Configuration
public class UimaResourceFactoryRegistrarPostProcessor implements BeanDefinitionRegistryPostProcessor {

  @Override
  public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry)
      throws BeansException {
    registry.registerBeanDefinition("collectionReaderFactory", BeanDefinitionBuilder
        .genericBeanDefinition(CollectionReaderFactory_impl.class).getBeanDefinition());
    registry.registerBeanDefinition("casInitializerFactory", BeanDefinitionBuilder
        .genericBeanDefinition(CasInitializerFactory_impl.class).getBeanDefinition());
    registry.registerBeanDefinition("customResourceFactory", BeanDefinitionBuilder
        .genericBeanDefinition(CustomResourceFactory_impl.class).getBeanDefinition());
    registry.registerBeanDefinition("analysisEngineFactory", BeanDefinitionBuilder
        .genericBeanDefinition(AnalysisEngineFactory_impl.class).getBeanDefinition());
    registry.registerBeanDefinition("casConsumerFactory", BeanDefinitionBuilder
        .genericBeanDefinition(CasConsumerFactory_impl.class).getBeanDefinition());
  }

  @Override
  public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory)
      throws BeansException {
    final CompositeResourceFactory uimaFactory = UIMAFramework.getResourceFactory();
    uimaFactory.registerFactory(CollectionReaderDescription.class, beanFactory.getBean
        (CollectionReaderFactory_impl.class));
    uimaFactory.registerFactory(CasConsumerDescription.class, beanFactory.getBean
        (CasConsumerFactory_impl.class));
    uimaFactory.registerFactory(CasInitializerDescription.class, beanFactory.getBean
        (CasInitializerFactory_impl.class));
    uimaFactory.registerFactory(AnalysisEngineDescription.class, beanFactory.getBean
        (AnalysisEngineFactory_impl.class));
    uimaFactory.registerFactory(CustomResourceSpecifier.class, beanFactory.getBean
        (CustomResourceFactory_impl.class));
  }
}
