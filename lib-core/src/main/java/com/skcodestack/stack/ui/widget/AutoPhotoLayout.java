package com.skcodestack.stack.ui.widget;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.LinearLayoutCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.joanzapata.iconify.widget.IconTextView;
import com.skcodestack.stack.R;
import com.skcodestack.stack.delegates.LemonDelegate;
import com.skcodestack.stack.util.log.LemonLogger;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * Email  1562363326@qq.com
 * Github https://github.com/skcodestack
 * Created by sk on 2018/7/22
 * Version  1.0
 * Description:
 */

public class AutoPhotoLayout extends LinearLayoutCompat {

    private static final RequestOptions OPTIONS = new RequestOptions().centerCrop().dontAnimate().diskCacheStrategy(DiskCacheStrategy.ALL);

    private int mCurrentNum = 0;
    private int mMaxNum = 0;
    private int mMaxLineNum = 0;
    private IconTextView mIconAdd = null;
    private LayoutParams mParams = null;

    private int mDeleteId = 0;
    private AppCompatImageView mTargetImageView = null;
    private float mImageMargin = 0;
    private LemonDelegate mDelegate = null;
    private List<View> mLineView = null;
    private AlertDialog mDialog = null;
    private static final String ICON_TEXT = "{fa-plus}";
    private float mIconSize = 0;

    private static final List<List<View>> ALL_VIEWS = new ArrayList<>();
    private static final List<Integer> LINE_HEIGHTS = new ArrayList<>();


    //防止多次测量
    private boolean isOnceMeasure = false;
    private boolean isOnceLayout = false;

    public AutoPhotoLayout(Context context) {
        this(context, null);
    }

