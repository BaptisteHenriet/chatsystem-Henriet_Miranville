**Compiler et exécuter le code**
Commandes : 

mvn compile

mvn package

mvn exec:java -Dexec.mainClass="chatsystem.Main" 

Pour pouvoir chatter : 

- Entrer un Username (lorsque la fenêtre s'ouvre, un broadcast est envoyé pour récupérer les contacts déjà connectés et donc leur username. De ce fait, une erreur est levée si vous entrez un username déjà utilisé par un autre)
  
- Cliquer sur un username dans la liste des contacts ( si vous êtes le premier connecté, attendez que quelqu'un d'autre se connecte, son username s'affichera alors dans votre liste.)
  
- Chattez !
