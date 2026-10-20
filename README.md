

## 1. Objectifs pédagogiques

Ce projet a pour objectif de mettre en pratique la gestion des exceptions en Java à travers un système simplifié de virements bancaires.

Les notions abordées sont :

- Utiliser les blocs `try`, `catch` et `finally`.
- Comprendre la différence entre `throw` et `throws`.
- Créer et utiliser des exceptions personnalisées.
- Comprendre la propagation des exceptions.
- Valider les opérations avant de modifier les données.
- Garantir la cohérence des soldes en cas d'échec d'un virement.

## 2. Structure du projet

Le projet est organisé de la manière suivante :

```text
banking-app/
├── BankAccount.java
├── BankingException.java
├── InsufficientFundsException.java
├── InvalidAccountException.java
├── TransferLimitExceededException.java
├── TransferService.java
├── Main.java
└── README.md
```

### Description des fichiers

| Fichier | Rôle |
|---|---|
| `BankAccount.java` | Représente un compte bancaire avec un numéro, un solde et des opérations de débit et de crédit. |
| `BankingException.java` | Définit l'exception métier de base, de type checked. |
| `InsufficientFundsException.java` | Signale un solde insuffisant pour effectuer un virement. |
| `InvalidAccountException.java` | Signale un compte invalide ou un virement entre deux références au même compte. |
| `TransferLimitExceededException.java` | Signale le dépassement du plafond autorisé. |
| `TransferService.java` | Contient la logique du virement et les règles de validation. |
| `Main.java` | Permet d'exécuter le programme et de tester différents scénarios. |

## 3. Règles de validation d'un virement

Avant toute modification des comptes, le service vérifie les conditions suivantes :

| Condition | Exception levée |
|---|---|
| Le compte source est `null` | `InvalidAccountException` |
| Le compte destinataire est `null` | `InvalidAccountException` |
| Le compte source et le destinataire sont identiques | `InvalidAccountException` |
| Le montant est inférieur ou égal à zéro | `IllegalArgumentException` |
| Le montant dépasse 10 000 € | `TransferLimitExceededException` |
| Le montant dépasse le solde disponible | `InsufficientFundsException` |

Toutes les validations doivent être effectuées **avant le débit et le crédit des comptes**, afin d'éviter toute modification lorsque les paramètres du virement sont invalides.

## 4. Prérequis

Pour compiler et exécuter le projet, il faut disposer de :

- Un JDK 11 ou supérieur.
- Un terminal ou une invite de commandes.

Vérifier l'installation de Java :

```bash
java -version
javac -version
```

### Installation sous Debian / Ubuntu

Si Java n'est pas installé, exécuter les commandes suivantes :

```bash
sudo apt update
sudo apt install -y default-jdk
```

## 5. Compilation et exécution

Depuis le répertoire contenant les fichiers `.java`, compiler le projet avec :

```bash
javac -encoding UTF-8 *.java
```

Puis lancer le programme :

```bash
java Main
```

## 6. Exemple de résultat attendu

Pour un compte source disposant de 500 € et un compte destinataire disposant de 100 €, une tentative de virement de 800 € doit échouer en raison d'un solde insuffisant.

Le programme affiche alors :

```text
Manque 300.0 €
Solde A = 500.0 | Solde B = 100.0
```

Le message indique qu'il manque 300 € pour effectuer le virement. Les soldes restent inchangés, car les validations sont réalisées avant toute modification des comptes.

## 7. Tester différents scénarios

Pour tester les différents cas, modifier le montant de l'appel à `transfer` dans `Main.java` :

```java
service.transfer(a, b, 800);
```

Remplacer `800` par l'un des montants suivants :

| Montant | Résultat attendu |
|---|---|
| `50` | Virement réussi |
| `800` | Exception pour fonds insuffisants |
| `20000` | Exception pour dépassement du plafond |
| `-5` | `IllegalArgumentException` |

Il est également possible de tester les cas suivants :

- Un compte source ou destinataire égal à `null`.
- Un virement vers le même compte.
- Un montant égal à zéro.
- Un virement correspondant exactement au solde disponible.
- Un montant égal au plafond de 10 000 €.

## 8. Limite connue : absence de rollback

Même si les validations sont réalisées avant toute modification, une défaillance technique peut survenir pendant l'exécution du virement.

Par exemple, si le débit du compte source réussit, mais que le crédit du compte destinataire échoue, le montant est retiré du premier compte sans être ajouté au second.

Les soldes deviennent alors incohérents.

### Amélioration prévue

La suite de l'exercice consiste à utiliser `try`, `catch` et `finally` pour étudier la gestion des échecs et mettre en place un mécanisme de rollback permettant de restaurer les soldes initiaux si le virement échoue après le débit.

**Attention :** un bloc `finally` ne réalise pas automatiquement un rollback. La restauration doit être programmée explicitement, généralement dans un bloc `catch`, tandis que `finally` peut servir à effectuer des opérations de nettoyage.

## 9. Conclusion

Ce projet permet de comprendre comment gérer les erreurs dans une application bancaire Java, depuis la validation des données jusqu'à la création d'exceptions personnalisées.

Il met également en évidence l'importance de préserver la cohérence des données lorsqu'une opération comporte plusieurs étapes susceptibles d'échouer.

L'étape suivante sera d'implémenter un mécanisme de rollback afin de rendre le virement plus robuste en cas de défaillance technique.
