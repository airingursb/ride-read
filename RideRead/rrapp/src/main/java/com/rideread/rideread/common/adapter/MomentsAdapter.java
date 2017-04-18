package com.rideread.rideread.common.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.rideread.rideread.R;
import com.rideread.rideread.common.base.BaseActivity;
import com.rideread.rideread.common.util.ImgLoader;
import com.rideread.rideread.common.util.ListUtils;
import com.rideread.rideread.common.util.ToastUtils;
import com.rideread.rideread.common.util.Utils;
import com.rideread.rideread.common.widget.NineGridImgView.NineGridImgView;
import com.rideread.rideread.common.widget.NineGridImgView.NineGridImgViewAdapter;
import com.rideread.rideread.data.result.Comment;
import com.rideread.rideread.data.result.DefJsonResult;
import com.rideread.rideread.data.result.Moment;
import com.rideread.rideread.data.result.MomentUser;
import com.rideread.rideread.data.result.ThumbsUpUser;
import com.rideread.rideread.function.net.retrofit.ApiUtils;
import com.rideread.rideread.function.net.retrofit.BaseCallback;
import com.rideread.rideread.function.net.retrofit.BaseModel;
import com.rideread.rideread.module.circle.view.ImagesActivity;
import com.rideread.rideread.module.circle.view.MomentDetailActivity;
import com.rideread.rideread.module.profile.view.UserMomentsActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.rideread.rideread.common.util.TimeUtils.getFriendlyTimeSpanByNow;
import static com.rideread.rideread.common.util.Utils.getString;

/**
 * Created by SkyXiao on 2017/4/9.
 */

