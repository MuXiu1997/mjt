package com.github.muxiu1997.mjt.utils

import com.github.muxiu1997.mjt.spring.BeanFactoryGetter
import org.springframework.context.expression.BeanFactoryResolver
import org.springframework.expression.ParserContext
import org.springframework.expression.common.TemplateParserContext
import org.springframework.expression.spel.standard.SpelExpressionParser
import org.springframework.expression.spel.support.StandardEvaluationContext

object SpelExpUtils {
    val parser = SpelExpressionParser()
    val beanResolver by lazy { BeanFactoryResolver(BeanFactoryGetter.getBeanFactory()) }

    val templateParserContext by lazy { TemplateParserContext() }

    @JvmStatic
    @JvmOverloads
    @JvmName("_parseExpression")
    inline fun <reified T> parseExpression(
        expressionString: String,
        parserContext: ParserContext? = null,
        contextBlock: StandardEvaluationContext.() -> Unit = {},
    ): T? {
        val context = StandardEvaluationContext()
        context.beanResolver = beanResolver
        contextBlock(context)
        return parser.parseExpression(expressionString, parserContext).getValue(context, T::class.java)
    }

    @JvmStatic
    @JvmOverloads
    inline fun parseExpression(
        expressionString: String,
        parserContext: ParserContext? = null,
        contextBlock: StandardEvaluationContext.() -> Unit = {},
    ): Any? {
        return parseExpression<Any>(expressionString, parserContext, contextBlock)
    }
}
