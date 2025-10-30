# TrainFixer

**Course:** Algorithms and Data Structures — Fall 2025  
**Assignment 1:** TrainFixer: Validate and Repair Trains with Linked Lists  
**Language:** Java 21  
**Build system:** Maven

---

## 👥 
- Marcus Mortensen (mamo0005)

---

##  How to Run Tests
Run all tests with:
```bash
mvn test
```
You can also run it through the maven tab and select "test".

To run a specific test class, use:
```bash
mvn -Dtest=ClassName test
```
Replace `ClassName` with the name of the test class you want to run.

To run a specific test method within a class, use:
```bash
mvn -Dtest=ClassName#methodName test
```
Replace `ClassName` with the name of the test class and `methodName` with the name of the test method you want to run.

# Køretidskompleksitet

Min løsning af TrainFixer-opgaven har to hovedfunktioner:

TrainValidator (tjekker om toget er gyldigt)

TrainFixer (retter toget så det bliver gyldigt)

### TrainValidator

TrainValidator går toget igennem én gang for tjekke om alle regler er overholdt.
Derfor vokser tiden lineært med antallet af vogne O(n).

Den bruger o(1) plads. Og derfor bruges der ikke meget ekstra hukommelse (kun nogle få variabler) O(1) plads.

TrainFixer

Trainfixer laver to gennemløb af toget. 
Først bliver alle vogne delt op i chunks (lokomotiver, passagervogne, godsvogne osv.).
Derefter bliver de sat sammen igen i den rigtige rækkefølge.
Derfor tager funktionen O(n) tid i alt. Derfor er det stadig lineært, fordi to gennemløb af n elementer stadig er proportionalt med n.
Den bruger lidt ekstra hukommelse til de midlertidige lister – altså O(n) plads.

Fordelen ved at bruge to gennemløb er, at koden bliver meget lettere at læse og forstå, og risikoen for fejl er lille.
En løsning med kun ét gennemløb ville bruge mindre hukommelse, men være meget sværere at implementere og teste.
