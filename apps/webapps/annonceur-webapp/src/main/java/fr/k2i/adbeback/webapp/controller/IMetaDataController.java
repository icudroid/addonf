package fr.k2i.adbeback.webapp.controller;

/**
 * User: dimitri
 * Date: 11/12/13
 * Time: 10:06
 * Goal:
 */
public interface IMetaDataController {

    interface PathUtils{
        String REDIRECT = "redirect:/";
        String CONTEXT_RELATIVE="contextRelative:";
    }

    interface Path{

        String ENROLL_FORM =                            "/signup.html";
        String REGISTRATION_CONFIRM =                   "/confirmEnroll/{crypt}/{code}";

        String CREATE_CAMPAIGN_STEP_1 =                 "/createCampaign/step1";
        String CREATE_CAMPAIGN_STEP_2 =                 "/createCampaign/step2";
        String CREATE_CAMPAIGN_STEP_3 =                 "/createCampaign/step3";

        String MANAGE_ADS_PARTIALS =                    "/manageAds/partials/{html}.html";
        String GET_ALL_ADS =                            "/manageAds/getAll";
        String MANAGE_ADS_PARTIALS_ACTION =             "/manageAds/partials/{action}/{html}.html";
        String LIST_CAMPAIGNS =                         "listCampaign.html";
        String CREATE_CAMPAIGN =                        "/createCampaign";
        String RULE =                                   "/createCampaign/rule";
        String SAVE =                                   "/createCampaign/save";
        String UPLOAD_IMG =                             "/createCampaign/upload/{numOpenRule}/{numResponse}";
    }

    interface View{
        String HOME =                                   "home";

        String LOGIN =                                  "login";

        String ENROLL_FORM =                            "enroll";
        String ENROLLED_FORM =                          "enrolled";
        String REGISTRATION_BRAND_CONFIRM =             "enrollConfirmed";
        String DASHBOARD_ADS =                          "manage/ads/show";
        String LIST_CAMPAIGNS =                         "campaigns";

        String CREATE_CAMPAIGN_STEP_1=                  "manage/ads/partials/create/step1";
        String CREATE_CAMPAIGN_STEP_2=                  "manage/ads/partials/create/step2";
        String CREATE_CAMPAIGN_STEP_3 =                 "manage/ads/partials/create/step3";
    }
}
