**<span style="font-size:larger;text-decoration:underline;">Marc-Andréa Dehais</span>**  
  
Ce projet permet de gérer une liste de clients, de véhicules et de réservations dans une agence de location de voitures. Les fonctionnalités principales sont les suivantes :

+ **<span style="font-size:larger;text-decoration:underline;">CLIENT</span>**    
Créer un client  
Supprimer un client  
Modifier la fiche d'un client  
Voir la liste des clients  
Voir les détails d'un client avec ses réservations et les véhicules qu'il a loués  
Contraintes pour les clients : âge minimum de 18 ans, nom et prénom d'au moins 3 lettres, format email et date à respecter, pas de doublon d'email  
  
+ **<span style="font-size:larger;text-decoration:underline;">VEHICLE</span>**  
Créer un véhicule  
Supprimer un véhicule  
Modifier la fiche d'un véhicule  
Voir la liste des véhicules  
Voir les détails d'un véhicule (ses réservations et à quel client il appartient)  
Contraintes pour les véhicules : nombre de places entre 2 et 9, un constructeur et un modèle doivent être spécifiés  
  
+ **<span style="font-size:larger;text-decoration:underline;">RESERVATION</span>**  
Créer une réservation  
Voir la liste des réservations  
Modifier une réservation  
Supprimer une réservation  
Voir les détails d'une réservation (à quel client et avec quelle voiture)  
Contraintes pour les réservations : pas de superposition de réservations, pas de réservation de plus de 7 jours, si un client est supprimé, toutes ses réservations sont également supprimées  
  
+ **<span style="font-size:larger;text-decoration:underline;">Problèmes rencontrés</span>**  
Si une superposition de réservations, une réservation de plus de 7 jours ou un doublon d'email sont détectés, une page d'erreur s'affiche. Bien que cela ne soit pas idéal, je n'ai pas réussi à trouver une meilleure solution pour le moment. Il y avait également un problème avec le modèle des véhicules qui a été corrigé dans toutes les parties du code
