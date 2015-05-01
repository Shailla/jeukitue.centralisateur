package jkt.centralisateur.interlocutor.converter;

import jkt.centralisateur.interlocutor.data.in.ServiceDataIn;
import jkt.centralisateur.interlocutor.exception.CorruptedDataException;
import jkt.centralisateur.interlocutor.exception.UnknownDataException;

public interface DataInTranslator {
	ServiceDataIn convert(Object entry) throws CorruptedDataException,
									  UnknownDataException;
}
