package com.example.onlinelawsystem.helper;

import java.util.Random;

public class UniqueIdGenerator {
    private static final String ALPHABET_CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String NUMERIC_CHARS = "0123456789";
    private static final int ALPHABET_LENGTH = 8;
    private static final int NUMERIC_LENGTH = 4;

    //For courtID
    private static final String COURT_PREFIX = "COU";
    private static final int NUM_DIGITS = 10;

    //FOR LAW ID
    private static final String LAW_PREFIX = "IPC";

    //FOR LAW ID
    private static final String LAWYER_PREFIX = "LAW";

    //FOR CRIMINAL ID
    private static final String CRIMINAL_PREFIX = "CRI";

    //FOR CASE STUDY ID
    private static final String CASE_STUDY_PREFIX = "CAST";

    //FOR LAW ID
    private static final String LAW_BOOK_PREFIX = "LAWBK";
    private static final String PUBLIC_CASE_PREFIX = "LAWBK";

    /*public static String generateEventId() {
        SecureRandom secureRandom = new SecureRandom();
        StringBuilder stringBuilder = new StringBuilder();

        // Generate alphabetic characters
        for (int i = 0; i < ALPHABET_LENGTH; i++) {
            int randomIndex = secureRandom.nextInt(ALPHABET_CHARS.length());
            char randomChar = ALPHABET_CHARS.charAt(randomIndex);
            stringBuilder.append(randomChar);
        }

        // Generate numeric characters
        for (int i = 0; i < NUMERIC_LENGTH; i++) {
            int randomIndex = secureRandom.nextInt(NUMERIC_CHARS.length());
            char randomChar = NUMERIC_CHARS.charAt(randomIndex);
            stringBuilder.append(randomChar);
        }

        return stringBuilder.toString();
    }*/

    public static String generateCourtID() {
        StringBuilder sb = new StringBuilder(COURT_PREFIX);

        // Generate 8 random digits
        Random random = new Random();
        for (int i = 0; i < NUM_DIGITS; i++) {
            int randomDigit = random.nextInt(10); // Generate a random digit between 0 and 9
            sb.append(randomDigit);
        }

        return sb.toString();
    }

    public static String generateLawID() {
        StringBuilder sb = new StringBuilder(LAW_PREFIX);

        // Generate 8 random digits
        Random random = new Random();
        for (int i = 0; i < NUM_DIGITS; i++) {
            int randomDigit = random.nextInt(10); // Generate a random digit between 0 and 9
            sb.append(randomDigit);
        }

        return sb.toString();
    }

    public static String generateLawyerID() {
        StringBuilder sb = new StringBuilder(LAWYER_PREFIX);

        // Generate 8 random digits
        Random random = new Random();
        for (int i = 0; i < NUM_DIGITS; i++) {
            int randomDigit = random.nextInt(10); // Generate a random digit between 0 and 9
            sb.append(randomDigit);
        }

        return sb.toString();
    }

    public static String generateCriminalID() {
        StringBuilder sb = new StringBuilder(CRIMINAL_PREFIX);

        // Generate 8 random digits
        Random random = new Random();
        for (int i = 0; i < NUM_DIGITS; i++) {
            int randomDigit = random.nextInt(10); // Generate a random digit between 0 and 9
            sb.append(randomDigit);
        }

        return sb.toString();
    }

    public static String generateCaseStudyID() {
        StringBuilder sb = new StringBuilder(CASE_STUDY_PREFIX);

        // Generate 8 random digits
        Random random = new Random();
        for (int i = 0; i < NUM_DIGITS; i++) {
            int randomDigit = random.nextInt(10); // Generate a random digit between 0 and 9
            sb.append(randomDigit);
        }

        return sb.toString();
    }

    public static String generateLawBookID() {
        StringBuilder sb = new StringBuilder(LAW_BOOK_PREFIX);

        // Generate 8 random digits
        Random random = new Random();
        for (int i = 0; i < NUM_DIGITS; i++) {
            int randomDigit = random.nextInt(10); // Generate a random digit between 0 and 9
            sb.append(randomDigit);
        }

        return sb.toString();
    }

    public static String generatePublicCaseID() {
        StringBuilder sb = new StringBuilder(PUBLIC_CASE_PREFIX);

        // Generate 8 random digits
        Random random = new Random();
        for (int i = 0; i < NUM_DIGITS; i++) {
            int randomDigit = random.nextInt(10); // Generate a random digit between 0 and 9
            sb.append(randomDigit);
        }

        return sb.toString();
    }
}