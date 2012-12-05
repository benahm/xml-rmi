package com.aladyn;

/**
 * Centralisation des codes et messages d'erreur
 * 
 * @author Mario Matar
 * @author Ahmed Bensaad
 *
 */
public class ProtoResponseFault
{
	public static final int SERVER_EXCEPTION = 100;
	public static final int XML_DOCUMENT_NOT_VALID = 200;
	public static final int REQUEST_CONSTRUCTION = 200;
	public static final int METHOD_CALL_FALL = 400;
	public static final int CLIENT_EXCEPTION = 500;
	
	
	public static final String PACKAGE_DONT_EXISTS = "Le package n'existe pas : ";
	public static final String CLASS_NOT_FOUND = "La classe suivante ne peut pas être chargée : ";
	public static final String INTERFACE_NOT_FOUND = "Interface non trouvé : ";
	
	public static final String TYPE_OBJECTPARAM_ENCAPSULATION = "Object non encapsulé dans un ObjectParam!!";
	public static final String TYPE_DONT_EXISTS = "Erreur sur le type de valeur";
	public static final String TYPE_ARRAY_ERROR = "Erreur sur le type de valeur";
	public static final String TYPE_STRUCT_ERROR = "Erreur une valeur est null dans la struct";
	public static final String TYPE_OBJECT_ERROR = "Erreur à la création des champs d'un objet";
	public static final String OBJECT_UPDATE_ERROR = "Erreur de mis à jour des champs d'un objet";
	public static final String NULL_OBJECT_FIELD_ERROR = "Un objet non initialisé est déclaré comme un champs d'un autre objet";
	public static final String OBJECT_NOT_FOUND = "Objet à mettre à jour non trouvé";

	
	public static final String VALIDATION_ERROR = "Erreur lors de la validation";
	public static final String LIBRARY_SCHEMA = "Erreur charement librairie de validation schema";
	public static final String NOT_VALID_RNG = "Le document RelaxNG n'est pas valide";
	public static final String NOT_VALID_XML = "Le document ne valide pas le schema RelaxNG";
	
	public static final String XML_ENCODAGE = "Mauvais encodage du document XML";
	public static final String FILE_NOT_FOUND = "Fichier non trouvé";
	public static final String FILE_IO_ERROR = "Erreur lors de la lecture du fichier";
	
	public static final String SOCKET_FLOW = "L'objet reçu n'est pas une chaine de caractère";
	
	public static final String PARSE_ERROR = "Erreur lors du parse";
	
	public static final String METHOD_DONT_EXISTS = "La méthode n'existe pas";
	public static final String SECURITY_VIOLATION = "Erreur dû au Security manager lors d'appele de méthode";
	public static final String ILLEGAL_ARGUMENTS = "Les arguments sont erronées";
	public static final String INVOCATION_ERROR = "La méthod invoqué à lancée une exception";
	
	public static final String XML_GENERATIONG_NOT_CONFORM = "Le XML généré n'est pas conforme au protocole XML-RMI";
	public static final String METHOD_NAME_NOT_CONFORME = "Le nom de la méthode n'est pas conforme au protocole XML-RMI";
	public static final String CONNECTION_NOT_ESTABLISH = "Connection non établie avec le serveur, mauvais port ou IP";
	public static final String COMMUNICATION_WITH_SERVER_ERROR = "Erreur de communication avec le serveur";
	
	public static final String CAN_NOT_CREATE_METHOD_FILE_SOUCE = "Impossible de créé le fichier srcMethod.java";
	public static final String METHOD_NOT_FOUND = "La method n'a pas été trouvé";
	
	public static final String OBJECT_TO_UPDATE_NOT_FOUND = "Objet à mettre à jour non trouvé";
	public static final String ERROR_RECEIVE = "Erreur envoyée par le serveur : ";
	public static final String ERROR_OBJECT_BUILDER="Erreur Object déjà construit";
	
}