package com.airtribe.ridewise.enums;

/**
 * @author Priyanka Pitla
 */
public enum VehicleType {
    // 1. Define constants and pass the description to the constructor
    ADMIN("Administrator with full system access"),
    MANAGER("Supervisor with mid-level approvals"),
    CUSTOMER("Standard user with read-only view");

    // 2. Declare the description field as private final
    private final String description;

    // 3. Create a private constructor (private keyword is implied for enums)
    private VehicleType(String description) {
        this.description = description;
    }

    // 4. Expose the description via a standard getter
    public String getDescription() {
        return this.description;
    }
}

