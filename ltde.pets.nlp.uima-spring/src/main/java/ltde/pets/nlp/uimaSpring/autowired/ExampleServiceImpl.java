/**
 * Fraunhofer Institute for Computer Graphics Research (IGD)
 * Competence Center for Information Visualization and Visual Analytics
 * 
 * Copyright (c) 2015 Fraunhofer IGD. All rights reserved.
 * 
 * This source code is property of the Fraunhofer IGD and underlies
 * copyright restrictions. It may only be used with explicit
 * permission from the respective owner.
 */

package ltde.pets.nlp.uimaSpring.autowired;

import ltde.pets.nlp.uimaSpring.autowired.ExampleService;
import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicInteger;

@Service
public class ExampleServiceImpl implements ExampleService {

  private AtomicInteger ai = new AtomicInteger();

  @Override
  public String get() {
    return String.format("Example Service (%s) has been called %s times", this.hashCode(), ai.incrementAndGet());
  }

}
