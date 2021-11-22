package tr.com.teamfaster.domain.utils;

public class BlendInputValidator {
    public static ValidationStatus validate(String input) {

        try {
            int typeRank = Integer.parseInt(input);
            if (typeRank <= 0 || typeRank > EntityType.values().length) return ValidationStatus.INVALID_INPUT;
        } catch (NumberFormatException e) {
            return ValidationStatus.INVALID_FORMAT;
        }
        return ValidationStatus.SUCCESSFUL;
    }
}
