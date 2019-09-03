package thread;

/**
 * 测试volatile 关键字的作用
 * 用volatile修饰的变量，线程在每次使用变量的时候，都会读取变量修改后的最终的值。volatile很容易被误用，用来进行原子性操作。
 * 如果要深入了解volatile关键字的作用，就必须先来了解一下JVM在运行时候的内存分配过程。
 * 在 java 垃圾回收整理一文中，描述了jvm运行时刻内存的分配。其中有一个内存区域是jvm虚拟机栈，每一个线程运行时都有一个线程栈，
 * 线程栈保存了线程运行时候变量值信息。当线程访问某一个对象时候值的时候，首先通过对象的引用找到对应在堆内存的变量的值，然后把堆内存
 * 变量的具体值load到线程本地内存中，建立一个变量副本，之后线程就不再和对象在堆内存变量值有任何关系，而是直接修改副本变量的值
 * 在修改完之后的某一个时刻（线程退出之前），自动把线程变量副本的值回写到对象在堆中变量。这样在堆中的对象的值就产生变化了
 *
 * @Date 2019/9/3 14:53
 * Created by Wangxuehuo
 */
public class VolatileTest implements Runnable {
    private volatile boolean flag = false;
    private int i = 0;

    public void run() {
        while (!flag) {
            i++;
        }
    }

    public static void main(String[] args) throws InterruptedException {
        VolatileTest vt = new VolatileTest();
        Thread thread = new Thread(vt);
        thread.start();
        Thread.sleep(2000);
        vt.flag = true;
        System.out.println("stope" + vt.i);
    }
}
