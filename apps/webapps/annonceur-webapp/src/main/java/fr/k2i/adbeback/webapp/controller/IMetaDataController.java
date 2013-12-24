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
        String DASHBOARD_ADS =                          "/manageAds";
        String MANAGE_ADS_PARTIALS =                    "/manageAds/partials/{html}.html";
        String GET_ALL_ADS =                            "/manageAds/getAll";
        String MANAGE_ADS_PARTIALS_ACTION =             "/manageAds/partials/{action}/{html}.html";
        String CREATE_CAMPAIGN =                        "/manageAds/create";
        String SAVE_STEP =                              "/manageAds/saveStep/{step}";
    }

    interface View{
        String HOME =                                   "home";

        String LOGIN =                                  "login";

        String ENROLL_FORM =                            "enroll";
        String ENROLLED_FORM =                          "enrolled";
        String REGISTRATION_BRAND_CONFIRM =             "enrollConfirmed";
        String DASHBOARD_ADS =                          "manage/ads/show";
    }
}
