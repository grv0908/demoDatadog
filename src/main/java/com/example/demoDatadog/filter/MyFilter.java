package com.example.demoDatadog.filter;

import org.honton.chas.datadog.apm.SpanBuilder;
import org.honton.chas.datadog.apm.TraceOperation;
import org.honton.chas.datadog.apm.Tracer;
import org.honton.chas.datadog.apm.Tracer.HeaderAccessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.apache.logging.log4j.ThreadContext;

import com.example.demoDatadog.DemoDatadogApplication;

import javax.inject.Inject;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.Enumeration;
import java.util.HashMap;

@WebFilter("/*")
@Component
public class MyFilter implements Filter {

	private Tracer tracer;

	@Inject
	void setTracer(Tracer tracer) {
		this.tracer = tracer;
	}

	@Override
	public void init(FilterConfig filterConfig) {
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain)
			throws IOException, ServletException {

		final HttpServletRequest req = (HttpServletRequest) request;
		final HttpServletResponse resp = (HttpServletResponse) response;

		SpanBuilder sb = tracer.importSpan(name -> req.getHeader(name));
		try {
			sb.resource(normalize(req.getServerName()) + ':' + req.getServerPort())
					.operation(req.getMethod() + ' ' + URLDecoder.decode(req.getRequestURI(), "UTF-8"))
					.type(TraceOperation.WEB);

			System.out.println("********* Trace Id  " + sb.traceId());
			System.out.println("********* Span Id  " + sb.spanId());
			System.out.println("********* Parent Id  " + sb.parentId());

			String currentTraceId = req.getHeader(RequestCorrelation.TRACE_ID);

			if (currentTraceId == null) {
				currentTraceId = String.valueOf(sb.traceId());
			}

			RequestCorrelation.setTraceId(currentTraceId);
			RequestCorrelation.setSpanId(String.valueOf(sb.spanId()));

			filterChain.doFilter(req, resp);
		} finally {
			int status = resp.getStatus();
			sb.error(status < 200 || status >= 400);
			tracer.closeSpan(sb);
		}
	}

	private static String normalize(String host) {
		if (Character.isDigit(host.charAt(0))) {
			return ':' + host;
		}
		return host;
	}

	@Override
	public void destroy() {
	}
}