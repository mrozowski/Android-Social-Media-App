package com.example.test_store.Home;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;


import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.test_store.Constants;
import com.example.test_store.Database.Database;
import com.example.test_store.Database.ResultDataListenerAdapter;
import com.example.test_store.Post.PostView;
import com.example.test_store.list.ItemDetails;
import com.example.test_store.list.MyRecyclerViewAdapter;
import com.example.test_store.list.VerticalSpaceItemDecoration;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.formats.MediaView;
import com.google.android.gms.ads.formats.UnifiedNativeAd;
import com.google.android.gms.ads.formats.UnifiedNativeAdView;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.example.test_store.R;

import java.util.ArrayList;

import static com.example.test_store.Constants.TAG;

public class HomePresenter extends ResultDataListenerAdapter implements MyRecyclerViewAdapter.ItemClickListener{
    private HomeView view;
    private Database database;
    MyRecyclerViewAdapter adapter;
    private UnifiedNativeAd nativeAd;

    private ArrayList<ItemDetails> postList;

    public HomePresenter(HomeView view) {
        database = new Database();
        database.setListener(this);
        database.getAllPost();
        this.view = view;
        postList = new ArrayList<>();

        //add initialize
        MobileAds.initialize(view.getContext(), new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {

            }
        });
        showAd();
    }

    //ca-app-pub-2476411962578956/5462698218 ca-app-pub-3940256099942544/2247696110
    private void showAd(){
        AdLoader.Builder builder = new AdLoader.Builder(view.getContext(), "ca-app-pub-3940256099942544/2247696110");

        builder.forUnifiedNativeAd(new UnifiedNativeAd.OnUnifiedNativeAdLoadedListener() {
            @Override
            public void onUnifiedNativeAdLoaded(UnifiedNativeAd unifiedNativeAd) {
                if(nativeAd!=null){
                    nativeAd = unifiedNativeAd;
                }
                UnifiedNativeAdView adView = (UnifiedNativeAdView) view.getActivity().getLayoutInflater().inflate(R.layout.native_ad_layout, null);
                populateNativeAd(unifiedNativeAd, adView);
                view.cardView.addView(adView);

                if(view.getActivity().isDestroyed())
                    nativeAd.destroy();
            }
        });

        AdLoader adLoader = builder.withAdListener(new AdListener(){
            @Override
            public void onAdFailedToLoad(LoadAdError loadAdError) {
                view.cardView.setVisibility(View.GONE);
                Log.d(TAG, "Failed to load ad");
                super.onAdFailedToLoad(loadAdError);
            }
        }).build();
        adLoader.loadAd(new AdRequest.Builder().build());

    }

    private void populateNativeAd(UnifiedNativeAd nativeAd, UnifiedNativeAdView adView){
        adView.setHeadlineView(adView.findViewById(R.id.ad_heading));
        adView.setAdvertiserView(adView.findViewById(R.id.ad_advertiser_name));
        adView.setBodyView(adView.findViewById(R.id.ad_body));
        adView.setStarRatingView(adView.findViewById(R.id.ad_star_rating));
        adView.setMediaView((MediaView) adView.findViewById(R.id.ad_media_view));
        adView.setCallToActionView(adView.findViewById(R.id.ad_call_to_action));
        adView.setIconView(adView.findViewById(R.id.ad_icon));

        adView.getMediaView().setMediaContent(nativeAd.getMediaContent());
        ((TextView)adView.getHeadlineView()).setText(nativeAd.getHeadline());

        if(nativeAd.getBody()==null){
            adView.getBodyView().setVisibility(View.INVISIBLE);
        }
        else{
            ((TextView)adView.getBodyView()).setText(nativeAd.getBody());
            adView.getBodyView().setVisibility(View.VISIBLE);
        }

        if(nativeAd.getAdvertiser() == null){
            adView.getAdvertiserView().setVisibility(View.INVISIBLE);
        }
        else{
            ((TextView)adView.getAdvertiserView()).setText(nativeAd.getAdvertiser());
            adView.getAdvertiserView().setVisibility(View.VISIBLE);
        }

        if(nativeAd.getStarRating() == null){
            adView.getStarRatingView().setVisibility(View.INVISIBLE);
        }
        else{
            ((RatingBar)adView.getStarRatingView()).setRating(nativeAd.getStarRating().floatValue());
            adView.getStarRatingView().setVisibility(View.VISIBLE);
        }

        if(nativeAd.getIcon() == null){
            adView.getIconView().setVisibility(View.INVISIBLE);
        }
        else{
            ((ImageView)adView.getIconView()).setImageDrawable(nativeAd.getIcon().getDrawable());
            adView.getIconView().setVisibility(View.VISIBLE);
        }

        if(nativeAd.getCallToAction() == null){
            adView.getCallToActionView().setVisibility(View.INVISIBLE);
        }
        else {
            ((Button)adView.getCallToActionView()).setText(nativeAd.getCallToAction());
            adView.getCallToActionView().setVisibility(View.VISIBLE);
        }

        adView.setNativeAd(nativeAd);
    }

    private void showPostList(){
        view.recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        view.recyclerView.addItemDecoration(new VerticalSpaceItemDecoration(20));

        adapter = new MyRecyclerViewAdapter(view.getContext(), postList);
        adapter.setClickListener(this);
        view.recyclerView.setAdapter(adapter);
        view.recyclerView.setNestedScrollingEnabled(false);
    }


    public void openPostFragment(String postID){
        PostView postFragment = new PostView();
        Bundle bundle = new Bundle();
        bundle.putString("postID", postID);
        postFragment.setArguments(bundle);

        view.getActivity().getSupportFragmentManager().beginTransaction()
                .replace(((ViewGroup)view.getView().getParent()).getId(), postFragment, "postFragment")
                .addToBackStack(view.getClass().getName())
                .commit();
    }

    @Override
    public void onItemClick(View view, int position) {
        String postID = adapter.getItem(position);
        openPostFragment(postID);
    }

    @Override
    public void onReceiveUserPostListListener(Task<QuerySnapshot> task) {
        if(task.getResult() != null){
            for(QueryDocumentSnapshot doc : task.getResult()){
                postList.add(new ItemDetails(doc.getString("title"),
                        "author",
                        doc.getString("category"),
                        doc.getLong("commentsCount").intValue(),
                        doc.getLong("likes").intValue(),
                        Constants.getDefaultDateFormat(doc.getDate("postDate")),
                        doc.getId()));
            }

            showPostList();
        }
        else{
            Log.d(TAG, "Couldn't load user posts list");
        }
    }

}
