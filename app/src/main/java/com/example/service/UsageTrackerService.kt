package com.example.service

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log

/**
 * Distraction Interception & Accountability Overlay Service
 * 
 * ARCHITECTURE OUTLINE:
 * 1. UsageStatsManager Polling:
 *    - A Coroutine or HandlerThread queries [android.app.usage.UsageStatsManager] every 1-2 seconds.
 *    - It requires the android.permission.PACKAGE_USAGE_STATS permission from the user.
 * 
 * 2. Distraction Detection Logic:
 *    - Calls usageStatsManager.queryEvents(beginTime, endTime) for the last 5 seconds.
 *    - Parses `UsageEvents.Event.ACTIVITY_RESUMED` events to determine the currently active foreground package.
 *    - Matches the foreground package against a known blocklist:
 *      val distractingApps = listOf("com.instagram.android", "com.zhiliaoapp.musically", "com.twitter.android")
 * 
 * 3. Milestone Validation:
 *    - Checks local Room Database (AppRepository) or a cached Boolean to verify if `current_milestone_completed` is true.
 * 
 * 4. Interception & Overlay Execution:
 *    - If a distracting app is in the foreground AND the milestone is NOT complete:
 *    - System launches `AccountabilityOverlayActivity.kt`:
 *      val overlayIntent = Intent(this, AccountabilityOverlayActivity::class.java).apply {
 *          addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP)
 *      }
 *      startActivity(overlayIntent)
 *    - NOTE: Since Android 10+ restricts background activity launches, the service might alternatively
 *      need the SYSTEM_ALERT_WINDOW permission to directly draw a Jetpack Compose WindowManager overlay over the screen.
 */
class UsageTrackerService : Service() {

    private var isTracking = false

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d("UsageTrackerService", "Distraction tracker started.")
        isTracking = true
        startTrackingLoop()
        return START_STICKY
    }

    private fun startTrackingLoop() {
        // Implementation Structure:
        /*
        coroutineScope.launch {
            while(isTracking) {
                val topPackage = getForegroundPackageViaUsageStats()
                val milestoneCompleted = checkRepositoryForMilestone()
                
                if (topPackage in blocklist && !milestoneCompleted) {
                    launchAccountabilityOverlay()
                }
                delay(1500L)
            }
        }
        */
    }

    override fun onDestroy() {
        super.onDestroy()
        isTracking = false
        Log.d("UsageTrackerService", "Distraction tracker stopped.")
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }
}
