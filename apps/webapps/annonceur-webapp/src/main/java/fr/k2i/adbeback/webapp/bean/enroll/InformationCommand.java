package fr.k2i.adbeback.webapp.bean.enroll;

import fr.k2i.adbeback.core.business.user.LegalStatus;
import fr.k2i.adbeback.webapp.bean.AddressBean;
import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Data
public class InformationCommand implements Serializable{
    @NotEmpty(message = "error.agency.name.empty")
    @Size(max = 50, message = "error.size.50")
    private String name;

    private String siret;

    @NotNull(message = "error.agency.legalStatus.empty")
    private LegalStatus legalStatus;

    private String creationDate;

    private AddressBean address = new AddressBean();


    private String phone;



}
