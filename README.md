xml-rmi
=======

XML-RMI protocole de sérialisation et d'appelle de méthodes distantes
---------------------------------------------------------------------


Il reprend la spécification de XML-RPC et ajoute la possibilité de passer des objets en
paramètres à des appels de méthodes à distance, de recevoir des objets en résultat, et de récupérer
des modifications à l’état des objets passés en paramètres pendant l’exécution de la méthode
distante.

L’objet passé en paramètre sera recréé sur le site distant, modifié localement par la méthode
appelée, puis retourné à l’appelant pour récupérer ses modifications en fin d’exécution de l’appel.

XML-RPC prévoit uniquement les types suivants comme paramètres ou résultat d’un appel :
    – entiers (int),
    – réels (double),
    – booléens (boolean),
    – chaînes de caractères (string),
    – moments dans le temps (dateTime ISO8601),
    – binaire (base64),
    – des tableaux (array) de valeurs homogènes des autres types de la norme, et
    – des valeurs structurées struct contenant des champs nommés avec des valeurs hétérogènes des autres types de la norme.

La spécification complète de XML-RPC se trouve à l’URL http://xmlrpc.scripting.com/spec.html

Le type Objet a été ajouté au XML-RPC pour devenir XML-RMI



