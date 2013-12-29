package fr.k2i.adbeback.core.business;

/**
 * Created with IntelliJ IDEA.
 * User: dev
 * Date: 16/12/13
 * Time: 21:44
 * To change this template use File | Settings | File Templates.
 */
public interface IMetaData {

    interface TableMetadata {
        String AD_RESPONSE =                            "ad_response";
        String AD_RULE =                                "ad_rule";
        String AD =                                     "ad";
        String BRAND =                                  "brand";
        String CONTACT =                                "contact";
        String PRODUCT =                                "product";
        String VIEWED_AD =                              "viewed_ad";
        String CITY =                                   "city";
        String OTP =                                    "one_time_pwd";
    }

    interface ColumnMetadata {

        public interface AdResponse {
            String RESPONSE =                           "response";
            String ID =                                 "id";
        }

        public interface AdRule {
            String SEX =                                "sex";
            String AROUND =                            "around";

            public interface Discrimator{
                String DISCRIMINATOR =                  "classe";

                String AGE_RULE =                       "Age";
                String AMOUNT_RULE =                    "Amount";
                String BRAND_RULE =                     "Brand";
                String CITY_RULE =                      "City";
                String COUNTRY_RULE =                   "Country";
                String DATE_RULE =                      "Date";
                String OPEN_RULE =                      "Open";
                String PRODUCT_RULE =                   "Product";
                String SEX_RULE =                       "Sex";
            }
            String QUESTION =                           "question";
            String AGE_MIN =                            "age_min";
            String AGE_MAX =                            "age_max";
            String AMOUNT =                             "amount";
            String CITY_JOIN  =                         "city_id";
            String JOIN_COUNTRY =                       "country_id";
            String START_DATE =                         "start_date";
            String END_DATE =                           "end_date";
            String RESPONSE_JOIN =                      "rule_id";
            String CORRECT_RESPONSE =                   "correct_id";
        }

        public interface Ad {
            String ID =                                 "id";
            String BRAND =                              "brand_id";
            String PRODUCT =                            "product_id";
            String RULE_JOIN =                          "ad_id";
            String TYPE =                               "type";
            String VIDEO =                              "video";
            String AUDIO =                              "audio";
            String IMG =                                "img";
            String INITIAL_AMOUNT =                     "initial_amount";
            String START_DATE =                         "start_date";
            String END_DATE =                           "end_date";

            public interface Discrimator {
                String DISCRIMINATOR =                  "classe";
                String VIDEO_AD =                       "video";
                String AUDIO_AD =                       "audio";
                String STATIC_AD =                      "static";
            }
        }

        public interface Brand {
            String ID =                                 "id";
            String PRODUCT_JOIN =                       "brand_id";
            String MAIN_CONTACT =                       "main_contact_id";
            String NAME =                               "name";
            String LOGO =                               "logo";
            String SIRET =                              "siret";
            String PWD =                                "password";
            String ROLE_TABLE_JOIN =                    "brand_role";
            String ROLE_JOIN =                          "role_id";
            String BRAND_JOIN =                         "brand_id";
            String LOGIN =                              "email";
        }

        public interface Contact {
            String ID =                                 "id";
            String LASTNAME =                           "lastname";
            String FIRSTNAME =                          "firstname";
            String SEX =                                "sex";
            String PHONE =                              "phone";
            String MOBILE =                             "mobile";
            String EMAIL =                              "email";
            String BRAND =                              "brand_id";
            String FUNCTION =                           "function";
        }

        public interface Product {
            String ID =                                 "id";
            String NAME =                               "name";
            String DESC =                               "description";
            String PUBLIC_FEE =                         "public_fee";
            String AD_FEE =                             "ad_fee";
            String LOGO =                               "logo";
        }

        public interface ViewedMedia {
            String ID =                                 "id";
            String PLAYER =                             "player_id";
            String AD =                                 "ad_id";
            String NB =                                 "nb";
            String RULE =                               "rule_id";
        }

        public interface City {
            String ZIPCODE =                            "zipcode";
            String CITY =                               "city";
            String LON =                                "lon";
            String LAT =                                "lat";
            String COUNTRY =                            "country_id";
        }

        public interface Address {
            String CITY_JOIN =                          "city_id";
            String COUNTRY_JOIN =                       "country_id";
        }

        public interface OTPSecurity {
            String KEY =                                "otp_key";
            String CREATION_DATE =                      "creation_date";
            String EXPIRATION_DATE =                    "expiration_date";
            String BRAND_JOIN =                         "brand_id";

            public interface Discrimator {
                String DISCRIMINATOR =                  "classe";
                String BRAND =                          "brand";
            }
        }
    }
}