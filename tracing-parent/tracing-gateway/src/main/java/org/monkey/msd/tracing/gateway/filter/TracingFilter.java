package org.monkey.msd.tracing.gateway.filter;

import io.micrometer.tracing.Span;
import io.micrometer.tracing.TraceContext;
import io.micrometer.tracing.Tracer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.lang.NonNull;
import org.springframework.lang.NonNullApi;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

/**
 * TracingFilter
 *
 * @author cc
 * @since 2025/4/1 10:34
 */
@Component
public class TracingFilter implements WebFilter {

    private static final Logger log = LoggerFactory.getLogger(TracingFilter.class);
    @Autowired
    private Tracer tracer;

    @Override
    @NonNull
    public Mono<Void> filter(@NonNull ServerWebExchange exchange, @NonNull WebFilterChain chain) {
        log.info("TracingFilter.filter");
        // 1. 创建新 Span（网关作为根 Span）
        Span span = tracer.nextSpan().name("gateway-request").start();

        // 2. 获取 Trace ID 和 Span ID
        TraceContext context = span.context();
        String traceId = context.traceId();
        String spanId = context.spanId();
        String parentId = context.parentId();

        MDC.put("traceId", traceId);
        MDC.put("spanId", spanId);
        log.info("traceId: {}, spanId: {}, parentId: {}", traceId, spanId, parentId);

        // 3. 将 Trace ID 和 Span ID 注入请求头
        ServerHttpRequest request = exchange.getRequest().mutate()
                .header("X-B3-TraceId", traceId)
                .header("X-B3-SpanId", spanId)
                .header("X-B3-ParentSpanId", parentId) // 父 Span ID（此处为 null，因为网关是根）
                .build();

        // 4. 将 Span 存储到 Exchange 属性中，供后续处理
        exchange.getAttributes().put("gatewaySpan", span);
        ServerWebExchange.Builder mutate = exchange.mutate();
        ServerWebExchange.Builder builder = mutate.request(request);
        ServerWebExchange serverWebExchange = builder.build();

        // 5. 继续执行过滤器链，并在完成后结束 Span
        return chain.filter(serverWebExchange)
                .doOnSuccess(v -> {
                    log.info("TracingFilter.filter.doOnSuccess");
                })
                .doOnError(e -> {
                    log.info("TracingFilter.filter.doOnError");
                    if (e != null) {
                        span.error(e);
                    }
                })
                .doFinally(signalType -> {
                    log.info("TracingFilter.filter.doFinally");
                    span.end();
                    MDC.remove("traceId");
                    MDC.remove("spanId");
                });
    }
}
