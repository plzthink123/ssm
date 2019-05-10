package com.think123.ssm.controller;

import com.think123.domain.SysLog;
import com.think123.service.ISysLogService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Date;

@Component
@Aspect
public class LogAop {
    @AutoWired
    private ISysLogService sysLogService;

    @AutoWired
    private HttpServletRequest request;

    private Date visitTime;//访问时间
    private Class clazz;//访问的类
    private Method method;//访问的方法
    /**
     * 前置通知,主要获取开始时间,执行的类是哪一个,执行的方法是哪一个
     * @param jp
     */
    @Before("execution(* com.think123.ssm.controller.*.*(..))")
    public void doBefore(JoinPoint jp) throws NoSuchMethodException {
        //获取当前的访问时间
        visitTime=new Date();
        //具体要访问的类对象
        //利用JoinPoint对象方法
        clazz = jp.getTarget().getClass();
        String methodName = jp.getSignature().getName();
        //获取访问方法的参数
        Object[] args = jp.getArgs();
        //获取执行的方法的Method对象
        if(args==null&&args.length==0){
            method=clazz.getMethod(methodName);//只能获取无参数的方法
        }else{
            //装参数的class
            Class[] classArgs=new Class[args.length];
            for(int i=0;i<args.length;i++){
                classArgs[i]=args[i].getClass();
            }
            /*
                参数含义:
                    methodName:方法名称
                    classArgs:带有参数的方法,所带参数的Class对象数组
             */
            method=clazz.getMethod(methodName,classArgs);
        }

    }
    //后置通知
    @After("execution(* com.think123.ssm.controller.*.*(..))")
    public void doAfter(JoinPoint jp) throws Exception {
        long time=new Date().getTime()-visitTime.getTime();
        String url="";
        //获取url开始
        //判断如果是访问的clazz为当前LogAop.class则不做处理
        if(clazz!=null&&method!=null&&clazz!= LogAop.class){
            //1.获取类上的注解
            RequestMapping classAnnotation = (RequestMapping) clazz.getAnnotation(RequestMapping.class);
            if(classAnnotation!=null){
                //返回class的String数组
                String[] classValue = classAnnotation.value();
                //2.获取方法上的value值
                RequestMapping methodAnnotation = method.getAnnotation(RequestMapping.class);
                if(methodAnnotation!=null){
                    String[] methodValue = methodAnnotation.value();
                    url=classValue[0]+methodValue[0];
                }
            }
        }
        //获取url结束

        //获取访问的ip地址
        String ip=request.getRemoteAddr();
        //获取访问的ip地址结束
        /*
        获取当前操作的用户
            方法1:通过springSecurityContext获取
            方法2:通过request获取
         */
        SecurityContext context = SecurityContextHolder.getContext();//从上下文中获取当前登录的用户
        User user = (User) context.getAuthentication().getPrincipal();
        String username=user.getUsername();
        //获取当前操作的用户结束
        //将日志相关信息封装到syslog对象中
        SysLog sysLog=new SysLog();
        sysLog.setExecutionTime(time);
        sysLog.setIp(ip);
        sysLog.setUsername(username);
        sysLog.setVisitTime(visitTime);
        sysLog.setUrl(url);
        sysLog.setMethod("[类名] "+clazz.getName()+"[方法名] "+method.getName());
        //调用service完成日志的记录操作
        sysLogService.save(sysLog);
    }
}