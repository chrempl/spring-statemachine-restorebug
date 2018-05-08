package com.mgmtp.bugreport.statemachine.resetbug;

import java.util.HashMap;
import java.util.Map;

import org.springframework.statemachine.StateMachineContext;
import org.springframework.statemachine.StateMachinePersist;

public class InMemoryStateMachinePersist implements StateMachinePersist<State, Event, String> {

	private final Map<String, StateMachineContext<State, Event>> contexts = new HashMap<>();

	@Override
	public void write(final StateMachineContext<State, Event> context, final String contextObj) throws Exception {
		contexts.put(contextObj, context);
	}

	@Override
	public StateMachineContext<State, Event> read(final String contextObj) throws Exception {
		return contexts.get(contextObj);
	}
}