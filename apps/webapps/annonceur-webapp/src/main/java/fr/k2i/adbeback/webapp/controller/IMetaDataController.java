package fr.k2i.adbeback.webapp.controller;

/**
 * User: dimitri
 * Date: 11/12/13
 * Time: 10:06
 * Goal:
 */
public interface IMetaDataController {

    interface PathUtils{
        String REDIRECT = "redirect:";
        String CONTEXT_RELATIVE="contextRelative:";
    }

    interface Path{

        String ENROLL_FORM =                            "/signup.html";
        String REGISTRATION_CONFIRM =                   "/confirmEnroll/{crypt}/{code}";

        String CREATE_CAMPAIGN_STEP_0 =                 "/createCampaign/step0";
        String CREATE_CAMPAIGN_STEP_1 =                 "/createCampaign/step1";
        String CREATE_CAMPAIGN_STEP_2 =                 "/createCampaign/step2";
        String CREATE_CAMPAIGN_STEP_3 =                 "/createCampaign/step3";
        String CREATE_CAMPAIGN_STEP_4 =                 "/createCampaign/step4";

        String MANAGE_ADS_PARTIALS =                    "/manageAds/partials/{html}.html";
        String GET_ALL_ADS =                            "/manageAds/getAll";
        String MANAGE_ADS_PARTIALS_ACTION =             "/manageAds/partials/{action}/{html}.html";
        String LIST_CAMPAIGNS =                         "/listCampaign.html";
        String CREATE_CAMPAIGN =                        "/createCampaign";
        String RULE =                                   "/createCampaign/rule";
        String SAVE =                                   "/createCampaign/save";
        String UPLOAD_IMG =                             "/createCampaign/upload/{numOpenRule}/{numResponse}";

        String MODIFY_CAMPAIGN =                        "/modifyCampaign/{idAd}";
        String MODIFY_CAMPAIGN_STEP_1 =                 "/modifyCampaign/step1";
        String MODIFY_CAMPAIGN_STEP_2 =                 "/modifyCampaign/step2";
        String MODIFY_CAMPAIGN_STEP_3 =                 "/modifyCampaign/step3";
        String MODIFY_CAMPAIGN_STEP_4 =                 "/modifyCampaign/step4";

        String MODIFY =                                 "/modifyCampaign/save";
        String UPLOAD =                                 "/uploadAttachement/{fileId}";
        String REGISTRATION_AGENCY_ADMIN_CONFIRM =      "/confirmAgencyAdmin/{crypt}/{code}";
        String REGISTRATION_AGENCY_USER_CONFIRM =      "/confirmAgencyUser/{crypt}/{code}";

        String REGISTRATION_MEDIA_USER_CONFIRM =      "/confirmMediaUser/{crypt}/{code}";
        String REGISTRATION_ADV_USER_CONFIRM =        "/confirmAdvUser/{crypt}/{code}";


        String SHOW_PASSPHRASE =                        "/media/passphrase";
        String SHOW_PASSPHRASE_GENERATE_NEW =           "/media/passhrase/generate";
        String SHOW_EXT_ID =                            "/media/extId";
        String SHOW_MY_SERVICES =                       "/media/myservices";
    }

    interface View{
        String HOME =                                   "home";

        String LOGIN =                                  "login";

        String ENROLL_FORM =                            "enroll";
        String ENROLLED_FORM =                          "enrolled";
        String REGISTRATION_BRAND_CONFIRM =             "enrollConfirmed";
        String REGISTRATION_AGENCY_ADMIN_CONFIRM =      "enroll/agency/adminConfirmed";
        String REGISTRATION_AGENCY_USER_CONFIRM =       "enroll/agency/userConfirmed";

        String REGISTRATION_MEDIA_CONFIRM =             "enroll/media/userCreated";
        String REGISTRATION_ADV_CONFIRM =               "enroll/adv/userCreated";

        String DASHBOARD_ADS =                          "manage/ads/show";
        String LIST_CAMPAIGNS =                         "campaigns";

        String CREATE_CAMPAIGN_STEP_0 =                 "manage/ads/partials/common/step0";
        String CREATE_CAMPAIGN_STEP_1=                  "manage/ads/partials/common/step1";
        String CREATE_CAMPAIGN_STEP_2=                  "manage/ads/partials/common/step2";
        String CREATE_CAMPAIGN_STEP_3 =                 "manage/ads/partials/common/step3";
        String CREATE_CAMPAIGN_STEP_4 =                 "manage/ads/partials/common/step4";

        String MODIFY_CAMPAIGN_STEP_1=                  "manage/ads/partials/common/step1";
        String MODIFY_CAMPAIGN_STEP_2=                  "manage/ads/partials/common/step2";
        String MODIFY_CAMPAIGN_STEP_3 =                 "manage/ads/partials/common/step3";
        String MODIFY_CAMPAIGN_STEP_4 =                 "manage/ads/partials/common/step4";

        String REGISTRATION_AGENCY_USER_CONFIRMED =     "enroll/agency/userCreated";
        String REGISTRATION_TIMEOUT =                   "enroll/common/timeout";
        String REGISTRATION_KO =                        "enroll/common/ko";


        String SHOW_PASSPHRASE =                        "/media/passphrase";
        String EXT_ID =                                 "/media/extId";
        String MY_SERVICES =                            "/media/service";
    }
}
