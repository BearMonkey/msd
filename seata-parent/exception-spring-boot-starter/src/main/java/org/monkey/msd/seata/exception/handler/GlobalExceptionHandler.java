package org.monkey.msd.seata.exception.handler;

import jakarta.servlet.http.HttpServletRequest;
import org.monkey.msd.seata.common.dto.Result;
import org.monkey.msd.seata.exception.exception.BusinessException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * GlobalExceptionHandler
 *
 * @author cc
 * @since 2025/3/3 9:13
 */
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(value = { Exception.class })
    public ResponseEntity<Result<?>> handleException(HttpServletRequest request, Exception ex) {
        // 自定义异常处理逻辑，例如记录日志、返回错误信息等。
        return ResponseEntity.status(500).body(Result.fail(ex.getMessage()));
    }

    @ExceptionHandler(value = { BusinessException.class })
    public ResponseEntity<Object> handleBusinessException(HttpServletRequest request, BusinessException ex) {
        // 自定义异常处理逻辑，例如记录日志、返回错误信息等。
        return ResponseEntity.status(500).body(Result.fail(ex.getMessage()));
    }
}
