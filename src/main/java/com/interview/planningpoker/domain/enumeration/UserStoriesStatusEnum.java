package com.interview.planningpoker.domain.enumeration;


public enum UserStoriesStatusEnum {
    PENDING("PENDING"),
    VOTING("VOTING"),
    VOTED("VOTED");

    private final String value;

    UserStoriesStatusEnum(String value) {
        this.value = value;
    }

    public static UserStoriesStatusEnum toEnum(String value) {
        for (UserStoriesStatusEnum userStoriesStatusEnum : UserStoriesStatusEnum.values()) {
            if (userStoriesStatusEnum.getValue().equalsIgnoreCase(value)) {
                return userStoriesStatusEnum;
            }
        }
        return null;
    }

    public String getValue() {
        return value;
    }
}
