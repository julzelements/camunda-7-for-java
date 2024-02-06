package com.camunda.training;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.test.Deployment;
import org.camunda.bpm.extension.process_test_coverage.junit5.ProcessEngineCoverageExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import java.util.HashMap;
import java.util.Map;
import static org.camunda.bpm.engine.test.assertions.bpmn.BpmnAwareTests.assertThat;
import static org.camunda.bpm.engine.test.assertions.bpmn.BpmnAwareTests.runtimeService;

@ExtendWith(ProcessEngineCoverageExtension.class)
  public class ProcessJUnitTest {

  @Test
  @Deployment(resources = "TwitterQualityAssurance.bpmn")
  public void testHappyPath() {
    Map<String, Object> variables = new HashMap<String, Object>();
    variables.put("approved", true);
    variables.put("content", "Exercise 4 test - 🐥Tweet🐥");
    // start process with Java API and variables

    ProcessInstance processInstance = runtimeService().startProcessInstanceByKey("TwitterQAProcess", variables);
    assertThat(processInstance).isEnded();
  }

}
