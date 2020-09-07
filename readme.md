## 失败
> 预期 登录localhost 应该会在浏览器弹出认证框，输入用户名和密码 admin/123 可以成功登录
> 但是，事实是 似乎只是pom文件里引入的依赖包生效了。 而core里的配置类没有生效。
> core里的配置类是正确的，因为把配置类放入web目录下，项目就正常了。
>
>


>当设置 maxSessionsPreventsLogin(true) 同一用户在第2个地方登录时，则不允许他登录。
>但是一旦设置后，RememberMe 记住我功能会失效.原因如下:

``` 
执行流程:
RememberMeAuthenticationFilter 调用> SessionManagementFilter 调用> CompositeSessionAuthenticationStrategy 其中执行了以下3个策略:
1. ConcurrentSessionControlAuthenticationStrategy.allowableSessionsExceeded 只要.maxSessionsPreventsLogin(true)就上面会抛出异常
2. ChangeSessionIdAuthenticationStrategy 3. RegisterSessionAuthenticationStrategy```


1. 在内存中存放用户名密码
2. 在配置文件中存放用户名和密码
3. 在数据库中存放用户名和密码
4. 使用html 页面登录
5. 使用弹窗 登录
6. 使用特定的登录页面登录
7. 设置登录成功后 进入的html页面
8. 设置登录成功后 返回的json串
9. 设置登录失败后 进入的html页面
10. 设置登录失败后 返回的json串
11. 使用记住我功能
12. 增加手机号登录页面
13. 登录失败后进入特定的错误页面
14. 登录失败后错误页面显示错误信息
15. 增加记住我功能
16. 会话管理 二次登录，首次下线；二次登录，拒绝上线
17. 会话保存在redis
18. 退出功能




