package ymm.springbootshiro.config;

import lombok.Data;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import ymm.springbootshiro.config.Realm.UserRealm;

import java.util.LinkedHashMap;

/**
 * @Author: ymm
 * @Date: 2018/8/31 14:18
 * @Description:
 */
@Configuration
@Data
public class ShiroConfig {

    /*这个map用来控制url需要哪些权限，key是路径，value是需要的权限*/
    @Bean("filterChainDefinitionMap")
    public LinkedHashMap<String, String> getFilterChainDefinitionMap() {
        LinkedHashMap<String, String> map = new LinkedHashMap();
        map.put("/index", "anon");
        map.put("/userLogin", "anon");
        map.put("/add", "perms[user:add]");//添加页面一定要有user:add这个权限
        map.put("/*", "authc");
        return map;
    }


    /*创建ShiroFilterFactoryBean*/
    @Bean
    public ShiroFilterFactoryBean getShiroFilterFactoryBean(@Qualifier("securityManager") DefaultWebSecurityManager securityManager,
                                                            @Qualifier("filterChainDefinitionMap") LinkedHashMap map) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        //设置安全管理器
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        //设计登陆页面
        shiroFilterFactoryBean.setLoginUrl("/login");
        //添加shiro内置过滤器
        shiroFilterFactoryBean.setFilterChainDefinitionMap(map);

        return shiroFilterFactoryBean;
    }


    /*创建 DefaultWebSecurityManager*/
    @Bean("securityManager")
    public DefaultWebSecurityManager getDefaultWebSecurityManager(@Qualifier("userRealm") UserRealm userRealm) {
        DefaultWebSecurityManager defaultWebSecurityManager = new DefaultWebSecurityManager();
        defaultWebSecurityManager.setRealm(userRealm);
        return defaultWebSecurityManager;
    }

    /*创建Realm*/
    @Bean("userRealm")
    public UserRealm getRealm() {
        return new UserRealm();
    }
}
