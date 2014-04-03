package fr.k2i.adbeback.webapp.state.enroll;

import fr.k2i.adbeback.webapp.bean.enroll.EnrollFlowState;
import lombok.Data;


@Data
public class AdvEnrollFlowState extends EnrollFlowState {
    public AdvEnrollFlowState(){
        registration = EnrollFlow.ADV;
    }
}
