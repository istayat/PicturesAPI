package com.example.athini.picturesapi.Controller;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import com.example.athini.picturesapi.R;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

/**
 * Created by athini on 2018/04/24.
 */

public class PictureAdapter extends RecyclerView.Adapter<PictureAdapter.ViewHolder> {
    private Context context;
    private List<Feed> feedList;

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView image_status;
        ViewHolder(View itemView) {
            super(itemView);
            image_status = itemView.findViewById(R.id.image_status);
        }
    }
    public PictureAdapter(Context context, List<Feed> feedList){
        this.context = context;
        this.feedList = feedList;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.feed, parent, false);
        return new ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final Feed feed = feedList.get(position);
        holder.image_status.setImageBitmap(getImage(feed.getImage()));
        holder.image_status.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater layoutInflater = (LayoutInflater) v.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                assert layoutInflater != null;
                View view = layoutInflater.inflate(R.layout.view_image, (ViewGroup) v.findViewById(R.id.popup));
                ImageView imageView =  view.findViewById(R.id.profile_picture);
                TextView close = view.findViewById(R.id.close);
                TextView name = view.findViewById(R.id.name);
                TextView date = view.findViewById(R.id.date);
                date.setText(feed.getDate());
                name.setText(feed.getName());
                if (null == getImage(feed.getContent())){
                    imageView.setImageBitmap(getImage(feed.getImage()));

                }else {
                    imageView.setImageBitmap(getImage(feed.getContent()));

                }
                final PopupWindow popupWindow = new PopupWindow(view, DrawerLayout.LayoutParams.MATCH_PARENT,DrawerLayout.LayoutParams.MATCH_PARENT,true);
                popupWindow.showAtLocation(view, Gravity.CENTER,0,0);
                close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        popupWindow.dismiss();
                    }
                });
            }
        });
    }
    public Bitmap getImage(String u){
        Bitmap bitmap = null;
        try {
            URL url = new URL(u);
            bitmap = BitmapFactory.decodeStream(url.openConnection().getInputStream());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }
    @Override
    public int getItemCount() {
        return feedList.size();
    }
}
