import util.FileTransferClient;

import java.io.File;

/**
 * Created by xiezl on 19-8-19.
 */
public class Main {
    private static String fileName="D:\\upload\\TB_BASE_BOATER.csv";
    // 服务端IP
    private static final String SERVER_IP = "127.0.0.1";
    // 服务端端口
    private static final int SERVER_PORT = 8899; 
    
    public static void main(String[] args) {
        try {
            File file = new File(fileName);
            // 文件存在则开启端口
            while (file.exists()) {
                // 启动客户端连接
                FileTransferClient client = new FileTransferClient(SERVER_IP, SERVER_PORT);
                // 传输文件
                client.sendFile(fileName); 
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
