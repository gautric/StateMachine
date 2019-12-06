package net.a.g.jbpm.process;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import org.jbpm.process.workitem.transform.TransformWorkItemHandler;
import org.jbpm.test.JbpmJUnitBaseTestCase;
import org.junit.Test;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.manager.RuntimeEngine;
import org.kie.api.runtime.process.ProcessInstance;
import org.kie.internal.runtime.manager.context.EmptyContext;

import net.a.g.jbpm.model.StateData;
import net.a.g.jbpm.transform.StringToStateData;

public class TestStateMachineTransform extends JbpmJUnitBaseTestCase {

	public TestStateMachineTransform() {
		super(true, true);
	}

	@Test
	public void testAssertNodeActive() throws Exception {
		createRuntimeManager("net/a/g/jbpm/process/StateMachineTransform.bpmn");

		RuntimeEngine engine = getRuntimeEngine(EmptyContext.get());

		TransformWorkItemHandler transform = new TransformWorkItemHandler();

		transform.registerTransformer(StringToStateData.class);
		
		KieSession ksession = engine.getKieSession();
		ksession.getWorkItemManager().registerWorkItemHandler("Log",
				new org.jbpm.process.instance.impl.demo.SystemOutWorkItemHandler());
		ksession.getWorkItemManager().registerWorkItemHandler("Transform", transform);

		Map<String, Object> params = new HashMap<String, Object>();

		StateData sd = new StateData();
		sd.setId(42);
		sd.setDate(LocalDateTime.now());
		sd.setName("Loonquawl");
		sd.setTxId(new Random().nextLong());
		params.put("state", sd);

		int[] list = { 1, 8 };

		params.put("list", list);

		String processId = "net.a.g.jbpm.process.StateMachineTransform";

		ProcessInstance processInstance = ksession.startProcess(processId, params);
		long processInstanceId = processInstance.getId();

		ksession.signalEvent("NextStepSignal", "Mon Signal " + UUID.randomUUID().toString(), processInstanceId);

		ksession.getProcessInstance(processInstanceId);

		//StateData sd3 = (StateData) ("state", processInstanceId, ksession);

		assertProcessInstanceCompleted(processInstanceId);

	}
}
