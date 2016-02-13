package ltde.pets.nlp.uimaSpring.autowired;

import com.google.common.base.Preconditions;
import de.tudarmstadt.ukp.dkpro.core.io.text.TextReader;
import io.swagger.annotations.ApiOperation;
import org.apache.uima.CompositeResourceFactory;
import org.apache.uima.UIMAException;
import org.apache.uima.UIMAFramework;
import org.apache.uima.analysis_engine.AnalysisEngine;
import org.apache.uima.analysis_engine.AnalysisEngineDescription;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.cas.CAS;
import org.apache.uima.collection.CasConsumerDescription;
import org.apache.uima.collection.CasInitializerDescription;
import org.apache.uima.collection.CollectionReader;
import org.apache.uima.collection.CollectionReaderDescription;
import org.apache.uima.fit.pipeline.SimplePipeline;
import org.apache.uima.impl.CompositeResourceFactory_impl;
import org.apache.uima.impl.UIMAFramework_impl;
import org.apache.uima.resource.CustomResourceSpecifier;
import org.apache.uima.resource.ResourceCreationSpecifier;
import org.apache.uima.resource.ResourceInitializationException;
import org.apache.uima.resource.ResourceManager;
import org.apache.uima.resource.metadata.ResourceMetaData;
import org.apache.uima.util.CasCreationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigUtils;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.apache.uima.fit.spring.factory.*;


import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;
import static org.apache.uima.UIMAFramework.produceCollectionReader;
import static org.apache.uima.fit.factory.AnalysisEngineFactory.createEngine;
import static org.apache.uima.fit.factory.AnalysisEngineFactory.createEngineDescription;
import static org.apache.uima.fit.factory.CollectionReaderFactory.createReader;
import static org.apache.uima.fit.factory.CollectionReaderFactory.createReaderDescription;
import static org.apache.uima.fit.util.LifeCycleUtil.collectionProcessComplete;

@SuppressWarnings("ALL")
@RestController
@RequestMapping(value = "ufic", method = RequestMethod.GET)
public class UimaAutowiredController {

    @Value("${ltde.service.example:#{systemProperties['user.dir']}/uimaspring/data/}")
    public File inputFolder;

//    @Autowired
//    ApplicationContext ac;

    @Autowired
    ExampleService exampleService;

    /**
     * This method works. i.e. the exampleService is avaliable.
     * @throws ResourceInitializationException
     * @throws AnalysisEngineProcessException
     */
    @ApiOperation(value = "apiOperation")
    @RequestMapping("pureAEtest")
    public void uimaAe() throws ResourceInitializationException, AnalysisEngineProcessException {
//        Preconditions.checkNotNull(ac);
//        // Acquire application context
//        ApplicationContext ctx = getApplicationContext();
//
//        // Configure UIMA for this context
//        initUimaApplicationContext(ctx);

        // Instantiate component
        AnalysisEngine ae = createEngine(MyAutowiredAnalysisEngine.class);

        // Test that injection works
        ae.process(ae.newJCas());
    }

    /**
     * This works partially. Injection works, but @ConfigurationParameter is always "null".
     *
     * @throws UIMAException
     * @throws IOException
     */
    @RequestMapping("simpleFit")
    public void simpleFit() throws UIMAException, IOException {
//        Preconditions.checkNotNull(ac);
        // Acquire application context
//        ApplicationContext ctx = getApplicationContext();
//
//        // Configure UIMA for this context
//        initUimaApplicationContext(ctx);

        CollectionReader reader = createReader(TextReader.class,
                TextReader.PARAM_SOURCE_LOCATION, inputFolder,
                TextReader.PARAM_LANGUAGE, "en",
                TextReader.PARAM_PATTERNS, "*.txt");

        AnalysisEngine aed = createEngine(MyAutowiredAnalysisEngine.class,
                MyAutowiredAnalysisEngine.ARBITRARY_BUT_CERTAINLY_NOT_OVERLOADED_NAME, "aed1");
        AnalysisEngine aed2 = createEngine(MyAutowiredAnalysisEngine.class,
                MyAutowiredAnalysisEngine.ARBITRARY_BUT_CERTAINLY_NOT_OVERLOADED_NAME, "aed2");


        SimplePipeline.runPipeline(reader, aed, aed2);
    }

