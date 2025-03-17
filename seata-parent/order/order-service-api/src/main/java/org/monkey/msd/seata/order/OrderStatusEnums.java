package org.monkey.msd.seata.order;

import lombok.Getter;

/**
 * OrderStatusEnums
 *
 * @author cc
 * @since 2025/3/1 10:46
 */
@Getter
public enum OrderStatusEnums {

    CREATED(0, "下单成功"),
    PAYED(1, "支付成功"),
    COMPLETED(2, "订单完成"),
    FAIL(99, "下单失败"),
    ;

    private final int status;
    private final String desc;

    OrderStatusEnums(int status, String desc) {
        this.status = status;
        this.desc = desc;
    }
}
