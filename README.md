# Système sécurisé de virements bancaires

Exercice pratique de **gestion des exceptions en Java** (Master 1 – Programmation Java).

## Objectifs pédagogiques

- Utiliser `try` / `catch` / `finally`
- Distinguer `throw` et `throws`
- Concevoir des exceptions personnalisées
- Comprendre la propagation des exceptions
- Garantir la cohérence des données après un échec

## Structure du projet

```
banking-app/
├── BankAccount.java                    # Compte bancaire (numéro, solde, débit, crédit)
├── BankingException.java               # Exception métier de base (checked)
├── InsufficientFundsException.java     # Fonds insuffisants
├── InvalidAccountException.java        # Compte null ou identique
├── TransferLimitExceededException.java # Plafond de 10 000 € dépassé
├── TransferService.java                # Logique de virement et validation
├── Main.java                           # Programme de démonstration
└── README.md
```

## Hiérarchie des exceptions

```
Exception
└── BankingException
    ├── InsufficientFundsException
    ├── TransferLimitExceededException
    └── InvalidAccountException
```

## Règles de validation d'un virement

| Règle                                | Exception levée                  |
|--------------------------------------|----------------------------------|
| Compte source ou destinataire `null` | `InvalidAccountException`        |
| Même compte source et destinataire   | `InvalidAccountException`        |
| Montant <= 0                         | `IllegalArgumentException`       |
| Montant > 10 000 €                   | `TransferLimitExceededException` |
| Montant > solde du compte source     | `InsufficientFundsException`     |

Toutes les validations sont effectuées **avant** toute modification des comptes.

## Prérequis

- JDK 11 ou supérieur

```bash
java -version
javac -version
```

Installation sous Debian / Ubuntu :

```bash
sudo apt update && sudo apt install -y default-jdk
```

## Compilation et exécution

```bash
javac -encoding UTF-8 *.java
java Main
```

Résultat attendu :

```
Manque 300.0 €
Solde A = 500.0 | Solde B = 100.0
```

## Tester d'autres cas

Modifier le montant dans `Main.java` (`service.transfer(a, b, ...)`), puis recompiler :

| Montant | Résultat           |
|---------|--------------------|
| `50`    | Virement réussi    |
| `800`   | Fonds insuffisants |
| `20000` | Plafond dépassé    |
| `-5`    | Argument invalide  |

## Nettoyage

```bash
rm *.class
```

## Limite connue

Si une défaillance technique survient entre `debit` et `credit`, le compte source est déjà débité et les données deviennent incohérentes. Ce point sera traité dans la suite de l'exercice (rollback avec `try/catch/finally`).