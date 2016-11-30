package mariocsee.android.shoppinglist.adapter;

/**
 * Created by mariocsee on 11/7/16.
 */

public interface ItemTouchHelperAdapter {

    void onItemDismiss(int position);

    void onItemMove(int fromPosition, int toPosition);

}
