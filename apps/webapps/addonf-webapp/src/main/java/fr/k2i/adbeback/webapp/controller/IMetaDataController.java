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

        String MY_BORROW = "/myborrow.html";
        String HISTORY_BORROW_GAME = "/historiesBorrowGame/{tr}";
        String MY_CREDIT = "/sold.html";
        String GET_CREDITS = "/getCredits";
    }

    interface View{


        String REGISTRATION_CONFIRM                                                 = "enroll/userCreated";
        String REGISTRATION_TIMEOUT                                                 = "enroll/timeout";
        String REGISTRATION_KO                                                      = "enroll/ko";

        public interface MyBorrowController {
            String NO_BORROWS                                                       = "borrow/no_borrows" ;
            String BORROW                                                           = "borrow/borrow";
            String LIST_BORROWS                                                     = "borrow/list";
        }

        public interface MyCreditController {

            String SHOW                                                             = "credit";
        }
    }
}
