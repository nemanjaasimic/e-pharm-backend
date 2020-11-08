package com.isa.epharm.util;

public class ValidationConstants {

    public static class User {
        public static final int USERNAME_MAX_SIZE = 30;
        public static final int PASSWORD_HASH_SIZE = 64;
        public static final int EMAIL_MAX_SIZE = 254;
        public static final int PHONE_NUMBER_MAX_SIZE = 100;
    }

    public static class Common {
        public static final int NAME_MAX_SIZE = 100;
        public static final int DENY_REASON_MAX_SIZE = 200;

    }

    public static class Patient {
        public static final int INSURANCE_NUMBER_MAX_SIZE = 100;
        public static final int ADDRESS_MAX_SIZE = 500;
        public static final int CITY_MAX_SIZE = 100;
        public static final int COUNTRY_MAX_SIZE = 100;
    }

    public static class Examination {
        public static final int ROOM_NAME_MAX_SIZE = 200;
    }

}
