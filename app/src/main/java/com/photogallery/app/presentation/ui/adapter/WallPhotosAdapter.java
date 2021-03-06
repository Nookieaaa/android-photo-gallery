package com.photogallery.app.presentation.ui.adapter;

import android.content.Context;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.photogallery.app.R;
import com.photogallery.app.data.model.Photo;
import com.photogallery.app.data.model.Wall;
import com.pnikosis.materialishprogress.ProgressWheel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class WallPhotosAdapter extends RecyclerView.Adapter<WallPhotosAdapter.PhotoViewHolder> {

    private Context context;
    private OnPhotoClickListener onPhotoClickListener;
    private List<Photo> data = new ArrayList<>();

    public WallPhotosAdapter(OnPhotoClickListener onPhotoClickListener) {
        this.onPhotoClickListener = onPhotoClickListener;
    }

    public void updateWallPhotos(Wall wall) {
        this.data.addAll(wall.getPhotos());
        this.notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public PhotoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (context == null) {
            context = parent.getContext();
        }
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_wall_photo_item, parent, false);
        return new PhotoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PhotoViewHolder holder, int position) {
        Photo photo = data.get(position);
        holder.bindPhotoImage(photo.getSmallImageUrl());
    }

    class PhotoViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_photo)
        AppCompatImageView ivPhotoItem;
        @BindView(R.id.pw_photo_item)
        ProgressWheel pwPhotoItem;

        PhotoViewHolder(final View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(view -> onPhotoClickListener.onPhotoClick(data.get(getAdapterPosition())));
        }

        void bindPhotoImage(String iconUrl) {
            pwPhotoItem.setVisibility(View.VISIBLE);
            Glide.with(context)
                    .load(iconUrl)
                    .centerCrop()
                    .listener(new RequestListener<String, GlideDrawable>() {
                        @Override
                        public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                            if (target.getRequest().isFailed()) {
                                target.getRequest().begin();
                            }
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                            pwPhotoItem.setVisibility(View.GONE);
                            return false;
                        }
                    })
                    .into(ivPhotoItem);
        }

    }
}
