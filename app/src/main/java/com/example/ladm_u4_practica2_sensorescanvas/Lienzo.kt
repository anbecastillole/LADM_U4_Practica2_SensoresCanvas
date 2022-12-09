package com.example.ladm_u4_practica2_sensorescanvas

import android.content.Context
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.view.View

class Lienzo(p: MainActivity): View(p), SensorEventListener {
    var bruja = BitmapFactory.decodeResource(resources,R.drawable.bruja1)
    var nube1 = BitmapFactory.decodeResource(resources,R.drawable.nube1b)
    var nube2 = BitmapFactory.decodeResource(resources,R.drawable.nube2b)
    var nube3 = BitmapFactory.decodeResource(resources,R.drawable.nube3b)
    var nube4 = BitmapFactory.decodeResource(resources,R.drawable.nube4b)
    var nube5 = BitmapFactory.decodeResource(resources,R.drawable.nube5b)
    var sol = BitmapFactory.decodeResource(resources,R.drawable.sol1)
    var luna = BitmapFactory.decodeResource(resources,R.drawable.luna1)
    var noche = false
    var xx=0f
    var yy=0f
    //senssores
    var SENSOR_SERVICE = Context.SENSOR_SERVICE
    var managerProximidad : SensorManager ?= null
    var sensorP: Sensor ?= null
    var managerAcelerometro : SensorManager ?= null
    var sensorA : Sensor ?= null

    init{
        proximidad()
        acelerometro()
    }


    fun proximidad() {
        managerProximidad = this.context.getSystemService(SENSOR_SERVICE) as SensorManager?
        sensorP = managerProximidad!!.getDefaultSensor(Sensor.TYPE_PROXIMITY)
        managerProximidad!!.getDefaultSensor(Sensor.TYPE_PROXIMITY)?.also {
            managerProximidad!!.registerListener(
                this,
                it,
                SensorManager.SENSOR_DELAY_FASTEST,
                SensorManager.SENSOR_DELAY_FASTEST
            )
        }
    }

   fun acelerometro() {
        managerAcelerometro  = this.context.getSystemService(SENSOR_SERVICE) as SensorManager?
        sensorA = managerAcelerometro!!.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        managerAcelerometro!!.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)?.also {
            managerAcelerometro!!.registerListener(
                this,
                it,
                SensorManager.SENSOR_DELAY_FASTEST,
                SensorManager.SENSOR_DELAY_FASTEST
            )
        }
    }

    override fun onSensorChanged(p0: SensorEvent?) {
        if(p0?.sensor?.type==Sensor.TYPE_PROXIMITY) {
            println("detecta prox")
            noche = p0.values[0] <= 1
            invalidate()
        }
        if(p0?.sensor?.type == Sensor.TYPE_ACCELEROMETER){
            println("detecta")
            xx=p0!!.values[0]*20
            yy=p0!!.values[1]*20
            invalidate()
        }

    }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {
        return
    }

    override fun onDraw(c: Canvas) {
        super.onDraw(c)
        var p = Paint()
        //dia, noche
        if(noche==false){ //dia
            p.setColor(Color.rgb(86,199,239))
            c.drawRect(0f,0f,1200f,2500f,p)
            //sol
            c.drawBitmap(sol,200f+xx,200f+yy,p)
        }else{ //noche
            p.setColor(Color.rgb(19,70,168))
            c.drawRect(0f,0f,1200f,2500f,p)
            //luna
            c.drawBitmap(luna,200f+xx,200f+yy,p)
        }
        //pintar
        c.drawBitmap(nube1,100f+xx,500f+yy,p)
        c.drawBitmap(nube2,300f+xx,1200f+yy,p)
        c.drawBitmap(nube3,20f+xx,1500f+yy,p)
        c.drawBitmap(nube4,350f+xx,300f+yy,p)
        c.drawBitmap(nube5,500f+xx,800f+yy,p)
        c.drawBitmap(bruja,250f,700f,p)


    }


}