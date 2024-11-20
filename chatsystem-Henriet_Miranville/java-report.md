**Tech Stack**

La technologie UDP a été choisie pour le contact discovery, car la majeure partie des échanges lors du contact discovery se fait en broadcast, donc forcément en UDP.
TCP a été choisi pour le reste des échanges (échanges de messages) pour assurer la bonne arrivée des messages et leur conformité.
Pour la base de données, nous avons choisi SQLite car nous n’avions besoin que d’une petite BDD locale pour mémoriser les échanges, nous aurions aussi pu mémoriser les utilisateurs dans la BDD mais avons préféré limiter les accès à la BDD dans le programme.
Pour l’interface, c’est Swing qui a été choisi car c’était la technologie présentée en TD et que nous avions déjà utilisé pour le projet PDLA, elle nous était donc familière.  Bien que peu esthétique, Swing est de plus très efficace pour des interfaces peu complexes comme celles créées dans ce projet.

**Testing Policy** 

Concernant les tests, nous avons fait peu de tests unitaires car nous avons priorisé le fonctionnement des programmes, ce qui nous a été très chronophages. Les tests (et debugs) ont été effectués en fonction des comportements des programmes et de leurs retours, et nous n’avons malheureusement pas eu le temps de conclure plus de tests unitaires sur les fonctionnalités de notre projet.


**Highlights** 

Les classes concernées sont : 

Main.java, 
UDPServer.java 
UDPServerListenerThread.java
TCPServer.java
TCPServerListenerThread.java
Events.java
NetworkConnectionEvent.java
NetworkUDPMessageEvent.java 
NetworkTCPMessageEvent.java

Il ne s’agit là que d’une structure de code facilement remplaçable, mais au début du projet, nous avions opté pour une méthode de callback utilisant des Consumers de socket et d’évènements pour récupérer et gérer les messages reçus en fonction de leur nature. Cependant nous avons abandonné l’idée un peu avant le premier milestone car leur utilisation et adaptation en fonction des différents évènements pouvant survenir a été difficile à mettre en place. Nous avons gardé la structure de base pour la réception des messages, et avons fini par utiliser les outils qui finalement possèdent leur utilité de gestion des messages et actions à prendre, à savoir les Observers et un Controller. 
