package com.exampl.grillmaster.android

import android.os.Bundle

import com.badlogic.gdx.backends.android.AndroidApplication
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration
import com.exampl.grillmaster.GrillMaster

/** Launches the Android application. */
class AndroidLauncher : AndroidApplication() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initialize(GrillMaster(), AndroidApplicationConfiguration().apply {
            // Configure your application here.
        })
    }
}