public class MomentsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_HEADER = 0, TYPE_ITEM = 1, TYPE_FOOT = 2;
    private View headView;
    private View footView;
    private int headViewSize = 0;
    private int footViewSize = 0;
    private boolean isAddFoot = false;
    private boolean isAddHead = false;
    private LayoutInflater mLayoutInflater;
    private List<Moment> mMomentList;
    private BaseActivity mActivity;


    public MomentsAdapter(BaseActivity baseActivity, List<Moment> moments) {
        this.mActivity = baseActivity;
        this.mMomentList = moments;
        mLayoutInflater = LayoutInflater.from(Utils.getAppContext());
    }

    public void addHeadView(View view) {
        headView = view;
        headViewSize = 1;
        isAddHead = true;
    }

    public void addFootView(View view) {
        footView = view;
        footViewSize = 1;
        isAddFoot = true;
    }

    @Override
    public int getItemViewType(int position) {
        int type = TYPE_ITEM;
        if (headViewSize == 1 && position == 0) {
            type = TYPE_HEADER;
        } else if (footViewSize == 1 && position == getItemCount() - 1) {
            //最后一个位置
            type = TYPE_FOOT;
        }
        return type;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (TYPE_ITEM == viewType) {
            View v = mLayoutInflater.inflate(R.layout.view_moment, parent, false);
            return new MomentViewHolder(v);
        } else if (TYPE_HEADER == viewType) {
            return new HeadViewHolder(headView);
        } else {
            return new FootViewHolder(footView);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder tHolder, int position) {


        if (TYPE_ITEM == tHolder.getItemViewType()) {
            Moment moment = mMomentList.get(position);
            MomentUser user = moment.getUser();
            boolean isAttent = 0 == user.getIsFollowed();

            MomentViewHolder holder = (MomentViewHolder) tHolder;
            holder.mClMomentLayout.setOnClickListener(v -> {
                Bundle bundle = new Bundle();
                bundle.putSerializable(MomentDetailActivity.SELECTED_MOMENT, moment);
                bundle.putInt(MomentDetailActivity.USER_TYPE, isAttent ? MomentDetailActivity.USER_TYPE_ATTENTED : MomentDetailActivity.USER_TYPE_NEARBY);
                mActivity.gotoActivity(MomentDetailActivity.class, bundle);
            });


            ImgLoader.getInstance().displayImage(user.getFaceUrl(), holder.mImgAvatar);
            holder.mTvName.setText(user.getUsername());
            holder.mImgAvatar.setOnClickListener(v -> {
                Bundle bundle = new Bundle();
                bundle.putInt(UserMomentsActivity.SELECTED_UID, user.getUid());
                bundle.putString(UserMomentsActivity.SELECTED_USERNAME, user.getUsername());
                mActivity.gotoActivity(UserMomentsActivity.class, bundle);
            });

            String msg = moment.getMsg();
            if (!TextUtils.isEmpty(msg)) {
                holder.mTvMomentText.setText(msg);
                holder.mTvMomentText.setVisibility(View.VISIBLE);
            } else {
                holder.mTvMomentText.setVisibility(View.GONE);
            }

            List<String> pictures = moment.getPictures();
            if (!ListUtils.isEmpty(pictures)) {
                holder.mNineGridImgView.setAdapter(adapter);
                holder.mNineGridImgView.setImagesData(pictures);
                holder.mNineGridImgView.setVisibility(View.VISIBLE);
            } else {
                holder.mNineGridImgView.setVisibility(View.GONE);
            }


            holder.mBtnAttention.setBackgroundResource(isAttent ? R.drawable.icon_attented : R.drawable.icon_attention);
            holder.mBtnAttention.setOnClickListener(v -> {
                if (isAttent) {
                    ApiUtils.unfollow(user.getUid(), new BaseCallback<BaseModel<DefJsonResult>>() {
                        @Override
                        protected void onSuccess(BaseModel<DefJsonResult> model) throws Exception {
                            holder.mBtnAttention.setBackgroundResource(R.drawable.icon_attention);
                            user.setIsFollowed(-1);
                        }
                    });
                } else {
                    ApiUtils.follow(user.getUid(), new BaseCallback<BaseModel<DefJsonResult>>() {
                        @Override
                        protected void onSuccess(BaseModel<DefJsonResult> model) throws Exception {
                            holder.mBtnAttention.setBackgroundResource(R.drawable.icon_attented);
                            user.setIsFollowed(1);
                        }
                    });
                }
            });
            List<ThumbsUpUser> thumbsUp = moment.getThumbsUp();
            int likeCount = ListUtils.isEmpty(thumbsUp) ? 0 : thumbsUp.size();
            holder.mBtnLike.setOnClickListener(v -> ApiUtils.updateThumbsUp(moment.getMid(), new BaseCallback<BaseModel<DefJsonResult>>() {
                @Override
                protected void onSuccess(BaseModel<DefJsonResult> model) throws Exception {
                    ToastUtils.show("成功点赞");
                    holder.mTvLikeCount.setText((likeCount + 1) + "");
                }
            }));
            holder.mTvTime.setText(getFriendlyTimeSpanByNow(moment.getCreatedAt()));


            holder.mTvLikeCount.setText(likeCount + "");
            List<Comment> comments = moment.getComment();
            int commentSize = ListUtils.isEmpty(comments) ? 0 : comments.size();
            holder.mTvCommentCount.setText(commentSize + "");
            holder.mTvLocInfo.setText(moment.getMomentLocation() + " 距离我" + moment.getDistanceString());//TODO
            holder.setIsRecyclable(false);
        } else if (TYPE_HEADER == tHolder.getItemViewType()) {

        } else {
            //最后一个位置
        }
    }

    @Override
    public int getItemCount() {
        return mMomentList.size();
    }


    class MomentViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.cl_moment_layout) ConstraintLayout mClMomentLayout;
        @BindView(R.id.img_avatar) SimpleDraweeView mImgAvatar;
        @BindView(R.id.tv_name) TextView mTvName;
        @BindView(R.id.btn_attention) ImageButton mBtnAttention;
        @BindView(R.id.tv_time) TextView mTvTime;
        @BindView(R.id.tv_moment_text) TextView mTvMomentText;
        @BindView(R.id.nine_grid_img_view) NineGridImgView mNineGridImgView;
        @BindView(R.id.tv_loc_info) TextView mTvLocInfo;
        @BindView(R.id.btn_like) ImageButton mBtnLike;
        @BindView(R.id.tv_like_count) TextView mTvLikeCount;

        @BindView(R.id.tv_comment_count) TextView mTvCommentCount;

        public MomentViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    class HeadViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.btn_msg_tips) Button mBtnMsgTips;

        public HeadViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    class FootViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.btn_msg_tips) Button mBtnMsgTips;

        public FootViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }


    NineGridImgViewAdapter<String> adapter = new NineGridImgViewAdapter<String>() {
        @Override
        protected void onDisplayImage(Context context, SimpleDraweeView imageView, String url) {
            //            PicassoImgLoader.displayImage(imageView, url);
            ImgLoader.getInstance().displayImage(url, imageView);
        }

        @Override
        protected void onItemImageClick(Context context, SimpleDraweeView imageView, int index, List<String> list) {

            StringBuilder urls = new StringBuilder();
            for (int i = 0; i < list.size(); i++) {
                urls.append(list.get(i));
                urls.append(",");
            }
            urls.deleteCharAt(urls.length() - 1);

            Bundle bundle = new Bundle();
            bundle.putString(ImagesActivity.IMAGE_URLS, urls.toString());
            bundle.putInt(ImagesActivity.POSITION, index);

            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                mActivity.gotoActivity(ImagesActivity.class, bundle, false);
            } else {
                Intent intent = new Intent(mActivity, ImagesActivity.class);
                intent.putExtras(bundle);
                //5.0及以上系统实现共享元素动画
                ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(mActivity, imageView, getString(R.string.transition_image));
                ActivityCompat.startActivity(mActivity, intent, options.toBundle());
            }


        }
    };

}
