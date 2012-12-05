package com.aladyn;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 
 * @author Mario Matar
 * @author Ahmed Bensaad
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface XMLRMIField {
	// nom à utiliser dans la sérialisation XML-RMI
	String serializationName(); 

	// type XML-RMI à utiliser dans la sérialisation
	String serializationType(); 
}