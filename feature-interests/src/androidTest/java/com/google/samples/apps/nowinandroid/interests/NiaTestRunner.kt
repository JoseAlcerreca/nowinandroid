package com.google.samples.apps.nowinandroid.interests

import android.app.Application
import android.content.Context
import dagger.hilt.android.testing.HiltTestApplication
import com.karumi.shot.ShotTestRunner

class NiaTestRunner : ShotTestRunner() {
    override fun newApplication(cl: ClassLoader?, name: String?, context: Context?): Application {
        return super.newApplication(cl, HiltTestApplication::class.java.name, context)
    }
}
