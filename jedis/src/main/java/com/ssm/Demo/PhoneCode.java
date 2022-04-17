package com.ssm.Demo;

import redis.clients.jedis.Jedis;

import java.util.Random;

/**
 * @author shaoshao
 * @version 1.0
 * @date 2022/4/17 16:04
 */
public class PhoneCode {
    public static void main(String[] args) {

//        verifyCode("19858165529");
        getRedisCode("19858165529","098883");
    }



    //生成6位数字验证码
    public static StringBuffer getCode() {
        Random random = new Random();
//        String code = "";
        StringBuffer code = new StringBuffer();
        for (int i = 0; i < 6; i++) {
            int rand = random.nextInt(10);
            code.append(rand);
        }
        return code;
    }

    // 每个手机每天发送3个验证码，验证码放到redis中，设置过期时间
    public static String verifyCode(String phone) {
        Jedis jedis = new Jedis("127.0.0.1",6379);

        // 手机发送次数key
        String countKey = "VerifyCode"+phone+":count";
        // 验证码key
        String codeKey = "VerifyCode"+phone+":code";

        String count = jedis.get(countKey);
        if (count == null){
            // 第一次发送
            // 设置发送次数是1
            jedis.setex(countKey,24*60*60,"1");
        } else if (Integer.parseInt(count)<=2){
            jedis.incr(countKey);
        } else if (Integer.parseInt(count)>2){
            System.out.println("发送次数超过三次");
            jedis.close();
        }
        // 发送验证码到redis里面
        String vcode = getCode().toString();
        jedis.setex(codeKey,120,vcode);
        jedis.close();
        return vcode;
    }

    // 验证码校验
    public static void getRedisCode(String phone,String code){
        Jedis jedis = new Jedis("127.0.0.1",6379);
        // 验证码key
        String codeKey = "VerifyCode"+phone+":code";
        String redisCode = jedis.get(codeKey);
        System.out.println(redisCode);
        if (redisCode.equals(code)){
            System.out.println("成功");
        } else {
            System.out.println("失败");
        }
        jedis.close();
    }
}
