package jkt.centralisateur.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jkt.centralisateur.authentication.UserData;
import jkt.centralisateur.service.chat.dto.ChatMessage;
import jkt.centralisateur.service.chat.manager.ServiceTChatController;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class TchatController {
    
    @Autowired
    @Qualifier("serviceTChatManager")
    private ServiceTChatController serviceTChatManager;

    @RequestMapping(value="/secure/tchat/list.do")
    public ModelAndView list() {
        final List<ChatMessage> chatMessages = serviceTChatManager.getTchatMessageHistoric();
        
        final Map<String, Object> model = new HashMap<String, Object>();
        model.put("messages", chatMessages);
        model.put("message", new ChatMessage());
        
        return new ModelAndView("tchat", model);
    }
    
    /**
     * Receive a new AJax tchat message of a client by JSon.
     * 
     * @param messageContent content of the message
     * @return new historic message list
     */
    @RequestMapping(value="/secure/tchat/add_tchat_message.do", method=RequestMethod.GET)
    public ModelAndView jsonClientMessage(final @RequestParam String messageContent) {
        
        if(StringUtils.isNotBlank(messageContent)) {
            final SecurityContext context = SecurityContextHolder.getContext();
            final Authentication authentification = context.getAuthentication();
            final UserData userData = (UserData) authentification.getPrincipal();
            final String userName = userData.getUsername();
            serviceTChatManager.distributeChatMessage(userName, messageContent.trim());
        }
        
        // Update the messages on the client side
        final Map<String, Object> model = new HashMap<String, Object>();
        final List<ChatMessage> chatMessages = serviceTChatManager.getTchatMessageHistoric();
        model.put("messages", chatMessages);
        
        return new ModelAndView("jsonView", model);
    }
}
