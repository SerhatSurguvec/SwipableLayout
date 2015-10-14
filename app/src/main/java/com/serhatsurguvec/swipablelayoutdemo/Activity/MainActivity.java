package com.serhatsurguvec.swipablelayoutdemo.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.serhatsurguvec.swipablelayoutdemo.Model.Item;
import com.serhatsurguvec.swipablelayoutdemo.R;
import com.squareup.picasso.Picasso;


public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_main);

        // Setup the GridView and set the adapter
        GridView mGridView = (GridView) findViewById(R.id.grid);
        mGridView.setOnItemClickListener(this);
        GridAdapter mAdapter = new GridAdapter();
        mGridView.setAdapter(mAdapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        return id == R.id.action_settings || super.onOptionsItemSelected(item);

    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
        // Construct an Intent as normal
        Intent intent = new Intent(this, SwipableActivity.class);
        intent.putExtra("index", position);

        ActivityOptionsCompat activityOptions = ActivityOptionsCompat.makeSceneTransitionAnimation(
                this,

                // Now we provide a list of Pair items which contain the view we can transitioning
                // from, and the name of the view it is transitioning to, in the launched activity
                new Pair<>(view.findViewById(R.id.imageview_item),
                        SwipableActivity.VIEW_NAME_HEADER_IMAGE + ":" + position));

        // Now we can start the Activity, providing the activity options as a bundle
        ActivityCompat.startActivity(this, intent, activityOptions.toBundle());

    }


    /**
     * {@link android.widget.BaseAdapter} which displays items.
     */
    private class GridAdapter extends BaseAdapter {

        @Override
        public int getCount() {

            return Item.ITEMS.length;
        }

        @Override
        public Item getItem(int position) {
            return Item.ITEMS[position];
        }

        @Override
        public long getItemId(int position) {
            return getItem(position).getId();
        }

        @Override
        public View getView(int position, View view, ViewGroup viewGroup) {
            if (view == null) {
                view = getLayoutInflater().inflate(R.layout.grid_item, viewGroup, false);
            }

            final Item item = getItem(position);
            // Load the thumbnail image
            final ImageView image = (ImageView) view.findViewById(R.id.imageview_item);
            Picasso.with(image.getContext()).load(item.getThumbnailUrl()).into(image);
            ViewCompat.setTransitionName(image, SwipableActivity.VIEW_NAME_HEADER_IMAGE + ":" + position);
            // Set the TextView's contents
            TextView name = (TextView) view.findViewById(R.id.textview_name);
            name.setText(item.getName());

            return view;
        }
    }
}
