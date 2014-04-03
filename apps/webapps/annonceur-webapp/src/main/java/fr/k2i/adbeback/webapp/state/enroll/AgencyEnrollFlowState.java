package fr.k2i.adbeback.webapp.state.enroll;

import fr.k2i.adbeback.webapp.bean.enroll.EnrollFlowState;
import lombok.Data;

import java.io.Serializable;


@Data
public class AgencyEnrollFlowState extends EnrollFlowState {
    public AgencyEnrollFlowState(){
        registration = EnrollFlow.AGENCY;
    }
}
