package com.mgmtp.bugreport.statemachine.resetbug;

import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.config.EnableStateMachineFactory;
import org.springframework.statemachine.config.StateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;

@EnableStateMachineFactory
@Configuration
public class StatemachineConfig extends StateMachineConfigurerAdapter<State, Event> {

	@Override
	public void configure(final StateMachineStateConfigurer<State, Event> states) throws Exception {

		states.withStates()
				.end(SuperState.END)
				.state(SuperState.PARENT)
				.initial(SuperState.INITIAL)
				.and()
				.withStates()
				.parent(SuperState.PARENT)
				.initial(SubState.SUB_INITIAL)
				.state(SubState.SUB_NEXT)
				.end(SubState.SUB_END);
	}

	@Override
	public void configure(final StateMachineTransitionConfigurer<State, Event> transitions) throws Exception {

		transitions
				.withExternal()
				.source(SuperState.INITIAL)
				.target(SuperState.PARENT)
				.event(Event.GO)
				.and()

				.withExternal()
				.source(SuperState.PARENT)
				.target(SuperState.END)
				.event(Event.GO)
				.and()

				.withExternal()
				.source(SubState.SUB_INITIAL)
				.target(SubState.SUB_NEXT)
				.event(Event.GO)
				.and()

				.withExternal()
				.source(SubState.SUB_NEXT)
				.target(SubState.SUB_END)
				.event(Event.GO);
	}
}
