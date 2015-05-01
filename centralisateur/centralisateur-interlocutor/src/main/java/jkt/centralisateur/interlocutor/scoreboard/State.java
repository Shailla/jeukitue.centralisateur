package jkt.centralisateur.interlocutor.scoreboard;

public enum State {
	STOPPED(0), 
	STOPPING(1), 
	STARTED(2), 
	STARTING(3);
	private final int state;
	
	private State(final int state) {
		this.state= state;
	}
	
	public boolean isStopped() {
		return state == STOPPED.state;
	}
	
	public boolean isStarted() {
		return state == STARTED.state;
	}

	public boolean isStopping() {
		return state == STOPPING.state;
	}
	
	public boolean isStarting() {
		return state == STARTING.state;
	}

}
