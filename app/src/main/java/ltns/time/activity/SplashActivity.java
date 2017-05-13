package ltns.time.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import ltns.time.R;
import ltns.time.activity.base.BaseActivity;

/**
 * Created by guyuepeng on 2017/5/10.
 * Email: gu.yuepeng@foxmail.com
 */

public class SplashActivity extends BaseActivity {
    private static final int MISS_LL = 1;
    private static final int START_NEW_AVTIVITY = 0;

    private static final int HANDLER_DELAY_TIME = 800;


    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MISS_LL:
                    miss(mLlLogo, 800, new Animation.AnimationListener() {
                        @Override
                        public void onAnimationStart(Animation animation) {

                        }

                        @Override
                        public void onAnimationEnd(Animation animation) {
                            mHandler.sendEmptyMessage(START_NEW_AVTIVITY);
                        }

                        @Override
                        public void onAnimationRepeat(Animation animation) {

                        }
                    });
                    break;
                case START_NEW_AVTIVITY:
                    Intent mIntent = new Intent(mContext, MainActivity.class);
                    startActivity(mIntent);
                    finish();
                    break;
            }
        }
    };
    @BindView(R.id.tv_content)
    TextView mTvContent;
    @BindView(R.id.logo)
    ImageView mLogo;
    @BindView(R.id.tv_soho)
    TextView mTvSoho;
    @BindView(R.id.ll_logo)
    LinearLayout mLlLogo;


    @Override
    protected int getLayoutRes() {
        return R.layout.activity_splash;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        startAnimation();
    }

    private void startAnimation() {
        show(mLogo, 600, new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                mLogo.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                show(mTvSoho, 800, new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {
                        mTvSoho.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        mHandler.sendEmptyMessageDelayed(MISS_LL, HANDLER_DELAY_TIME);
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }


    /**
     * textView的透明度变大
     *
     * @param view
     * @param animationListener
     */
    private void miss(View view, long time, Animation.AnimationListener animationListener) {
        Animation animation = new AlphaAnimation(1.0f, 0f);
        animation.setDuration(time);
        animation.setFillAfter(true);
        animation.setAnimationListener(animationListener);
        view.startAnimation(animation);

    }

    /**
     * textView的透明度变小
     *
     * @param view
     * @param animationListener
     */
    private void show(View view, long time, Animation.AnimationListener animationListener) {
        Animation animation = new AlphaAnimation(0f, 1.0f);
        animation.setDuration(time);
        animation.setFillAfter(true);
        animation.setAnimationListener(animationListener);
        view.startAnimation(animation);
    }

    @Override
    public void onBackPressed() {
    }
}
