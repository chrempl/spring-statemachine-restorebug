package com.mgmtp.bugreport.statemachine.resetbug;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineFactory;
import org.springframework.statemachine.persist.DefaultStateMachinePersister;
import org.springframework.statemachine.persist.StateMachinePersister;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = { StatemachineConfig.class })
public class StatemachineTest {

	private static final String STATE_KEY = "state1";

	@Autowired
	private StateMachineFactory<State, Event> statemachineFactory;

	@Test
	public void states() {

		StateMachine<State, Event> statemachine = statemachineFactory.getStateMachine();

		org.springframework.statemachine.state.State<State, Event> state = tranitToSubInitial(statemachine);

		assertSubInitialState(state);
	}

	@Test
	public void persist() throws Exception {

		InMemoryStateMachinePersist stateMachinePersist = new InMemoryStateMachinePersist();
		StateMachinePersister<State, Event, String> persister = new DefaultStateMachinePersister<>(stateMachinePersist);

		StateMachine<State, Event> statemachine1 = statemachineFactory.getStateMachine();

		org.springframework.statemachine.state.State<State, Event> state = tranitToSubInitial(statemachine1);
		assertSubInitialState(state);

		persister.persist(statemachine1, STATE_KEY);

		StateMachine<State, Event> statemachine2 = statemachineFactory.getStateMachine();
		persister.restore(statemachine2, STATE_KEY);

		// Assertion: persisted state is restored
		assertSubInitialState(statemachine2.getState());
	}

	/**
	 * Assertion: StateMachines State is {@link SuperState#PARENT} and Child-State
	 * {@link SubState#SUB_INITIAL}
	 *
	 * @param state
	 */
	private void assertSubInitialState(final org.springframework.statemachine.state.State<State, Event> state) {
		Assert.assertTrue(state.getIds().size() == 2 && state.getIds().contains(SuperState.PARENT) && state.getIds().contains(SubState.SUB_INITIAL));
	}

	/**
	 * Sends one {@link Event#GO} to a {@link StateMachine}.
	 *
	 * @param statemachine
	 * @return {@link StateMachine#getState()}
	 */
	private org.springframework.statemachine.state.State<State, Event> tranitToSubInitial(final StateMachine<State, Event> statemachine) {
		statemachine.start();
		statemachine.sendEvent(Event.GO);
		org.springframework.statemachine.state.State<State, Event> state = statemachine.getState();
		return state;
	}
}
