package fr.k2i.adbeback.core.business.goosegame;

/**
 * Created with IntelliJ IDEA.
 * User: dev
 * Date: 09/12/13
 * Time: 22:24
 * To change this template use File | Settings | File Templates.
 */
public enum GooseCaseType {
    AddPotGooseCase(5),
    DeadGooseCase(4),
    EndLevelGooseCase(3),
    JailGooseCase(6),
    JumpGooseCase(1),
    NoneGooseCase(8),
    ReductionGooseCase(2),
    StartLevelGooseCase(0),
    WinGooseCase(7);

    private int typeIhm;

    GooseCaseType(int typeIhm) {
        this.typeIhm = typeIhm;
    }

    static public GooseCaseType findByType(int typeIhm){
        for (GooseCaseType gooseCaseType : values()) {
            if(gooseCaseType.typeIhm == typeIhm){
                return gooseCaseType;
            }
        }
        return null;
    }


}
