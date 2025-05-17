package com.blueprinthell.model;

import java.util.Arrays;
import java.util.List;

public class Shop {
    private static final Shop instance = new Shop();

    private static final OAtar oAtar = OAtar.getInstance();
    private static final OAiryaman oAiryaman = OAiryaman.getInstance();
    private static final OAnahita oAnahita = OAnahita.getInstance();

    private static final List<ShopItem> items = Arrays.asList(oAtar, oAiryaman, oAnahita);

    private Shop() {

    }

    public static Shop getInstance() {
        return instance;
    }

    public List<ShopItem> getItems() {
        return items;
    }


}
