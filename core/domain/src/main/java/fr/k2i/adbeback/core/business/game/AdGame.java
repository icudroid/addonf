package fr.k2i.adbeback.core.business.game;

import javax.persistence.*;
import java.util.List;

@Entity
@DiscriminatorValue("AdGame")
public class AdGame extends AbstractAdGame {

	private static final long serialVersionUID = -4291589475824097583L;

    @Override
    public String toString() {
        return "AdGame";
    }
}

