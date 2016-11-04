package com.boxtrotstudio.fishing.core.game.entities

import com.boxtrotstudio.fishing.core.game.Entity
import com.boxtrotstudio.fishing.core.game.fishing.rods.BasicRod
import com.boxtrotstudio.fishing.utils.CosSineTable
import com.boxtrotstudio.fishing.utils.Global
import com.boxtrotstudio.fishing.utils.Vector2f

class Hook(var parent: BasicRod) : Entity("sprites/hook.png") {
    var inWater = false
    private var active = true

    private var ss = 0
    var tick = Global.RANDOM.nextInt()
    var startY = 0f
    var subtract = 0.4f
    var acceleration = 0f

    val mue = 0.3f

    override fun tick() {
        if (!active)
            return

        if (!inWater) {
            if (y < 180) {
                inWater = true
                velocity = Vector2f(velocity.x / 3f, velocity.y / 2.4f)
                rotationAcceleration = 0f
                rotationVelocity = 0f
                startY = y
                hasGravity(false)
            }
        } else {
            if (ss == 2) {
                var add = CosSineTable.getCos((tick * (1.0 / 16.0)).toInt())

                tick++

                y = (startY + add).toFloat()

                var netForce = 0f
                var N = -velocity.x

                for (i in parent.hookList.size - 1 downTo 0) {
                    val hook = parent.hookList[i]

                    if (hook == this || !hook.inWater || hook.ss != 2 || hook.scaleX == 0f)
                        continue

                    val m = scaleX * hook.scaleX
                    val r = x - hook.x

                    if (Math.abs(r) < 20f) {
                        val base = Math.max(scaleX, hook.scaleX)
                        val adder = Math.min(scaleX, hook.scaleX)
                        setScale(base + (adder * 0.08f))
                        hook.active = false
                        velocity.x = 0f
                        N = 0f
                        parent.removeHook(hook)
                        continue
                    }

                    var f = m/r

                    netForce += f
                }

                netForce += (mue * N)

                acceleration = netForce/scaleX

                if (acceleration == Float.POSITIVE_INFINITY || acceleration == Float.NEGATIVE_INFINITY)
                    acceleration = 0f

                acceleration *= 0.3f

            } else if (ss == 1) {
                y += 0.5f
                if (y >= 100) {
                    ss = 2
                }
            } else {
                velocity.y += subtract
                
                if (velocity.y > -0.5 && velocity.y < 0.5) {
                    ss = 1
                    velocity.y = 0f
                    hasGravity(false)
                }
            }

            velocity.x -= acceleration
        }

        super.tick()
    }
}