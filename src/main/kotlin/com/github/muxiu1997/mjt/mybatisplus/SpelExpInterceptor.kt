package com.github.muxiu1997.mjt.mybatisplus

import com.baomidou.mybatisplus.core.toolkit.PluginUtils
import com.baomidou.mybatisplus.extension.plugins.inner.InnerInterceptor
import com.github.muxiu1997.mjt.utils.SpelExprUtils
import org.apache.ibatis.executor.Executor
import org.apache.ibatis.executor.statement.StatementHandler
import org.apache.ibatis.mapping.BoundSql
import org.apache.ibatis.mapping.MappedStatement
import org.apache.ibatis.session.ResultHandler
import org.apache.ibatis.session.RowBounds
import org.springframework.expression.ParserContext
import java.sql.Connection

/**
 * This is a MyBatis interceptor that evaluates Spring Expression Language (SpEL) expressions in SQL statements.
 * By default, this interceptor uses `{{- exp -}}` syntax to avoid interference with MyBatis' own processing of `#{exp}` syntax.
 * @see <a href="https://docs.spring.io/spring/docs/current/spring-framework-reference/core.html#expressions">Spring Expression Language (SpEL)</a>
 */
class SpelExpInterceptor(private val parserContext: ParserContext) : InnerInterceptor {
    constructor() : this(defaultParserContext)

    override fun beforeQuery(
        executor: Executor?,
        ms: MappedStatement?,
        parameter: Any?,
        rowBounds: RowBounds?,
        resultHandler: ResultHandler<*>?,
        boundSql: BoundSql?,
    ) {
        val mpBs = PluginUtils.mpBoundSql(boundSql)
        evaluateSpELInBoundSql(mpBs)
    }

    override fun beforePrepare(sh: StatementHandler?, connection: Connection?, transactionTimeout: Int?) {
        val mpSh = PluginUtils.mpStatementHandler(sh)
        val mpBs = mpSh.mPBoundSql()
        evaluateSpELInBoundSql(mpBs)
    }

    private fun evaluateSpELInBoundSql(mpBs: PluginUtils.MPBoundSql) {
        val sql = mpBs.sql()
        mpBs.sql(SpelExprUtils.evalExpr<String>(sql, parserContext))
    }

    companion object {
        private val defaultParserContext: ParserContext = object : ParserContext {
            override fun isTemplate(): Boolean = true
            override fun getExpressionPrefix(): String = "{{- "
            override fun getExpressionSuffix(): String = " -}}"
        }
    }
}
