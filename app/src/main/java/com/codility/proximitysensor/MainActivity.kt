@file:Suppress("DEPRECATION")

package com.codility.proximitysensor

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import android.view.WindowManager

class MainActivity : AppCompatActivity() {
    private var sensorManager: SensorManager? = null
    private var mProximitySensor: Sensor? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sensorManager = applicationContext.getSystemService(Context.SENSOR_SERVICE) as SensorManager?
        mProximitySensor = sensorManager!!.getDefaultSensor(Sensor.TYPE_PROXIMITY)
        if (mProximitySensor == null) {
            proximitySensor.text = "No Proximity Sensor!"
        } else {
            sensorManager!!.registerListener(proximitySensorEventListener, mProximitySensor, SensorManager.SENSOR_DELAY_NORMAL)
        }
    }

    private var proximitySensorEventListener = object : SensorEventListener {
        override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) {

        }

        override fun onSensorChanged(event: SensorEvent) {
            val params = this@MainActivity.window.attributes
            if (event.sensor.type == Sensor.TYPE_PROXIMITY) {

                if (event.values[0] == 0f) {
                    params.flags = params.flags or WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
                    params.screenBrightness = 0f
                    window.attributes = params
                    tvSensor.text = "Too Near"
                    tvSensor.setTextColor(resources.getColor(android.R.color.black))
                } else {
                    params.flags = params.flags or WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
                    params.screenBrightness = -1f
                    window.attributes = params
                    tvSensor.text = "Far Away"
                    tvSensor.setTextColor(resources.getColor(android.R.color.holo_red_dark))
                }
            }
        }
    }
}