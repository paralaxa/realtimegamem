/* CREATE TABLE */
CREATE TABLE IF NOT EXISTS question(
  id BIGINT PRIMARY KEY,
  content TEXT,
  answer TEXT,
  score INT DEFAULT 1
);
/* INSERT QUERY */
INSERT INTO question(id, content, answer)
VALUES
  (
    1, 'Doplň porekadlo. Sýty hladnému…',
    'neveri'
  );
/* INSERT QUERY */
INSERT INTO question(id, content, answer)
VALUES
  (
    2, 'Koľko meria najvyšší vrch najväčšieho európského štátu (metre)',
    5642
  );
/* INSERT QUERY */
INSERT INTO question(id, content, answer)
VALUES
  (
    3, 'Prvé meno, druhého manžela, známej americkej herečky, ktorá zomrela 5.9.1962',
    'Joe'
  );
/* INSERT QUERY */
INSERT INTO question(id, content, answer)
VALUES
  (
    4, 'Názov najznámejšej skladby z druhého albumu speváčky, ktorá sa narodila 14. septembra 1983',
    'Rehab'
  );
/* INSERT QUERY */
INSERT INTO question(id, content, answer)
VALUES
  (
    5, 'Prvé slovo z názvu filmu, v ktorom Eva Mázikova tlačila kapustu nohami.',
    'Pacho'
  );
/* INSERT QUERY */
INSERT INTO question(id, content, answer)
VALUES
  (
    6, 'Koľko účastníkov sa prihlásilo tento rok na medzinárodný maratón mieru tento rok? ',
    13010
  );
/* INSERT QUERY */
INSERT INTO question(id, content, answer)
VALUES
  (
    7, '
Priezvisko moderátora známej oldschulovej relácie “Repete” ',
    'Krajicek'
  );
/* INSERT QUERY */
INSERT INTO question(id, content, answer)
VALUES
  (
    8, 'V ktorom roku sa potopil Titanic?',
    1912
  );
/* INSERT QUERY */
INSERT INTO question(id, content, answer)
VALUES
  (
    9, 'Aký uhol (v stupňoch) opíše sekundová ručička za pol minúty?',
    180
  );
/* INSERT QUERY */
INSERT INTO question(id, content, answer)
VALUES
  (
    10, 'Ktorému hrdinovi bolo povedané: S veľkou mocou prichádza veľká zodpovednosť?',
    'Spiderman'
  );
/* INSERT QUERY */
INSERT INTO question(id, content, answer)
VALUES
  (
    11, 'Koľko kolies má spravidla vysokozdvižný vozík?',
    4
  );
/* INSERT QUERY */
INSERT INTO question(id, content, answer)
VALUES
  (
    12, 'Koľko dievčat tvorilo skupinu, ktorá vzniklad v roku 1994 v Anglicku?',
    5
  );
/* INSERT QUERY */
INSERT INTO question(id, content, answer)
VALUES
  (
    13, 'Názov funkcie človeka stojaceho na čele fakulty vysokej školy',
    'Dekan'
  );
/* INSERT QUERY */
INSERT INTO question(id, content, answer)
VALUES
  (
    14, 'Kto naspieval titulnú pieseň k seriálu, ktorý mal 236 častí a 10 sérii?',
    'The Rembrandts'
  );
/* INSERT QUERY */
INSERT INTO question(id, content, answer)
VALUES
  (
    15, 'Meno psa Adolfa Hitlera?', 'Blondi'
  );
/* INSERT QUERY */
INSERT INTO question(id, content, answer)
VALUES
  (
    16, 'Obec, nachádzajúca sa v okrese Příbram, ktorá ma 568 obyvateľov',
    'Picin'
  );
/* INSERT QUERY */
INSERT INTO question(id, content, answer)
VALUES
  (
    17, 'Z akého filmu je toto GIF? (oficiálny anglický názov) http://gph.is/2c6UdX2',
    'The Shining'
  );
/* INSERT QUERY */
INSERT INTO question(id, content, answer)
VALUES
  (
    18, 'Autor citátu Truly wonderful, the mind of a child is.',
    'Yoda'
  );
/* INSERT QUERY */
INSERT INTO question(id, content, answer)
VALUES
  (
    19, 'Predmet, do ktorého Voldemort ukryl časť svojej duše',
    'horcrux'
  );
/* INSERT QUERY */
INSERT INTO question(id, content, answer)
VALUES
  (
    20, 'Krstné meno autora obrazu: https://wallpapercave.com/wp/FUA1WNB.jpg',
    'Leonardo'
  );
