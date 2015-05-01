package jkt.centralisateur.interlocutor.scoreboard;

import java.util.HashMap;
import java.util.Map;

public class ScoreboardStateDto {
	private State state;
	private final Map<String, State> servicesStates = new HashMap<String, State>(); 

	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}
	
	public void addServiceState(final String listenerName, State state) {
		servicesStates.put(listenerName, state);
	}
	
	public Map<String, State> getListenerStates() {
		return servicesStates;
	}
}
