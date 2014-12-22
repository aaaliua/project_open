
package com.tiger.quicknews.wedget.viewimage.Animations;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Interpolator;
import android.widget.RelativeLayout;

import com.tiger.quicknews.R;
import com.tiger.quicknews.wedget.viewimage.Indicators.PagerIndicator;
import com.tiger.quicknews.wedget.viewimage.SliderTypes.BaseSliderView;
import com.tiger.quicknews.wedget.viewimage.Transformers.AccordionTransformer;
import com.tiger.quicknews.wedget.viewimage.Transformers.BackgroundToForegroundTransformer;
import com.tiger.quicknews.wedget.viewimage.Transformers.BaseTransformer;
import com.tiger.quicknews.wedget.viewimage.Transformers.CubeInTransformer;
import com.tiger.quicknews.wedget.viewimage.Transformers.DefaultTransformer;
import com.tiger.quicknews.wedget.viewimage.Transformers.DepthPageTransformer;
import com.tiger.quicknews.wedget.viewimage.Transformers.FadeTransformer;
import com.tiger.quicknews.wedget.viewimage.Transformers.FlipHorizontalTransformer;
import com.tiger.quicknews.wedget.viewimage.Transformers.FlipPageViewTransformer;
import com.tiger.quicknews.wedget.viewimage.Transformers.ForegroundToBackgroundTransformer;
import com.tiger.quicknews.wedget.viewimage.Transformers.RotateDownTransformer;
import com.tiger.quicknews.wedget.viewimage.Transformers.RotateUpTransformer;
import com.tiger.quicknews.wedget.viewimage.Transformers.StackTransformer;
import com.tiger.quicknews.wedget.viewimage.Transformers.TabletTransformer;
import com.tiger.quicknews.wedget.viewimage.Transformers.ZoomInTransformer;
import com.tiger.quicknews.wedget.viewimage.Transformers.ZoomOutSlideTransformer;
import com.tiger.quicknews.wedget.viewimage.Transformers.ZoomOutTransformer;
import com.tiger.quicknews.wedget.viewimage.Tricks.FixedSpeedScroller;
import com.tiger.quicknews.wedget.viewimage.Tricks.InfinitePagerAdapter;
import com.tiger.quicknews.wedget.viewimage.Tricks.InfiniteViewPager;
import com.tiger.quicknews.wedget.viewimage.Tricks.ViewPagerEx;

import java.lang.reflect.Field;
import java.util.Timer;
import java.util.TimerTask;

/**
 * SliderLayout is compound layout. This is combined with
 * {@link com.example.androidimageslider.wedget.viewimage.Indicators.daimajia.slider.library.Indicators.PagerIndicator}
 * and
 * {@link com.example.androidimageslider.wedget.viewimage.Tricks.daimajia.slider.library.Tricks.ViewPagerEx}
 * . There is some properties you can set in XML: indicator_visibility visible
 * invisible indicator_shape oval rect indicator_selected_color
 * indicator_unselected_color indicator_selected_drawable
 * indicator_unselected_drawable pager_animation Default Accordion
 * Background2Foreground CubeIn DepthPage Fade FlipHorizontal FlipPage
 * Foreground2Background RotateDown RotateUp Stack Tablet ZoomIn ZoomOutSlide
 * ZoomOut pager_animation_span
 */
public class SliderLayout extends RelativeLayout {

    private Context mContext;
    /**
     * InfiniteViewPager is extended from ViewPagerEx. As the name says, it can
     * scroll without bounder.
     */
    private InfiniteViewPager mViewPager;

    /**
     * InfiniteViewPager adapter.
     */
    private SliderAdapter mSliderAdapter;

    /**
     * {@link com.example.androidimageslider.wedget.viewimage.Tricks.daimajia.slider.library.Tricks.ViewPagerEx}
     * indicator.
     */
    private PagerIndicator mIndicator;

    /**
     * A timer and a TimerTask using to cycle the
     * {@link com.example.androidimageslider.wedget.viewimage.Tricks.daimajia.slider.library.Tricks.ViewPagerEx}
     * .
     */
    private Timer mCycleTimer;
    private TimerTask mCycleTask;

    /**
     * For resuming the cycle, after user touch or click the
     * {@link com.example.androidimageslider.wedget.viewimage.Tricks.daimajia.slider.library.Tricks.ViewPagerEx}
     * .
     */
    private Timer mResumingTimer;
    private TimerTask mResumingTask;

    /**
     * If
     * {@link com.example.androidimageslider.wedget.viewimage.Tricks.daimajia.slider.library.Tricks.ViewPagerEx}
     * is Cycling
     */
    private boolean mCycling;

