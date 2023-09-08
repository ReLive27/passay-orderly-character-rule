package com.relive;

import org.junit.Assert;
import org.junit.Test;
import org.passay.PasswordData;
import org.passay.PasswordValidator;
import org.passay.RuleResult;

/**
 * @author: ReLive27
 * @date: 2022/4/18 4:08 下午
 */
public class OrderlyCharacterRuleTest {

    @Test
    public void orderlyCharacterTest() {
        //限制连续有序字符长度不超过3
        OrderlyCharacterRule orderlyCharacterRule = new OrderlyCharacterRule(3);
        PasswordValidator passwordValidator = new PasswordValidator(orderlyCharacterRule);
        PasswordData passwordData = new PasswordData("abdbcd");
        RuleResult validate = passwordValidator.validate(passwordData);
        Assert.assertEquals(validate.isValid(), false);

        passwordData.setPassword("Admin1124");
        RuleResult validate1 = passwordValidator.validate(passwordData);
        Assert.assertEquals(validate1.isValid(), true);
    }
}
