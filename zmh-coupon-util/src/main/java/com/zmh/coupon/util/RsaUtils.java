package com.zmh.coupon.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.util.StringUtils;

import javax.crypto.Cipher;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @Title: RsaUtils.java
 * @Package com.zmh.demo.util
 * @Description: TODO(RSA工具类)
 * @author wuque.hua
 * @date 2020年5月1日 下午5:33:56
 * @version V1.0
 */
public class RsaUtils {

    public class Keys {

    }

    public static final String KEY_ALGORITHM = "RSA";
    // public static final String SIGNATURE_ALGORITHM = "MD5withRSA";
    private static final String PUBLIC_KEY = "RSAPublicKey";
    private static final String PRIVATE_KEY = "RSAPrivateKey";

    // 获得公钥
    public static String getPublicKey(Map<String, Object> keyMap) throws Exception {
        // 获得map中的公钥对象 转为key对象
        Key key = (Key) keyMap.get(PUBLIC_KEY);
        // byte[] publicKey = key.getEncoded();
        // 编码返回字符串
        return encryptBASE64(key.getEncoded());
    }

    // 获得私钥
    public static String getPrivateKey(Map<String, Object> keyMap) throws Exception {
        // 获得map中的私钥对象 转为key对象
        Key key = (Key) keyMap.get(PRIVATE_KEY);
        // byte[] privateKey = key.getEncoded();
        // 编码返回字符串
        return encryptBASE64(key.getEncoded());
    }

    // 解码返回byte
    public static byte[] decryptBASE64(String key) throws Exception {
        // JDK8 新增API
        return java.util.Base64.getDecoder().decode(key);
        // return (new BASE64Decoder()).decodeBuffer(key);
    }

    // 编码返回字符串
    public static String encryptBASE64(byte[] key) throws Exception {
        // JDK8 新增API
        return java.util.Base64.getEncoder().encodeToString(key);
        // return (new BASE64Encoder()).encodeBuffer(key);
    }

    // map对象中存放公私钥
    public static Map<String, Object> initKey() throws Exception {
        // 获得对象 KeyPairGenerator 参数 RSA 2048个字节
        KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance(KEY_ALGORITHM);

        keyPairGen.initialize(2048);
        // 通过对象 KeyPairGenerator 获取对象KeyPair
        KeyPair keyPair = keyPairGen.generateKeyPair();
        // 通过对象 KeyPair 获取RSA公私钥对象RSAPublicKey RSAPrivateKey
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
        // 公私钥对象存入map中
        Map<String, Object> keyMap = new HashMap<String, Object>(2);
        keyMap.put(PUBLIC_KEY, publicKey);
        keyMap.put(PRIVATE_KEY, privateKey);
        return keyMap;
    }

    /**
     * 
     * 公钥加密
     * 
     * 根据对方给的公钥加密字符串
     * 
     * @param originalStr
     *            明文字符串
     * @param publicKeyStr
     *            对方给到我方的公钥串
     * @return
     */
    public static String publicKeyEncrypt(String originalStr, String publicKeyStr) {
        try {
            String str = originalStr;
            X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(decryptBASE64(publicKeyStr));
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            PublicKey publicKey = keyFactory.generatePublic(x509EncodedKeySpec);
            // 初始化加密
            // Cipher类为加密和解密提供密码功能，通过getinstance实例化对象
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            // 加密二进制
            byte[] result = cipher.doFinal(str.getBytes());
            // 加密后BASE64字符串
            String encryptStr = encryptBASE64(result);
            return encryptStr;
        } catch (Exception e) {
            // TODO: handle exception
            return null;
        }

    }

