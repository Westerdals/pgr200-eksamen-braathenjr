Martin Braathen - bramar17
Kevin Berg - berjoh17

Vi fikk først ikke koden til å bygge og kjøre grønt. Men etter vi leste readme filen som sa at vi måtte kjøre migrate som argument før vi gjorde noe annet, så funket på bygging og at tester ble grønn. (mvn test).

Vi la merke til at i både title og description så kunne vi kun ha et ord. Det kunne være så langt som ønskelig, men så fort vi skrev et mellomrom og kjørte med det fikk vi "InputMismatchException". 

Forklaringen i readme-filen var lett forståelig.

Vi fikk bedre forsåtelse av testdekning, og fikk innblikk i en løsning med flere tabeller enn kun en.

Jeg ville foreslått å refaktorere noen metoder, ved å foreksempel ha sysout i egne metoder - "single responsibility principle". Mulig mer spesifikke metodenavn. eks: deleteTopic isteden for kun delete.