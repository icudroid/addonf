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
        String GOOSE_LEVEL =                            "goose_level";
        String ATTACHEMENTS =                           "attachements";
        String SECTOR =                                 "sector";

        String CUSTOMER_TARGET =                        "customer_target";
        String CATEGORY_PRICE =                         "cat_price";
        String CATEGORY =                               "category";
        String ATTACHMENTS_BRAND =                     "brand_attachment";
        String ATTACHMENTS_AGENCY =                    "agency_attachment";
        String ATTACHMENTS_MEDIA =                      "media_attachment";
        String ATTACHMENTS =                            "company_attachment";
        String BID_CATEGORY_MEDIA =                     "bid_cat_media";
        String WALLET =                                 "wallet";
        String TRANSACTION =                            "transaction";
        String ORDER =                                  "player_order";
        String TRANSACTION_HISTORY =                    "tr_history";
        String MERCHANT_PRODUCT =                       "merchant_product";
    }

    interface ColumnMetadata {

        public interface AdResponse {
            String RESPONSE =                           "response";
            String ID =                                 "id";
            String RESPONSE_IMG =                       "response_img";
        }

        public interface AdRule {
            String SEX =                                "sex";
            String AROUND =                            "around";
            String BRAND_NO_DISPLAY_TABLE_JOIN =        "brand_no_display_with";
            String BRAND_RULE_JOIN =                    "brand_rule_id";
            String BRAND_JOIN =                         "brand_id";
            String INITIAL_AMOUNT =                     "initial_amount";
            String INSERTED =                           "inserted";
            String RESPONSE_CORRECT_JOIN =              "correct_for_rule_id";
            String MULTI_RESPONSE_JOIN =                "multi_rule_id";

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
                String MULTI_RESPONSE_RULE =            "MultiResponses";
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
            String TYPE =                               "sector_id";
            String INITIAL_AMOUNT =                     "initial_amount";
            String START_DATE =                         "start_date";
            String END_DATE =                           "end_date";
            String DURATION =                           "duration";
            String AD_FILE =                            "ad_file";
            String NAME =                               "name";
            String AD_FILE_ENCODED =                    "encoded";
            String PARTNER =                            "partner_id";
            String JOIN =                               "ad_id";
            String MEDIA_ID =                           "media_id";
            String DISPLAY_ON_MEDIA =                   "display_on_media";

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
            String USER_JOIN =                          "user_id";
            String TYPE =                               "sector";
            String JOIN =                               "brand_id";
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
            String USER_JOIN =                          "user_id";

            public interface Discrimator {
                String DISCRIMINATOR =                  "classe";
                String USER =                          "user";
            }
        }

        public interface GooseLevel {
            public interface Discrimator {
                String DISCRIMINATOR =                  "classe";
                String SINGLE =                         "single";
                String MULTI =                          "multi";
                String DICE =                           "dice";
            }
        }

        public interface User {
            String AGENCY_JOIN =                         "agency_id";
            String IN_CHARGE_OF_TABLE_JOIN =             "in_charge_of";
            String AGENCY_RULE_JOIN =                    "agency_id";
            String BRAND_JOIN =                          "brand_id";
            String AD_JOIN =                             "user_id";
        }


        public interface Agency {
            String CREATED_DATE =                        "creation_date";
            String SIRET_NUMBER =                        "siret";
            String SIREN_NUMBER =                        "siren";
            String ATTACHEMENTS =                        "attachement_id";
            String CONFIG =                              "config_id" ;
            String LEGAL_STATUS =                        "legal_status";
            String USER_JOIN =                           "agency_id";
        }


        public interface Media {
            String CREATED_DATE =                        "creation_date";
            String SIRET_NUMBER =                        "siret";
            String SIREN_NUMBER =                        "siren";
            String ATTACHEMENTS =                        "attachement_id";
            String CONFIG =                              "config_id" ;
            String LEGAL_STATUS =                        "legal_status";
            String USER_JOIN =                           "user_id";
            String JOIN =                                "media_id";
        }


        public interface Attachement {
            String ORIGINAL_NAME                        = "origin_name";
            String SIZE                                 = "size";
            String FULL_PATH                            = "full_path";
            String STATUS                               = "status";
            String EXTENTION                            = "extention";
            String ID                                   = "attachement_id";
        }

        public interface Transaction {
            String JOIN                                 = "transaction_id"  ;

            public interface Discrimator {
                String CREDIT                           = "credit";
                String DEBIT                            = "debit" ;
                String EMPREINT                         = "empreint";
            }
        }

        public interface Order {
            String MEDIA                                = "media_id";
            String JOIN                                 = "order_id";
        }
    }
}
