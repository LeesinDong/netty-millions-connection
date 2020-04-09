package netty.thread;

/**
 * @description:
 * @author: Leesin.Dong
 * @date: Created in 2020/4/9 15:24
 * @version: ${VERSION}
 * @modified By:
 */
public class Test {
    //把操作交给其他的线程，不会阻塞主线程
    public static void main(String[] args) {
        new Thread(()->{
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("哈哈哈哈哈");
        }).start();

        System.out.println("完成");
    }
}
