package kr.dkshin.android.pagingwithnetworkandroom.view.start

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import kr.dkshin.android.pagingwithnetworkandroom.R
import kr.dkshin.android.pagingwithnetworkandroom.view.main.MainActivity

class StartActivity : AppCompatActivity() {

    private var mDelayHandler: Handler? = null
    private var mTimeBeforeDelay: Long = 0
    private val SPLASH_DELAY: Long = 3000 //3 seconds

    internal val mRunnable: Runnable = Runnable {
        if (!isFinishing) {
            val intent = Intent(applicationContext, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                    WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
        }
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)
        //Initialize the Handler
        mDelayHandler = Handler()
    }

    override fun onResume() {
        super.onResume()
        // The first time mTimeBeforeDelay will be 0.
        var gapTime = System.currentTimeMillis() - mTimeBeforeDelay
        if (gapTime > SPLASH_DELAY) {
            gapTime = SPLASH_DELAY
        }
        mDelayHandler!!.postDelayed(mRunnable, gapTime)
        // Save the time before the delay.
        mTimeBeforeDelay = System.currentTimeMillis()
    }

    override fun onPause() {
        super.onPause()
        if (mDelayHandler != null) {
            mDelayHandler!!.removeCallbacks(mRunnable)
        }
    }

}

