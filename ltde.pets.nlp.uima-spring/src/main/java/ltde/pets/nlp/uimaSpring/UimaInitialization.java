package ltde.pets.nlp.uimaSpring;

import org.apache.uima.CompositeResourceFactory;
import org.apache.uima.UIMAFramework;
import org.apache.uima.analysis_engine.AnalysisEngineDescription;
import org.apache.uima.collection.CasConsumerDescription;
import org.apache.uima.collection.CollectionReaderDescription;
import org.apache.uima.fit.spring.factory.*;
import org.apache.uima.resource.CustomResourceSpecifier;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.context.*;
import org.springframework.context.annotation.AnnotationConfigUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.core.annotation.Order;

/**
 * Created by hatieke on 2016-02-13.
 */
@Configuration
public class UimaInitialization implements ApplicationContextInitializer {

    @Override
    public void initialize(ConfigurableApplicationContext ctx) {

        final GenericApplicationContext extendedCtx = new GenericApplicationContext(ctx);
        AnnotationConfigUtils.registerAnnotationConfigProcessors(extendedCtx);

        extendedCtx.registerBeanDefinition("collectionReaderFactory",
                BeanDefinitionBuilder
                        .genericBeanDefinition(CollectionReaderFactory_impl.class)
                        .getBeanDefinition());
//        ctx.registerBeanDefinition("casInitializerFactory",
//                BeanDefinitionBuilder
//                        .genericBeanDefinition(CasInitializerFactory_impl.class)
//                        .getBeanDefinition());
        extendedCtx.registerBeanDefinition("customResourceFactory",
                BeanDefinitionBuilder
                        .genericBeanDefinition(CustomResourceFactory_impl.class)
                        .getBeanDefinition());
        extendedCtx.registerBeanDefinition("analysisEngineFactory",
                BeanDefinitionBuilder
                        .genericBeanDefinition(AnalysisEngineFactory_impl.class)
                        .getBeanDefinition());
        extendedCtx.registerBeanDefinition("casConsumerFactory",
                BeanDefinitionBuilder
                        .genericBeanDefinition(CasConsumerFactory_impl.class)
                        .getBeanDefinition());

        ctx.refresh();

        CompositeResourceFactory factory = UIMAFramework.getResourceFactory();

        factory.registerFactory(CollectionReaderDescription.class,
                ctx.getBean(CollectionReaderFactory_impl.class));
        factory.registerFactory(CasConsumerDescription.class,
                ctx.getBean(CasConsumerFactory_impl.class));
//        factory.registerFactory(CasInitializerDescription.class,
//                ctx.getBean(CasInitializerFactory_impl.class));
        factory.registerFactory(CustomResourceSpecifier.class,
                ctx.getBean(CustomResourceFactory_impl.class));
//        factory.registerFactory(ResourceCreationSpecifier.class,
//                ctx.getBean(AnalysisEngineFactory_impl.class));
        factory.registerFactory(AnalysisEngineDescription.class,
                ctx.getBean(AnalysisEngineFactory_impl.class));


    }
}
