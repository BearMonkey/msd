
DROP TABLE IF EXISTS `ord_order`;
CREATE TABLE `ord_order` (
                             `id` BIGINT NOT NULL COMMENT 'ID',
                             `create_by` varchar(30)  DEFAULT NULL COMMENT '创建人编号',
                             `create_time` datetime DEFAULT NULL COMMENT '创建时间',
                             `update_by` varchar(30)  DEFAULT NULL COMMENT '修改人编号',
                             `update_time` datetime DEFAULT NULL COMMENT '修改时间',
                             `del_flag` tinyint DEFAULT 0 COMMENT '逻辑删除标识；1-已删除，0-未删除',
                             `order_no` varchar(32) DEFAULT NULL COMMENT '订单号',
                             `total_price` DECIMAL(16,2)  DEFAULT 0.00 COMMENT '总价',
                             `status` int  DEFAULT 0 COMMENT '订单状态',
                             PRIMARY KEY (`id`),
                             KEY `idx_order_no` (`order_no`)
) ENGINE=InnoDB COMMENT='订单表';


DROP TABLE IF EXISTS `ord_order_detail`;
CREATE TABLE `ord_order_detail` (
                                    `id` BIGINT NOT NULL COMMENT 'ID',
                                    `create_by` varchar(30)  DEFAULT NULL COMMENT '创建人编号',
                                    `create_time` datetime DEFAULT NULL COMMENT '创建时间',
                                    `update_by` varchar(30)  DEFAULT NULL COMMENT '修改人编号',
                                    `update_time` datetime DEFAULT NULL COMMENT '修改时间',
                                    `del_flag` tinyint DEFAULT 0 COMMENT '逻辑删除标识；1-已删除，0-未删除',
                                    `order_no` varchar(32) DEFAULT NULL COMMENT '订单号',
                                    `stock_id` BIGINT  NOT NULL COMMENT '库存id',
                                    `goods_name` varchar(256)  DEFAULT 0.00 COMMENT '商品名称',
                                    `quantity` int DEFAULT 0 COMMENT '商品数量',
                                    `unit_price` DECIMAL(16,2)  DEFAULT 0.00 COMMENT '单价',
                                    PRIMARY KEY (`id`),
                                    KEY `idx_order_no` (`order_no`)
) ENGINE=InnoDB COMMENT='订单明细表';


DROP TABLE IF EXISTS `stc_stock`;
CREATE TABLE `stc_stock` (
                             `id` BIGINT NOT NULL COMMENT 'ID',
                             `create_by` varchar(30)  DEFAULT NULL COMMENT '创建人编号',
                             `create_time` datetime DEFAULT NULL COMMENT '创建时间',
                             `update_by` varchar(30)  DEFAULT NULL COMMENT '修改人编号',
                             `update_time` datetime DEFAULT NULL COMMENT '修改时间',
                             `del_flag` tinyint DEFAULT 0 COMMENT '逻辑删除标识；1-已删除，0-未删除',
                             `goods_name` varchar(256) DEFAULT NULL COMMENT '商品名称',
                             `goods_desc` varchar(2000) DEFAULT NULL COMMENT '商品描述',
                             `amount` int DEFAULT 0 COMMENT '库存数量',
                             `unit_price` DECIMAL(16,2)  DEFAULT 0.00 COMMENT '单价',
                             PRIMARY KEY (`id`),
                             KEY `idx_goods_name` (`goods_name`)
) ENGINE=InnoDB COMMENT='商品表';

