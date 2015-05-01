package jkt.centralisateur.service.chat.data.in;

import java.util.Date;

import jkt.centralisateur.interlocutor.data.in.ServiceUdpDataIn;


public class PingAckData extends ServiceUdpDataIn {
    private Date requestSentAtTime;
	private Date ackReceivedAtTime;
    private long pingValue;

    public Date getRequestSentAtTime() {
        return requestSentAtTime;
    }

    public void setRequestSentAtTime(Date date) {
        this.requestSentAtTime = date;
    }

    public Date getAckReceivedAtTime() {
        return ackReceivedAtTime;
    }

    public void setAckReceivedAtTime(Date now) {
        this.ackReceivedAtTime = now;
    }
    
	public long getPingValue() {
		return pingValue;
	}
	
	public void setPingValue(long pingValue) {
	    this.pingValue = pingValue;
	}
}