    /**
     * Use description for the reader, but instances for the engines.
     *
     * @throws UIMAException
     * @throws IOException
     */
    @RequestMapping("simpleFitPart")
    public void simpleFitPart() throws UIMAException, IOException {
//        Preconditions.checkNotNull(ac);
        // Acquire application context
//        ApplicationContext ctx = getApplicationContext();
//
//        // Configure UIMA for this context
//        initUimaApplicationContext(ctx);

        CollectionReaderDescription readerDesc = createReaderDescription(TextReader.class,
                TextReader.PARAM_SOURCE_LOCATION, inputFolder,
                TextReader.PARAM_LANGUAGE, "en",
                TextReader.PARAM_PATTERNS, "*.txt");
        ResourceManager resMgr = UIMAFramework.newDefaultResourceManager();

        AnalysisEngine aed = createEngine(MyAutowiredAnalysisEngine.class,
                MyAutowiredAnalysisEngine.ARBITRARY_BUT_CERTAINLY_NOT_OVERLOADED_NAME, "aed1");
        AnalysisEngine aed2 = createEngine(MyAutowiredAnalysisEngine.class,
                MyAutowiredAnalysisEngine.ARBITRARY_BUT_CERTAINLY_NOT_OVERLOADED_NAME, "aed2");
        AnalysisEngine aed3 = createEngine(MyAutowiredAnalysisEngine.class,
                MyAutowiredAnalysisEngine.ARBITRARY_BUT_CERTAINLY_NOT_OVERLOADED_NAME, "aed3");

        CollectionReader reader = UIMAFramework.produceCollectionReader(readerDesc, resMgr, null);

        SimplePipeline.runPipeline(reader, aed, aed2);


        reader = UIMAFramework.produceCollectionReader(readerDesc, resMgr, null);
        SimplePipeline.runPipeline(reader, aed, aed3);

    }

