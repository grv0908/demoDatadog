package com.example.demoDatadog.filter;

public class RequestCorrelation {

    public static final String TRACE_ID = "x-ddtrace-parent_trace_id";
    public static final String SPAN_ID = "x-ddtrace-parent_span_id";

    private static final ThreadLocal<String> trace_id = new ThreadLocal<String>();
    private static final ThreadLocal<String> span_id = new ThreadLocal<String>();


    public static void setTraceId(String id) {
    	trace_id.set(id);
    }

    public static String getTraceId() {
        return trace_id.get();
    }
    
    public static void setSpanId(String id) {
    	span_id.set(id);
    }

    public static String getSpanId() {
        return span_id.get();
    } 
}