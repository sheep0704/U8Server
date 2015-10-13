package com.u8.server.sdk.jinli;

import com.u8.server.data.UChannel;
import com.u8.server.data.UOrder;
import com.u8.server.data.UUser;
import com.u8.server.log.Log;
import com.u8.server.sdk.*;
import com.u8.server.sdk.jinli.jinli.JinLiBase64;
import com.u8.server.sdk.jinli.jinli.RSASignature;
import com.u8.server.utils.TimeFormater;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.CharEncoding;
import org.apache.http.entity.ByteArrayEntity;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.util.*;

/**
 * 金立SDK
 * Created by ant on 2015/5/5.
 */
public class JinLiSDK implements ISDKScript{

    private static final String HOST = "id.gionee.com";
    private static final String PORT = "443";
    private static final String METHOD = "POST";
    private static final String URL = "/account/verify.do";


    private static String builderAuthorization(UChannel channel) {

        Long ts = System.currentTimeMillis() / 1000;
        String nonce = StringUtil.randomStr().substring(0, 8);
        String mac = CryptoUtility.macSig(HOST, PORT, channel.getCpAppSecret(), ts.toString(), nonce, METHOD, URL);
        mac = mac.replace("\n", "");
        StringBuilder authStr = new StringBuilder();
        authStr.append("MAC ");
        authStr.append(String.format("id=\"%s\"", channel.getCpAppKey()));
        authStr.append(String.format(",ts=\"%s\"", ts));
        authStr.append(String.format(",nonce=\"%s\"", nonce));
        authStr.append(String.format(",mac=\"%s\"", mac));
        return authStr.toString();
    }

    @Override
    public void verify(final UChannel channel, String extension, final ISDKVerifyListener callback) {

        try{


            JSONObject json = JSONObject.fromObject(extension);
            final String playerID = json.getString("playerID");
            String token = json.getString("token");
            final String username = json.getString("username");

            UHttpAgent httpClient = UHttpAgent.getInstance();

            Map<String, String> headers = new HashMap<String, String>();
            headers.put("Content-Type", "application/json");

            String auth = builderAuthorization(channel);

            Log.e("The auth is "+auth);
            Log.e("The token is "+token);

            headers.put("Authorization", auth);


            httpClient.post(channel.getMaster().getAuthUrl(), headers, new ByteArrayEntity(token.getBytes(Charset.forName("UTF-8"))), new UHttpFutureCallback() {
                @Override
                public void completed(String result) {

                    Log.d("The auth result is "+result);

                    JSONObject json = JSONObject.fromObject(result);
                    if(!json.containsKey("r") || "0".equals(json.getString("r"))){

                        callback.onSuccess(new SDKVerifyResult(true, playerID, username, ""));
                        return;
                    }

                    callback.onFailed(channel.getMaster().getSdkName() + " verify failed. the post result is " + result);

                }

                @Override
                public void failed(String e) {

                    callback.onFailed(channel.getMaster().getSdkName() + " verify failed. " + e);
                }


            });




        }catch (Exception e){
            callback.onFailed(channel.getMaster().getSdkName() + " verify execute failed. the exception is "+e.getMessage());
            Log.e(e.getMessage());
        }

    }

    @Override
    public void onGetOrderID(UUser user, final UOrder order, final ISDKOrderListener callback) {

        try{

            String player_id = user.getChannelUserID();         //不参与签名
            String api_key = user.getChannel().getCpAppKey();
            DecimalFormat df = new DecimalFormat(".00");
            String deal_price = df.format((order.getMoney() / 100f));
            String deliver_type = "1";
            //String expire_time = "";
            //String notify_url = "";           //后台配置
            String out_order_no = order.getOrderID()+"";
            String subject = order.getProductName();
            String submit_time = TimeFormater.format_yyyyMMddHHmmss(new Date());
            String total_fee = deal_price;

            StringBuilder sb = new StringBuilder();
            sb.append(api_key).append(deal_price).append(deliver_type).append(out_order_no)
                    .append(subject).append(submit_time).append(total_fee);

            //String sign = RSAUtils.sign(sb.toString(), user.getChannel().getCpPayPriKey(), "UTF-8");
            String sign = RSASignature.sign(sb.toString(), user.getChannel().getCpPayPriKey(), CharEncoding.UTF_8);


            JSONObject data = new JSONObject();
            data.put("player_id", player_id);
            data.put("api_key", api_key);
            data.put("deal_price", deal_price);
            data.put("deliver_type", deliver_type);
            data.put("out_order_no", out_order_no);
            data.put("subject", subject);
            data.put("submit_time", submit_time);
            data.put("total_fee", total_fee);
            data.put("sign", sign);

            Map<String,String> headers = new HashMap<String, String>();
            headers.put("Content-Type", "application/json");

            UHttpAgent.getInstance().post(order.getChannel().getMaster().getOrderUrl(), headers, new ByteArrayEntity(data.toString().getBytes(Charset.forName("UTF-8"))), new UHttpFutureCallback() {
                @Override
                public void completed(String content) {

                    try{

                        JSONObject json = JSONObject.fromObject(content);
                        String status = json.getString("status");

                        if("200010000".equals(status)){
                            String out_order_no = json.getString("out_order_no");
                            String submit_time = json.getString("submit_time");
                            JSONObject json2 = new JSONObject();
                            json2.put("out_order_no", out_order_no);
                            json2.put("submit_time", submit_time);
                            callback.onSuccess(json2.toString());
                            return;
                        }

                    }catch (Exception e){
                        e.printStackTrace();
                    }


                    callback.onFailed("parse Order failed."+content);

                }

                @Override
                public void failed(String e) {

                    callback.onFailed(order.getChannel().getChannelID() + " get channel order failed. "+ e);
                }

            });

        }catch (Exception e){
            callback.onFailed(e.getMessage());
            e.printStackTrace();
        }

    }

