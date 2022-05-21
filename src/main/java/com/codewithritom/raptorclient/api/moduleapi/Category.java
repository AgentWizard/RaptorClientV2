package com.codewithritom.raptorclient.api.moduleapi;

public enum Category {

    Movement("Movement", "Contains modules for improving movement"),
    Render("Render", "Contains modules for rendering improvements"),
    Misc("Misc", "Contains all modules not available in other categories"),
    Combat("Combat","Contains all modules for combat improvements");

    public final String name,description;
    public boolean visible = true;
    Category(String name,String description,boolean visible) {
        this.name = name;
        this.description = description;
        this.visible = visible;
    }

    Category(String name,String description) {
        this.name = name;
        this.description = description;
    }

}
