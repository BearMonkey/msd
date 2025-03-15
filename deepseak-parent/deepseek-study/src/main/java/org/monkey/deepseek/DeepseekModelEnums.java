package org.monkey.deepseek;

import lombok.Getter;

/**
 * DeepseekModelEnums
 *
 * @author cc
 * @since 2025/2/8 14:35
 */
@Getter
public enum DeepseekModelEnums {
    // deepseek-chat, deepseek-reasoner
    CHAT("deepseek-chat"),
    REASONER("deepseek-reasoner"),
    ;

    private final String model;

    DeepseekModelEnums(String model) {
        this.model = model;
    }
}