    static class CryptoUtility {

        private static final String MAC_NAME = "HmacSHA1";

        public static String macSig(String host, String port, String macKey, String timestamp, String nonce, String method, String uri) {
            // 1. build mac string
            // 2. hmac-sha1
            // 3. base64-encoded

            StringBuffer buffer = new StringBuffer();
            buffer.append(timestamp).append("\n");
            buffer.append(nonce).append("\n");
            buffer.append(method.toUpperCase()).append("\n");
            buffer.append(uri).append("\n");
            buffer.append(host.toLowerCase()).append("\n");
            buffer.append(port).append("\n");
            buffer.append("\n");
            String text = buffer.toString();

            byte[] ciphertext = null;
            try {
                ciphertext = hmacSHA1Encrypt(macKey, text);
            } catch (Throwable e) {
                e.printStackTrace();
                return null;
            }

            String sigString = JinLiBase64.encodeToString(ciphertext, JinLiBase64.DEFAULT);
            return sigString;
        }

        public static byte[] hmacSHA1Encrypt(String encryptKey, String encryptText) throws InvalidKeyException, NoSuchAlgorithmException {
            Mac mac = Mac.getInstance(MAC_NAME);
            mac.init(new SecretKeySpec(StringUtil.getBytes(encryptKey), MAC_NAME));
            return mac.doFinal(StringUtil.getBytes(encryptText));
        }

    }

    static class StringUtil {
        public static final String UTF8 = "UTF-8";
        private static final byte[] BYTEARRAY = new byte[0];

        public static boolean isNullOrEmpty(String s) {
            if (s == null || s.isEmpty() || s.trim().isEmpty())
                return true;
            return false;
        }

        public static String randomStr() {
            return CamelUtility.uuidToString(UUID.randomUUID());
        }

        public static byte[] getBytes(String value) {
            return getBytes(value, UTF8);
        }

        public static byte[] getBytes(String value, String charset) {
            if (isNullOrEmpty(value))
                return BYTEARRAY;
            if (isNullOrEmpty(charset))
                charset = UTF8;
            try {
                return value.getBytes(charset);
            } catch (UnsupportedEncodingException e) {
                return BYTEARRAY;
            }
        }
    }

    static class CamelUtility {
        public static final int SizeOfUUID = 16;
        private static final int SizeOfLong = 8;
        private static final int BitsOfByte = 8;
        private static final int MBLShift = (SizeOfLong - 1) * BitsOfByte;

        private static final char[] HEX_CHAR_TABLE = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

        public static String uuidToString(UUID uuid) {
            long[] ll = {uuid.getMostSignificantBits(), uuid.getLeastSignificantBits()};
            StringBuilder str = new StringBuilder(SizeOfUUID * 2);
            for (int m = 0; m < ll.length; ++m) {
                for (int i = MBLShift; i > 0; i -= BitsOfByte)
                    formatAsHex((byte) (ll[m] >>> i), str);
                formatAsHex((byte) (ll[m]), str);
            }
            return str.toString();
        }

        public static void formatAsHex(byte b, StringBuilder s) {
            s.append(HEX_CHAR_TABLE[(b >>> 4) & 0x0F]);
            s.append(HEX_CHAR_TABLE[b & 0x0F]);
        }

    }
}
