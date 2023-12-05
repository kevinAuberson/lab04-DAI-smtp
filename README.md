# Rapport laboratoire no4 - SMTP
Kevin Auberson et Adrian Rogner
## Introduction
Ce rapport présente le laboratoire no4 du cours de Développement d'applications Internet. 

L'objectif de ce laboratoire est de développer une application client qui permet d'envoyer des courriels à des victimes. 
L'application doit être configurable et doit permettre d'envoyer un courriel à un groupe de victimes.

## Mock SMTP Server
### Description
Pour tester l'application, nous avons utilisé un serveur SMTP mock.  
Ce serveur permet de simuler un serveur SMTP et de recevoir les courriels envoyés par l'application.
Nous utilisons un serveur mock afin d'éviter d'utiliser un serveur SMTP en production.
Ce serveur mock est déployé sur docker et est accessible à l'adresse suivante: [Mock SMTP Server](https://github.com/maildev/maildev).

### Configuration
Pour installer le serveur SMTP mock, il faut suivre les étapes suivantes:

1. Installer docker sur votre machine
2. Lancer le serveur SMTP mock avec la commande suivante:
```bash
docker run -d -p 1080:1080 -p 1025:1025 maildev/maildev
```
3. Accéder à l'interface web du serveur SMTP mock à l'adresse suivante: [Mock SMTP Server](http://localhost:1080/)
4. Accéder au serveur SMTP mock à l'adresse suivante: [Mock SMTP Server](http://localhost:1025/)


## Client SMTP
### Description
### Configuration
### Utilisation
### Implémentation
### Diagramme de classe
### Exemple de dialogue


Instructions for setting up your mock SMTP server. The user who wants to experiment with your tool but does not really want to send pranks immediately should be able to use a mock SMTP server.

Clear and simple instructions for configuring your tool and running a prank campaign. If you do a good job, an external user should be able to clone your repo, edit a couple of files and send a batch of e-mails in less than 10 minutes.

A description of your implementation: document the key aspects of your code. It is a good idea to start with a class diagram. Decide which classes you want to show (focus on the important ones) and describe their responsibilities in text. It is also certainly a good idea to include examples of dialogues between your client and an SMTP server (maybe you also want to include some screenshots here).