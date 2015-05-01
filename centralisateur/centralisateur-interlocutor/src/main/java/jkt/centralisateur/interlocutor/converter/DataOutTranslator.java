package jkt.centralisateur.interlocutor.converter;

import jkt.centralisateur.interlocutor.data.out.ServiceDataOut;

public interface DataOutTranslator {
    public Object convert(ServiceDataOut dataOut);
}
