# Kata Foo Bar Quix

Projet Java permettant de lire un nombre entre 0 et 100 et retournant une chaine de caractère contenant soit : 

- Le nombre
- FOO
- BAR
- QUIX
- Un mix de FOO BAR et QUIX 

Voici l'algorithme déterminant le retour :

- Si le nombre est divisible par 3 ou s'il contient 3, la chaîne de caractères doit contenir "FOO"
- Si le nombre est divisible par 5 ou s'il contient 5, la chaîne de caractères doit contenir "BAR"
- Si le nombre contient 7, la chaîne de caractères doit contenir "QUIX"
- La règle "divisible par" est plus prioritaire que la règle "contient"
- Le contenu est analysé de gauche à droite
- Si aucune des règles n'est vérifiée, retourner le nombre en entrée sous forme de chaîne de caractères

## 🧱 Technologies utilisées

- Java 21
- Spring Boot
- Spring Batch
- Spring Web
- Maven
- JUnit 5 + Mockito
- Swagger OpenAPI (annotations @Operation, @ApiResponse)

## 📁 Fonctionnement

### 🎮 API Controller – FooBarQuix
Ce contrôleur expose plusieurs endpoints REST pour :
- Convertir un nombre en une chaîne de caractères spéciale selon des règles définies (FooBarQuix)
- Lancer un traitement batch sur un fichier d’entrée
- Suivre l’état d’un traitement batch
- Télécharger le fichier résultat généré par le batch

🔧 Endpoints disponibles

- GET /api/fooBarQuix/{num}
- GET /api/fooBarQuix/batch/start
- GET /api/fooBarQuix/batch/status/{jobId}
- GET /api/fooBarQuix/batch/result

📁 Fichiers impliqués
- Entrée : input_numbers.txt
- Sortie : temp/output.csv


### 📃 Batch
#### 📥 Lecture (Reader)

- Lecture d’un fichier .txt contenant des nombres allant de 0 à 100 grâce à `FlatFileItemReader`.

#### 🔄 Traitement (Processor)

- Fait appel au service réalisant l'algorithme

#### 💾 Écriture (Writer)

Écriture de la transformation dans un fichier output.csv en sortie avec `FlatFileItemWriter`.

## 🧪 Tests

- **Tests unitaires** : controller, service, processor
- **Tests d’intégration** : du batch complet avec un vrai fichier .txt

Lancement des tests :

```bash
mvn test
```

Lancement de l'application :

```bash
mvn spring-boot:run
```
