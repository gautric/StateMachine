package net.a.g.jbpm.transform;

import java.time.LocalDateTime;

import org.jbpm.process.workitem.transform.Transformer;

import net.a.g.jbpm.model.StateData;

public class StringToStateData {

	@Transformer
	public static StateData StringToStateData(String file) {
		StateData ret = new StateData();
		ret.setName(file);
		ret.setId(-1);
		ret.setDate(LocalDateTime.now().minusDays(10));
		return ret;
	}
}
