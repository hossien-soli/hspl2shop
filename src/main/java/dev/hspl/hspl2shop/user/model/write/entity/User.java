package dev.hspl.hspl2shop.user.model.write.entity;

import dev.hspl.hspl2shop.common.ApplicationUser;
import dev.hspl.hspl2shop.common.value.EmailAddress;
import dev.hspl.hspl2shop.common.value.FullName;
import dev.hspl.hspl2shop.common.value.PhoneNumber;
import dev.hspl.hspl2shop.common.value.UserRole;
import dev.hspl.hspl2shop.user.value.ProtectedPassword;
import lombok.Getter;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@NullMarked
public class User implements ApplicationUser {
    private final UUID id;

    private FullName fullName;

    private PhoneNumber phoneNumber;
    private ProtectedPassword password;

    @Nullable
    private EmailAddress emailAddress;

    private UserRole role;

    private boolean banned;

    private final LocalDateTime createdAt;

    @Nullable
    private LocalDateTime updatedAt;

    @Nullable
    private LocalDateTime lastTokenRefresh;

    @Nullable
    private final Short version; // this field should be managed by Repository implementations
    // in domain or business logic only use it for client-side concurrency control checks
    // null = entity is new and should be persisted

    private User(
            UUID id, FullName fullName, PhoneNumber phoneNumber,
            ProtectedPassword password, @Nullable EmailAddress emailAddress,
            UserRole role, boolean banned, LocalDateTime createdAt,
            @Nullable LocalDateTime updatedAt, @Nullable LocalDateTime lastTokenRefresh,
            @Nullable Short version
    ) {
        this.id = id;
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.emailAddress = emailAddress;
        this.role = role;
        this.banned = banned;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.lastTokenRefresh = lastTokenRefresh;
        this.version = version;
    }

    public static User newUser(
            UUID newId, FullName fullName, PhoneNumber phoneNumber, ProtectedPassword password,
            @Nullable EmailAddress emailAddress, UserRole role, LocalDateTime currentDateTime
    ) {
        return new User(newId, fullName, phoneNumber, password, emailAddress, role,
                false, currentDateTime, null, null, null);
    }

    public static User existingUser(
            UUID id, FullName fullName, PhoneNumber phoneNumber,
            ProtectedPassword password, @Nullable EmailAddress emailAddress,
            UserRole role, boolean banned, LocalDateTime createdAt,
            @Nullable LocalDateTime updatedAt, @Nullable LocalDateTime lastTokenRefresh,
            @Nullable Short version
    ) {
        return new User(id, fullName, phoneNumber, password, emailAddress, role, banned,
                createdAt, updatedAt, lastTokenRefresh, version);
    }

    public void updateBasicInfo(FullName fullName, @Nullable EmailAddress emailAddress,
                                LocalDateTime currentDateTime) {
        this.fullName = fullName;
        this.emailAddress = emailAddress;
        this.updatedAt = currentDateTime;
    }

    public void updatePhoneNumber(PhoneNumber phoneNumber, LocalDateTime currentDateTime) {
        this.phoneNumber = phoneNumber;
        this.updatedAt = currentDateTime;
    }

    public void updatePassword(ProtectedPassword newPassword, LocalDateTime currentDateTime) {
        this.password = newPassword;
        this.updatedAt = currentDateTime;
    }

    public void banUser(LocalDateTime currentDateTime) {
        this.banned = true;
        this.updatedAt = currentDateTime;
    }

    public void unbanUser(LocalDateTime currentDateTime) {
        this.banned = false;
        this.updatedAt = currentDateTime;
    }

    public void changeUserRole(UserRole newRole, LocalDateTime currentDateTime) {
        this.role = newRole;
        this.updatedAt = currentDateTime;
    }

    public void newTokenRefresh(LocalDateTime currentDateTime) {
        this.lastTokenRefresh = currentDateTime;
    }

    @Override
    public UUID id() {
        return id;
    }

    @Override
    public FullName fullName() {
        return fullName;
    }

    @Override
    public PhoneNumber phoneNumber() {
        return phoneNumber;
    }

//    @Override
//    public EmailAddress emailAddress() {
//        return emailAddress;
//    }

    @Override
    public UserRole role() {
        return role;
    }

    @Override
    public boolean isAccountActive() {
        return !banned;
    }
}
