package com.boxtrotstudio.fishing.core.game.fishing.rods

import com.badlogic.gdx.graphics.Color
import com.boxtrotstudio.fishing.Fishing
import com.boxtrotstudio.fishing.core.game.entities.Hook
import com.boxtrotstudio.fishing.utils.Global
import com.boxtrotstudio.fishing.utils.Vector2f
import java.util.*

open class BasicRod : AutoRod() {

    val hookList = ArrayList<Hook>()
    override fun description(): String {
        return "Catches a fish every 10 seconds, with a 30% fail rate"
    }

    override fun dispose() {
        hookList.clear()
    }

    override fun getName(): String {
        return "Basic Rod"
    }

    override fun tick() {
        super.tick()

        if (hookList.size > 0) {
            val hook = hookList[0]
            toastLocation = hook.position.add(Vector2f(0f, (hook.height / 2f) * hook.scaleY))
        }
    }

    override fun load() {
        frequency = 5000
        failRate = 0.3
        minCatch = 1
        maxCatch = 3

        spawnHook()
    }

    override fun incrementalUpgrade() {
        minCatch++
        maxCatch += 3
        //failRate /= 1.123
        spawnHook()
    }

    protected fun spawnHook() {
        spawnHook(Color.WHITE)
    }

    protected fun spawnHook(color: Color) {
        val hook = Hook(this)
        hook.hasGravity(true)
        hook.setCenter(Global.rand(500f, 1200f), 780f)
        hook.velocity = Vector2f(0f, 0f)
        hook.color = color

        Fishing.GAME.spriteScene.addEntity(hook)

        hookList.add(hook)
    }

    public fun removeHook(hook: Hook) {
        Fishing.GAME.spriteScene.removeEntity(hook)
        hookList.remove(hook)
    }
}