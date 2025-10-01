package entity;

import enums.TypeTransaction;

import java.time.LocalDate;

public record Transaction(
        int id,
        LocalDate date,
        double montant,
        TypeTransaction type,
        String lieu,
        int idCompte
) {}
