package com.star.Ba.jsf.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Nombre {

    // ── Unités ───────────────────────────────────────────────────
    private static final String[] UNITES = {
        "", "un", "deux", "trois", "quatre", "cinq", "six", "sept",
        "huit", "neuf", "dix", "onze", "douze", "treize", "quatorze",
        "quinze", "seize", "dix-sept", "dix-huit", "dix-neuf"
    };

    // ── Dizaines ─────────────────────────────────────────────────
    private static final String[] DIZAINES = {
        "", "", "vingt", "trente", "quarante", "cinquante",
        "soixante", "soixante", "quatre-vingt", "quatre-vingt"
    };

    // ── Tranches ─────────────────────────────────────────────────
    private static final String[] TRANCHES = {
        "", "mille", "million", "milliard"
    };


    /**
     * Met en majuscule la première lettre d'une chaîne (gère aussi les
     * accents, ex: "élément" -> "Élément").
     */
    private static String majFirst(String s) {
        if (s == null || s.isEmpty()) return s;
        return Character.toUpperCase(s.charAt(0)) + s.substring(1);
    }


    // ────────────────────────────────────────────────────────────
    //  API PUBLIQUE
    // ────────────────────────────────────────────────────────────

    /**
     * Convertit un montant TND en lettres.
     * ex: "1 503,000 (TND)" → "mille cinq cent trois dinars et zéro millime"
     * ex: "100 003,000 TND" → "cent mille trois dinars et zéro millime"
     */
    public static String montantTND(String montant) {
        return montantTND(parseMontant(montant));
    }

    /**
     * ex: 1503.000 → "mille cinq cent trois dinars et zéro millime"
     * Attention : un double peut introduire des imprécisions binaires
     * (ex: 0.1 + 0.2). Préférez BigDecimal pour des montants sensibles
     * (facturation, comptabilité...).
     */
    public static String montantTND(double montant) {
        // BigDecimal.valueOf(double) passe par Double.toString(), ce qui
        // évite les artefacts binaires classiques de "new BigDecimal(double)".
        return montantTND(BigDecimal.valueOf(montant));
    }

    /**
     * ex: new BigDecimal("1503.000") → "mille cinq cent trois dinars et zéro millime"
     */
    public static String montantTND(BigDecimal montant) {
        long[] parts = extraireDinarsEtMillimes(montant);
        long dinars   = parts[0];
        long millimes = parts[1];

        String partDinars   = nombreEnLettres(dinars)   + " dinar"  + (dinars   > 1 ? "s" : "");
        String partMillimes = nombreEnLettres(millimes) + " millime" + (millimes > 1 ? "s" : "");

        return majFirst(partDinars + " et " + partMillimes);
    }

    /**
     * Convertit un montant TND entièrement en millimes.
     * ex: "1 503,000 (TND)" → "un million cinq cent trois mille millimes"
     */
    public static String montantEnMillimes(String montant) {
        return montantEnMillimes(parseMontant(montant));
    }

    /** ex: 1503.000 → "un million cinq cent trois mille millimes" */
    public static String montantEnMillimes(double montant) {
        return montantEnMillimes(BigDecimal.valueOf(montant));
    }

    /** ex: new BigDecimal("1503.000") → "un million cinq cent trois mille millimes" */
    public static String montantEnMillimes(BigDecimal montant) {
        long[] parts = extraireDinarsEtMillimes(montant);
        long total = (parts[0] * 1000L) + parts[1];
        return majFirst(nombreEnLettres(total) + " millime" + (total > 1 ? "s" : ""));
    }

    /**
     * Convertit un entier long en lettres.
     * ex: 100003 → "cent mille trois"
     * ex: 80     → "quatre-vingts"
     * ex: 200000 → "deux cent mille"
     */
    public static String getLettre(long n) {
        return majFirst(nombreEnLettres(n));
    }

    /** Version interne, sans majuscule — utilisée pour composer des phrases. */
    private static String nombreEnLettres(long n) {
        if (n == 0)  return "zéro";
        if (n < 0)   return "moins " + convertir(-n, true).trim();
        return convertir(n, true).trim();
    }


    // ────────────────────────────────────────────────────────────
    //  NETTOYAGE / PARSING DE L'ENTRÉE
    // ────────────────────────────────────────────────────────────

    /**
     * Parse une chaîne comme "1 503,000 (TND)", "100 003,000 TND", "1503.5"...
     * en BigDecimal. Accepte espaces, parenthèses, devise, "." ou ","
     * comme séparateur décimal.
     */
    private static BigDecimal parseMontant(String montant) {
        // On ne garde que les chiffres, le séparateur décimal et le signe moins.
        // Les espaces, "TND", parenthèses, etc. sont éliminés.
        String propre = montant
            .replaceAll("[^0-9,.\\-]", "") // supprime tout sauf chiffres , . -
            .trim();

        if (propre.isEmpty()) {
            throw new IllegalArgumentException("Montant invalide : " + montant);
        }

        // Le séparateur décimal est le dernier "," ou "." rencontré
        int lastComma = propre.lastIndexOf(',');
        int lastDot   = propre.lastIndexOf('.');
        int sepIndex  = Math.max(lastComma, lastDot);

        String entierStr;
        String decimalStr;

        if (sepIndex == -1) {
            entierStr  = propre;
            decimalStr = "";
        } else {
            // Tout ce qui précède le séparateur (en retirant les autres séparateurs
            // éventuels utilisés comme séparateurs de milliers) = partie entière
            entierStr  = propre.substring(0, sepIndex).replaceAll("[,.\\s]", "");
            decimalStr = propre.substring(sepIndex + 1).replaceAll("[,.\\s]", "");
        }

        if (entierStr.isEmpty() || entierStr.equals("-")) {
            entierStr = "0";
        }
        if (decimalStr.isEmpty()) {
            decimalStr = "0";
        }

        return new BigDecimal(entierStr + "." + decimalStr);
    }

    /**
     * Extrait [dinars, millimes] à partir d'un BigDecimal, avec arrondi
     * correct au millime le plus proche (au lieu d'une simple troncature).
     */
    private static long[] extraireDinarsEtMillimes(BigDecimal montant) {
        BigDecimal arrondi = montant.setScale(3, RoundingMode.HALF_UP);

        long dinars = arrondi.longValue(); // partie entière (troncature vers 0)
        BigDecimal partieDecimale = arrondi.subtract(new BigDecimal(dinars));
        long millimes = partieDecimale.abs()
                                       .movePointRight(3)
                                       .longValue(); // 0..999

        return new long[] { dinars, millimes };
    }


    // ────────────────────────────────────────────────────────────
    //  LOGIQUE INTERNE
    // ────────────────────────────────────────────────────────────

    /**
     * @param isLast indique si ce segment est la toute dernière partie du
     *               nombre (rien ne suit). Nécessaire pour l'accord de
     *               "quatre-vingts" et "cents" (règle : ils prennent un "s"
     *               uniquement s'ils ne sont pas suivis d'un autre nombre).
     */
    private static String convertir(long n, boolean isLast) {
        if (n == 0) return "";

        if (n < 20)  return UNITES[(int) n];

        if (n < 100) return dizaine((int) n, isLast);

        if (n < 1000) return centaine((int) n, isLast);

        // Tranches : mille, million, milliard
        for (int i = TRANCHES.length - 1; i >= 1; i--) {
            long seuil = puissance(1000, i);
            if (n >= seuil) {
                long quotient = n / seuil;
                long reste    = n % seuil;

                String prefixe;

                // "mille" est invariable (pas "deux milles")
                // Le quotient n'est jamais le dernier segment : "mille"/"million"
                // le suit toujours, donc isLast=false ici.
                if (i == 1) {
                    prefixe = (quotient == 1) ? "mille" : convertir(quotient, false) + " mille";
                } else {
                    prefixe = convertir(quotient, false) + " " + TRANCHES[i]
                            + (quotient > 1 ? "s" : "");
                }

                if (reste == 0) {
                    return prefixe.trim();
                }
                // Le reste hérite du isLast d'origine : s'il n'y a rien après
                // le nombre entier, il n'y a rien après le reste non plus.
                return (prefixe + " " + convertir(reste, isLast)).trim();
            }
        }

        return "";
    }

    private static String dizaine(int n, boolean isLast) {
        int d = n / 10;
        int u = n % 10;

        switch (d) {
            case 7:
                // 70-79 : soixante-dix, soixante et onze...
                if (u == 0) return "soixante-dix";
                if (u == 1) return "soixante et onze";
                return "soixante-" + UNITES[10 + u];

            case 9:
                // 90-99 : quatre-vingt-dix... (jamais de "s" sur vingt ici)
                if (u == 0) return "quatre-vingt-dix";
                return "quatre-vingt-" + UNITES[10 + u];

            default:
                if (u == 0) {
                    // Seul "quatre-vingt" (d == 8) est un multiple de "vingt"
                    // et peut donc prendre un "s" en fin de nombre.
                    if (d == 8) return isLast ? "quatre-vingts" : "quatre-vingt";
                    return DIZAINES[d];
                }
                if (u == 1 && d != 8) return DIZAINES[d] + " et un";
                return DIZAINES[d] + "-" + UNITES[u];
        }
    }

    private static String centaine(int n, boolean isLast) {
        int c = n / 100;
        int r = n % 100;

        if (c == 1) {
            // "cent" (jamais "un cent") ; jamais de "s" puisque c == 1
            return r == 0 ? "cent" : "cent " + convertir(r, isLast);
        }

        if (r == 0) {
            // "deux cents" si en fin de nombre, "deux cent" sinon
            // (ex: "deux cent mille")
            return UNITES[c] + " " + (isLast ? "cents" : "cent");
        }
        // "deux cent quatre-vingts" : "cent" jamais de "s" ici car suivi de r
        return UNITES[c] + " cent " + convertir(r, isLast);
    }

    private static long puissance(long base, int exp) {
        long result = 1L;
        for (int i = 0; i < exp; i++) result *= base;
        return result;
    }


    // ────────────────────────────────────────────────────────────
    //  DEMO
    // ────────────────────────────────────────────────────────────

    public static void main(String[] args) {
        // String
        System.out.println(montantTND("1 503,000 (TND)"));
        System.out.println(montantTND("100 003,000 TND"));

        // double
        System.out.println(montantTND(1503.000));
        System.out.println(montantEnMillimes(1503.5));

        // BigDecimal (recommandé pour des montants sensibles)
        System.out.println(montantTND(new BigDecimal("1503.000")));
        System.out.println(montantEnMillimes(new BigDecimal("100003.750")));

        // getLettre seul
        System.out.println(getLettre(80));
        System.out.println(getLettre(200000));
    }
}