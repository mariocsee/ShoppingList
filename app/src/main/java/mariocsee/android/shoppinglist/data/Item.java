package mariocsee.android.shoppinglist.data;

import com.orm.SugarRecord;

import java.io.Serializable;

import mariocsee.android.shoppinglist.R;

/**
 * Created by mariocsee on 11/6/16.
 */

public class Item extends SugarRecord implements Serializable {

    public enum ItemCategory {
        FOOD(0, R.drawable.food),
        CLOTHES(1, R.drawable.clothes),
        GADGETS(2, R.drawable.gadgets);

        private int value;
        private int iconId;

        private ItemCategory(int value, int iconId) {
            this.value = value;
            this.iconId = iconId;
        }

        public int getValue() {
            return value;
        }

        public int getIconId() {
            return iconId;
        }

        public static ItemCategory fromInt(int value) {
            for (ItemCategory p : ItemCategory.values()) {
                if (p.value == value) {
                    return p;
                }
            }
            return FOOD;
        }
    }

    private String itemTitle;
    private String itemPrice;
    private String itemDescription;
    private boolean isBought;
    private ItemCategory itemCategory;

    public Item() {

    }

    public Item(String itemTitle, String itemPrice, String itemDescription, boolean isBought, ItemCategory itemCategory) {
        this.itemTitle = itemTitle;
        this.itemPrice = itemPrice;
        this.itemDescription = itemDescription;
        this.isBought = isBought;
        this.itemCategory = itemCategory;
    }

    public String getItemTitle() {
        return itemTitle;
    }

    public String getItemPrice() {
        return itemPrice;
    }

    public String getItemDescription() {
        return itemDescription;
    }

    public boolean isBought() {
        return isBought;
    }

    public void setItemTitle(String itemTitle) {
        this.itemTitle = itemTitle;
    }

    public void setItemPrice(String itemPrice) {
        this.itemPrice = itemPrice;
    }

    public void setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
    }

    public boolean setBought(boolean bought) {
        return isBought = bought;
    }

    public ItemCategory getItemCategory() {
        return itemCategory;
    }

    public void setItemCategory(ItemCategory itemCategory) {
        this.itemCategory = itemCategory;
    }

}
