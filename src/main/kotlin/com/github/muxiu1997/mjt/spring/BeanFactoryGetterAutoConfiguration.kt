package com.github.muxiu1997.mjt.spring

import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import

@Configuration
@Import(BeanFactoryGetter::class)
open class BeanFactoryGetterAutoConfiguration
