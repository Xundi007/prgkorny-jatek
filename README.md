# prgkorny-jatek
#### Logikai kétszemélyes társasjáték.

## Játékszabályok:
- Két mezővel elmozdíthatunk egy saját korongot vízszintesen, függőlegesen vagy átlósan, ha így az üres mezőre kerül.
- Nyolcszomszédos üres mezőre helyezhetünk egy újabb saját korongot.
- Korong áthelyezés közben át is lehet ugrani bármelyik korongot.
- A játékot az nyeri meg, akinek a végén több pontja van.

## Előkövetelmény:
- maven 3.x
- jdk 1.8.0_40+

## Futtatás:
A projekt könyvtárából a következő parancsokkal:

- Fordítás:

        > mvn package
- Futtatás:

        > java -jar target/prgkorny-jatek-2.0-jar-with-dependencies.jar
vagy

        > mvn exec:java -Dexec.mainClass=view.View
