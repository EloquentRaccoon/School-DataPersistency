-- ------------------------------------------------------------------------
-- Data & Persistency
-- Opdracht S6: Views
--
-- (c) 2020 Hogeschool Utrecht
-- Tijmen Muller (tijmen.muller@hu.nl)
-- André Donk (andre.donk@hu.nl)
-- ------------------------------------------------------------------------


-- S6.1.
--
-- 1. Maak een view met de naam "deelnemers" waarmee je de volgende gegevens uit de tabellen inschrijvingen en uitvoering combineert:
--    inschrijvingen.cursist, inschrijvingen.cursus, inschrijvingen.begindatum, uitvoeringen.docent, uitvoeringen.locatie
CREATE OR REPLACE VIEW deelnemers AS
    SELECT inschrijvingen.cursist AS Cursist_code,
       inschrijvingen.cursus AS Cursus_code,
       inschrijvingen.begindatum AS Datum,
       uitvoeringen.docent AS Docent_code,
       uitvoeringen.locatie AS Locatie FROM inschrijvingen
    JOIN uitvoeringen on inschrijvingen.cursus = uitvoeringen.cursus and inschrijvingen.begindatum = uitvoeringen.begindatum;
SELECT * FROM deelnemers;
-- 2. Gebruik de view in een query waarbij je de "deelnemers" view combineert met de "personeels" view (behandeld in de les):
    CREATE OR REPLACE VIEW personeel AS
	     SELECT mnr, voorl, naam as medewerker, afd, functie
      FROM medewerkers;
SELECT * FROM personeel;

CREATE OR REPLACE VIEW mensen AS SELECT * FROM personeel, deelnemers;
SELECT * FROM mensen;
-- 3. Is de view "deelnemers" updatable ? Waarom ?
-- nee, er zit een join in, dus bij een update weet de database niet welke table hij moet updaten.

-- S6.2.
--
-- 1. Maak een view met de naam "dagcursussen". Deze view dient de gegevens op te halen: 
--      code, omschrijving en type uit de tabel curssussen met als voorwaarde dat de lengte = 1. Toon aan dat de view werkt.
CREATE OR REPLACE VIEW dagcursussen AS
    SELECT code as cursus, omschrijving, type FROM cursussen WHERE lengte = 1;
SELECT * FROM dagcursussen;
-- 2. Maak een tweede view met de naam "daguitvoeringen". 
--    Deze view dient de uitvoeringsgegevens op te halen voor de "dagcurssussen" (gebruik ook de view "dagcursussen"). Toon aan dat de view werkt
CREATE OR REPLACE VIEW daguitvoeringen AS
    SELECT begindatum,docent,locatie, dagcursussen.* FROM uitvoeringen JOIN dagcursussen ON dagcursussen.cursus = uitvoeringen.cursus;
SELECT * FROM daguitvoeringen;
-- 3. Verwijder de views en laat zien wat de verschillen zijn bij DROP view <viewnaam> CASCADE en bij DROP view <viewnaam> RESTRICT
DROP VIEW dagcursussen CASCADE; --Dit verwijderd alle VIEW die gebruik maken van de VIEW 'dagcursussen' & verwijderd de VIEW 'dagcursussen'.
DROP VIEW dagcursussen RESTRICT; --Dit verwijderd alleen de VIEW 'dagcursussen', indien deze wordt gebruikt door een andere VIEW kan hij niet worden verwijderd.

