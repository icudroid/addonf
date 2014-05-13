package fr.k2i.adbeback.core.business.game;

public enum StatusGame {
	Win("OK"),Lost("KO"),Playing("KO");

    private String label;

    StatusGame(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
