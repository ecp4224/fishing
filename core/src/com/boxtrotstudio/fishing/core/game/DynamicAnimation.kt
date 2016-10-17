package com.boxtrotstudio.fishing.core.game

import com.badlogic.gdx.utils.TimeUtils
import com.boxtrotstudio.fishing.Fishing
import com.boxtrotstudio.fishing.core.logic.Logical
import com.boxtrotstudio.fishing.utils.PFunction
import com.boxtrotstudio.fishing.utils.PRunnable

class DynamicAnimation(var action: PRunnable<Long>) : Logical {
    private var _startTime : Long = 0L
    private lateinit var _stopFun : PFunction<Void, Boolean>
    private var _ended : Runnable? = null
    private var disposed: Boolean = false

    public val hasEnded : Boolean
        get() = disposed

    public val elaspe: Long
        get() = TimeUtils.millis() - _startTime

    constructor(action: PRunnable<Long>, duration: Long) :this(action) {
        _startTime = TimeUtils.millis()
        _stopFun = PFunction {
            v ->
            TimeUtils.millis() - _startTime >= duration
        }
    }

    constructor(action: PRunnable<Long>, stop: PFunction<Void, Boolean>) :this(action) {
        _stopFun = stop
    }

    fun until(stop: PFunction<Void, Boolean>) : DynamicAnimation {
        _stopFun = stop
        return this
    }

    fun onEnded(end: Runnable) : DynamicAnimation {
        _ended = end
        return this
    }

    fun start() : DynamicAnimation {
        _startTime = TimeUtils.millis()
        Fishing.getInstance().addLogical(this)
        return this
    }

    fun end() : DynamicAnimation {
        Fishing.getInstance().removeLogical(this)
        disposed = true

        _ended?.run()

        return this
    }

    override fun tick() {
        if (hasEnded)
            return

        action.run(elaspe)

        if (_stopFun.run(null))
            end()
    }

    override fun dispose() { }
}
