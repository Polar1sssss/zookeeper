package com.hujtb.zookeeper;

import org.apache.zookeeper.*;

import java.io.IOException;

/**
 * @author hujtb
 * @create on 2019-01-09-15:55
 */
public class DistributeServer {

    private ZooKeeper zkClient = null;
    private String connectString = "hadoop1:2181";
    private int sessionTimeOut = 2000;

    public static void main(String[] args) throws Exception {

        args = new String[]{"hadoop1"};
        DistributeServer server = new DistributeServer();

        //����zk��Ⱥ
        server.getConnect();

        //ע��ڵ�
        server.regist(args[0]);

        //ҵ���߼�����
        server.business();

    }

    private void getConnect() throws IOException {
        zkClient = new ZooKeeper(connectString, sessionTimeOut, new Watcher() {
            @Override
            public void process(WatchedEvent watchedEvent) {

            }
        });
    }

    private void regist(String hostname) throws KeeperException, InterruptedException {
        // �ڵ㴴��Ҫ�ظ�ִ�У��������ػ��ڵ����ߣ����Դ�����Ӧ���Ƕ��ݴ���ŵĽڵ�
        String path = zkClient.create("/servers/server", hostname.getBytes(),
                ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
        System.out.println(hostname + " is online ");
    }

    private void business() throws InterruptedException {
        Thread.sleep(Long.MAX_VALUE);
    }
}
