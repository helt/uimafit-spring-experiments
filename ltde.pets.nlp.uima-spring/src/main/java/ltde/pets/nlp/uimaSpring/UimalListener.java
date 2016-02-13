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
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.AnnotationConfigUtils;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.ContextStartedEvent;
import org.springframework.context.event.ContextStoppedEvent;
import org.springframework.context.support.GenericApplicationContext;

/**
 * Created by hatieke on 2016-02-13.
 */
public class UimalListener implements ApplicationListener<ContextStartedEvent> {

    @Override
    public void onApplicationEvent(ContextStartedEvent event) {
        ApplicationContext ctx = event.getApplicationContext();

        final GenericApplicationContext extendedCtx = new GenericApplicationContext(ctx);
        AnnotationConfigUtils.registerAnnotationConfigProcessors(extendedCtx);

        AbstractBeanDefinition corf = BeanDefinitionBuilder
                .genericBeanDefinition(CollectionReaderFactory_impl.class)
                .getBeanDefinition();
        extendedCtx.registerBeanDefinition("collectionReaderFactory", corf);
//        ctx.registerBeanDefinition("casInitializerFactory",
//                BeanDefinitionBuilder
//                        .genericBeanDefinition(CasInitializerFactory_impl.class)
//                        .getBeanDefinition());
        AbstractBeanDefinition curf = BeanDefinitionBuilder
                .genericBeanDefinition(CustomResourceFactory_impl.class)
                .getBeanDefinition();
        extendedCtx.registerBeanDefinition("customResourceFactory",  curf);
        AbstractBeanDefinition aef = BeanDefinitionBuilder
                .genericBeanDefinition(AnalysisEngineFactory_impl.class)
                .getBeanDefinition();
        extendedCtx.registerBeanDefinition("analysisEngineFactory",
                aef);
        AbstractBeanDefinition ccf = BeanDefinitionBuilder
                .genericBeanDefinition(CasConsumerFactory_impl.class)
                .getBeanDefinition();
        extendedCtx.registerBeanDefinition("casConsumerFactory", ccf);

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

