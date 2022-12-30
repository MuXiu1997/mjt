package com.github.muxiu1997.mjt.spring.extension

import com.github.muxiu1997.mjt.spring.BeanFactoryGetter

/**
 * This function provides a way to lazily inject a bean of the given type [T] into a variable.
 * The bean is retrieved from the BeanFactory, making use of reified type parameters and Kotlin's lazy delegation.
 * If a name is provided, it will get the bean with that specific name. Otherwise, it retrieves the bean by type.
 *
 * @param name Optional name of the bean if more than one exists of the same type [T].
 * @return a Lazy delegate to be used for property delegation, which fetches the bean when accessed for the first time.
 */
inline fun <reified T> autowired(name: String? = null): Lazy<T> = lazy(LazyThreadSafetyMode.SYNCHRONIZED) {
    if (name == null) {
        BeanFactoryGetter.getBeanFactory().getBean(T::class.java)
    } else {
        BeanFactoryGetter.getBeanFactory().getBean(name, T::class.java)
    }
}
