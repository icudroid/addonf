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






    public interface SignupController {
        interface View {
            String REGISTRATION_CONFIRM                                                 = "enroll/userCreated";
            String REGISTRATION_TIMEOUT                                                 = "enroll/timeout";
            String REGISTRATION_KO                                                      = "enroll/ko";
        }
        interface Path {


        }
    }

    public interface MyBorrowController {
        interface View {
            String NO_BORROWS                                                       = "borrow/no_borrows" ;
            String BORROW                                                           = "borrow/borrow";
            String LIST_BORROWS                                                     = "borrow/list";
        }
        interface Path {
            String MY_BORROW                                                        = "/myborrow.html";
            String HISTORY_BORROW_GAME                                              = "/historiesBorrowGame/{tr}";

        }
    }

    public interface MyCreditController {
        interface View {
            String SHOW                                                             = "credit";
        }
        interface Path {
            String MY_CREDIT                                                        = "/sold.html";
            String GET_CREDITS                                                      = "/getCredits";

        }

    }


    public interface MyPurchaseController {
        interface View {
            String SHOW                                                             = "purchase";
            String DETAIL                                                           = "purchaseDetailPopup";
        }
        interface Path {
            String MY_PURCHASE                                                      = "/purchase.html";
            String GET_PURCHASES                                                    = "/getPurchase";

            String DETAIL                                                           = "/purchase/{id}";
        }

    }

}
