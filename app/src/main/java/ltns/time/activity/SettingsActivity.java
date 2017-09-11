package ltns.time.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.kunzisoft.switchdatetime.SwitchDateTimeDialogFragment;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ltns.time.R;
import ltns.time.activity.base.BaseActivity;
import ltns.time.api.Config;
import ltns.time.network.bean.VersionBean;
import ltns.time.network.callback.CheckUpdateCallback;
import ltns.time.utils.AppUtils;
import ltns.time.utils.PreferencesUtils;

/**
 * Created by guyuepeng on 2017/5/10.
 * Email: gu.yuepeng@foxmail.com
 */

public class SettingsActivity extends BaseActivity {
    @BindView(R.id.tv_username)
    TextView mTvUsername;
    @BindView(R.id.tv_important_day)
    TextView mTvImportantDay;
    @BindView(R.id.iv_a)
    ImageView mIvA;
    @BindView(R.id.ll_a)
    LinearLayout mLlA;
    @BindView(R.id.iv_b)
    ImageView mIvB;
    @BindView(R.id.ll_b)
    LinearLayout mLlB;
    @BindView(R.id.iv_c)
    ImageView mIvC;
    @BindView(R.id.ll_c)
    LinearLayout mLlC;
    @BindView(R.id.iv_d)
    ImageView mIvD;
    @BindView(R.id.ll_d)
    LinearLayout mLlD;
    @BindView(R.id.iv_a1)
    ImageView mIvA1;
    @BindView(R.id.ll_a1)
    LinearLayout mLlA1;
    @BindView(R.id.iv_b1)
    ImageView mIvB1;
    @BindView(R.id.ll_b1)
    LinearLayout mLlB1;
    @BindView(R.id.iv_c1)
    ImageView mIvC1;
    @BindView(R.id.ll_c1)
    LinearLayout mLlC1;
    @BindView(R.id.iv_d1)
    ImageView mIvD1;
    @BindView(R.id.ll_d1)
    LinearLayout mLlD1;
    @BindView(R.id.tv_current_version)
    TextView mTvCurrentVersion;
    @BindView(R.id.tv_copyright)
    TextView mTvCopyright;
    @BindView(R.id.iv_e1)
    ImageView mIvE1;
    @BindView(R.id.ll_e1)
    LinearLayout mLlE1;
    private String[] typefaces = new String[]{
            Config.Typeface.QT
            , Config.Typeface.SD
            , Config.Typeface.XH
            , Config.Typeface.XY};

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_settings;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        initVersion();
    }

    private void initVersion() {
        mTvCurrentVersion.setText(getStrRes(R.string.versionStr) + AppUtils.getAppVersionName(mContext) + getStrRes(R.string.checkVersion));
    }


    /**
     * set Typeface and save the setting
     *
     * @param typefaceIndex
     */
    private void setTypeface(int typefaceIndex) {
        if (this.typeface.equals(typefaces[typefaceIndex]))
            return;
        this.typeface = typefaces[typefaceIndex];
        getTypefaceCheckView(typefaceIndex).setVisibility(View.VISIBLE);
        resetTypefaceChooseStatus();
        PreferencesUtils.put(mContext, Config.SharePreference.KEY_TYPEFACE, typeface);
        onTypefaceChange(typeface);
    }

    /**
     * 设置首页时间差显示的方式
     *
     * @param opinion
     */
    private void setDiffOpinion(int opinion) {
        if (this.diffOpinion == opinion)
            return;
        resetDiffStatus();
        getOpinionCheckView(opinion).setVisibility(View.VISIBLE);
        this.diffOpinion = opinion;
        PreferencesUtils.put(mContext, Config.SharePreference.KEY_DIFF_OPINION, opinion);
    }

    private void setUserName(String username) {
        PreferencesUtils.put(mContext, Config.SharePreference.KEY_USERNAME, username);
        initUsername();
    }

    private void setImportantDay(Calendar mC) {
        PreferencesUtils.saveCalendar(mContext, mC);
        initImportantDate();
    }

    @OnClick({R.id.tv_username, R.id.tv_important_day, R.id.ll_a, R.id.ll_b, R.id.ll_c, R.id.ll_d
            , R.id.ll_a1, R.id.ll_b1, R.id.ll_c1, R.id.ll_d1, R.id.ll_e1, R.id.tv_current_version})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_username:
                doChangeNick();
                break;
            case R.id.tv_important_day:
                doChangeDate();
                break;
            case R.id.ll_a:
                setTypeface(0);
                mIvA.setVisibility(View.VISIBLE);
                break;
            case R.id.ll_b:
                setTypeface(1);
                mIvB.setVisibility(View.VISIBLE);
                break;
            case R.id.ll_c:
                setTypeface(2);
                mIvC.setVisibility(View.VISIBLE);
                break;
            case R.id.ll_d:
                setTypeface(3);
                mIvD.setVisibility(View.VISIBLE);
                break;
            case R.id.ll_a1:
                setDiffOpinion(Config.DiffOpinion.DIFF_MONTH);
                break;
            case R.id.ll_b1:
                setDiffOpinion(Config.DiffOpinion.DIFF_WEEK);
                break;
            case R.id.ll_c1:
                setDiffOpinion(Config.DiffOpinion.DIFF_DAY);
                break;
            case R.id.ll_d1:
                setDiffOpinion(Config.DiffOpinion.DIFF_HOUR);
                break;
            case R.id.ll_e1:
                setDiffOpinion(Config.DiffOpinion.DIFF_MIN);
                break;
            case R.id.tv_current_version:
                doCheckVersion();
                break;
        }
    }

    private void doCheckVersion() {
        AppUtils.checkUpdate(mContext, new CheckUpdateCallback() {
            @Override
            public void onCheckFailed(Exception e) {
                Toast.makeText(mContext, getStrRes(R.string.getVersionError) + e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCheckSucceed(VersionBean mVersionBean) {
                String versionCodeStr = mVersionBean.getVersion();
                int versionCode = Integer.parseInt(versionCodeStr);
                AlertDialog.Builder mDialog = new AlertDialog.Builder(mContext);
                if (versionCode > AppUtils.getAppVersionCode(mContext)) {
                    mDialog.setTitle(getStrRes(R.string.confirmUpdate));
                    mDialog.setMessage(getStrRes(R.string.updateLog)
                            + (mVersionBean.getChangelog().length() == 0
                            ? getStrRes(R.string.defaultUpdateLog) : mVersionBean.getChangelog()));
                    mDialog.setPositiveButton(getStrRes(R.string.doUpdate), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            AppUtils.doUpdate(mContext);
                            dialog.dismiss();
                        }
                    });
                    mDialog.setNegativeButton(getStrRes(R.string.doNotUpdate), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                } else {
                    mDialog.setTitle(getStrRes(R.string.notNeedUpdateDialogTitle));
                    mDialog.setMessage(getStrRes(R.string.notNeedUpdateLog));
                    mDialog.setPositiveButton(getStrRes(R.string.notNeedUpdateConfirm), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                }
                mDialog.show();
            }
        });

    }

    private void doChangeDate() {
        //  https://github.com/Kunzisoft/Android-SwitchDateTimePicker
        // Initialize
        SwitchDateTimeDialogFragment dateTimeFragment = SwitchDateTimeDialogFragment.newInstance(
                getStrRes(R.string.dateDialogTitle),
                getStrRes(R.string.dateDialogConfirm),
                getStrRes(R.string.dateDialogCancel)
        );
        // Assign values
        dateTimeFragment.startAtCalendarView();
        dateTimeFragment.set24HoursMode(true);
        Calendar mC = Calendar.getInstance();
        dateTimeFragment.setMaximumDateTime(mC.getTime());
        mC = PreferencesUtils.readCalendar(mContext);
        dateTimeFragment.setDefaultDateTime(mC.getTime());

        // Set listener
        dateTimeFragment.setOnButtonClickListener(new SwitchDateTimeDialogFragment.OnButtonClickListener() {
            @Override
            public void onPositiveButtonClick(Date date) {
                // Date is get on positive button click
                Log.i("-->", date + "-?>-" + new Date());
                if (date.after(new Date())) {
                    Toast.makeText(mContext, getStrRes(R.string.dateOverNow), Toast.LENGTH_SHORT).show();
                    return;
                }
                Calendar mC = Calendar.getInstance();
                mC.setTime(date);
                setImportantDay(mC);
            }

            @Override
            public void onNegativeButtonClick(Date date) {
                // Date is get on negative button click
            }
        });

        // Show
        dateTimeFragment.show(getSupportFragmentManager(), "dialog_time");
    }

    private void doChangeNick() {
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(mContext);
        View dialogView = LayoutInflater.from(mContext).inflate(R.layout.dialog_set_username, null);
        final EditText usernameEt = (EditText) dialogView.findViewById(R.id.et_username);
        usernameEt.setText(username);
        usernameEt.setSelection(username.length());
        mBuilder.setTitle(getStrRes(R.string.how2CallU));
        mBuilder.setView(dialogView);
        mBuilder.setPositiveButton(getStrRes(R.string.usernameConfirm), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                setUserName(usernameEt.getText().toString());
                dialog.dismiss();
            }
        });
        mBuilder.setNegativeButton(getStrRes(R.string.usernameCancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        mBuilder.show();
    }

    private void resetTypefaceChooseStatus() {
        mIvA.setVisibility(View.INVISIBLE);
        mIvB.setVisibility(View.INVISIBLE);
        mIvC.setVisibility(View.INVISIBLE);
        mIvD.setVisibility(View.INVISIBLE);
    }

    private void resetDiffStatus() {
        mIvA1.setVisibility(View.INVISIBLE);
        mIvB1.setVisibility(View.INVISIBLE);
        mIvC1.setVisibility(View.INVISIBLE);
        mIvD1.setVisibility(View.INVISIBLE);
        mIvE1.setVisibility(View.INVISIBLE);
    }

    private View getOpinionCheckView(int opinion) {
        switch (opinion) {
            case Config.DiffOpinion.DIFF_MONTH:
                return mIvA1;
            case Config.DiffOpinion.DIFF_WEEK:
                return mIvB1;
            case Config.DiffOpinion.DIFF_DAY:
                return mIvC1;
            case Config.DiffOpinion.DIFF_HOUR:
                return mIvD1;
            case Config.DiffOpinion.DIFF_MIN:
                return mIvE1;
        }
        return null;
    }

    private View getTypefaceCheckView(int typefaceIndex) {
        switch (typefaceIndex) {
            case 0:
                return mIvA;
            case 1:
                return mIvB;
            case 2:
                return mIvC;
            case 3:
                return mIvD;
        }
        return null;
    }

    @Override
    protected void initOpinions() {
        super.initOpinions();
        getOpinionCheckView(diffOpinion).setVisibility(View.VISIBLE);
    }

    @Override
    protected void initTypeface() {
        super.initTypeface();
        int index = Arrays.asList(typefaces).indexOf(typeface);
        getTypefaceCheckView(index).setVisibility(View.VISIBLE);
    }

    @Override
    protected void initUsername() {
        super.initUsername();
        mTvUsername.setText(username);
    }

    @Override
    protected void initImportantDate() {
        super.initImportantDate();
        mTvImportantDay.setText(importantDate);
    }
}
