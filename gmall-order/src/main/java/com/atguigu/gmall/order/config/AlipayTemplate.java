package com.atguigu.gmall.order.config;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.atguigu.gmall.order.vo.PayVo;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "alipay")
@Component
@Data
public class AlipayTemplate {

    //在支付宝创建的应用的id
    private   String app_id = "2016101500689477";

    // 商户私钥，您的PKCS8格式RSA2私钥
    private  String merchant_private_key = "MIIEvAIBADANBgkqhkiG9w0BAQEFAASCBKYwggSiAgEAAoIBAQCDX0wdcq23NGzJOZpuJBP2bz/NwoX0tyyT+HasyyOGEhywYQHcdULHuAAhgLtRVE2WywyTqFzGXNMlFHt55q1J/nh8SHzUT5G1vtH2LG8hl632hpwJGYBxznfqWVSlGn5LPhnXcPjX+yiaa/YJwPb9VcnD97PZPTyelDdJQWyh16dYVe0aC/Qrq6Vu337J31W5tVz/eGZnoMu+CAA0yhYO40kIgArF8DktcnXe55brdp7ZEQEWe8hd56TdGYNJ9nL3VXZQ0eSLCwhNG6MSZdGqcKavAaefz0TqXIGOZ7RkE+miDrIzhipIJcMKP7K/tsZ5wQFAGLXBpq7GB4abEh5NAgMBAAECggEAVc6K8rkSyhUN8SkUu1gJSe9EJ2sk/8Ac99FUAmz0mP9/iZzzEEHvCnAedcOVlfuQaivZ3SAc6Nt8dj24oNiQ20HBBLx7/Fi0fZdToI1JURG3aMxnYnIruSn4nTMX3Hc/gnnfex6pCrlbvfFCVQCa0sGT9dH2wLAExjddHRYElHhNeUJwb/c37o/Be8pKncAMQRIsvw1uKbe5LzT+V3ARRyx6SHOyEtbi7vy90dcOrO8XEhJalulI5VM6ih8mPEyE5fN5YsKtJX1CXVNvREryvfkoYKLmLNUSlrxR4Y/2FwxxK7OVX0s2UQ+EewhqpUDpC88QxKX6H62h2sQK5IJawQKBgQC95RRatxaH/IWnY+gDRBYoh+xTkMzPfkf/Poy0ecUHJAtQ7m/TjdbxDK2sHU7KbLXrlPw6pJpL3PH3pq8mSQoY1uds4jzRMSkSCXwL+6st3KWpkiNQLNjFumdWBEERapa39j5P0qOzLFQCqQqYf41fQ2U/mvSt3PvLfgz8O78xZQKBgQCxGtdleIa8lkDqiYnq+XG0xulnfF1PUYPvwNXLhMZCUWwPkEkQGombuUjbIcsgmsEz3O72ctCD0fhqnjDB3I628+WjZEuo7wJdcptvqsrRGz2YqoCZTr/pGkRfKe0VKNz2xk2PoiboeQs9DqjcyQueXzRRWHRnWwXCA/IJ0BieyQKBgFwQ2qN4rOXVWn9jgrNCqXORItUJGSNoWEO58cH2X74sjYf5ciIhXJqVyQXvV6nwtuq22usbrusk/fNRzw1C0DBm0OELduz9ZqomCwxMQhwEJBVoktJmS9rhmwQrH9jv0o7yRhexSkiuLzTy+/4fqU0MgeQqnoJE55crT+Rimzm5AoGAPi2/S9fJ7jIObomFS2LyYdaoIXYPSIbE/IrGROLDvIrblodAr4+xmmOtyYmOyE6s4CmEGDBtNqqkPcJ2OcnWrTLp/7h5u8DWIrrXCZh8Bng64vuRlMGKLSvUr1iH7TLgiR9MIc57PIkqwfAApytKkfq44opcq+DQH7LE6l8OFckCgYApESrkIWj996wW58apGx3C/VrQ2d+LKoE7hWSz2USiIlsdYrHOfI2GdnR6VpMNzNfBAi4QL9ihX5qc56rPp0lGgLKu6qwsSSKXBMYbQzk8omcc3DxzRhqCAsQFfMJRWrXD/s/o0X0RybwAzBZlLROoyRObtRi68BUOGcz2ycA1tA==";
    // 支付宝公钥,查看地址：https://openhome.alipay.com/platform/keyManage.htm 对应APPID下的支付宝公钥。
    private  String alipay_public_key = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAgflagBuG5I8p3KZF+sgwszLtvPX4wiaOuMKmyfmekFT8JvN+XwgUsYLHuANtqMdDuO7Q5yReBthix36THfBvLV7URQKxurbzXVcW5WIv8NRSQpcE1IGf0MFrZc5NzBbT3BcaX2W+rZhVql6ocPCkfWVE1Aa3F1tDLU8qbbbQMkY72w1nvabkV/vEyx8bPshb/PxjYFXaE9iiw9SH0c6egWO8JftcdImZBpI1KUjvQTnuHhS0wYOJhvq/XXHujm/qWrgix3QhVlr72P1bFXZPR9wlwVqMb4UpuNY6io0mMHv5B/2ASbUXzgccCghgrZie+o3S7/IFO879T0upKt/QIwIDAQAB";
    // 服务器[异步通知]页面路径  需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
    // 支付宝会悄悄的给我们发送一个请求，告诉我们支付成功的信息
    private  String notify_url = "http://71gos5l9l8.52http.net/api/order/pay/success";

    // 页面跳转同步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
    //同步通知，支付成功，一般跳转到成功页
    private  String return_url = null;

    // 签名方式
    private  String sign_type = "RSA2";

    // 字符编码格式
    private  String charset = "utf-8";

    // 支付宝网关； https://openapi.alipaydev.com/gateway.do
    private  String gatewayUrl = "https://openapi.alipaydev.com/gateway.do";

    public  String pay(PayVo vo) throws AlipayApiException {

        //AlipayClient alipayClient = new DefaultAlipayClient(AlipayTemplate.gatewayUrl, AlipayTemplate.app_id, AlipayTemplate.merchant_private_key, "json", AlipayTemplate.charset, AlipayTemplate.alipay_public_key, AlipayTemplate.sign_type);
        //1、根据支付宝的配置生成一个支付客户端
        AlipayClient alipayClient = new DefaultAlipayClient(gatewayUrl,
                app_id, merchant_private_key, "json",
                charset, alipay_public_key, sign_type);

        //2、创建一个支付请求 //设置请求参数
        AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();
        alipayRequest.setReturnUrl(return_url);
        alipayRequest.setNotifyUrl(notify_url);

        //商户订单号，商户网站订单系统中唯一订单号，必填
        String out_trade_no = vo.getOut_trade_no();
        //付款金额，必填
        String total_amount = vo.getTotal_amount();
        //订单名称，必填
        String subject = vo.getSubject();
        //商品描述，可空
        String body = vo.getBody();

        alipayRequest.setBizContent("{\"out_trade_no\":\""+ out_trade_no +"\","
                + "\"total_amount\":\""+ total_amount +"\","
                + "\"subject\":\""+ subject +"\","
                + "\"body\":\""+ body +"\","
                + "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"}");

        String result = alipayClient.pageExecute(alipayRequest).getBody();

        //会收到支付宝的响应，响应的是一个页面，只要浏览器显示这个页面，就会自动来到支付宝的收银台页面
        System.out.println("支付宝的响应："+result);

        return result;

    }
}
