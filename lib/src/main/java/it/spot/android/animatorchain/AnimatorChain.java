package it.spot.android.animatorchain;

import android.view.View;
import android.view.ViewPropertyAnimator;

import java.util.ArrayList;

/**
 * This class allows to chain more view property animations, avoiding the developer
 * to write messy and incomprehensible code.<br/>
 * It exposes all the most used features and can be easily extended. Right now it:
 * <ul>
 * <li>chains animations;</li>
 * <li>allows to repeat the entire chain;</li>
 * <li>allows to change properties of some chained animation at runtime.</li>
 * </ul>
 *
 * @author a.rinaldi
 */
public class AnimatorChain {

    private ArrayList<IAnimatorChainListener> mListeners;
    private ArrayList<AnimatorChainItem> mChainItems;

    private boolean mRepeat;
    private int mRepeatCount;
    private int mMaxRepeatCount;
    private int mCurrentChainItem;
    private ViewPropertyAnimator mCurrentViewPropertyAnimator;

    private View mTargetView;

    // region Construction

    public AnimatorChain() {
        super();

        this.mCurrentChainItem = -1;

        this.mChainItems = new ArrayList<AnimatorChainItem>();
        this.mListeners = new ArrayList<IAnimatorChainListener>();
    }

    // endregion

    // region Public methods

    /**
     * Starts the execution of all the chained animations, stopping
     * a previous execution if needed.
     *
     * @param targetView the view the animations will be applied to
     */
    public void execute(View targetView) {
        if (this.mTargetView != null) {
            this.cancel();
        }

        this.mTargetView = targetView;

        this.mRepeatCount = 0;
        this.mCurrentChainItem = 0;

        this.executeChainItem();
    }

    /**
     * Cancels the execution of the chain.
     */
    public void cancel() {
        if (this.mCurrentViewPropertyAnimator != null) {
            this.mCurrentViewPropertyAnimator.cancel();
            this.mCurrentViewPropertyAnimator = null;
        }

        if (this.mTargetView != null) {
            this.mTargetView.clearAnimation();
            this.mTargetView = null;
        }

        this.mCurrentChainItem = -1;
    }

    /**
     * Adds an animation to the chain, if not already present.
     *
     * @param item the {@link AnimatorChainItem} to add
     * @return the {@link AnimatorChain} itself, allowing chained method calls
     */
    public AnimatorChain chain(AnimatorChainItem item) {
        if (!this.mChainItems.contains(item)) {
            this.mChainItems.add(item);
        }
        return this;
    }

    /**
     * Removes an animation from the chain, if present.
     *
     * @param item the {@link AnimatorChainItem} to remove
     * @return the {@link AnimatorChain} itself, allowing chained method calls
     */
    public AnimatorChain unchain(AnimatorChainItem item) {
        if (this.mChainItems.contains(item)) {
            this.mChainItems.remove(item);
        }
        return this;
    }

    /**
     * Allows to get the list of all the chained animation items.
     *
     * @return the list
     */
    public ArrayList<AnimatorChainItem> getChainedItems() {
        return this.mChainItems;
    }

    /**
     * Enables, or disables, the repetition of the entire chain
     * when it comes to its end.
     *
     * @param repeat the {@code boolean} to enable/disable the repetition
     * @return the {@link AnimatorChain} itself, allowing chained method calls
     */
    public AnimatorChain enableRepeat(boolean repeat) {
        this.mRepeat = repeat;
        return this;
    }

    /**
     * Sets the maximum times the chain can be repeated.<br/>
     * Calling this method without enabling the repetition of the chain
     * through the {@link #enableRepeat(boolean)} will sort no effect.
     *
     * @param repeatCount the maximum times value
     * @return the {@link AnimatorChain} itself, allowing chained method calls
     */
    public AnimatorChain setRepeatCount(int repeatCount) {
        this.mMaxRepeatCount = repeatCount;
        return this;
    }

    /**
     * Registers a listener to the events triggered by the
     * execution of the chain, if not already present.
     *
     * @param listener the listener to add
     * @return the {@link AnimatorChain} itself, allowing chained method calls
     */
    public AnimatorChain registerListener(IAnimatorChainListener listener) {
        if (!this.mListeners.contains(listener)) {
            this.mListeners.add(listener);
        }
        return this;
    }

    /**
     * Unregisters a listener, if already present.
     *
     * @param listener the listener to remove
     * @return the {@link AnimatorChain} itself, allowing chained method calls
     */
    public void unregisterListener(IAnimatorChainListener listener) {
        if (this.mListeners.contains(listener)) {
            this.mListeners.remove(listener);
        }
    }

    // endregion

    // region Private methods

    /**
     * This method starts the execution of the current item of the chain.
     */
    private void executeChainItem() {
        AnimatorChainItem item = this.mChainItems.get(this.mCurrentChainItem);
        this.mCurrentViewPropertyAnimator = item.apply(this.mTargetView.animate())
                .withStartAction(this.mStartAction)
                .withEndAction(this.mEndAction);
        this.mCurrentViewPropertyAnimator.start();
    }

    // }

    // region Inner Runnables' implementations

    /**
     * A private runnable that centralizes the handling of the start of every
     * chained animation.<br/>
     * In the specific, it notifies the registered listeners so that they can
     * execute some synchronous code.
     */
    private Runnable mStartAction = new Runnable() {

        @Override
        public void run() {
            for (IAnimatorChainListener listener : mListeners) {
                listener.onChainedItemStart(mCurrentChainItem);
            }
        }
    };

    /**
     * A private runnable that centralizes the handling of the end of every
     * chained animation.<br/>
     * In the specific, it notifies the registered listeners so that they can
     * execute some synchronous code and setup the execution of the next chained item,
     * restarting the chain if needed and allowed.
     */
    private Runnable mEndAction = new Runnable() {

        @Override
        public void run() {
            for (IAnimatorChainListener listener : mListeners) {
                listener.onChainedItemEnd(mCurrentChainItem);
            }

            mCurrentChainItem++;

            if (mCurrentChainItem == mChainItems.size()) {
                mCurrentChainItem = 0;

                if (mRepeat && mRepeatCount < mMaxRepeatCount) {
                    mRepeatCount++;
                    executeChainItem();
                }
            } else {
                executeChainItem();
            }
        }
    };

    // endregion
}
