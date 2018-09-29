package util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 编写jwt规范的工具类
 * 过期时间自己决定，把过期时间编写到配置文件中，读取配置文件，获取到过期时间。
 * SpringBoot架构读取application.yml配置文件的值
 * 秘钥编写到配置文件中
 */
@ConfigurationProperties(value = "jwt.config")
public class JwtUtils {

    // 过期时间
    private long num;
    // 秘钥
    private String key;

    public long getNum() {
        return num;
    }
    public void setNum(long num) {
        this.num = num;
    }
    public String getKey() {
        return key;
    }
    public void setKey(String key) {
        this.key = key;
    }

    /**
     * 生成token字符串
     * @param id        主键
     * @param subject   用户名
     * @param roles     角色信息
     * @return
     */
    public String createToken(String id, String subject, String roles){
        JwtBuilder builder = Jwts.builder();
        Map<String,Object> map = new HashMap<>();
        map.put("typ","JWT");
        // 设置头部信息
        builder.setHeader(map);

        // 设置载荷
        builder.setId(id);
        // 用户名
        builder.setSubject(subject);
        // 设置角色
        builder.claim("roles",roles);
        // 设置生成token的时间
        builder.setIssuedAt(new Date());
        // 获取当前时间
        long millis = System.currentTimeMillis();
        // 设置时间，设置一天时间
        // millis += 1000*60*60*24;
        // 设置过期时间
        millis += num;
        // 设置过期时间
        builder.setExpiration(new Date(millis));
        // 设置签名
        builder.signWith(SignatureAlgorithm.HS256,key);

        // 生成token，返回
        return builder.compact();
    }

    /**
     * 解析token字符串，返回载荷对象
     * @param token
     * @return
     */
    public Claims parseToken(String token){
        return Jwts.parser().setSigningKey(key).parseClaimsJws(token).getBody();
    }

}
