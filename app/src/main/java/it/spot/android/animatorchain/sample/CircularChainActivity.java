package it.spot.android.animatorchain.sample;

import android.os.Bundle;
import android.view.View;

import it.spot.android.animatorchain.AnimatorChain;
import it.spot.android.animatorchain.AnimatorChainItem;

public class CircularChainActivity extends BaseActivity {

    private View mViewToAnimate;
    private AnimatorChain mChain;

    // region Activity life cycle

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_circular);

        this.mViewToAnimate = this.findViewById(R.id.cursor);
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (this.mChain == null) {
            this.mChain = new AnimatorChain();
            this.mChain
                    .chain(AnimatorChainItem.create().setDuration(200).setY(100).setX(200))
                    .chain(AnimatorChainItem.create().setDuration(200).setY(200).setX(300))
                    .chain(AnimatorChainItem.create().setDuration(200).setY(300).setX(200))
                    .chain(AnimatorChainItem.create().setDuration(200).setY(200).setX(100))
                    .enableRepeat(true)
                    .setRepeatCount(200)
                    .execute(this.mViewToAnimate);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (this.mChain != null) {
            this.mChain.cancel();
            this.mChain = null;
        }
    }

    // endregion
}
