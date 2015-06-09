package it.spot.android.animatorchain.sample;

import android.os.Bundle;
import android.view.View;

import java.util.Timer;
import java.util.TimerTask;

import it.spot.android.animatorchain.AnimatorChain;
import it.spot.android.animatorchain.AnimatorChainItem;

public class DynamicChainActivity extends BaseActivity {

    private View mViewToAnimate;

    private AnimatorChain mChain;

    private long mCount;
    private Timer mTimer;
    private TimerTask mTimerTask;

    // region Activity life cycle

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_dynamic);

        this.mCount = 0;

        this.mViewToAnimate = this.findViewById(R.id.cursor);
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (this.mChain == null) {
            this.mChain = new AnimatorChain();
            this.mChain
                    .chain(AnimatorChainItem.create().setDuration(200).setY(300))
                    .chain(AnimatorChainItem.create().setDuration(400).setY(100))
                    .enableRepeat(true)
                    .setRepeatCount(200)
                    .execute(this.mViewToAnimate);

            this.mTimer = new Timer();
            this.mTimerTask = new TimerTask() {

                @Override
                public void run() {
                    mCount++;
                    if (mCount % 2 == 0) {
                        mChain.getChainedItems().get(1).setDuration(3000);
                    } else {
                        mChain.getChainedItems().get(1).setDuration(400);
                    }
                }
            };
            this.mTimer.scheduleAtFixedRate(this.mTimerTask, 200, 200);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (this.mChain != null) {
            this.mChain.cancel();
            this.mChain = null;
        }

        if (this.mTimer != null && this.mTimerTask != null) {
            this.mTimerTask.cancel();
            this.mTimerTask = null;

            this.mTimer.cancel();
            this.mTimer.purge();
            this.mTimer = null;
        }
    }

    // endregion
}
