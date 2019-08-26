package util;

import java.io.*;
import java.net.Socket;

/**
 * Created by xiezl on 19-8-15.
 */
public class FileTransferClient extends Socket {
    private Socket client;

    private FileInputStream fis;

    private DataOutputStream dos;

    public FileTransferClient(String SERVER_IP, int SERVER_PORT) throws Exception {
        super(SERVER_IP, SERVER_PORT);
        this.client = this;
        System.out.println("Cliect[port:" + client.getLocalPort() + "] 成功连接服务端");
    }

    public void sendFile(String fileName) throws Exception {
        try {
            File file = new File(fileName);
            System.out.println("文件大小：" + file.length() + "kb");
            fis = new FileInputStream(file);
            dos = new DataOutputStream(client.getOutputStream());

            // 文件名和长度
            dos.writeUTF(file.getName());
            // 清空缓冲区数据
            dos.flush();
            dos.writeLong(file.length());
            dos.flush();

            // 开始传输文件
            System.out.println("======== 开始传输文件 ========");
            byte[] bytes = new byte[1024];
            int length = 0;
            long progress = 0;
            while((length = fis.read(bytes, 0, bytes.length)) != -1) {
                dos.write(bytes, 0, length);
                dos.flush();
                progress += length;
                System.out.print("| " + (100*progress/file.length()) + "% |");
            }
            System.out.println();
            System.out.println("======== 文件传输成功 ========");
            dos.flush();
            System.out.println("文件上传结束");
            dos.close();
            fis.close();
            deleteFile(fileName);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(fis != null)
                fis.close();
            if(dos != null)
                dos.close();
            client.close();
        }
    }

    /**
     * 删除单个文件
     *
     * @param fileName
     *            要删除的文件的文件名
     * @return 单个文件删除成功返回true，否则返回false
     */
    public static boolean deleteFile(String fileName) {
        File file = new File(fileName);
        // 如果文件路径所对应的文件存在，并且是一个文件，则直接删除
        if (file.exists() && file.isFile()) {
            if (file.delete()) {
                System.out.println("删除单个文件" + fileName + "成功！");
                return true;
            } else {
                System.out.println("删除单个文件" + fileName + "失败！");
                return false;
            }
        } else {
            System.out.println("删除单个文件失败：" + fileName + "不存在！");
            return false;
        }
    }
}
