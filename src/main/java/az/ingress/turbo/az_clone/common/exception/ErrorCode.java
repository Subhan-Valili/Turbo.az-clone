package az.ingress.turbo.az_clone.common.exception;

import java.text.MessageFormat;
import java.util.Arrays;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    VALIDATION_FAILED(
            "VALIDATION_FAILED",
            "Validation failed",
            "One or more fields failed validation.",
            HttpStatus.BAD_REQUEST),
    METHOD_VALIDATION_FAILED(
            "METHOD_VALIDATION_FAILED",
            "Method validation failed",
            "One or more method parameters failed validation.",
            HttpStatus.BAD_REQUEST),
    CONSTRAINT_VIOLATION(
            "CONSTRAINT_VIOLATION",
            "Constraint violation",
            "One or more constraints were violated.",
            HttpStatus.BAD_REQUEST),
    REQUEST_BODY_NOT_READABLE(
            "REQUEST_BODY_NOT_READABLE",
            "Malformed request body",
            "Request body is missing or malformed.",
            HttpStatus.BAD_REQUEST),
    MISSING_REQUEST_PARAMETER(
            "MISSING_REQUEST_PARAMETER",
            "Missing request parameter",
            "A required request parameter is missing.",
            HttpStatus.BAD_REQUEST),
    TYPE_MISMATCH(
            "TYPE_MISMATCH",
            "Type mismatch",
            "Request parameter type is invalid.",
            HttpStatus.BAD_REQUEST),
    BAD_REQUEST(
            "BAD_REQUEST",
            "Bad request",
            "Request is malformed or contains invalid data.",
            HttpStatus.BAD_REQUEST),
    AKB_INTEGRATION_ERROR(
            "AKB_INTEGRATION_ERROR",
            "AKB integration error",
            "AKB integration failed during {0}.",
            HttpStatus.SERVICE_UNAVAILABLE),
    INTERNAL_SERVER_ERROR(
            "INTERNAL_SERVER_ERROR",
            "Internal server error",
            "An unexpected error occurred.",
            HttpStatus.INTERNAL_SERVER_ERROR),
    SUBMISSION_ALREADY_EXISTS(
            "IDEMPOTENCY_KEY_EXISTS",
            "Submission with this idempotency key already exists",
            "A credit submission with idempotency key ''{0}'' has already been processed. This is a duplicate request. If you need to submit a new credit, please generate a new idempotency key.",
            HttpStatus.CONFLICT),
    SUBMISSION_NOT_FOUND(
            "SUBMISSION_NOT_FOUND",
            "Submission not found",
            "Submission with ID ''{0}'' was not found.",
            HttpStatus.NOT_FOUND),
    SCORING_INQUIRY_NOT_FOUND(
            "SCORING_INQUIRY_NOT_FOUND",
            "Scoring inquiry not found",
            "Scoring inquiry with ID ''{0}'' was not found.",
            HttpStatus.NOT_FOUND),
    ACCOUNT_NOT_FOUND(
            "ACCOUNT_NOT_FOUND",
            "Account not found",
            "Account with ID ''{0}'' was not found.",
            HttpStatus.NOT_FOUND),

    // --- Auth module ---
    USER_NOT_FOUND(
            "USER_NOT_FOUND",
            "User not found",
            "İstifadəçi tapılmadı.",
            HttpStatus.NOT_FOUND),
    INVALID_OLD_PASSWORD(
            "INVALID_OLD_PASSWORD",
            "Invalid old password",
            "Köhnə parol yanlışdır.",
            HttpStatus.UNAUTHORIZED),
    SAME_PASSWORD(
            "SAME_PASSWORD",
            "New password same as old",
            "Yeni parol köhnə parol ilə eyni ola bilməz.",
            HttpStatus.BAD_REQUEST),
    INVALID_REFRESH_TOKEN(
            "INVALID_REFRESH_TOKEN",
            "Invalid refresh token",
            "Refresh token etibarsızdır və ya vaxtı bitib.",
            HttpStatus.UNAUTHORIZED),
    EMAIL_ALREADY_EXISTS(
            "EMAIL_ALREADY_EXISTS",
            "Email already exists",
            "''{0}'' email ünvanı ilə istifadəçi artıq qeydiyyatdan keçib.",
            HttpStatus.CONFLICT),
    REGISTRATION_SESSION_EXPIRED(
            "REGISTRATION_SESSION_EXPIRED",
            "Registration session expired",
            "Qeydiyyat sessiyasının vaxtı bitib və ya mövcud deyil.",
            HttpStatus.BAD_REQUEST),
    OTP_EXPIRED(
            "OTP_EXPIRED",
            "OTP expired",
            "OTP kodunun vaxtı bitib. Yenidən kod istəyin.",
            HttpStatus.BAD_REQUEST),
    OTP_ATTEMPTS_EXCEEDED(
            "OTP_ATTEMPTS_EXCEEDED",
            "OTP attempts exceeded",
            "Maksimum cəhd sayı aşıldı. Yenidən OTP istəyin.",
            HttpStatus.TOO_MANY_REQUESTS),
    OTP_INVALID(
            "OTP_INVALID",
            "Invalid OTP",
            "OTP kod yanlışdır. Qalan cəhd sayı: {0}",
            HttpStatus.BAD_REQUEST),
    OTP_COOLDOWN_ACTIVE(
            "OTP_COOLDOWN_ACTIVE",
            "OTP cooldown active",
            "Yenidən OTP göndərmək üçün bir qədər gözləyin.",
            HttpStatus.TOO_MANY_REQUESTS),
    ROLE_NOT_FOUND(
            "ROLE_NOT_FOUND",
            "Role not found",
            "''{0}'' rolu verilənlər bazasında tapılmadı.",
            HttpStatus.INTERNAL_SERVER_ERROR),

    // --- Car module ---
    CAR_NOT_FOUND(
            "CAR_NOT_FOUND",
            "Car not found",
            "ID ''{0}'' olan maşın tapılmadı.",
            HttpStatus.NOT_FOUND),
    BRAND_NOT_FOUND(
            "BRAND_NOT_FOUND",
            "Brand not found",
            "ID ''{0}'' olan marka tapılmadı.",
            HttpStatus.NOT_FOUND),
    MODEL_NOT_FOUND(
            "MODEL_NOT_FOUND",
            "Model not found",
            "ID ''{0}'' olan model tapılmadı.",
            HttpStatus.NOT_FOUND),
    NO_IMAGES_PROVIDED(
            "NO_IMAGES_PROVIDED",
            "No images provided",
            "Ən azı 1 şəkil göndərilməlidir.",
            HttpStatus.BAD_REQUEST),

    UNKNOWN(
            "UNKNOWN",
            "Unknown error",
            "An unrecognized error code was received.",
            HttpStatus.INTERNAL_SERVER_ERROR);

    @Getter(onMethod_ = @JsonValue)
    private final String code;
    private final String message;
    private final String detailsTemplate;
    private final HttpStatus httpStatus;

    public String formatDetails(Object... params) {
        if (params == null || params.length == 0) {
            return this.detailsTemplate;
        }
        return MessageFormat.format(this.detailsTemplate, params);
    }

    @JsonCreator
    public static ErrorCode fromCode(String value) {
        if (value == null || value.isBlank()) {
            return null;
        }
        return Arrays.stream(values())
                .filter(c -> c.code.equals(value))
                .findFirst()
                .orElse(UNKNOWN);
    }
}