    public AutoPhotoLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AutoPhotoLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.camera_flow_layout);
        if (typedArray != null) {
            mMaxNum = typedArray.getInt(R.styleable.camera_flow_layout_max_count, 10);
            mImageMargin = typedArray.getDimension(R.styleable.camera_flow_layout_item_margin, 0);
            mMaxLineNum = typedArray.getInt(R.styleable.camera_flow_layout_line_count, 3);
            mIconSize = typedArray.getDimension(R.styleable.camera_flow_layout_icon_size, 20);
            typedArray.recycle();
        }
    }

    public final void onCropTarget(Uri uri) {
        createNewImageView();
        Glide.with(mDelegate)
                .load(uri)
                .apply(OPTIONS)
                .into(mTargetImageView);
    }

    private void createNewImageView() {
        mTargetImageView = new AppCompatImageView(getContext());
        mTargetImageView.setId(mCurrentNum);
        mTargetImageView.setLayoutParams(mParams);
        mTargetImageView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                //删除图片
                mDeleteId = v.getId();
                mDialog.show();
                Window window = mDialog.getWindow();
                if (window != null) {
                    window.setContentView(R.layout.dialog_img_click);
                    window.setGravity(Gravity.BOTTOM);
                    window.setWindowAnimations(R.style.anim_up_from_bottom);
                    window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    WindowManager.LayoutParams layoutParams = window.getAttributes();
                    layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
                    layoutParams.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
                    layoutParams.alpha = 1f;
                    window.setAttributes(layoutParams);
                    window.findViewById(R.id.dialog_btn_delete).setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //删除图片
                            final AppCompatImageView deleteImageView = (AppCompatImageView) findViewById(mDeleteId);
                            //图片
//                            AlphaAnimation animation = new AlphaAnimation(1,0);
//                            animation.setDuration(500);
//                            animation.setRepeatCount(0);
//                            animation.setFillAfter(true);
//                            animation.setStartOffset(0);
//                            deleteImageView.setAnimation(animation);
//
//                            animation.setAnimationListener(new Animation.AnimationListener() {
//                                @Override
//                                public void onAnimationStart(Animation animation) {
//
//                                }
//
//                                @Override
//                                public void onAnimationEnd(Animation animation) {
//                                    AutoPhotoLayout.this.removeView(deleteImageView);
//                                    mCurrentNum -=1;
//                                    if(mCurrentNum < mMaxNum){
//                                        mIconAdd.setVisibility(VISIBLE);
//                                    }
//
//                                }
//
//                                @Override
//                                public void onAnimationRepeat(Animation animation) {
//
//                                }
//                            });
//                            animation.start();

                            AutoPhotoLayout.this.removeView(deleteImageView);
                            mCurrentNum -= 1;
                            if (mCurrentNum < mMaxNum) {
                                mIconAdd.setVisibility(VISIBLE);
                            }
                            mDialog.cancel();
                        }
                    });
                    window.findViewById(R.id.dialog_btn_cancel).setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mDialog.cancel();
                        }
                    });
                }

            }
        });
        this.addView(mTargetImageView, mCurrentNum);
        mCurrentNum++;
        //当天家数目大于mMaxNum时，自动隐藏添加按钮
        if (mCurrentNum >= mMaxNum) {
            mIconAdd.setVisibility(View.GONE);
        }
    }

    private void initAdd() {
        mIconAdd = new IconTextView(getContext());
        mIconAdd.setText(ICON_TEXT);
        mIconAdd.setTextSize(mIconSize);
        mIconAdd.setGravity(Gravity.CENTER);
        mIconAdd.setTextColor(ContextCompat.getColor(getContext(), R.color.orange_dark));
        mIconAdd.setBackgroundResource(R.drawable.border_text);
        mIconAdd.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mDelegate.startCamera();
            }
        });
        this.addView(mIconAdd);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int sizeWidth = MeasureSpec.getSize(widthMeasureSpec);
        int modeWidth = MeasureSpec.getMode(widthMeasureSpec);
        int sizeHeight = MeasureSpec.getSize(heightMeasureSpec);
        int modeHeight = MeasureSpec.getMode(heightMeasureSpec);


        int width = 0;
        int height = 0;

        int lineWidth = 0;
        int lineHeight = 0;

        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View view = getChildAt(i);
            //测量
            measureChild(view, widthMeasureSpec, heightMeasureSpec);
            MarginLayoutParams marginLayoutParams = (MarginLayoutParams) view.getLayoutParams();
            //view 的宽度
            int childWidth = view.getMeasuredWidth() + marginLayoutParams.leftMargin + marginLayoutParams.rightMargin;
            int childHeight = view.getMeasuredHeight() + marginLayoutParams.topMargin + marginLayoutParams.bottomMargin;
            if (lineWidth + childWidth > sizeWidth - getPaddingLeft() - getPaddingRight()) {
                //换行
                width = Math.max(width, lineWidth);
                //重置行width  and height
                height += lineHeight;
                lineWidth = childWidth;
                lineHeight = childHeight;

            } else {
                lineWidth += childWidth;
                lineHeight = Math.max(lineHeight, childHeight);
            }
            if (i == childCount - 1) {
                width = Math.max(lineWidth, width);
                height += lineHeight;
            }
        }
        setMeasuredDimension(
                modeWidth == MeasureSpec.EXACTLY ? widthMeasureSpec : MeasureSpec.makeMeasureSpec(width + getPaddingLeft() + getPaddingRight(), MeasureSpec.EXACTLY),
                modeHeight == MeasureSpec.EXACTLY ? heightMeasureSpec : MeasureSpec.makeMeasureSpec(height + getPaddingTop() + getPaddingBottom(), MeasureSpec.EXACTLY));
        //设置一行所有图片的宽高
        int imageSideLen = (int) (((sizeWidth - getPaddingLeft() - getPaddingRight()) / mMaxLineNum) - mImageMargin);

        if (!isOnceMeasure) {
            mParams = new LayoutParams(imageSideLen, imageSideLen);
            isOnceMeasure = true;
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        ALL_VIEWS.clear();
        LINE_HEIGHTS.clear();

        int width = getWidth();
        int lineWidth = 0;
        int lineHeight = 0;

        if (!isOnceLayout) {
            mLineView = new ArrayList<>();
            isOnceLayout = true;
        }
        final int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            final View child = getChildAt(i);
            final MarginLayoutParams lp = (MarginLayoutParams) child.getLayoutParams();
            final int childWith = child.getMeasuredWidth();
            final int childHeight = child.getMeasuredHeight();
            if (lineWidth + childWith + lp.leftMargin + lp.rightMargin > width - getPaddingLeft() - getPaddingRight()) {
                LINE_HEIGHTS.add(lineHeight);
                ALL_VIEWS.add(mLineView);
                lineWidth = 0;
                lineHeight = childHeight + lp.topMargin + lp.bottomMargin;
                mLineView = new ArrayList<>();
            }
            lineWidth += childWith + lp.rightMargin + lp.leftMargin;
            lineHeight = Math.max(lineHeight, childHeight + lp.topMargin + lp.bottomMargin);
            mLineView.add(child);
        }
        LINE_HEIGHTS.add(lineHeight);
        ALL_VIEWS.add(mLineView);

        int left = getPaddingLeft();
        int top = getPaddingTop();

        int lineSize = ALL_VIEWS.size();
        for (int i = 0; i < lineSize; i++) {
            mLineView = ALL_VIEWS.get(i);
            lineHeight = LINE_HEIGHTS.get(i);
            int size = mLineView.size();
            for (int j = 0; j < size; j++) {
                View view = mLineView.get(j);
                if (view.getVisibility() == GONE) {
                    continue;
                }
                final MarginLayoutParams lp = (MarginLayoutParams) view.getLayoutParams();
                int lc = left + lp.leftMargin;
                int tc = top + lp.topMargin;
                int rc = (int) (lc + view.getMeasuredWidth() - mImageMargin);
                int bc = tc + view.getMeasuredHeight();

                view.layout(lc, tc, rc, bc);
                left += view.getMeasuredWidth() + lp.rightMargin + lp.leftMargin;
            }
            left = getPaddingLeft();
            top += lineHeight;
        }
        mIconAdd.setLayoutParams(mParams);
        isOnceLayout = false;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        initAdd();
        mDialog = new AlertDialog.Builder(getContext()).create();
    }

    public void setDelegate(LemonDelegate delegate) {
        this.mDelegate = delegate;
    }
}
