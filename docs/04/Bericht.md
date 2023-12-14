

**Erste Messung:** 

Ohne Optimierung

*Durchlaufzeit an altem Laptop: 02:15:00h*

**Zweite Messung**: 

Named Queries in der Player Entity; Das Holen der Kategorie Liste haben wir aus der Schleife geholt und nur ein einziges Mal von der Datenbank geholt.
Hash Map anstelle eines Arrays um alle Kategorien, die für ein jeweiliges Spiel gewählt werden sollen zu erfassen.


*Durchlaufzeit an altem Laptop: 00:16:40h*

**Dritte Messung**:

Implementierung von Batch Transaction. Es werden 1000 Spiele in eine Transaktion abgelegt.

Vergleich der Durchlaufzeit mit flush() im Entity Manager vs ohne flush:

| Gerät   | flush()   | ohne flush() |
|:--------|:----------|:------------:| 
| Laptop  | 00:05:50h |  00:05:58h   |
| Desktop | 00:00:65h |  00:00:64h   |

Wir haben uns entschieden flush() und clear() nicht explizit zu nutzen da beide Methoden bereits beim Commit der Transaktion mit berücksichtigt werden.
und keine Performancegewinne garantieren.




