package ltde.pets.nlp.uimaSpring.autowired;

import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Implementation of a spring service. Simply increments a counter each time its only method is
 * called.
 */
@Service
public class ExampleServiceImpl implements ExampleService {

  private AtomicInteger ai = new AtomicInteger();

  @Override
  public int get() {
    return ai.incrementAndGet();
  }

}
