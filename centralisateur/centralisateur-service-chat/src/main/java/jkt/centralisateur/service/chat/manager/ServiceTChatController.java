package jkt.centralisateur.service.chat.manager;

import java.util.List;

import jkt.centralisateur.interlocutor.listener.ServiceController;
import jkt.centralisateur.interlocutor.scoreboard.Scoreboard;
import jkt.centralisateur.service.chat.dto.ChatMessage;

public interface ServiceTChatController extends Scoreboard, ServiceController {
    
    /**
     * Renvoie la liste de l'historique des 100 derniers messages de tchat.
     * 
     * @return 100 derniers messages de chat
     */
    List<ChatMessage> getTchatMessageHistoric();
   
    /**
     * Ajoute un message de tchat à l'historique des messages de chat.
     * 
     * @param clientName nom du joueur
     * @param message contenu du message
     */
    void distributeChatMessage(String clientName, String message);
}
