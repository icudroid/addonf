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

    }

    interface View{


        String REGISTRATION_CONFIRM                                                 = "enroll/userCreated";
        String REGISTRATION_TIMEOUT                                                 = "enroll/timeout";
        String REGISTRATION_KO                                                      = "enroll/ko";
    }
}
