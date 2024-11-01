package org.sopt.hobby

import jakarta.servlet.Filter
import jakarta.servlet.FilterChain
import jakarta.servlet.ServletRequest
import jakarta.servlet.ServletResponse
import jakarta.servlet.http.HttpServletResponse
import org.springframework.stereotype.Component

@Component
class CorsFilter : Filter {
    override fun doFilter(req: ServletRequest, res: ServletResponse, fc: FilterChain) {
        val response = (res as HttpServletResponse).apply {
            addHeader("Access-Control-Allow-Origin", "*")
            addHeader("Access-Control-Allow-Methods", "*")
            addHeader("Access-Control-Max-Age", "3600")
            addHeader("Access-Control-Allow-Headers", "*")
        }

        fc.doFilter(req, response)
    }
}