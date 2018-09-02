package ymm.springbootshiro.config.Realm;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import ymm.springbootshiro.pojo.User;

/**
 * @Author: ymm
 * @Date: 2018/8/31 14:40
 * @Description:
 */
public class UserRealm extends AuthorizingRealm {
    /*授权*/
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(final PrincipalCollection principalCollection) {
        System.out.println("授权 ");
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        //获取当前用户
        Subject currentUser = SecurityUtils.getSubject();
        //强转
        User u = (User) currentUser.getPrincipal();
        if (u.getUserName().equals("tom")) {
            info.addStringPermission("user:add");
        }

        return info;
    }

    /*认证，就是登陆*/
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(final AuthenticationToken authenticationToken) throws AuthenticationException {
        System.out.println("认证");
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        //这个应该获取数据库中的数据，然后进行比对
        User user = new User();
        user.setPassword(token.getPassword().toString());
        user.setUserName(token.getUsername().toString());
        //比对的过程由shiro帮我们完成，密码为123就可以登录，你可以点进去看其他都构造函数，这里用一个比较简单的
        //这里把user传进去，然后再授权的时候可以获取这个user对象，在26行
        return new SimpleAuthenticationInfo(user, "123", getName());
    }
}
