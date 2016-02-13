package ltde.pets.nlp.uimaSpring.autowired;

import de.tudarmstadt.ukp.dkpro.core.io.text.TextReader;
import io.swagger.annotations.ApiOperation;
import org.apache.uima.UIMAException;
import org.apache.uima.UIMAFramework;
import org.apache.uima.analysis_engine.AnalysisEngine;
import org.apache.uima.analysis_engine.AnalysisEngineDescription;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.collection.CollectionReader;
import org.apache.uima.collection.CollectionReaderDescription;
import org.apache.uima.fit.pipeline.SimplePipeline;
import org.apache.uima.resource.ResourceInitializationException;
import org.apache.uima.resource.ResourceManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;

import static org.apache.uima.fit.factory.AnalysisEngineFactory.createEngine;
import static org.apache.uima.fit.factory.AnalysisEngineFactory.createEngineDescription;
import static org.apache.uima.fit.factory.CollectionReaderFactory.createReader;
import static org.apache.uima.fit.factory.CollectionReaderFactory.createReaderDescription;

@RestController
@RequestMapping(value = "ufic", method = RequestMethod.GET)
public class UimaAutowiredController {

  @Value("${ltde.service.example:#{systemProperties['user.dir']}/uimaspring/data/}")
  public File inputFolder;

  /**
   * This method works. i.e. the exampleService is avaliable.
   *
   * @throws ResourceInitializationException
   * @throws AnalysisEngineProcessException
   */
  @ApiOperation(value = "apiOperation")
  @RequestMapping("pureAEtest")
  public void uimaAe() throws ResourceInitializationException, AnalysisEngineProcessException {
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
   * runs a pipeline using use createReaderDescription and createEngineDescription as
   * instantiation templates
   *
   * @throws IOException
   * @throws UIMAException
   */
  @RequestMapping("simpleFitDescriptions")
  public void simpleFitDescriptions() throws IOException, UIMAException {
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
}
