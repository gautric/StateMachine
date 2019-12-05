/*
 * Copyright 2017 Red Hat, Inc. and/or its affiliates.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package net.a.g.jbpm.process;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kie.server.api.KieServerConstants;
import org.kie.server.api.marshalling.MarshallingFormat;
import org.kie.server.api.model.ReleaseId;
import org.kie.server.api.model.definition.QueryDefinition;
import org.kie.server.api.model.instance.ProcessInstance;
import org.kie.server.client.KieServicesClient;
import org.kie.server.client.KieServicesConfiguration;
import org.kie.server.client.KieServicesFactory;
import org.kie.server.client.ProcessServicesClient;
import org.kie.server.client.QueryServicesClient;
import org.kie.server.client.admin.ProcessAdminServicesClient;

import net.a.g.jbpm.model.StateData;

public class TestRESTStateMachine {

	static {
		System.clearProperty("org.kie.server.json.format.date");
		System.setProperty("org.kie.server.json.format.date", "true");
	}

	static final String ARTIFACT_ID = "evaluation";
	static final String GROUP_ID = "org.jbpm.test";
	static final String VERSION = "1.0.0";

	private String user = "john";
	private String password = "john@pwd1";

	private String containerAlias = "statemachine";
	private String containerId = "statemachine";
	private String containerId2 = "statemachine";
	private String processId = "net.a.g.jbpm.StateMachine";

	private KieServicesClient kieServicesClient;

	@AfterClass
	public static void generalCleanup() {
		System.clearProperty(KieServerConstants.KIE_SERVER_MODE);
	}

	@Before
	public void setup() {

		ReleaseId releaseId = new ReleaseId(GROUP_ID, ARTIFACT_ID, VERSION);
		String serverUrl = "http://localhost:" + 8090 + "/rest/server";
		KieServicesConfiguration configuration = KieServicesFactory.newRestConfiguration(serverUrl, user, password);
		configuration.setTimeout(60000);
		configuration.setMarshallingFormat(MarshallingFormat.XSTREAM);

		configuration.getExtraClasses().add(StateData.class);

		this.kieServicesClient = KieServicesFactory.newKieServicesClient(configuration);

	}

	@After
	public void cleanup() {
	}

	@Test
	public void testProcessStart() {

		QueryServicesClient queryClient = kieServicesClient.getServicesClient(QueryServicesClient.class);
		ProcessServicesClient processClient = kieServicesClient.getServicesClient(ProcessServicesClient.class);
		ProcessAdminServicesClient processAdminClient = kieServicesClient
				.getServicesClient(ProcessAdminServicesClient.class);

		Map<String, Object> params = new HashMap<String, Object>();
		StateData sd = new StateData();
		sd.setId(42);
		sd.setDate(LocalDateTime.now());
		sd.setName("Loonquawl");
		sd.setTxId(new Random().nextLong());
		params.put("state", sd);

		processId = "net.a.g.jbpm.process.StateMachineSignal";

		Long processInstanceId = processClient.startProcess(containerId, processId, params);
		assertNotNull(processInstanceId);

		processClient.signal(containerId, "NextStepSignal", "Mon Signal " + UUID.randomUUID().toString());
	}

}
