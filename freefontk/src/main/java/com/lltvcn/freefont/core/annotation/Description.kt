package com.lltvcn.freefont.core.annotation

import kotlin.reflect.KClass
import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy

/**
 * Created by zhaolei on 2017/10/16.
 */
@Target(AnnotationTarget.FIELD)
@Retention(RetentionPolicy.RUNTIME)
annotation class Description(val name: String, val cls: KClass<*> = Void::class)