    /**
     * runs a pipeline using use createReaderDescription and createEngineDescription as instantiation templates
     * @throws IOException
     * @throws UIMAException
     */
    @RequestMapping("simpleFitDescriptions")
    public void simpleFitDescriptions() throws IOException, UIMAException {
//        Preconditions.checkNotNull(ac);
//        // Acquire application context
//        ApplicationContext ctx = getApplicationContext();

        // Configure UIMA for this context
//        initUimaApplicationContext(ctx);

        CollectionReaderDescription reader = createReaderDescription(TextReader.class,
                TextReader.PARAM_SOURCE_LOCATION, inputFolder,
                TextReader.PARAM_LANGUAGE, "en",
                TextReader.PARAM_PATTERNS, "*.txt");

        AnalysisEngineDescription aed = createEngineDescription(MyAutowiredAnalysisEngine.class,
                MyAutowiredAnalysisEngine.ARBITRARY_BUT_CERTAINLY_NOT_OVERLOADED_NAME, "aed1");
        AnalysisEngineDescription aed2 = createEngineDescription(MyAutowiredAnalysisEngine.class,
                MyAutowiredAnalysisEngine.ARBITRARY_BUT_CERTAINLY_NOT_OVERLOADED_NAME, "aed2");

        SimplePipeline.runPipeline(reader, aed, aed2);

    }


//    private ApplicationContext getApplicationContext() {
//        final GenericApplicationContext ctx = new GenericApplicationContext(ac);
//        AnnotationConfigUtils.registerAnnotationConfigProcessors(ctx);
//        ctx.registerBeanDefinition("collectionReaderFactory", BeanDefinitionBuilder
//                .genericBeanDefinition(CollectionReaderFactory_impl.class).getBeanDefinition());
//        ctx.registerBeanDefinition("casInitializerFactory", BeanDefinitionBuilder
//                .genericBeanDefinition(CasInitializerFactory_impl.class).getBeanDefinition());
//        ctx.registerBeanDefinition("customResourceFactory", BeanDefinitionBuilder
//                .genericBeanDefinition(CustomResourceFactory_impl.class).getBeanDefinition());
//        ctx.registerBeanDefinition("analysisEngineFactory", BeanDefinitionBuilder
//                .genericBeanDefinition(AnalysisEngineFactory_impl.class).getBeanDefinition());
//        ctx.registerBeanDefinition("casConsumerFactory",
//                BeanDefinitionBuilder.genericBeanDefinition(CasConsumerFactory_impl.class)
//                        .getBeanDefinition());
//        ctx.refresh();
//        return ctx;
//    }
//
//    /**
//     * this somewhat awkward method should be called once, when starting up the whole spring jvm.
//     *
//     * @param aApplicationContext
//     */
//    private static UIMAFramework_impl initUimaApplicationContext(final ApplicationContext aApplicationContext) {
//        UIMAFramework_impl impl = new UIMAFramework_impl() {
//            {
//                CompositeResourceFactory_impl factory = (CompositeResourceFactory_impl) getResourceFactory();
//                factory.registerFactory(CollectionReaderDescription.class,
//                        aApplicationContext.getBean(CollectionReaderFactory_impl.class));
//                factory.registerFactory(CasConsumerDescription.class,
//                        aApplicationContext.getBean(CasConsumerFactory_impl.class));
//                factory.registerFactory(CasInitializerDescription.class,
//                        aApplicationContext.getBean(CasInitializerFactory_impl.class));
//                factory.registerFactory(AnalysisEngineDescription.class,
//                        aApplicationContext.getBean(AnalysisEngineFactory_impl.class));
//                factory.registerFactory(CustomResourceSpecifier.class,
//                        aApplicationContext.getBean(CustomResourceFactory_impl.class));
//            }
//        };
//        return impl;
//    }
//
//    private void myRunPipeline(final CollectionReader reader, final AnalysisEngine... engines)
//            throws UIMAException, IOException {
//        final List<ResourceMetaData> metaData = new ArrayList<ResourceMetaData>();
//        metaData.add(reader.getMetaData());
//        for (AnalysisEngine engine : engines) {
//            metaData.add(engine.getMetaData());
//        }
//
//        final CAS cas = CasCreationUtils.createCas(metaData);
//        reader.typeSystemInit(cas.getTypeSystem());
//
//        while (reader.hasNext()) {
//            reader.getNext(cas);
//            for (AnalysisEngine engine : engines) {
//                engine.process(cas);
//            }
//            cas.reset();
//        }
//
//        collectionProcessComplete(engines);
//
//    }
//
//    private void myRunPipeline(final CollectionReaderDescription readerDesc,
//                               final AnalysisEngineDescription... descs) throws UIMAException, IOException {
//        ResourceManager resMgr = UIMAFramework.newDefaultResourceManager();
//
//        // Create the components
//        final CollectionReader reader = produceCollectionReader(readerDesc, resMgr, null);
//
//        // Create AAE
//        final AnalysisEngineDescription aaeDesc = createEngineDescription(descs);
//
//        // Instantiate AAE
//        final AnalysisEngine aae = UIMAFramework.produceAnalysisEngine(aaeDesc, resMgr, null);
//
//        // Create CAS from merged metadata
//        final CAS cas = CasCreationUtils.createCas(asList(reader.getMetaData(), aae.getMetaData()));
//        reader.typeSystemInit(cas.getTypeSystem());
//
//        try {
//            // Process
//            while (reader.hasNext()) {
//                reader.getNext(cas);
//                aae.process(cas);
//                cas.reset();
//            }
//
//            // Signal end of processing
//            aae.collectionProcessComplete();
//        } finally {
//            // Destroy
//            aae.destroy();
//        }
//    }

}
