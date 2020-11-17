package com.example.property.Adapter;


import android.content.Context;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.property.PropertyDetail;
import com.example.property.R;
import com.example.property.models.Plots;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import androidx.viewpager.widget.PagerAdapter;

public class SlidingImage_Adapter extends PagerAdapter {


    private ArrayList<Plots> IMAGES;
    private LayoutInflater inflater;
    private Context context;
    ArrayList<String> arrayList2 = new ArrayList<>();


    public SlidingImage_Adapter(Context context,ArrayList<Plots> IMAGES) {
        this.context = context;
        this.IMAGES=IMAGES;
        inflater = LayoutInflater.from(context);
    }

    public SlidingImage_Adapter(PropertyDetail context, ArrayList<String> arrayList2) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.arrayList2 = arrayList2;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return arrayList2.size();
    }

    @Override
    public Object instantiateItem(ViewGroup view, int position) {

        View imageLayout = inflater.inflate(R.layout.slidingimages_layout, view, false);

        Log.e("getImageUrl", arrayList2.get(position)+"at position" + position+"\n");
        Log.e("getImageUrl", arrayList2.size()+"");

        final ImageView imageView = imageLayout.findViewById(R.id.image);

        Picasso.get().load(String.valueOf(arrayList2.get(position))).into(imageView);

        view.addView(imageLayout, position);

        return imageLayout;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }

    @Override
    public void restoreState(Parcelable state, ClassLoader loader) {
    }

    @Override
    public Parcelable saveState() {
        return null;
    }


}
