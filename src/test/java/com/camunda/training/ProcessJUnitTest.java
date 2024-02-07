package com.camunda.training;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.task.Task;
import org.camunda.bpm.engine.test.Deployment;
import org.camunda.bpm.extension.process_test_coverage.junit5.ProcessEngineCoverageExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import static  org.assertj.core.api.Assertions.*;

import static org.camunda.bpm.engine.test.assertions.bpmn.BpmnAwareTests.*;

@ExtendWith(ProcessEngineCoverageExtension.class)
class ProcessJUnitTest {

    @Test
    @Deployment(resources = "TwitterQualityAssurance.bpmn")
    void testHappyPath() {
        Map<String, Object> variables = new HashMap<>();
        variables.put("content", "Exercise 4 test - 🐥Tweet🐥");
        // start process with Java API and variables

        ProcessInstance processInstance = runtimeService().startProcessInstanceByKey("TwitterQAProcess", variables);
        assertThat(processInstance).isWaitingAt("ReviewTweet");
        List<Task> taskList = taskService()
                .createTaskQuery()
                .taskCandidateGroup("management")
                .processInstanceId(processInstance.getId())
                .list();
        assertThat(taskList).isNotNull().hasSize(1);

        Task task = taskList.get(0);

        Map<String, Object> approvedMap = new HashMap<>();
        approvedMap.put("approved", true);
        taskService().complete(task.getId(), approvedMap);
        assertThat(processInstance).isEnded();
    }
}