    /**
     * If auto recover after user touch the
     * {@link com.example.androidimageslider.wedget.viewimage.Tricks.daimajia.slider.library.Tricks.ViewPagerEx}
     */
    private boolean mAutoRecover;

    private int mTransformerId;

    /**
     * {@link com.example.androidimageslider.wedget.viewimage.Tricks.daimajia.slider.library.Tricks.ViewPagerEx}
     * transformer time span.
     */
    private int mTransformerSpan;

    private boolean mAutoCycle;

    /**
     * Visibility of
     * {@link com.example.androidimageslider.wedget.viewimage.Indicators.daimajia.slider.library.Indicators.PagerIndicator}
     */
    private PagerIndicator.IndicatorVisibility mIndicatorVisibility = PagerIndicator.IndicatorVisibility.Visible;

    /**
     * {@link com.example.androidimageslider.wedget.viewimage.Tricks.daimajia.slider.library.Tricks.ViewPagerEx}
     * 's transformer
     */
    private BaseTransformer mViewPagerTransformer;

    /**
     * @see com.example.androidimageslider.wedget.viewimage.Animations.daimajia.slider.library.Animations.BaseAnimationInterface
     */
    private BaseAnimationInterface mCustomAnimation;

    /**
     * {@link com.example.androidimageslider.wedget.viewimage.Indicators.daimajia.slider.library.Indicators.PagerIndicator}
     * shape, rect or oval.
     */

    public SliderLayout(Context context) {
        this(context, null);
    }

    public SliderLayout(Context context, AttributeSet attrs) {
        this(context, attrs, R.attr.SliderStyle);
    }

    public SliderLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mContext = context;
        LayoutInflater.from(context).inflate(R.layout.slider_layout, this, true);

        final TypedArray attributes = context.getTheme().obtainStyledAttributes(attrs,
                R.styleable.SliderLayout,
                defStyle, 0);

        mTransformerSpan = attributes.getInteger(R.styleable.SliderLayout_pager_animation_span,
                1100);
        mTransformerId = attributes.getInt(R.styleable.SliderLayout_pager_animation,
                Transformer.Default.ordinal());
        mAutoCycle = attributes.getBoolean(R.styleable.SliderLayout_auto_cycle, true);
        int visibility = attributes.getInt(R.styleable.SliderLayout_indicator_visibility, 0);
        for (PagerIndicator.IndicatorVisibility v : PagerIndicator.IndicatorVisibility.values()) {
            if (v.ordinal() == visibility) {
                mIndicatorVisibility = v;
                break;
            }
        }
        mSliderAdapter = new SliderAdapter(mContext);
        PagerAdapter wrappedAdapter = new InfinitePagerAdapter(mSliderAdapter);

        mViewPager = (InfiniteViewPager) findViewById(R.id.daimajia_slider_viewpager);
        mViewPager.setAdapter(wrappedAdapter);

