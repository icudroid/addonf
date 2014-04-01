package fr.k2i.adbeback.webapp.state.enroll;

import lombok.Data;

import java.io.Serializable;


@Data
public class AgencyEnrollFlowState implements Serializable {
    private String idAgency;
    private EnrollFlow registration;

    public AgencyEnrollFlowState(){
        registration = EnrollFlow.AGENCY;
    }

}
