package fr.k2i.adbeback.webapp.bean;

import lombok.Data;

import java.util.List;

/**
 * User: dimitri
 * Date: 06/10/14
 * Time: 16:07
 * Goal:
 */
@Data
public class OrderDTO {
    private CompleteAddressDTO shipping;
    private CompleteAddressDTO billing;

    private List<BillingLineDTO> billingLines;

    private String mediaId;
    private String transactionId;

    private String currency;
    private Double globalAmountWithVAT;
    private Double globalAmountWithoutVAT;

    private String crc;
}
