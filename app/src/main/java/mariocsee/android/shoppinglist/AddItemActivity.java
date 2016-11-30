package mariocsee.android.shoppinglist;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import mariocsee.android.shoppinglist.data.Item;

/*
 * Created by mariocsee on 11/7/16.
 */

public class AddItemActivity extends AppCompatActivity {

    public static final String KEY_ITEM = "KEY_ITEM";
    private Item itemToEdit = null;
    private EditText etItem;
    private EditText etPrice;
    private EditText etDescription;
    private Spinner spCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);
        etItem = (EditText) findViewById(R.id.etItem);
        etPrice = (EditText) findViewById(R.id.etPrice);
        etDescription = (EditText) findViewById(R.id.etDescription);

        spCategory = (Spinner) findViewById(R.id.spCategories);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.categories_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spCategory.setAdapter(adapter);

        if (getIntent() != null
                && getIntent().hasExtra(MainActivity.KEY_ITEM_TO_EDIT)) {
            itemToEdit = (Item) getIntent().getSerializableExtra(MainActivity.KEY_ITEM_TO_EDIT);
            etItem.setText(itemToEdit.getItemTitle());
            etPrice.setText(itemToEdit.getItemPrice());
            etDescription.setText(itemToEdit.getItemDescription());
            spCategory.setSelection(itemToEdit.getItemCategory().getValue());
        }

        Button btnAdd = (Button) findViewById(R.id.btnAddItem);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etItem.getText().toString().equals("")) {
                    etItem.setError("There is no title");
                } else if (etPrice.getText().toString().equals("")) {
                    etPrice.setError("There is no price.");
                } else if (etDescription.getText().toString().equals("")) {
                    etDescription.setError("There is no description.");
                } else {
                    Intent result = new Intent();

                    Item newItem = itemToEdit;
                    if (newItem == null) {
                        newItem = new Item(etItem.getText().toString(), etPrice.getText().toString(),
                                etDescription.getText().toString(), false, Item.ItemCategory.fromInt(spCategory.getSelectedItemPosition()));
                    }
                    else {
                        newItem.setItemTitle(etItem.getText().toString());
                        newItem.setItemPrice(etPrice.getText().toString());
                        newItem.setItemDescription(etDescription.getText().toString());
                        newItem.setItemCategory(Item.ItemCategory.fromInt(spCategory.getSelectedItemPosition()));
                    }

                    result.putExtra(KEY_ITEM, newItem);
                    setResult(RESULT_OK, result);
                    finish();
                }
            }
        });

    }
}
