package com.sqap.cons;



public enum Profiles {

    DEV("dev"),
    PROD("prod");

    private String profileName;

    Profiles(String profileName) {
        this.profileName = profileName;
    }

    public String getProfileName() {
        return profileName;
    }
}
