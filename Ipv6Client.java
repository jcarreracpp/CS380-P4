
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;

/**
 *
 * @author Jorge
 */
public class Ipv6Client {
    public static void main(String[] args) throws Exception {
        try (Socket socket = new Socket("codebank.xyz", 38004)) {
            int payload;
            
            InputStream is = socket.getInputStream();
            InputStreamReader isr = new InputStreamReader(is, "UTF-8");
            BufferedReader br = new BufferedReader(isr);
            OutputStream os = socket.getOutputStream();
            
            for(int i = 0; i < 12; i++ ){
                payload = 2;
                for(int j = i; j > 0; j--){
                    payload *=2;
                }
                
            os.write(recalculatePacket(payload));
            System.out.println("data length: "+payload);
            int[] read = new int[4];
            read[0]  = is.read();
            read[1] = is.read();
            read[2] = is.read();
            read[3] = is.read();
            
            for(int mn = 0; mn < 4; mn++){
                System.out.printf("%X", (read[mn]));
            }
            System.out.println();
            }
        }
    }
    public static byte[] recalculatePacket(int size) {
            int headertop = 6;
            headertop <<= 28;
            short paylen = (short)(size);
            byte nextHead = 17;
            byte hopLim = 20;
            long addrBuffers = 65535;
            int sourceAddr = 1824010952;
            int destAddr = 874862746;
            byte[] temp = new byte[(40+size)];
            temp[0] = (byte)(headertop >> 24);
            temp[1] = (byte)(headertop >> 16);
            temp[2] = (byte)(headertop >> 8);
            temp[3] = (byte)(headertop);
            temp[4] = (byte)(paylen >> 8);
            temp[5] = (byte)(paylen);
            temp[6] = nextHead;
            temp[7] = hopLim;
            temp[8] = 0;
            temp[9] = 0;
            temp[10] = 0;
            temp[11] = 0;
            temp[12] = 0;
            temp[13] = 0;
            temp[14] = 0;
            temp[15] = 0;
            temp[16] = 0;
            temp[17] = 0;
            temp[18] = (byte)(addrBuffers);
            temp[19] = (byte)(addrBuffers);
            temp[20] = (byte) (sourceAddr >> 24);
            temp[21] = (byte) (sourceAddr >> 16);
            temp[22] = (byte) (sourceAddr >> 8);
            temp[23] = (byte) (sourceAddr);
            temp[24] = 0;
            temp[25] = 0;
            temp[26] = 0;
            temp[27] = 0;
            temp[28] = 0;
            temp[29] = 0;
            temp[30] = 0;
            temp[31] = 0;
            temp[32] = 0;
            temp[33] = 0;
            temp[34] = (byte)(addrBuffers);
            temp[35] = (byte)(addrBuffers);
            temp[36] = (byte) (destAddr >> 24);
            temp[37] = (byte) (destAddr >> 16);
            temp[38] = (byte) (destAddr >> 8);
            temp[39] = (byte) (destAddr);
            
            for(int k = 40; k < (40 + size); k++){
                temp[k] = 0;
            }
            
            return temp;
    }
}
