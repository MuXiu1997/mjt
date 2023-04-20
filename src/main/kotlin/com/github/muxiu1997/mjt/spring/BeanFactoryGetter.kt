package com.github.muxiu1997.mjt.spring

import org.springframework.beans.BeansException
import org.springframework.beans.factory.ListableBeanFactory
import org.springframework.beans.factory.config.BeanFactoryPostProcessor
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory
import org.springframework.context.ApplicationContext
import org.springframework.context.ApplicationContextAware
import org.springframework.stereotype.Component

@Component
object BeanFactoryGetter : BeanFactoryPostProcessor, ApplicationContextAware {
    private var beanFactory: ConfigurableListableBeanFactory? = null
    private var applicationContext: ApplicationContext? = null

    @Throws(BeansException::class)
    override fun postProcessBeanFactory(beanFactory: ConfigurableListableBeanFactory) {
        this.beanFactory = beanFactory
    }

    override fun setApplicationContext(applicationContext: ApplicationContext) {
        this.applicationContext = applicationContext
    }

    fun getBeanFactory(): ListableBeanFactory {
        return (beanFactory ?: applicationContext)!!
    }
}
