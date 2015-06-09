package it.spot.android.animatorchain;

/**
 * A simple listener interface that allows to be notified
 * of the "start" and the "end" events of a chained animation item.
 *
 * @author a.rinaldi
 */
public interface IAnimatorChainListener {

    /**
     * Notifies that an animation item is about to start.
     *
     * @param position allows to identify the chained animation item
     */
    void onChainedItemStart(int position);

    /**
     * Notifies that an animation item is finished.
     *
     * @param position allows to identify the chained animation item
     */
    void onChainedItemEnd(int position);
}
