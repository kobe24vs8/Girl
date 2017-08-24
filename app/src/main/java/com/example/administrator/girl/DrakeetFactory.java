package com.example.administrator.girl;

/*
 * 项目名：   Girl
 * 包名:     com.example.administrator.girl
 * 文件名:   DrakeetFactory
 * 创建者:   LDW
 * 创建时间: 2017/8/10  16:51
 * 描述:    TODO
 */
public class DrakeetFactory {

    private static final Object monitor = new Object();

    static GankApi sGankIOSingleton = null;
    static DrakeetApi sDrakeetSingleton = null;

    public static boolean debug = true;

    //单次请求的数据长度
    public static final int meizhiSize = 10;
    public static final int gankSize = 10;

    //线程安全的懒汉式单例的创建
    //利用synchronized修饰，当一个线程执行到这个方法或者代码块的时候，该部分被锁定，
    //之后的其它执行到这些代码的线程被阻塞，直到上一个线程执行完毕，释放同步锁
    public static GankApi getGankIOSingleton() {
        synchronized (monitor) {
            if (sGankIOSingleton == null) {
                sGankIOSingleton = new DrakeetRetrofit().getGankService();
            }
            return sGankIOSingleton;
        }
    }
/*
    synchronized同步块处括号中的锁定对象采用的是一个无关的Object类实例。将它作为锁而不是通常synchronized所用的this，
    其原因是getInstance方法是一个静态方法，在它的内部不能使用未静态的或者未实例化的类对象（避免空指针异常）。
    同时也没有直接使用instance作为锁定的对象，是因为加锁之时，instance可能还没实例化（同样是为了避免空指针异常）
*/
    public static DrakeetApi getDrakeetSingleton() {
        synchronized (monitor) {
            if (sDrakeetSingleton == null) {
                sDrakeetSingleton = new DrakeetRetrofit().getDrakeetService();
            }
            return sDrakeetSingleton;
        }
    }

}
