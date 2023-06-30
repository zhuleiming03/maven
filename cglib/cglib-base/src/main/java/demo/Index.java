package demo;

import demo.interceptor.MyMethodInterceptor;
import demo.pojo.Cat;
import net.sf.cglib.proxy.Enhancer;

public class Index {

    public static void main(String[] args) {
        //创建Enhancer对象，类似于JDK动态代理的Proxy类，下一步就是设置几个参数
        Enhancer enhancer = new Enhancer();
        //设置目标类的字节码文件
        enhancer.setSuperclass(Cat.class);
        //设置回调函数
        enhancer.setCallback(new MyMethodInterceptor());
        //这里的creat方法就是正式创建代理类
        Cat proxyDog = (Cat) enhancer.create();
        //调用代理类的方法
        proxyDog.run();
        proxyDog.eat();
    }
}