/* INSERT QUERY */
INSERT INTO question(id, content, answer)
VALUES
  (
    21, 'Priezvisko vynálezcu hry Dungeons and Dragons',
    'Gygax'
  );
/* INSERT QUERY */
INSERT INTO question(id, content, answer)
VALUES
  (
    22, 'Značka akutálne najrýchlejšieho auta na svete. ',
    'Bugatti'
  );
/* INSERT QUERY */
INSERT INTO question(id, content, answer)
VALUES
  (
    23, 'Koľko krajín tvorí ostrov Veľká Británia?',
    4
  );
/* INSERT QUERY */
INSERT INTO question(id, content, answer)
VALUES
  (
    24, 'Krstné meno Darth Vadera.',
    'Anakin'
  );
/* INSERT QUERY */
INSERT INTO question(id, content, answer)
VALUES
  (
    25, 'Kde sa nachádza najväčšia púšť sveta?',
    'Antarktida'
  );
/* INSERT QUERY */
INSERT INTO question(id, content, answer)
VALUES
  (
    26, 'Názov ovocia na obrázku: https://www.kamnajedlo.eu/storage/app/media/old/1437463782.jpg',
    'Lici'
  );
/* INSERT QUERY */
INSERT INTO question(id, content, answer)
VALUES
  (
    27, 'Aká spoločnosť vyvinula programovací jazyk Swift?',
    'Apple'
  );
/* INSERT QUERY */
INSERT INTO question(id, content, answer)
VALUES
  (
    28, 'Panorámu akého mesta vidíte? https://techtour-prod-public.s3-eu-west-1.amazonaws.com/1203101?AWSAccessKeyId=AKIAIQY2UYNCMITUGSSA&Expires=1513198159&Signature=xzJEXjMKxZOXUWGPpqjdcTk5PKU%3D',
    'Istanbul'
  );
/* INSERT QUERY */
INSERT INTO question(id, content, answer)
VALUES
  (
    29, 'Na logo akého auta sa práve pozeráte? https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRkqO0ny4hVYoNPthPJP4-bPfcKcQxAxNDe6n9-mAPzTBWn1dXgAQ',
    'Cadillac'
  );
/* INSERT QUERY */
INSERT INTO question(id, content, answer)
VALUES
  (
    30, 'Podľa akého druhu zvieraťa sú pomenované Kanárske ostrovy?',
    'pes'
  );
/* INSERT QUERY */
INSERT INTO question(id, content, answer)
VALUES
  (
    31, 'Priezvisko autora tejto skladby: https://www.myinstants.com/instant/ode-to-joy-choir/',
    'Beethoven'
  );
/* INSERT QUERY */
INSERT INTO question(id, content, answer)
VALUES
  (
    32, 'Priezvisko herca v známom GIF: https://i.imgur.com/Oe7KX0d.mp4',
    'Hill'
  );
/* INSERT QUERY */
INSERT INTO question(id, content, answer)
VALUES
  (
    33, 'Ktorému štátu patrí táto vlajka? http://www.gamesforthebrain.com/game/flag/image/kt-lgflag.gif',
    'Australia'
  );
/* INSERT QUERY */
INSERT INTO question(id, content, answer)
VALUES
  (
    34, 'Kto stojí vo WOW proti Aliancii?',
    'horda'
  );
/* INSERT QUERY */
INSERT INTO question(id, content, answer)
VALUES
  (
    35, 'Akú knižnicu vyvinul Facebook ako alternatívu ku Angularu?',
    'React'
  );
/* INSERT QUERY */
INSERT INTO question(id, content, answer)
VALUES
  (
    36, 'Značka luxusných átu, ktorej 100% vlastníkom je Fiat Group.',
    'Maserati'
  );
/* INSERT QUERY */
INSERT INTO question(id, content, answer)
VALUES
  (
    37, 'Priezvisko človeka, ktorý rozlúštil Enigmu.',
    'Turing'
  );
/* INSERT QUERY */
INSERT INTO question(id, content, answer)
VALUES
  (
    38, 'Na tvar akého štátu sa práve pozeráte? https://lh4.googleusercontent.com/pOxX_5owtdbABY6E-xda58tleTQZOfaD693VDTYh5qO6War9nI_hwxz2r-iTVrjBO3CHsT-dXWhl51mLjrKONrHP_n2sXdxR0EIH2T7yFUIfbp8vfK2Yg5QM',
    'Kanada'
  );
/* INSERT QUERY */
INSERT INTO question(id, content, answer)
VALUES
  (
    39, 'Priezvisko autora tabuľky chemických prvkov.',
    'Mendelejev'
  );
/* INSERT QUERY */
INSERT INTO question(id, content, answer)
VALUES
  (
    40, 'Java je skratka pre JavaScript. ano/nie?',
    'Nie'
  );
