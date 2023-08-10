package kim.nzxy.kidding.controller;

import cn.dev33.satoken.stp.StpUtil;
import kim.nzxy.kidding.dto.UserLoginDTO;
import kim.nzxy.kidding.dto.UserRegisterDTO;
import kim.nzxy.kidding.util.AdminWorkplace;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;

/**
 * @author ly-chn
 */
@RestController
@RequestMapping("auth")
@RequiredArgsConstructor
public class AuthController {
    private final AdminWorkplace adminWorkplace;

    @GetMapping("check-login")
    public boolean checkLogin() {
        return StpUtil.isLogin();
    }

    @PostMapping("login")
    public String login(@RequestBody UserLoginDTO loginInfo) {
        boolean enable = adminWorkplace.readBool(String.format("账号: %s, 密码: %s 请求登录, 是否允许? ",
                        loginInfo.getUsername(),
                        loginInfo.getPassword()),
                Duration.ofSeconds(10));
        if (enable) {
            StpUtil.login(loginInfo.getUsername());
            return "登录成功";
        }
        return "管理员不让你登录";
    }

    @GetMapping("login")
    public String loginForGet(UserLoginDTO loginInfo) {
        return login(loginInfo);
    }

    @PostMapping("register")
    public String register(@RequestBody UserRegisterDTO registerInfo) {
        boolean enable = adminWorkplace.readBool(String.format("账号: %s, 密码: %s 申请注册, 是否允许? ",
                        registerInfo.getUsername(),
                        registerInfo.getPassword()),
                Duration.ofSeconds(10));
        if (enable) {
            StpUtil.login(registerInfo.getUsername());
            return "注册成功, 您可以使用该账号登录了";
        }
        return "管理员拒绝了您的注册请求";
    }

    @GetMapping("register")
    public String registerForGet(UserRegisterDTO registerInfo) {
        return register(registerInfo);
    }
}
