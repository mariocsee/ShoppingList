package mariocsee.android.shoppinglist.adapter;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import mariocsee.android.shoppinglist.EditInterface;
import mariocsee.android.shoppinglist.R;
import mariocsee.android.shoppinglist.data.Item;

/**
 * Created by mariocsee on 11/6/16.
 */

public class
ItemRecyclerAdapter extends
        RecyclerView.Adapter<ItemRecyclerAdapter.ViewHolder>
        implements ItemTouchHelperAdapter {

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView ivCategory;
        private TextView tvItem;
        private TextView tvPrice;
        private CheckBox cbBought;
        private Button btnDeleteItem;
        private Button btnEditItem;

        public ViewHolder(View itemView) {
            super(itemView);
            ivCategory = (ImageView) itemView.findViewById(R.id.ivCategory);
            tvItem = (TextView) itemView.findViewById(R.id.tvItem);
            tvPrice = (TextView) itemView.findViewById(R.id.tvPrice);
            cbBought = (CheckBox) itemView.findViewById(R.id.cbBought);
            btnDeleteItem = (Button) itemView.findViewById(R.id.btnDeleteItem);
            btnEditItem = (Button) itemView.findViewById(R.id.btnEditItem);
        }
    }

    private List<Item> itemsList;
    private EditInterface editInterface;

    public ItemRecyclerAdapter(List<Item> itemsList, EditInterface editInterface) {
        this.editInterface = editInterface;
        this.itemsList = itemsList;

        // Test items for first run, doesn't save into sugarORM
        itemsList.add(new Item("Dreher Tall Boy", "300", "Good cold beer", false, Item.ItemCategory.FOOD));
        itemsList.add(new Item("Helly Hansen Parka", "100000", "Warm jacket", true, Item.ItemCategory.CLOTHES));
        itemsList.add(new Item("Google Pixel XL", "130000", "Best phone ever", false, Item.ItemCategory.GADGETS));
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemRow = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.item_row, parent, false);
        return new ViewHolder(itemRow);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.cbBought.setChecked(itemsList.get(holder.getAdapterPosition()).isBought());
        holder.tvItem.setText(itemsList.get(holder.getAdapterPosition()).getItemTitle());
        holder.tvPrice.setText(itemsList.get(holder.getAdapterPosition()).getItemPrice());
        holder.ivCategory.setImageResource(
                itemsList.get(position).getItemCategory().getIconId());

        holder.cbBought.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.cbBought.isChecked()) {
                    holder.cbBought.setChecked(itemsList.get(holder.getAdapterPosition()).setBought(true));
                    itemsList.get(holder.getAdapterPosition()).save();
                }
                else {
                    holder.cbBought.setChecked(itemsList.get(holder.getAdapterPosition()).setBought(false));
                    itemsList.get(holder.getAdapterPosition()).save();
                }
            }
        });

        holder.btnEditItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editInterface.showEditDialog(itemsList.get(holder.getAdapterPosition()), holder.getAdapterPosition());
            }
        });

        holder.btnDeleteItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemDismiss(holder.getAdapterPosition());
            }
        });
    }

    public void deleteAll() {
        int listLength = getItemCount();
        itemsList.clear();
        Item.deleteAll(Item.class);
        notifyItemRangeRemoved(0, listLength);
    }

    @Override
    public int getItemCount() {
        return itemsList.size();
    }

    @Override
    public void onItemDismiss(int position) {
        itemsList.get(position).delete();
        itemsList.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public void onItemMove(int fromPosition, int toPosition) {
        itemsList.add(toPosition, itemsList.get(fromPosition));
        itemsList.remove(fromPosition);
        notifyItemMoved(fromPosition, toPosition);
    }

    public void addItem(Item item) {
        item.save();
        itemsList.add(item);
        notifyDataSetChanged();
    }

    public void editItem(Item item, int position) {
        item.save();
        itemsList.set(position, item);
        notifyItemChanged(position);
    }
}
