package com.rest;

import io.github.cdimascio.dotenv.Dotenv;
import io.github.cdimascio.dotenv.DotenvEntry;
import io.github.cdimascio.dotenv.DotenvException;

import java.util.Map;
import java.util.stream.Collectors;

public final class Secrets {

    private static Map<String, String> values;

    private Secrets() {
    }

    /** Postman API key, sent as the X-Api-Key header. */
    public static String postmanApiKey() {
        return required("POSTMAN_API_KEY");
    }

    /** Google OAuth 2.0 access token for the Gmail API. */
    public static String gmailAccessToken() {
        return required("GMAIL_ACCESS_TOKEN");
    }

    /** Gmail address the Gmail API tests read from and send to. */
    public static String gmailUser() {
        return required("GMAIL_USER");
    }

    private static synchronized Map<String, String> values() {
        if (values == null) {
            try {
                values = Dotenv.load().entries(Dotenv.Filter.DECLARED_IN_ENV_FILE).stream()
                        .collect(Collectors.toMap(DotenvEntry::getKey, DotenvEntry::getValue));
            } catch (DotenvException e) {
                throw new IllegalStateException(".env file not found in the project root. Create it with POSTMAN_API_KEY, GMAIL_ACCESS_TOKEN and GMAIL_USER (see README.md).", e);
            }
        }
        return values;
    }

    private static String required(String name) {
        String value = values().get(name);
        if (value == null || value.isBlank()) {
            throw new IllegalStateException(name + " is not set in .env");
        }
        return value;
    }
}
