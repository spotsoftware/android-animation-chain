package it.spot.android.animatorchain;

import android.animation.TimeInterpolator;
import android.view.ViewPropertyAnimator;
import android.view.animation.LinearInterpolator;

/**
 * This class represents an animation item that can be chained
 * to others in a very simple way.<br/>
 * It allows to set up the most common properties for animation
 * and can be easily extended, if needed.<br/>
 * Many of its public methods allows chaining, for a more pleasant usage.
 *
 * @author a.rinaldi
 */
public class AnimatorChainItem {

    private static final int DEFAULT_DELAY = 0;
    private static final int DEFAULT_DURATION = 500;

    private boolean mHasTranslationX;
    private float mTranslationX;
    private boolean mHasTranslationY;
    private float mTranslationY;
    private boolean mHasRotation;
    private float mRotation;
    private boolean mHasRotationX;
    private float mRotationX;
    private boolean mHasRotationY;
    private float mRotationY;
    private boolean mHasScaleX;
    private float mScaleX;
    private boolean mHasScaleY;
    private float mScaleY;
    private boolean mHasX;
    private float mX;
    private boolean mHasY;
    private float mY;
    private boolean mHasAlpha;
    private float mAlpha;

    private int mDelay;
    private int mDuration;

    private TimeInterpolator mInterpolator;

    // region Construction

    /**
     * This is the constructor.<br/>
     * It's protected because a static factory method is preferable
     * for chaining methods calls.<br/>
     */
    protected AnimatorChainItem() {
        super();

        this.mDelay = DEFAULT_DELAY;
        this.mDuration = DEFAULT_DURATION;
        this.mInterpolator = new LinearInterpolator();
    }

    /**
     * A public static factory method which returns an instance
     * of {@link AnimatorChainItem}.
     *
     * @return an item instance
     */
    public static AnimatorChainItem create() {
        return new AnimatorChainItem();
    }

    // endregion

    // region Public methods

    /**
     * Applies the properties of the item to an instance of
     * {@link ViewPropertyAnimator} without starting it.<br/>
     * It wraps many of the {@link ViewPropertyAnimator} features.
     *
     * @param animator the {@link ViewPropertyAnimator} to set up
     * @return the animator itself, allowing chaining.
     */
    public ViewPropertyAnimator apply(ViewPropertyAnimator animator) {
        synchronized (this) {
            animator.setDuration(this.mDuration)
                    .setStartDelay(this.mDelay)
                    .setInterpolator(this.mInterpolator);

            if (this.mHasAlpha) {
                animator.alpha(this.mAlpha);
            }

            if (this.mHasY) {
                animator.y(this.mY);
            }

            if (this.mHasX) {
                animator.x(this.mX);
            }

            if (this.mHasRotation) {
                animator.rotation(this.mRotation);
            }

            if (this.mHasRotationX) {
                animator.rotationX(this.mRotationX);
            }

            if (this.mHasRotationY) {
                animator.rotationY(this.mRotationY);
            }

            if (this.mHasScaleX) {
                animator.scaleX(this.mScaleX);
            }

            if (this.mHasScaleY) {
                animator.scaleY(this.mScaleY);
            }

            if (this.mHasTranslationX) {
                animator.translationX(this.mTranslationX);
            }

            if (this.mHasTranslationY) {
                animator.translationY(this.mTranslationY);
            }

            return animator;
        }
    }

    public AnimatorChainItem setY(float y) {
        synchronized (this) {
            this.mHasY = true;
            this.mY = y;
            return this;
        }
    }

    public AnimatorChainItem setX(float x) {
        synchronized (this) {
            this.mHasX = true;
            this.mX = x;
            return this;
        }
    }

    public AnimatorChainItem setRotation(float rotation) {
        synchronized (this) {
            this.mHasRotation = true;
            this.mRotation = rotation;
            return this;
        }
    }

    public AnimatorChainItem setRotationX(float rotationX) {
        synchronized (this) {
            this.mHasRotationX = true;
            this.mX = rotationX;
            return this;
        }
    }

    public AnimatorChainItem setRotationY(float rotationY) {
        synchronized (this) {
            this.mHasRotationY = true;
            this.mX = rotationY;
            return this;
        }
    }

    public AnimatorChainItem setTranslationX(float translationX) {
        synchronized (this) {
            this.mHasTranslationX = true;
            this.mTranslationX = translationX;
            return this;
        }
    }

    public AnimatorChainItem setTranslationY(float translationY) {
        synchronized (this) {
            this.mHasTranslationY = true;
            this.mTranslationY = translationY;
            return this;
        }
    }

    public AnimatorChainItem setScaleX(float scaleX) {
        synchronized (this) {
            this.mHasScaleX = true;
            this.mScaleX = scaleX;
            return this;
        }
    }

    public AnimatorChainItem setScaleY(float scaleY) {
        synchronized (this) {
            this.mHasScaleY = true;
            this.mScaleY = scaleY;
            return this;
        }
    }

    public AnimatorChainItem setStartDelay(int delay) {
        synchronized (this) {
            this.mDelay = delay;
            return this;
        }
    }

    public AnimatorChainItem setDuration(int duration) {
        synchronized (this) {
            this.mDuration = duration;
            return this;
        }
    }

    public AnimatorChainItem setAlpha(float alpha) {
        synchronized (this) {
            this.mHasAlpha = true;
            this.mAlpha = alpha;
            return this;
        }
    }

    public AnimatorChainItem setInterpolator(TimeInterpolator interpolator) {
        synchronized (this) {
            this.mInterpolator = interpolator;
            return this;
        }
    }

    // endregion

}
