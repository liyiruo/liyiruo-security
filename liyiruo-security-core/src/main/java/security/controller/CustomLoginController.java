package security.controller;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;

//@Controller
@Slf4j
public class CustomLoginController {
    private static final String SESSION_KEY = "SESSION_KEY_IMAGE_CODE";
    /**
     * 前往认证界面
     * @return
     */
    @RequestMapping("/login/page")
    public String toLogin() {
        return "login";
    }

    /**
     * 生成验证码
     */
    @Autowired
    private DefaultKaptcha defaultKaptcha;
    @RequestMapping("/code/image")
    public void imageCode(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //1.获取验证码的字符串
        String text = defaultKaptcha.createText();
        log.info("生成的图形验证码是:" + text);
        // 2. 字符串把它放到session中
        request.getSession().setAttribute(SESSION_KEY,text);
        // 3. 获取验证码图片
        BufferedImage image = defaultKaptcha.createImage(text);
        // 4. 将验证码图片把它写出去
        ServletOutputStream out = response.getOutputStream();
        ImageIO.write(image, "jpg", out);
    }
}
