package com.aladyn.client.params;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.aladyn.ProtoResponseFault;
import com.aladyn.client.exception.ToXMLException;

/**
 * StructParam class qui encapsule une Hastable pour 
 * l'envoyer en forme de 'struct' du protocole XML-RPC
 * 
 * @author Mario Matar
 * @author Ahmed Bensaad
 * 
 */
public class StructParam implements IParam {
	
	Map<String, IParam> struct;

	@SuppressWarnings("unchecked")
	public StructParam(Object objet) {
		Map<String, Object> hashMapObject;
		hashMapObject = ((Map<String, Object>) objet);
		struct = new HashMap<String, IParam>();
		IParam wrapValue;
		Object value;
		for (String key : hashMapObject.keySet()) {
			value = hashMapObject.get(key);
			wrapValue = (value == null) ? new VoidParam() : FieldFactory
					.wrapValue(value);
			struct.put(key, wrapValue);
		}
	}

	@Override
	public String getType() {
		return "struct";
	}

	@Override
	public String toXML() throws ToXMLException {
		String xml = "";
		IParam iParam;
		Set<String> keys = struct.keySet();
		for (String key : keys) {
			iParam = struct.get(key);
			if (iParam instanceof VoidParam)
				throw new ToXMLException(
					ProtoResponseFault.CLIENT_EXCEPTION,
					ProtoResponseFault.TYPE_STRUCT_ERROR);
			xml += "<member><name> " + key + " </name>" + iParam.toXML()
					+ "</member>";
		}
		return "<value><struct>" + xml + "</struct></value>";
	}

}
