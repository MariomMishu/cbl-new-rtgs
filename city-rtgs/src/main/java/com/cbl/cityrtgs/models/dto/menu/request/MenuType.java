package com.cbl.cityrtgs.models.dto.menu.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MenuType {
    Menu,
    Item;

    public static MenuType getValue(String type){

        if(type.equalsIgnoreCase("MENU")){
            return MenuType.Menu;
        }
        else{
            return MenuType.Item;
        }
    }
}
