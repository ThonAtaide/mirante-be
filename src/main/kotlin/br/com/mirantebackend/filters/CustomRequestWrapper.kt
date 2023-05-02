package br.com.mirantebackend.filters

import java.util.Collections
import java.util.Enumeration
import java.util.HashMap
import java.util.stream.Collectors
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletRequestWrapper

class CustomRequestWrapper(request: HttpServletRequest) : HttpServletRequestWrapper(request) {

    private val customHeaders: HashMap<String, String> = HashMap()

    fun putHeader(name: String, value: String) {
        customHeaders[name] = value
    }

    override fun getHeader(name: String): String? {
        if (customHeaders[name] == null) {
            return super.getHeader(name)
        }
        return customHeaders[name]!!
    }

    override fun getHeaderNames(): Enumeration<String> {
        val set: MutableSet<String> = HashSet(customHeaders.keys)
        val headerNames = (request as HttpServletRequest).headerNames

        while (headerNames.hasMoreElements()) {
            val next = headerNames.nextElement()
            set.add(next)
        }
        return Collections.enumeration(set)
    }

    override fun getHeaders(name: String): Enumeration<String> {
        val values = getHeader(name)
        if (customHeaders.containsKey(name) && values != null) {
            val set: MutableSet<String> = values.split(",").stream().collect(Collectors.toSet())
            return Collections.enumeration(set)
        }
        return super.getHeaders(name)
    }
}
