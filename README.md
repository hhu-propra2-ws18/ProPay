# ProPay

[![Build Status](https://travis-ci.org/hhu-propra2/ProPay.svg?branch=master)](https://travis-ci.org/hhu-propra2/ProPay)

*Next generation Payment Processor*

Hier finden Sie den Sourcecode zu ProPay -- der Zukunft von
Online-Bezahldiensten -- jetzt auch als OpenSource.

## Funktionsumfang

ProPay bietet Funktionen zur Accountverwaltung, Überweisungen und Reservierungen
an. Dabei können beliebige Accounts angelegt und Gelder transferiert /
abgehoben werden. Der Account-Controller ist dafür entsprechend ausgelegt.

Des Weiteren können Beträge zwischen Accounts transferiert werden, sowie
Reservierungen angelegt werden. Das ist dann sinnvoll, wenn man einen Betrag von
einem Account blockieren und im Schadensfall an den `targetAccount`
transferieren möchte. Reservierungen können auch einfach wieder gelöst werden,
wenn eine Kaution wieder auf den Ursprungsaccount zurückerstattet werden soll.

## Installation

ProPay kann bequem über
[DockerHub](https://cloud.docker.com/u/propra/repository/docker/propra/propay)
als Image bezogen werden. Dafür müssen Sie nur folgenden Befehl ausführen:

    docker run --name propay -p 8888:8888 propra/propay:latest
   
Mit dem Tag `latest` beziehen Sie die aktuellste Version von ProPay. Wenn Sie
mehr Sicherheit und Kontrolle über die verwendete Version haben möchten, sollten
Sie den Tag genauer spezifizieren. Da ProPay sich unter ständiger Entwicklung
befindet, könnte es andernfalls zu Komplikationen kommen.

Alternativ kann auch das Gradle-Projekt direkt ausgeführt werden:

    ./gradlew bootRun

### docker-compose

ProPay lässt sich als Service in Ihre docker-compose Konfiguration einbinden:

```
services:
  # ...
  propay:
    image: propra/propay
    ports:
      - "8888:8888"
    # Optional
    volumes:
      - ./data:/code/data
  # ...
```

### Persistenz

Die Datenbank wird automatisch in die Datei `data/propay.mv.db` gesichert. Um
einen neuen Container wieder mit den alten Daten zu starten, kann ein Volume
Mapping durchgeführt werden:

    docker run -v `pwd`/data/:/code/data -p 8888:8888 propra/propay:latest

Um die Datenbank zurückzusetzen, genügt es die Datei `data/propay.mv.db` zu
löschen.

## API

Der Onlinebezahldienst stellt eine REST-API zur Verfügung. Die Dokumentation ist
unter der URL http://localhost:8888/ zu erreichen. Dort können manuelle Tests
durchgeführt und die API nachgeschlagen werden.

Wenn ein Account nicht in der Datenbank gefunden werden konnte, erstellt ProPay
automatisch einen Account für Sie. Dabei muss der Accountname natürlich
eindeutig sein. Wie Sie diesen wählen, bleibt Ihnen überlassen. Der neue Account
verfügt dann über ein Guthaben von 0 €.


## Demo

Eine Live-Demo des Systems befindet sich online und wird stets die aktuellste
Version der API zur Verfügung stellen. Allerdings wird die Datenbank nicht
persistiert, das heißt die Daten können jederzeit verloren gehen oder sogar von
anderen Benutzern überschrieben werden.

Da wir einen kostenfreien Account von Heroku für die Demo verwenden, kann der
Start der Anwendung anfangs ein paar Sekunden dauern. Also einfach ein wenig
warten, bis die Anwendung verfügbar ist.

https://propra-propay.herokuapp.com/
