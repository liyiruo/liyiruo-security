## 失败
> 预期 登录localhost 应该会在浏览器弹出认证框，输入用户名和密码 admin/123 可以成功登录
> 但是，事实是 似乎只是pom文件里引入的依赖包生效了。 而core里的配置类没有生效。
> core里的配置类是正确的，因为把配置类放入web目录下，项目就正常了。