    /**
     * 私钥解密
     * 
     * @param encryptStr
     *            对方传输过来使用我方提供公钥加密后的密文串
     * @param privateKeyStr
     *            我方私钥，请保密存储。
     * @return
     */
    public static String privateKeyDecrypt(String encryptStr, String privateKeyStr) {
        try {
            PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(decryptBASE64(privateKeyStr));
            KeyFactory keyFactory2 = KeyFactory.getInstance("RSA");
            PrivateKey privateKey = keyFactory2.generatePrivate(pkcs8EncodedKeySpec);
            Cipher cipher2 = Cipher.getInstance("RSA");
            // 初始化解密
            cipher2.init(Cipher.DECRYPT_MODE, privateKey);
            // 解密后二进制
            // encryptStr 为使用公钥加密的秘文串
            byte[] decryptResult = cipher2.doFinal(decryptBASE64(encryptStr));
            // 解密后明文字符串
            String decryptStr = new String(decryptResult);
            return decryptStr;
        } catch (Exception e) {
            // TODO: handle exception
            return null;
        }

    }

    // 使用私钥签名
    /**
     * @param privateKeyStr
     * @param originalStr
     * @param charset
     * @param rsaType
     * @return
     */
    public static String privateKeySign(String privateKeyStr, String originalStr, String charset, String rsaType) {
        java.security.Signature signature = null;
        String sign = "";
        try {

            if ("RSA".equalsIgnoreCase(rsaType)) {

                signature = java.security.Signature.getInstance("SHA1WithRSA");
            } else if ("RSA2".equalsIgnoreCase(rsaType)) {

                signature = java.security.Signature.getInstance("SHA256WithRSA");
            } else {

                signature = java.security.Signature.getInstance("SHA1WithRSA");
            }

            // 根据私钥串获取私钥
            PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(decryptBASE64(privateKeyStr));
            KeyFactory keyFactory2 = KeyFactory.getInstance("RSA");
            PrivateKey privateKey = keyFactory2.generatePrivate(pkcs8EncodedKeySpec);
            //
            signature.initSign(privateKey);
            //
            if (StringUtils.isEmpty(charset)) {
                signature.update(originalStr.getBytes());
            } else {
                signature.update(originalStr.getBytes(charset));
            }

            byte[] signed = signature.sign();

            sign = java.util.Base64.getEncoder().encodeToString(signed);

            return sign;
        } catch (Exception e) {
            // TODO: handle exception
            return sign;
        }

    }

    /**
     * @param Sign
     * @param publicKeyStr
     * @return
     */
    public static boolean publicKeyCheck(String sign, String originalStr, String publicKeyStr, String charset,
            String rsaType) {

        boolean flag = false;
        X509EncodedKeySpec x509EncodedKeySpec;
        java.security.Signature signature = null;
        try {
            x509EncodedKeySpec = new X509EncodedKeySpec(decryptBASE64(publicKeyStr));
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            PublicKey publicKey = keyFactory.generatePublic(x509EncodedKeySpec);

            if ("RSA".equalsIgnoreCase(rsaType)) {
                signature = java.security.Signature.getInstance("SHA1WithRSA");
            } else if ("RSA2".equalsIgnoreCase(rsaType)) {
                signature = java.security.Signature.getInstance("SHA256WithRSA");
            } else {
                signature = java.security.Signature.getInstance("SHA1WithRSA");
            }
            //
            signature.initVerify(publicKey);

            if (StringUtils.isEmpty(charset)) {
                signature.update(originalStr.getBytes());
            } else {
                signature.update(originalStr.getBytes(charset));
            }
            //
            flag = signature.verify(java.util.Base64.getDecoder().decode(sign.getBytes()));

        } catch (Exception e) {
            flag = false;
            e.printStackTrace();
        }

        return flag;
    }

