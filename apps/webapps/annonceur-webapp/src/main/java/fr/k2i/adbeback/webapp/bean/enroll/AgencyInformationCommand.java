package fr.k2i.adbeback.webapp.bean.enroll;

import fr.k2i.adbeback.core.business.user.LegalStatus;
import fr.k2i.adbeback.webapp.bean.AddressBean;
import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.binding.message.MessageContext;
import org.springframework.binding.validation.ValidationContext;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;

@Data
public class AgencyInformationCommand implements Serializable{
    @NotEmpty(message = "error.agency.name.empty")
    @Size(max = 50, message = "error.size.50")
    private String name;

    private String siret;

    @NotNull(message = "error.agency.legalStatus.empty")
    private LegalStatus legalStatus;

    private Date creationDate = new Date();

    private AddressBean address = new AddressBean();

    @NotEmpty(message = "error.agency.email.empty")
    @Size(max = 60, message = "error.size.60")
    private String email;

    private String phone;



}
