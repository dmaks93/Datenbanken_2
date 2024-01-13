**Ausgabe aller Spieler (Spielername), die in einem bestimmten Zeitraum gespielt haben:**

SELECT DISTINCT g.player.username
FROM Game g
WHERE g.starttime BETWEEN :starttime AND :endtime;

z.B. starttime = '2024-02-26' und endtime = '2024-02-28'

**Ausgabe zu einem bestimmten Spieler: Alle Spiele (Id, Datum), sowie die Anzahl der korrekten Antworten pro Spiel mit Angabe der Gesamtanzahl der Fragen pro Spiel bzw. alternativ den Prozentsatz der korrekt beantworteten Fragen:**

SELECT g.id, g.endtime, COUNT(f.id) AS gesamtFragen, SUM(CASE WHEN f.korrekt = true THEN 1 ELSE 0 END) AS korrekteAntworten,
(SUM(CASE WHEN f.korrekt = true THEN 1 ELSE 0 END) / COUNT(f.id) * 100) AS prozentsatzKorrekt
FROM Game g
JOIN g.fragen f
WHERE g.username = "player2"
GROUP BY s.id, s.spielDatum

**Ausgabe aller Spieler mit Anzahl der gespielten Spiele, nach Anzahl absteigend geordnet:**

SELECT p.username, COUNT(p.playerid) AS gespielteSpiele
FROM game g
JOIN player p ON g.player_playerid = p.playerid
GROUP BY p.username
ORDER BY gespielteSpiele DESC

**Ausgabe der am meisten gefragten Kategorie, oder alternativ, die Beliebtheit der Kategorien nach Anzahl der Auswahl absteigend sortiert:**

SELECT f.kategorie, COUNT(f.id) AS anzahlFragen
FROM Frage f
GROUP BY f.kategorie
ORDER BY anzahlFragen DESC