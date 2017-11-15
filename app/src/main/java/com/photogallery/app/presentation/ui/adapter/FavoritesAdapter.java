package com.photogallery.app.presentation.ui.adapter;

import android.support.v7.util.DiffUtil;
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
import com.pnikosis.materialishprogress.ProgressWheel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class FavoritesAdapter extends RecyclerView.Adapter<FavoritesAdapter.FavoritesViewHolder> {

    private List<Photo> data = new ArrayList<>();
    private OnPhotoClickListener onPhotoClickListener;

    public FavoritesAdapter(OnPhotoClickListener clickListener) {
        this.onPhotoClickListener = clickListener;
    }

    public void setData(List<Photo> newData) {
        DiffUtil.DiffResult result = DiffUtil.calculateDiff(new DiffUtil.Callback() {
            @Override
            public int getOldListSize() {
                return data.size();
            }

            @Override
            public int getNewListSize() {
                return newData.size();
            }

            @Override
            public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                return data.get(oldItemPosition).getBigImageUrl().equals(newData.get(newItemPosition).getBigImageUrl());
            }

            @Override
            public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                return data.get(oldItemPosition).getBigImageUrl().equals(newData.get(newItemPosition).getBigImageUrl());
            }
        }, true);
        result.dispatchUpdatesTo(this);
        data = newData;
    }


    @Override
    public FavoritesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_wall_photo_item, parent, false);
        return new FavoritesViewHolder(v);
    }

    @Override
    public void onBindViewHolder(FavoritesViewHolder holder, int position) {
        holder.bindModel(data.get(holder.getAdapterPosition()));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class FavoritesViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_photo)
        AppCompatImageView ivPhotoItem;
        @BindView(R.id.pw_photo_item)
        ProgressWheel pwPhotoItem;

        public FavoritesViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(v -> {
                if (onPhotoClickListener != null)
                    onPhotoClickListener.onPhotoClick(data.get(getAdapterPosition()));
            });
        }

        public void bindModel(Photo photo) {
            Glide.with(ivPhotoItem.getContext())
                    .load(photo.getLocalPath()!=null ? photo.getLocalPath() : photo.getBigImageUrl())
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