/* INSERT QUERY */
INSERT INTO question(id, content, answer)
VALUES
  (
    41, 'Nedávno zavretú kartu v prehliadači opäť otvoríš, keď stlačíť Ctrl+Shift+?',
    'T'
  );
/* INSERT QUERY */
INSERT INTO question(id, content, answer)
VALUES
  (
    42, 'Aký bude výstup, ak funkciu mystery použijeme na reťazci Frontend masters?
String.prototype.mystery=function(){var t,r=0;if(0===this.length)return r;for(t=0;t<this.length;t++)r=(r<<5)-r+this.charCodeAt(t),r|=0;return r};',
    1750097507
  );
/* INSERT QUERY */
INSERT INTO question(id, content, answer)
VALUES
  (
    43, '-- .- -- / .-. .- -.. / -- --- .-. --.. . --- ...- -.- ..-',
    'mam rad morzeovku'
  );
/* INSERT QUERY */
INSERT INTO question(id, content, answer)
VALUES
  (
    44, 'ngytpebwpgtmtgkjnsik
hgkupbsaojshyoaxfmlg
tljxoghditihiegegzam
bljwwapphzcqatixthmi
sehwvnfggszzgtklqcaj
lthzlvkkqcloydijlhha
qaqtbwxdotqtpsfocgfo
ddwgxqmplhclwhmmecfo
picapqrxkaifrzqheybz
jsvwkelkiioykcxduqrp

3:10 9:8 9:19 1:0 0:5 8:6',
    'cipher'
  );
/* INSERT QUERY */
INSERT INTO question(id, content, answer)
VALUES
  (
    45, '- communication protocol
- full duplex communication
- single TCP connection',
    'websocket'
  );
/* INSERT QUERY */
INSERT INTO question(id, content, answer)
VALUES
  (
    46, 'Napíš skrátený názov parametra programu rsync, ktorý len zobrazí čo by sa synchronizovalo. Bez pomlčky',
    'n'
  );
/* INSERT QUERY */
INSERT INTO question(id, content, answer)
VALUES
  (
    47, 'Tento hash vyzerá povedome.
c2750a7d522eb4df4e842980ed3f3e78',
    'nbusr123'
  );
/* INSERT QUERY */
INSERT INTO question(id, content, answer)
VALUES
  (
    48, 'A webcomic of romance,
sarcasm, math, and language.',
    'xkcd'
  );
/* INSERT QUERY */
INSERT INTO question(id, content, answer)
VALUES
  (
    49, 'answer to life, the universe and everything',
    42
  );
/* INSERT QUERY */
INSERT INTO question(id, content, answer)
VALUES
  (
    50, 'Na ktorom riadku je chyba?

1:  procedure bubbleSort( A : list of sortable items )
2:  n = length(A)
3:  repeat
4:      swapped = false
5:      for i = 1 to n-1 inclusive do
6:          if A[i-1] > A[i] then
7:              swap(A[i], A[i])
8:              swapped = true
9:          end if
10:     end for
11:     n = n - 1
12: until not swapped
13: end procedure',
    7
  );
/* INSERT QUERY */
INSERT INTO question(id, content, answer)
VALUES
  (
    51, 'Do odpovede napíš quicksort v akomkolvek programovacom jazyku.',
    'quicksort v akomkolvek programovacom jazyku'
  );
/* INSERT QUERY */
INSERT INTO question(id, content, answer)
VALUES
  (
    52, 'Obľúbený programátorský had (angl.).',
    'python'
  );
/* INSERT QUERY */
INSERT INTO question(id, content, answer)
VALUES
  (
    53, 'Koľko jabĺk v ústach je možné si počas jednej minúty rozrezať motorovou pílou? (rekord)',
    21
  );
/* INSERT QUERY */
INSERT INTO question(id, content, answer)
VALUES
  (
    54, 'Cézar o päť
hjqd yjsyt yjcy oj utywjgsj ify it tiutajij',
    'cely tento text je potrebne dat do odpovede'
  );
/* INSERT QUERY */
INSERT INTO question(id, content, answer)
VALUES
  (
    55, '5 + 8 = 2
8 + 9 = 3
1 + 2 = 0
8 + 8 = 4
8 + 0 = ?',
    3
  );
/* INSERT QUERY */
INSERT INTO question(id, content, answer)
VALUES
  (
    56, '
+++++ +++++
[
  > +++++ ++++
  > +++++ +++
  > +++++ +
  > +++++ +++
  <<<< -
]
> -- .
> --- .
> +++++ .
> +++ .',
    'XMAS'
  );
