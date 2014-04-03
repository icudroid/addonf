package fr.k2i.adbeback.webapp.bean.enroll;

import fr.k2i.adbeback.webapp.state.enroll.EnrollFlow;
import lombok.Data;

import java.io.Serializable;

/**
 * User: dimitri
 * Date: 03/04/14
 * Time: 10:52
 * Goal:
 */
@Data
public class EnrollFlowState implements Serializable {
    protected String idAccount;
    protected EnrollFlow registration;
}
