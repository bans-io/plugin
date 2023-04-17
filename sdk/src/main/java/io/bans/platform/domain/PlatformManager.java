package io.bans.platform.domain;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

/**
 * PlatformManager is an interface that defines the management of user sessions
 * and moderation actions for an online platform.
 */
public interface PlatformManager {

    /**
     * Starts a new user session with the provided UUID, username, and domain.
     * @param uuid the unique identifier for the user
     * @param username the name of the user
     * @param domain the domain associated with the user
     */
    void startSession(UUID uuid, String username, String domain);

    /**
     * Ends the current server players session.
     */
    void endSession();

    /**
     * Ends the session for the provided UUID and username.
     * @param uuid the unique identifier for the user
     * @param username the name of the user
     */
    void endSession(UUID uuid, String username);

    /**
     * Bans a user from the platform for a specified duration.
     * @param uuid the unique identifier for the user
     * @param username the name of the user
     * @param reason the reason for the ban
     * @param duration duration the duration of the ban in milliseconds
     * @return a CompletableFuture containing the result of the ban action
     */
    CompletableFuture<Boolean> ban(UUID uuid, String username, String reason, long duration);

    /**
     * Removes a user's ban from the platform.
     * @param uuid the unique identifier for the user
     * @param username the name of the user
     * @param reason the reason for the unban
     * @return a CompletableFuture containing the result of the unban action
     */
    CompletableFuture<Boolean> unban(UUID uuid, String username, String reason);

    /**
     * Temporarily or indefinitely prevents a user from chatting on the platform for a specified duration.
     * @param uuid the unique identifier for the user
     * @param username the name of the user
     * @param reason the reason for the timeout
     * @param duration the duration of the timeout in milliseconds
     * @return a CompletableFuture containing the result of the chat mute action
     */
    CompletableFuture<Boolean> mute(UUID uuid, String username, String reason, long duration);

    /**
     * Removes a user's chat mute from the platform.
     * @param uuid the unique identifier for the user
     * @param username the name of the user
     * @param reason the reason for the unmute
     * @return a CompletableFuture containing the result of the chat unmute action
     */
    CompletableFuture<Boolean> unmute(UUID uuid, String username, String reason);

    /**
     * Kicks a user from the platform with a specified reason.
     * @param uuid the unique identifier for the user
     * @param username the name of the user
     * @param reason the reason for the kick
     * @return a CompletableFuture containing the result of the kick action
     */
    CompletableFuture<Boolean> kick(UUID uuid, String username, String reason);

    /**
     * Kicks all users from the platform with a specified reason.
     * @param reason the reason for the kick
     * @return a CompletableFuture containing the result of the kick action
     */
    CompletableFuture<Boolean> kickall(String reason);
}