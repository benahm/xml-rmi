package com.aladyn.client.params;

import java.util.List;
import java.util.Vector;

import com.aladyn.ProtoResponseFault;
import com.aladyn.client.exception.ToXMLException;

/**
 * ArrayPram Class qui encapsule le type de donnée Array
 * 
 * @author Mario Matar
 * @author Ahmed Bensaad
 * 
 */
public class ArrayParam implements IParam {	
	
	Vector<IParam> array;
	
	@SuppressWarnings("unchecked")
	public ArrayParam(Object object) {
		List<Object> arrayObject=(List<Object>) object;
		array=new Vector<IParam>();
		IParam wrapValue;
		for (int i = 0; i < arrayObject.size(); i++) {
			 wrapValue = FieldFactory.wrapValue(arrayObject.get(i));
			 array.add(i, wrapValue);
		}
	}

	@Override
	public String getType() {
		return "array";
	}

	@Override
	public String toXML() throws ToXMLException {
		String xml="";
		IParam iParam;
		for (int i = 0; i < array.size(); i++) {
			iParam = array.get(i);
			if(iParam == null) throw new ToXMLException(
					ProtoResponseFault.CLIENT_EXCEPTION,
					ProtoResponseFault.TYPE_ARRAY_ERROR);
			xml+=iParam.toXML();
		}
		return "<value><array><data> "+xml+" </data></array></value>";
	}

}
