package ltde.pets.nlp.uimaSpring.autowired;

import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.fit.component.JCasAnnotator_ImplBase;
import org.apache.uima.fit.descriptor.ConfigurationParameter;
import org.apache.uima.jcas.JCas;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Demo AnalysisEngine which private fields are being partially populated by spring and partially by uimafit.
 */
public class MyAutowiredAnalysisEngine extends JCasAnnotator_ImplBase {
  private static final Logger LOG = LoggerFactory.getLogger(MyAutowiredAnalysisEngine.class);

  @Autowired
  private ExampleService srv;

  public final static String ARBITRARY_BUT_CERTAINLY_NOT_OVERLOADED_NAME =
      "arbitraryButCertainlyNotOverloadedName";
  @ConfigurationParameter(name = ARBITRARY_BUT_CERTAINLY_NOT_OVERLOADED_NAME)
  private String randomNameThatIsNotAmbigue;

  public MyAutowiredAnalysisEngine() {
    LOG.info("Instantiating " + MyAutowiredAnalysisEngine.class.getCanonicalName()
        + " and got randomNameThatIsNotAmbigue '" + randomNameThatIsNotAmbigue + "'");
  }

  @Override
  public void process(JCas aJCas) throws AnalysisEngineProcessException {
    String documentText = aJCas.getDocumentText();
    LOG.info(String.format("AE %s (hash %s) processes cas-id %s with document '%s'.\nService has been called %s times", this
        .randomNameThatIsNotAmbigue, this.hashCode(), aJCas.hashCode(), documentText.substring(0,
        Math.min(10, documentText.length())), srv.get()));
  }
}
