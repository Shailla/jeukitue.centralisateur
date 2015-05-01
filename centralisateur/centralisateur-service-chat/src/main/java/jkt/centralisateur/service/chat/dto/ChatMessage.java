package jkt.centralisateur.service.chat.dto;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ChatMessage {
    private String clientName;
    private Date time;
    private String message;
    
    public String getClientName() {
        return clientName;
    }
    
    public void setClientName(String clientName) {
        this.clientName = clientName;
    }
    
    public Date getTime() {
        return time;
    }
    
    public String getDate() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy-hh:mm:ss");
        return simpleDateFormat.format(time);
    }
    
    public void setTime(Date time) {
        this.time = time;
    }
    
    public String getMessage() {
        return message;
    }
    
    public void setMessage(String message) {
        this.message = message;
    }
}