    public static void main(String[] args) {

        try {
            /** 每次获取的公钥和私钥都不相同，所以记录需要使用的 密钥对。 **/

            // Map<String, Object> keyMap;
            // keyMap = initKey();
            // String publicKeyStr = getPublicKey(keyMap);
            // System.out.println("公钥-----------》" + publicKeyStr);
            // String privateKeyStr = getPrivateKey(keyMap);
            // System.out.println("私钥-----------》" + privateKeyStr);

            /**
             * 测试公钥加密后 使用公私钥对的对应的私钥进行解密行为
             * 
             */
            /**
             * 代码生成的公私钥对
             * 
             **/
            // String publicKeyStr =
            // "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAglHsJ42+hj3TBiZ0EK4E7FS1MbSrh9Qd4hlnIXTtXuGlsQb7TMHqcUfeRjS9NWwbWT4PsLdvzyArBOvT1Aw92BvbuVT+GlnQasIBsW1jQWcb6YwA6RhfKwt/B3xigZN7wdqEQ/NB6FYNcjMzAQMGEH8rtlpIsOCJoKlYt4rzaRM3yLT9T3uszACt/FXH5kME/eezgwMgYXQiAPM0oAFM8NID/R4MkccEpisYaYnjAA9ZQOpYArzL4ece15d0QiBpSvNYlIy7JQOAWYptJYdnspke3exxYPiN+V4Jy0I5TLsFENGTsGfdBjCYxBthF+WPMdSfsUMbo+MAcRbjhWgxHwIDAQAB";
            // String privateKeyStr =
            // "MIIEvAIBADANBgkqhkiG9w0BAQEFAASCBKYwggSiAgEAAoIBAQCCUewnjb6GPdMGJnQQrgTsVLUxtKuH1B3iGWchdO1e4aWxBvtMwepxR95GNL01bBtZPg+wt2/PICsE69PUDD3YG9u5VP4aWdBqwgGxbWNBZxvpjADpGF8rC38HfGKBk3vB2oRD80HoVg1yMzMBAwYQfyu2Wkiw4ImgqVi3ivNpEzfItP1Pe6zMAK38VcfmQwT957ODAyBhdCIA8zSgAUzw0gP9HgyRxwSmKxhpieMAD1lA6lgCvMvh5x7Xl3RCIGlK81iUjLslA4BZim0lh2eymR7d7HFg+I35XgnLQjlMuwUQ0ZOwZ90GMJjEG2EX5Y8x1J+xQxuj4wBxFuOFaDEfAgMBAAECggEAbqVVpyOOvvVg5NjjNII7hdhJ20D1Xia3lI9S47IeE6/r+QjfdoUyIyoMy3jdqKz372Zk9fpZAPfj7pK30FLwczr77DgAYqkc7nd37ofcfNwpc7o9z6q0qpxhJx7xl+h7a9S29eZkb/oYAnz78gD6NilzGKlBDIQn9fOycmuUTQIg3UOoflD/GFSRegiX+mIm6Jh0ORntZDDL3ic/Ku1Cx1Wmh4Ygmb5s1yqSYDafML4aQn2M80Mv0dd3VZkGqMnQVn9vxM2zvr86Iie82M3KrEYv+DientXmJSduc38n3jIhRpd3tfg3Vc5RlaiWzSU8o6Pg5ISFLQyGlnW+D0tVMQKBgQC69Qo0IOSMuar0HGCfhTlOlAypqEXzxs7bwtqDGKnHBoAmH7Sc6y5W4KccITiWmMgcVKi/0sOGQFXb+rUzw98lPuk1+tdI/X1XMSCEJsVS8vzjNSqyh24I6ARJmXaz/hX2PVbrUzA52vfSIhuL9vNiggTMnZnhFZ1t9lg3chu2WQKBgQCycmU/G9cJgN6gi33zuAh4EK/IcpA0Q4iy5PFDdGmSv6oIGC3OHTe3ElGYcUJ9yUqemU4weACn5NyFAyICD86VBgcGGP2+ixbWNrDB2EUUspO062VmWiJurPbH86P8F7KqL49VA8iVNvIz/QPuAGnydJ23F9m2gujFnx8aB1ukNwKBgE92PPsBDAMgGyAcO5kxl7TlSiiGBA1j76IKRLO5tNeJ1Mffa/XSI+T7ISvclt+zWkJoXT0CyOXzzEpuWbwBwYNkJN0zm3NDgpWfpNFmZTInYwLzIw20cDlK3B+oIYLJOO5rDdmBhxrgDbr7kjfAI5w35PBPAAtiE4KcVMxkLJEpAoGAZKF6UzKB+8v+wA6qAqz/qaXPW3yvsal8B1ZwcBfQCX+ohVQNULTNXKIp/YuxcXn4aKQPoemqhQPdD+2KWhtL5mI1x8yEG9YYFMlWuJobquZhwZ8V2bhuvncv2+abzWgXyz1y2QB/NxZ7U7IVazw0gxQcdMowbmo4lml7gpQvq9UCgYBPSqVburlYNHV2Crh7vDMBUHAXTyK2Q7/7YknbDU7dxvCcZ1xCQdaRVdNff+FXhQtx536YE60WNbacmujdrBSgUVtpd7uE6HuNUU02TUme0yVBWTYU8KdzglCbiBieRu4uBz+wFaslpOzfO/sCe2zkdnXrh9XNlj5nn7+KK2ZlSg==";

            /**
             * 使用支付宝提供的工具生成的公私钥对。 工具地址：https://docs.open.alipay.com/291/105971/
             ***/
            String publicKeyStr = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAkW6sPD5CfUrwfu40ccA8ME7Qg2f0mqUrdpW9xg+7BxUjQwQ76EC0SQZOh5Uw74IDftxw5Texe63yjPRAAd80aYPDrMisvck201O3PIfflCvGnCFZLFSO4k39waOHusaT9TtskCc0IvqqrosrI95N6+F8TlPIfd71f43BGX7DIf/Bd6fFgDe9p2OFx1yqRxP9KvaNtTYLZszFftfhgaATv9vk5ip5OK+aE7DwxObOk1KgPE7q3rvTZgCGdTK8DFt4NqWxx+QnG8Ej6+V936+nyVPNukjyTT3Q2oBKRXtEiOuJKHg2dmstHpTJHzixlplMBd2fdTcIMZ11WdUB9o3+BQIDAQAB";
            String privateKeyStr = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQCRbqw8PkJ9SvB+7jRxwDwwTtCDZ/SapSt2lb3GD7sHFSNDBDvoQLRJBk6HlTDvggN+3HDlN7F7rfKM9EAB3zRpg8OsyKy9yTbTU7c8h9+UK8acIVksVI7iTf3Bo4e6xpP1O2yQJzQi+qquiysj3k3r4XxOU8h93vV/jcEZfsMh/8F3p8WAN72nY4XHXKpHE/0q9o21NgtmzMV+1+GBoBO/2+TmKnk4r5oTsPDE5s6TUqA8Tureu9NmAIZ1MrwMW3g2pbHH5CcbwSPr5X3fr6fJU826SPJNPdDagEpFe0SI64koeDZ2ay0elMkfOLGWmUwF3Z91NwgxnXVZ1QH2jf4FAgMBAAECggEAa6wdtoh935eBEOi5SVKC/V0MwY1QqsIIuMZKd34kAS1kKvTSibSGBd62e2k4jsqaXe1FZF9kduYlQrh/PyOLAWe9kooMYoK7paBAdH9AR8pVRDjodYqxMtl9O0Ugc2ccGh8B1IRB9HfTsGB28xpeZHpP9wnO0WomJmbEBWU1gVBgTJorDRhaQ+zVH86pXfBBic9HMptaqho7ReTWplVoeBokhDxIzcYVErqmjRnnGOjLAJ3rPfZ/dGiOaeWQUwp5s4cj7MNviEnwYPLKyJSFKXVWhJMQ2Na5HnbMZifiLZr/YeTawRXIMl5cXrBEUnBeCs6zwV4SykP2oTp3GZrJDQKBgQDlzwhOnkwzXVZuKMw9VhlGcGcJFXTCvvFLhlHLliJKIRP1vhaesGLRw0xy4pHmZO583iqjiSjKAqT4m06P96MryrMim+3UI/ifdOc1T1CI3gcjIZdGG7EKEyj6TZqC2JmWgH4YjvWkM8Njv0Eil150E2Qi/mDJnWTaHMLR1crjQwKBgQCiAdlpyouDjjenbleQgQ4MTQklrTNnWBrYZVjqEyrFDGi9evs+8OCfueusclr2fFG5o3Qsw77J9FgH14nNdO9wpwOObaW2/NyULBlANxqs/OXWA3/oE9D27q4Kg66BbdB6gpmhRfIC9mFRYjNiQ0WKdkGWrOLE3RcVNFFLFUFxFwKBgQCtLY4aCMptrrnSO+ZllntnjI7o1oVtuIyHmIoNPG9Mo46yEnRjU5K0dlMnBDxkfc0GxjklkdfuG34MZqUWcsxA1PiXbSAVTKIwL92IW+PZsE96lvaze2y3peascUytq+JZ+yyK/zh3bBajFV5VXfJs0KwWftGbPYPhpejeThkdlwKBgAUxk/u9P42/IG+grPP3/zBLw/BORiiY/XtUfG48oRFiHXV9nNJevITCXBjSLbJbN++AmvfYaqRkzfGqakOI7SgqYoXNZBOitZuWclTJr3Q+Q0wCHrac9AUGj9PMduq1T4qt5yjxahnINkR6pnUO/Qq8Ucv3zOlMwTGv1K9YhhmPAoGBAJHlZEOXo/cH0+jP6dhOiJ1ZTZ/u5lxzGNG4QEd83SlfI2wzgmUCbGo5XUH4Ujn8UBjB9uK1vML/WY0ekEPp6IdLuUlz0koiboOpETpK59z9WVq2kAVb9XAq4VcxnCuieS7pWELlMgSLn57APUQcUbNpKpIyCBgmf9Hy1V/ofnAL";

            /** 测试使用公钥加密 **/
            String str = "123456";
            String encryptStr = publicKeyEncrypt(str, publicKeyStr);
            System.out.println("[公钥加密]秘文:" + encryptStr);
            // 私钥解密 传入密文和密钥
            String str2 = privateKeyDecrypt(encryptStr, privateKeyStr);
            System.out.println("[私钥解密]明文:" + str2);

            /**
             * 验证签名 使用私钥签名后 对方使用公私钥对的对应公钥进行验签行为。
             * 
             * 
             **/
            String originalStr = "name=123&pwd=3435&type=2";
            String charset = "utf-8";

            String signRsa = privateKeySign(privateKeyStr, originalStr, charset, "RSA");

            System.out.println("RSA签名：***************-----》" + signRsa);

            boolean flag = publicKeyCheck(signRsa, originalStr, publicKeyStr, charset, "RSA");
            System.out.println("RSA验签：********---》" + flag);

            String signRsa2 = privateKeySign(privateKeyStr, originalStr, "utf-8", "RSA2");
            System.out.println("RSA2签名：*****************----》" + signRsa2);
            flag = publicKeyCheck(signRsa2, originalStr, publicKeyStr, charset, "RSA2");
            System.out.println("RSA2验签：********---》" + flag);

            /** j wt token **/

            // 获取签名私钥
            // KeyPair keyPair = JwtRsaUtil.getInstance().getKeyPair("private");

            // 生成JWT
            long nowMillis = System.currentTimeMillis();
            long expMillis = nowMillis + 10000;
            Date exp = new Date(expMillis);

            // 根据私钥串获取私钥
            PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(decryptBASE64(privateKeyStr));
            KeyFactory keyFactory2 = KeyFactory.getInstance("RSA");
            PrivateKey privateKey = keyFactory2.generatePrivate(pkcs8EncodedKeySpec);

            //
            Map<String, Object> headerMap = new HashMap<String, Object>();
            headerMap.put("typ", "JWT");
            headerMap.put("alg", "RS256");
            String JWT = Jwts.builder().setHeaderParams(headerMap)
                    // 保存权限（角色）
                    .claim("authorities", "ROLE_ADMIN,AUTH_WRITE")
                    // 用户名写入标题
                    .setSubject("test")
                    // 有效期设置
                    .setExpiration(exp)
                    // 签名设置
                    .signWith(SignatureAlgorithm.RS256, privateKey).compact();

            System.out.println("*******************" + "token:" + JWT);

            // 验签 ，根据公钥进行token 验证。
            X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(decryptBASE64(publicKeyStr));
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            PublicKey publicKey = keyFactory.generatePublic(x509EncodedKeySpec);

            // 解析 Token
            Claims claims = Jwts.parser()
                    // 验签
                    // .setSigningKey(SECRET)
                    .setSigningKey(publicKey)
                    // 去掉 Bearer
                    .parseClaimsJws(JWT.replace("test", "")).getBody();

            System.out.println("***********" + claims.getSubject());
            claims.getAudience();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}