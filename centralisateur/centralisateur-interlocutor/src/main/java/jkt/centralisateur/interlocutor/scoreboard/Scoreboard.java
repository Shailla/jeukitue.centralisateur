package jkt.centralisateur.interlocutor.scoreboard;

import java.util.Map;

import jkt.centralisateur.interlocutor.data.ClientIdentifier;

public interface Scoreboard {
	Map<ClientIdentifier, Client> getClients(); 
}
