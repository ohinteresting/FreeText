package com.lltvcn.freefont.core.data

import com.lltvcn.freefont.core.annotation.Description
import java.util.*

/**
 * Created by zhaolei on 2017/10/18.
 */
class IndexParam<T> {
    enum class Rule {
        Normal, Revert, Random
    }

    @Description(name = "变化规则", cls = Rule::class)
    var rule: String? = null
    @kotlin.jvm.JvmField
    var datas: Array<T>? = null
    fun available(): Boolean {
        return datas != null && datas!!.isNotEmpty()
    }

    fun getDataByIndex(index: Int): T? {
        var index = index
        if (datas != null) {
            val length = datas!!.size
            return if (length == 1) {
                datas!![0]
            } else {
                when (Rule.valueOf(rule!!)) {
                    Rule.Random -> datas!![random.nextInt(
                        length
                    )]
                    Rule.Normal -> datas!![index % length]
                    Rule.Revert -> {
                        index = if (index / length % 2 == 0) {
                            length - index % length - 1
                        } else {
                            index % length
                        }
                        datas!![index]
                    }
                }
            }
        }
        return null
    }

    companion object {
        @Transient
        private val random = Random(1000)
    }
}