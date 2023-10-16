package com.vananh.ecommerce.service.implementation;

import com.vananh.ecommerce.exception.OrderException;
import com.vananh.ecommerce.exception.UserException;
import com.vananh.ecommerce.model.Order;
import com.vananh.ecommerce.model.User;
import com.vananh.ecommerce.response.PaymentLinkResponse;
import com.vananh.ecommerce.service.OrderService;
import com.vananh.ecommerce.service.PaymentService;
import com.vananh.ecommerce.service.UserService;
import com.vananh.ecommerce.util.HMACUtil;
import com.vananh.ecommerce.util.PaymentConstant;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.json.JSONObject;
import org.springframework.web.client.RestTemplate;



import java.text.SimpleDateFormat;
import java.util.*;

@Service
@RequiredArgsConstructor
public class PaymentServiceImplementation implements PaymentService {

    private final OrderService orderService;

    private final UserService userService;

    @Override
    public PaymentLinkResponse createPayment(Long orderId,String jwt) throws OrderException, UserException {

        User user = userService.findUserProfileByJwt(jwt);

        Order orderById = orderService.findOrderById(orderId);

        if (orderById.getUser()!=user){
            throw new OrderException("User not valid");
        }

        Random rand = new Random();
        int random_id = rand.nextInt(1000000);

        Map<String, Object> embed_data = new HashMap<>();
        Map<String, Object> order = new HashMap<String, Object>() {};

        order.put("app_id", PaymentConstant.app_id);
        order.put("app_trans_id", getCurrentTimeString("yyMMdd") + "_" + random_id); // translation missing: vi.docs.shared.sample_code.comments.app_trans_id
        order.put("app_time", System.currentTimeMillis()); // milliseconds
        order.put("app_user", PaymentConstant.app_user);
        order.put("amount", orderById.getTotalPrice());
        order.put("description",PaymentConstant.description  + random_id);
        order.put("bank_code", "zalopayapp");
        order.put("callback_url",PaymentConstant.callback_url);
        order.put("item", new JSONObject(orderById).toString());
        embed_data.put("redirecturl",PaymentConstant.redirecturl);
        order.put("embed_data", new JSONObject(embed_data).toString());

        // app_id +”|”+ app_trans_id +”|”+ appuser +”|”+ amount +"|" + app_time +”|”+ embed_data +"|" +item
        String data = order.get("app_id") + "|" + order.get("app_trans_id") + "|" + order.get("app_user") + "|" + order.get("amount")
                + "|" + order.get("app_time") + "|" + order.get("embed_data") + "|" + order.get("item");
        order.put("mac", HMACUtil.HMacHexStringEncode(HMACUtil.HMACSHA256, PaymentConstant.key_1, data));

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> mapOrder= new LinkedMultiValueMap<>();
        for (Map.Entry<String, Object> e : order.entrySet()) {
            mapOrder.add(e.getKey(), e.getValue().toString());
        }

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(mapOrder, headers);
        ResponseEntity<String> response = restTemplate.postForEntity(PaymentConstant.endpoint, request, String.class);

        String resultJsonStr = response.getBody();
        JSONObject result = new JSONObject(resultJsonStr);

        int return_code = (int) result.get("return_code");
        String return_message = (String) result.get("return_message");
        String order_url = (String) result.get("order_url");


//        int sub_return_code = (int) result.get("sub_return_code");
//        String sub_return_message = (String) result.get("sub_return_message");
//        String zp_trans_token = (String) result.get("zp_trans_token");
//        String order_token = (String) result.get("order_token");
//        String qr_code = (String) result.get("qr_code");

        PaymentLinkResponse paymentLinkResponse = new PaymentLinkResponse();

        paymentLinkResponse.setMessage(return_message);
        paymentLinkResponse.setOrder_url(order_url);

        return paymentLinkResponse;
}

    public static String getCurrentTimeString(String format) {
        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT+7"));
        SimpleDateFormat fmt = new SimpleDateFormat(format);
        fmt.setCalendar(cal);
        return fmt.format(new Date());
    }

}
