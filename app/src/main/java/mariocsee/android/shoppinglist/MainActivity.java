package mariocsee.android.shoppinglist;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.List;

import mariocsee.android.shoppinglist.adapter.ItemRecyclerAdapter;
import mariocsee.android.shoppinglist.adapter.ItemTouchHelperCallback;
import mariocsee.android.shoppinglist.data.Item;

public class MainActivity extends AppCompatActivity implements EditInterface {

    public static final int REQUEST_CODE_ADD = 100;
    public static final String KEY_ITEM_TO_EDIT = "KEY_ITEM_TO_EDIT";
    public static final int REQUEST_CODE_EDIT = 101;
    private ItemRecyclerAdapter itemRecyclerAdapter;
    private RecyclerView recyclerItem;
    private int positionToEdit = -1;
    private Long idToEdit = null;

    private String itemText;
    private String priceText;
    private String descriptionText;
    public static final String KEY_ITEM = "KEY_ITEM";
    public static final String KEY_PRICE = "KEY_PRICE";
    public static final String KEY_DESCRIPTION = "KEY_DESCRIPTION";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupUI();
    }

    private void setupUI() {
        setupToolbar();
        setupFloatingActionButton();
        setupRecyclerView();
        // setupAddItem();
    }


    private void setupToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    private void setupRecyclerView() {
        recyclerItem = (RecyclerView) findViewById(
                R.id.recyclerItem);
        recyclerItem.setHasFixedSize(true);
        final LinearLayoutManager mLayoutManager =
                new LinearLayoutManager(this);
        recyclerItem.setLayoutManager(mLayoutManager);

        List<Item> itemsList = Item.listAll(Item.class);
        itemRecyclerAdapter = new ItemRecyclerAdapter(itemsList, this);

        ItemTouchHelper.Callback callback = new ItemTouchHelperCallback(itemRecyclerAdapter);
        ItemTouchHelper touchHelper = new ItemTouchHelper(callback);
        touchHelper.attachToRecyclerView(recyclerItem);

        recyclerItem.setAdapter(itemRecyclerAdapter);
    }


    private void setupFloatingActionButton() {
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fabAddItem);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentShowAdd = new Intent();
                intentShowAdd.setClass(MainActivity.this, AddItemActivity.class);
                startActivityForResult(intentShowAdd, REQUEST_CODE_ADD);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_CODE_ADD) {
                // add todo to the list
                Item newItem = (Item) data.getSerializableExtra(
                        AddItemActivity.KEY_ITEM);

                itemRecyclerAdapter.addItem(newItem);
                recyclerItem.scrollToPosition(0);
            } else if (requestCode == REQUEST_CODE_EDIT) {

                Item changedItem = (Item) data.getSerializableExtra(
                        AddItemActivity.KEY_ITEM);
                changedItem.setId(idToEdit);

                itemRecyclerAdapter.editItem(changedItem, positionToEdit);


            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_clear:
                itemRecyclerAdapter.deleteAll();
                break;
            case R.id.action_add_from_toolbar:
                Intent intentShowAdd = new Intent();
                intentShowAdd.setClass(MainActivity.this, AddItemActivity.class);
                startActivityForResult(intentShowAdd, REQUEST_CODE_ADD);
                break;
            default:
                break;
        }
        return true;
    }

    public void showEditDialog(Item itemToEdit, int position) {
        positionToEdit = position;
        idToEdit = itemToEdit.getId();
        Intent intentShowEdit = new Intent();
        intentShowEdit.setClass(MainActivity.this, AddItemActivity.class);
        intentShowEdit.putExtra(KEY_ITEM_TO_EDIT, itemToEdit);
        startActivityForResult(intentShowEdit, REQUEST_CODE_EDIT);
    }
}