        mViewPager.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getAction();
                switch (action) {
                    case MotionEvent.ACTION_UP:
                        recoverCycle();
                        break;
                }
                return false;
            }
        });

        attributes.recycle();
        setPresetIndicator(PresetIndicators.Center_Bottom);
        setPresetTransformer(mTransformerId);
        setSliderTransformDuration(mTransformerSpan, null);
        setIndicatorVisibility(mIndicatorVisibility);
        if (mAutoCycle) {
            startAutoCycle();
        }
    }

    public void setCustomIndicator(PagerIndicator indicator) {
        if (mIndicator != null) {
            mIndicator.destroySelf();
        }
        mIndicator = indicator;
        mIndicator.setIndicatorVisibility(mIndicatorVisibility);
        mIndicator.setViewPager(mViewPager);
        mIndicator.redraw();
    }

    public <T extends BaseSliderView> void addSlider(T imageContent) {
        mSliderAdapter.addSlider(imageContent);
    }

    public void startAutoCycle() {
        startAutoCycle(1000, 3400, true);
    }

    /**
     * start auto cycle.
     * 
     * @param delay delay time
     * @param period period time.
     * @param autoRecover
     */
    public void startAutoCycle(long delay, long period, boolean autoRecover) {
        mCycleTimer = new Timer();
        mAutoRecover = autoRecover;
        mCycleTask = new TimerTask() {
            @Override
            public void run() {
                mh.sendEmptyMessage(0);
            }
        };
        mCycleTimer.schedule(mCycleTask, delay, period);
        mCycling = true;
    }

    /**
     * pause auto cycle.
     */
    private void pauseAutoCycle() {
        if (mCycling) {
            mCycleTimer.cancel();
            mCycleTask.cancel();
            mCycling = false;
        } else {
            if (mResumingTimer != null && mResumingTask != null) {
                recoverCycle();
            }
        }
    }

    /**
     * stop the auto circle
     */
    public void stopAutoCycle() {
        if (mCycleTask != null) {
            mCycleTask.cancel();
        }
        if (mCycleTimer != null) {
            mCycleTimer.cancel();
        }
        if (mResumingTimer != null) {
            mResumingTimer.cancel();
        }
        if (mResumingTask != null) {
            mResumingTask.cancel();
        }
    }

    /**
     * when paused cycle, this method can weak it up.
     */
    private void recoverCycle() {

        if (!mAutoRecover) {
            return;
        }

        if (!mCycling) {
            if (mResumingTask != null && mResumingTimer != null) {
                mResumingTimer.cancel();
                mResumingTask.cancel();
            }
            mResumingTimer = new Timer();
            mResumingTask = new TimerTask() {
                @Override
                public void run() {
                    startAutoCycle();
                }
            };
            mResumingTimer.schedule(mResumingTask, 6000);
        }
    }

    private final android.os.Handler mh = new android.os.Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            mViewPager.nextItem();
        }
    };

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        int action = ev.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                pauseAutoCycle();
                break;
        }
        return false;
    }

    /**
     * set ViewPager transformer.
     * 
     * @param reverseDrawingOrder
     * @param transformer
     */
    public void setPagerTransformer(boolean reverseDrawingOrder, BaseTransformer transformer) {
        mViewPagerTransformer = transformer;
        mViewPagerTransformer.setCustomAnimationInterface(mCustomAnimation);
        mViewPager.setPageTransformer(reverseDrawingOrder, mViewPagerTransformer);
    }

    /**
     * set the duration between two slider changes.
     * 
     * @param period
     * @param interpolator
     */
    public void setSliderTransformDuration(int period, Interpolator interpolator) {
        try {
            Field mScroller = ViewPagerEx.class.getDeclaredField("mScroller");
            mScroller.setAccessible(true);
            FixedSpeedScroller scroller = new FixedSpeedScroller(mViewPager.getContext(),
                    interpolator, period);
            mScroller.set(mViewPager, scroller);
        } catch (Exception e) {

        }
    }

    /**
     * preset transformers and their names
     */
    public enum Transformer {
        Default("Default"),
        Accordion("Accordion"),
        Background2Foreground("Background2Foreground"),
        CubeIn("CubeIn"),
        DepthPage("DepthPage"),
        Fade("Fade"),
        FlipHorizontal("FlipHorizontal"),
        FlipPage("FlipPage"),
        Foreground2Background("Foreground2Background"),
        RotateDown("RotateDown"),
        RotateUp("RotateUp"),
        Stack("Stack"),
        Tablet("Tablet"),
        ZoomIn("ZoomIn"),
        ZoomOutSlide("ZoomOutSlide"),
        ZoomOut("ZoomOut");

        private final String name;

        private Transformer(String s) {
            name = s;
        }

        @Override
        public String toString() {
            return name;
        }

        public boolean equals(String other) {
            return (other == null) ? false : name.equals(other);
        }
    };

    /**
     * set a preset viewpager transformer by id.
     * 
     * @param transformerId
     */
    public void setPresetTransformer(int transformerId) {
        for (Transformer t : Transformer.values()) {
            if (t.ordinal() == transformerId) {
                setPresetTransformer(t);
                break;
            }
        }
    }

    /**
     * set preset PagerTransformer via the name of transforemer.
     * 
     * @param transformerName
     */
    public void setPresetTransformer(String transformerName) {
        for (Transformer t : Transformer.values()) {
            if (t.equals(transformerName)) {
                setPresetTransformer(t);
                return;
            }
        }
    }

    /**
     * Inject your custom animation into PageTransformer, you can know more
     * details in
     * {@link com.example.androidimageslider.wedget.viewimage.Animations.daimajia.slider.library.Animations.BaseAnimationInterface}
     * , and you can see a example in
     * {@link com.example.androidimageslider.wedget.viewimage.Animations.daimajia.slider.library.Animations.DescriptionAnimation}
     * 
     * @param animation
     */
    public void setCustomAnimation(BaseAnimationInterface animation) {
        mCustomAnimation = animation;
        if (mViewPagerTransformer != null) {
            mViewPagerTransformer.setCustomAnimationInterface(mCustomAnimation);
        }
    }

    /**
     * pretty much right? enjoy it. :-D
     * 
     * @param ts
     */
    public void setPresetTransformer(Transformer ts) {
        //
        // special thanks to https://github.com/ToxicBakery/ViewPagerTransforms
        //
        BaseTransformer t = null;
        switch (ts) {
            case Default:
                t = new DefaultTransformer();
                break;
            case Accordion:
                t = new AccordionTransformer();
                break;
            case Background2Foreground:
                t = new BackgroundToForegroundTransformer();
                break;
            case CubeIn:
                t = new CubeInTransformer();
                break;
            case DepthPage:
                t = new DepthPageTransformer();
                break;
            case Fade:
                t = new FadeTransformer();
                break;
            case FlipHorizontal:
                t = new FlipHorizontalTransformer();
                break;
            case FlipPage:
                t = new FlipPageViewTransformer();
                break;
            case Foreground2Background:
                t = new ForegroundToBackgroundTransformer();
                break;
            case RotateDown:
                t = new RotateDownTransformer();
                break;
            case RotateUp:
                t = new RotateUpTransformer();
                break;
            case Stack:
                t = new StackTransformer();
                break;
            case Tablet:
                t = new TabletTransformer();
                break;
            case ZoomIn:
                t = new ZoomInTransformer();
                break;
            case ZoomOutSlide:
                t = new ZoomOutSlideTransformer();
                break;
            case ZoomOut:
                t = new ZoomOutTransformer();
                break;
        }
        setPagerTransformer(true, t);
    }

    /**
     * Set the visibility of the indicators.
     * 
     * @param visibility
     */
    public void setIndicatorVisibility(PagerIndicator.IndicatorVisibility visibility) {
        if (mIndicator == null) {
            return;
        }

        mIndicator.setIndicatorVisibility(visibility);
    }

    public PagerIndicator.IndicatorVisibility getIndicatorVisibility() {
        if (mIndicator == null) {
            return mIndicator.getIndicatorVisibility();
        }
        return PagerIndicator.IndicatorVisibility.Invisible;

    }

    /**
     * get the
     * {@link com.example.androidimageslider.wedget.viewimage.Indicators.daimajia.slider.library.Indicators.PagerIndicator}
     * instance. You can manipulate the properties of the indicator.
     * 
     * @return
     */
    public PagerIndicator getPagerIndicator() {
        return mIndicator;
    }

    public enum PresetIndicators {
        Center_Bottom("Center_Bottom", R.id.default_center_bottom_indicator),
        Right_Bottom("Right_Bottom", R.id.default_bottom_right_indicator),
        Left_Bottom("Left_Bottom", R.id.default_bottom_left_indicator),
        Center_Top("Center_Top", R.id.default_center_top_indicator),
        Right_Top("Right_Top", R.id.default_center_top_right_indicator),
        Left_Top("Left_Top", R.id.default_center_top_left_indicator);

        private final String name;
        private final int id;

        private PresetIndicators(String name, int id) {
            this.name = name;
            this.id = id;
        }

        @Override
        public String toString() {
            return name;
        }

        public int getResourceId() {
            return id;
        }
    }

    public void setPresetIndicator(PresetIndicators presetIndicator) {
        PagerIndicator pagerIndicator = (PagerIndicator) findViewById(presetIndicator
                .getResourceId());
        setCustomIndicator(pagerIndicator);
    }

    private InfinitePagerAdapter getWrapperAdapter() {
        PagerAdapter adapter = mViewPager.getAdapter();
        if (adapter != null) {
            return (InfinitePagerAdapter) adapter;
        } else {
            return null;
        }
    }

    private SliderAdapter getRealAdapter() {
        PagerAdapter adapter = mViewPager.getAdapter();
        if (adapter != null) {
            return ((InfinitePagerAdapter) adapter).getRealAdapter();
        }
        return null;
    }

    /**
     * remove the slider at the position. Notice: It's a not perfect method, a
     * very small bug still exists.
     */
    public void removeSliderAt(int position) {
        if (getRealAdapter() != null) {
            getRealAdapter().removeSliderAt(position);
            mViewPager.setCurrentItem(mViewPager.getCurrentItem(), false);
        }
    }

    /**
     * remove all the sliders. Notice: It's a not perfect method, a very small
     * bug still exists.
     */
    public void removeAllSliders() {
        if (getRealAdapter() != null) {
            int count = getRealAdapter().getCount();
            getRealAdapter().removeAllSliders();
            // a small bug, but fixed by this trick.
            // bug: when remove adapter's all the sliders.some caching slider
            // still alive.
            mViewPager.setCurrentItem(mViewPager.getCurrentItem() + count, false);
        }
    }
}
