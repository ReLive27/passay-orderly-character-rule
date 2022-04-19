package com.relive;

import org.passay.PasswordData;
import org.passay.Rule;
import org.passay.RuleResult;
import org.passay.RuleResultMetadata;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author: ReLive
 * @date: 2022/4/18 12:18 下午
 */
public class OrderlyCharacterRule implements Rule {
    public static final String ERROR_CODE = "ILLEGAL_ORDERLY_CHARACTER";
    private final int[] chars;
    private final int orderlyCharacterLength;
    private final int arrayLength;

    public OrderlyCharacterRule(int length) {
        if (length < 2) {
            throw new IllegalArgumentException("argument must be greater than two");
        }
        this.orderlyCharacterLength = length;
        this.arrayLength = length + 1;
        this.chars = new int[this.arrayLength];
    }

    @Override
    public RuleResult validate(PasswordData passwordData) {
        boolean matches = matches(passwordData.getPassword());
        RuleResult result = new RuleResult();
        if (!matches) {
            result.addError(ERROR_CODE, this.createRuleResultDetailParameters());
        }
        result.setMetadata(this.createRuleResultMetadata(passwordData));
        return result;
    }

    protected Map<String, Object> createRuleResultDetailParameters() {
        Map<String, Object> m = new LinkedHashMap();
        m.put("orderlyCharacterLength", this.orderlyCharacterLength);
        return m;
    }

    protected RuleResultMetadata createRuleResultMetadata(PasswordData password) {
        return new RuleResultMetadata(RuleResultMetadata.CountCategory.Length, password.getPassword().length());
    }

    protected boolean matches(String password) {
        int front = 0;
        int rear = 0;
        char[] c = password.toCharArray();
        int length = c.length;
        int sum = 0;
        for (int i = 0; i < length - 1; i++) {
            int difference = Math.abs(c[i + 1] - c[i]);
            sum += difference;
            chars[rear] = difference;
            rear = (rear + 1) % this.arrayLength;
            if (difference == 0) {
                front = rear;
                sum = 0;
            }
            if ((rear + 1) % this.arrayLength == front) {
                if (sum == this.orderlyCharacterLength) {
                    return false;
                }
                sum -= chars[front];
                front = (front + 1) % this.arrayLength;
            }
        }
        return true;
    }

    public String toString() {
        return String.format("%s@%h::orderlyCharacterLength=%s,", this.getClass().getName(), this.hashCode(), this.orderlyCharacterLength);
    }
}
