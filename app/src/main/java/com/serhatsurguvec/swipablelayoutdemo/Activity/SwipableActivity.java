package com.serhatsurguvec.swipablelayoutdemo.Activity;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.transition.Transition;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.serhatsurguvec.swipablelayout.SwipeableLayout;
import com.serhatsurguvec.swipablelayoutdemo.Model.Item;
import com.serhatsurguvec.swipablelayoutdemo.R;
import com.serhatsurguvec.swipablelayoutdemo.Widget.TouchImageView;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

/**
 * Created by serhatsurguvec on 10/14/15.
 */
public class SwipableActivity extends AppCompatActivity implements SwipeableLayout.OnLayoutCloseListener {

    // View name of the header image. Used for activity scene transitions
    public static final String VIEW_NAME_HEADER_IMAGE = "detail:header:image";

    private SwipeableLayout swipeableLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swipable);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            // Postpone the shared element enter transition.
            postponeEnterTransition();
        }

        int index;
        if (getIntent().hasExtra("index")) {
            index = getIntent().getIntExtra("index", -1);
        } else {
            throw new NullPointerException("You have to provide index of image");
        }

        swipeableLayout = (SwipeableLayout) findViewById(R.id.swipableLayout);
        ViewPager mViewPager = (ViewPager) findViewById(R.id.pager);
        swipeableLayout.setOnLayoutCloseListener(this);

        CustomPagerAdapter mCustomPagerAdapter = new CustomPagerAdapter(this);

        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mCustomPagerAdapter);

        mViewPager.setCurrentItem(index);
    }


    @Override
    public void onActivityReenter(int resultCode, Intent data) {
        super.onActivityReenter(resultCode, data);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            // Postpone the shared element enter transition.
            postponeEnterTransition();
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void scheduleStartPostponedTransition(final View sharedElement) {
        sharedElement.getViewTreeObserver().addOnPreDrawListener(
                new ViewTreeObserver.OnPreDrawListener() {
                    @Override
                    public boolean onPreDraw() {
                        sharedElement.getViewTreeObserver().removeOnPreDrawListener(this);
                        startPostponedEnterTransition();
                        return true;
                    }
                });
    }

    @Override
    public void OnLayoutClosed() {

        onBackPressed();

    }


    /**
     * Load the item's thumbnail image into our {@link ImageView}.
     */
    private void loadThumbnail(final ImageView mHeaderImageView, Item mItem) {
        Picasso.with(this)
                .load(mItem.getThumbnailUrl())
                .noFade()
                .into(mHeaderImageView, new Callback() {
                    @Override
                    public void onSuccess() {
                        scheduleStartPostponedTransition(mHeaderImageView);
                    }

                    @Override
                    public void onError() {

                    }
                });
    }

    /**
     * Load the item's full-size image into our {@link ImageView}.
     */
    private void loadFullSizeImage(ImageView mHeaderImageView, Item mItem) {
        Picasso.with(this)
                .load(mItem.getPhotoUrl())
                .noPlaceholder()
                .noFade()
                .into(mHeaderImageView);
    }


    class CustomPagerAdapter extends PagerAdapter {

        Context mContext;
        LayoutInflater mLayoutInflater;

        public CustomPagerAdapter(Context context) {
            mContext = context;
            mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {
            return Item.ITEMS.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == ((LinearLayout) object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {

            Item item = Item.ITEMS[position];

            View itemView = mLayoutInflater.inflate(R.layout.pager_item, container, false);

            final TouchImageView imageView = (TouchImageView) itemView.findViewById(R.id.imageView);

            imageView.setOnTouchImageViewListener(new TouchImageView.OnTouchImageViewListener() {
                @Override
                public void onMove() {
                    if (imageView.isZoomed()) {
                        swipeableLayout.lock();
                    } else {
                        swipeableLayout.unLock();
                    }
                }
            });

            /**
             * Set the name of the view's which will be transition to, using the static values above.
             * This could be done in the layout XML, but exposing it via static variables allows easy
             * querying from other Activities
             */
            ViewCompat.setTransitionName(imageView, VIEW_NAME_HEADER_IMAGE + ":" + position);


            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP && addTransitionListener(imageView, item)) {
                // If we're running on Lollipop and we have added a listener to the shared element
                // transition, load the thumbnail. The listener will load the full-size image when
                // the transition is complete.
                loadThumbnail(imageView, item);
            } else {
                // If all other cases we should just load the full-size image now
                loadFullSizeImage(imageView, item);
            }

            container.addView(itemView);

            return itemView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((LinearLayout) object);
        }
    }

    /**
     * Try and add a {@link Transition.TransitionListener} to the entering shared element
     * {@link Transition}. We do this so that we can load the full-size image after the transition
     * has completed.
     *
     * @param imageView
     * @param item
     * @return true if we were successful in adding a listener to the enter transition
     */
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private boolean addTransitionListener(final ImageView imageView, final Item item) {
        final Transition transition = getWindow().getSharedElementEnterTransition();

        if (transition != null) {
            // There is an entering shared element transition so add a listener to it
            transition.addListener(new Transition.TransitionListener() {
                @Override
                public void onTransitionEnd(Transition transition) {
                    // As the transition has ended, we can now load the full-size image
                    loadFullSizeImage(imageView, item);

                    // Make sure we remove ourselves as a listener
                    transition.removeListener(this);
                }

                @Override
                public void onTransitionStart(Transition transition) {
                    // No-op
                }

                @Override
                public void onTransitionCancel(Transition transition) {
                    // Make sure we remove ourselves as a listener
                    transition.removeListener(this);
                }

                @Override
                public void onTransitionPause(Transition transition) {
                    // No-op
                }

                @Override
                public void onTransitionResume(Transition transition) {
                    // No-op
                }
            });
            return true;
        }

        // If we reach here then we have not added a listener
        return false;
    }


}
