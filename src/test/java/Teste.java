//
//
//import javax.imageio.ImageIO;
//import java.awt.*;
//
//import java.awt.image.BufferedImage;
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.IOException;
//import java.io.InputStream;
//import java.net.InetAddress;
//import java.net.UnknownHostException;
//import java.util.List;
//
//
//public class Teste {
//    private static final String ACCESS_TOKEN = "xS9g8-2gA7AAAAAAAAAATAmqaAm6F87MbOk1VwIqqzipXfD-fMqUSGGyPynqqge8";
//
//    public static void main(String[] args) throws AWTException, IOException, DbxException {
//
//        teste();
//
//    }
//
//
//    private static void teste() throws IOException, DbxException {
//        DbxRequestConfig config = DbxRequestConfig.newBuilder("dropbox/java-tutorial").build();
//        DbxClientV2 client = new DbxClientV2(config, ACCESS_TOKEN);
//
//        try (InputStream in = new FileInputStream("C:\\Users\\Daniel\\Desktop\\oauth-20-proteja-suas-aplicacoes-com-o-spring-security-oauth2.pdf")) {
//            FileMetadata metadata = client.files().uploadBuilder("/oauth-2.pdf")
//                    .uploadAndFinish(in);
//        }
//    }
//
//}
