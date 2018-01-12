package com.iteast.refresh;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Build;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.TouchDelegate;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


public class ZhiboUIUtils {

    static Toast toast = null;

    /**
     * 动态替换文本并改变文本颜色 仅限制一种颜色
     */
    public static SpannableStringBuilder getSpannableStringBuilder(Context context, int resStrId, int resColorId,
                                                                   Object... args) {
        return getSpannableStringBuilder(context, context.getString(resStrId, args), resColorId, args);
    }

    /**
     * 动态替换文本并改变文本颜色 仅限制一种颜色
     */
    public static SpannableStringBuilder getSpannableStringBuilder(Context context, String resStr, int resColorId,
                                                                   Object... args) {

        SpannableStringBuilder spanStr = new SpannableStringBuilder(resStr);
        int i = -1;
        for (Object o : args) {
            if (o instanceof String) {
                String arg = (String) o;
                i = resStr.indexOf(arg, i + 1);
                spanStr.setSpan(new ForegroundColorSpan(context.getResources().getColor(resColorId)), i,
                        i + arg.length(), Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
            }
        }
        return spanStr;
    }


    public static void showDialog(Dialog dialog) {
        if (dialog != null && !dialog.isShowing())
            dialog.show();
    }

    public static void dismissDialog(Dialog dialog) {
        if (dialog != null && dialog.isShowing())
            dialog.dismiss();
    }

    public static boolean isActivityNormal(Activity activity) {
        return activity != null && !activity.isFinishing();
    }

    /**
     * 扩大view的点击区域
     *
     * @param view
     * @param top
     * @param bottom
     * @param left
     * @param right
     */
    public static void expandViewTouchDelegate(final View view, final int top, final int bottom, final int left,
                                               final int right) {

        ((View) view.getParent()).post(new Runnable() {
            @Override
            public void run() {
                Rect bounds = new Rect();
                view.setEnabled(true);
                view.getHitRect(bounds);

                bounds.top -= top;
                bounds.bottom += bottom;
                bounds.left -= left;
                bounds.right += right;

                TouchDelegate touchDelegate = new TouchDelegate(bounds, view);

                if (View.class.isInstance(view.getParent())) {
                    ((View) view.getParent()).setTouchDelegate(touchDelegate);
                }
            }
        });
    }

    /**
     * 跳转到新activity
     *
     * @param activity  老activity，用于调起startActivity()方法
     * @param intent    跳到新activity的intent
     * @param enterAnim 新activity的进入动画
     * @param exitAnim  老activity的退出动画
     */
    public static void startActivityWithAnim(Activity activity, Intent intent, int enterAnim, int exitAnim) {
        activity.startActivity(intent);
        activity.overridePendingTransition(enterAnim, exitAnim);
        activity = null;
    }

    /**
     * 结束activity
     *
     * @param activity  老activity，用于调起finish()方法
     * @param enterAnim 新activity的进入动画
     * @param exitAnim  老activity的退出动画
     */
    public static void finishActivityWithAnim(Activity activity, int enterAnim, int exitAnim) {
        activity.finish();
        activity.overridePendingTransition(enterAnim, exitAnim);
        activity = null;
    }

    /**
     * 跳转到新activity
     *
     * @param activity  老activity，startActivityForResult()方法
     *  @param nRequest
     * @param intent    跳到新activity的intent
     * @param enterAnim 新activity的进入动画
     * @param exitAnim  老activity的退出动画
     */
    public static void startActivityForResultWithAnim(Activity activity, int nRequest, Intent intent, int enterAnim, int exitAnim) {
        activity.startActivityForResult( intent, nRequest);
        activity.overridePendingTransition(enterAnim, exitAnim);
        activity = null;
    }


    /**
     * 获取当前屏幕截图，包含状态栏
     *
     * @param activity
     * @return
     */
    public static Bitmap snapShotWithStatusBar(Activity activity) {
        View view = activity.getWindow().getDecorView();
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        Bitmap bmp = view.getDrawingCache();
        int width = getScreenWidth(activity);
        int height = getScreenHeight(activity);
        Bitmap bp = null;
        bp = Bitmap.createBitmap(bmp, 0, 0, width, height);
        view.destroyDrawingCache();
        return bp;

    }

    /**
     * 获取当前屏幕截图，不包含状态栏
     *
     * @param activity
     * @return
     */
    public static Bitmap snapShotWithoutStatusBar(Activity activity) {
        View view = activity.getWindow().getDecorView();
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        Bitmap bmp = view.getDrawingCache();
        Rect frame = new Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
        int statusBarHeight = frame.top;

        int width = getScreenWidth(activity);
        int height = getScreenHeight(activity);
        Bitmap bp = null;
        bp = Bitmap.createBitmap(bmp, 0, statusBarHeight, width, height - statusBarHeight);
        view.destroyDrawingCache();
        return bp;

    }

    private static int s_screenWidth;
    private static int s_screenHeiht;

    // 屏幕高度
    public static int getScreenHeight(Activity context, boolean reload) {
        if (reload) {
            Display display = context.getWindowManager().getDefaultDisplay();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
                Point size = new Point();
                display.getSize(size);
                s_screenHeiht = size.y;
            }
            s_screenHeiht = display.getHeight();
        }
        return s_screenHeiht;
    }
    @SuppressWarnings("deprecation")
    @SuppressLint("NewApi")
    public static int getScreenHeight(Activity context) {
        if (s_screenHeiht == 0) {
            Display display = context.getWindowManager().getDefaultDisplay();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
                Point size = new Point();
                display.getSize(size);
                s_screenHeiht = size.y;
            }
            s_screenHeiht = display.getHeight();
        }
        return s_screenHeiht;
    }
    @SuppressWarnings("deprecation")
    @SuppressLint("NewApi")
    public static int getScreenWidth(Activity context, boolean reload) {
        if (reload) {
            Display display = context.getWindowManager().getDefaultDisplay();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
                Point size = new Point();
                display.getSize(size);
                s_screenWidth = size.x;
            }
            s_screenWidth = display.getWidth();
        }
        return s_screenWidth;
    }

    // 屏幕宽度
    @SuppressWarnings("deprecation")
    @SuppressLint("NewApi")
    public static int getScreenWidth(Activity context) {
        if (s_screenWidth == 0) {
            Display display = context.getWindowManager().getDefaultDisplay();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
                Point size = new Point();
                display.getSize(size);
                s_screenWidth = size.x;
            }
            s_screenWidth = display.getWidth();
        }
        return s_screenWidth;
    }

    public static int sp2px(Context context, float spValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        float ntextSize = spValue * (scale - 0.6f);
        return (int) ntextSize;
    }

    // dp to px
    public static int dip2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    // px to dp
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 获得最小移动距离 后控件移动方向
     *
     * @param context
     * @return
     */
    public static int getTouchSlop(Context context) {
        final ViewConfiguration configuration = ViewConfiguration.get(context);
        return configuration.getScaledTouchSlop();
    }



    /**
     * 销毁toast
     */
    public static void cancelToast() {
        if (toast != null) {
            toast.cancel();
            toast = null;
        }
    }

    /**
     * 带List选项的Dialog; LYL
     *
     * @param context
     *            上下文；
     * @param title
     *            标题；
     * @param dataList
     *            list数据；
     * @param callBack
     *            回调函数 （点击的是第几个）；
     */
    /*
     * public static void showListDialog(final Context context, String title,
	 * final List<String> dataList, final ListDialogCallBack callBack) { View
	 * mListDialogView =
	 * LayoutInflater.from(context).inflate(R.layout.dialog_list, null);
	 * TextView tv_title = (TextView)
	 * mListDialogView.findViewById(R.id.mTV_listDialog_title); ListView
	 * lv_content = (ListView)
	 * mListDialogView.findViewById(R.id.mList_listDialog_Content);
	 * lv_content.setAdapter(new BaseAdapter() {
	 * 
	 * @Override public int getCount() { return dataList.size(); }
	 * 
	 * @Override public Object getItem(int position) { return
	 * dataList.get(position); }
	 * 
	 * @Override public long getItemId(int position) { return position; }
	 * 
	 * @Override public View getView(int position, View convertView, ViewGroup
	 * parent) { View mListDialogItem =
	 * LayoutInflater.from(context).inflate(R.layout.item_listdialog, null);
	 * TextView tv = (TextView)
	 * mListDialogItem.findViewById(R.id.mTV_listDialog_item);
	 * tv.setText(dataList.get(position)); return mListDialogItem; } });
	 * tv_title.setText(title); AlertDialog.Builder builder = new
	 * AlertDialog.Builder(context); builder.setView(mListDialogView); final
	 * Dialog dialog = builder.show(); lv_content.setOnItemClickListener(new
	 * AdapterView.OnItemClickListener() {
	 * 
	 * @Override public void onItemClick(AdapterView<?> parent, View view, int
	 * position, long id) { callBack.choosedItem(position); if (dialog != null
	 * && dialog.isShowing()) { dialog.dismiss(); }
	 * 
	 * } });
	 * 
	 * }
	 */

    /**
     * 带选项的Dialog回调接口； LYL
     */
    public interface ListDialogCallBack {
        void choosedItem(int item);
    }

    /**
     * 带一个输入框的Dialog回调接口； LYL
     */
    public interface OneEditDialogCallBack {
        void inputString(String str);
    }
}
