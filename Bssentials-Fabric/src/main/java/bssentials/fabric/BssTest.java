package bssentials.fabric;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.io.*;
import java.util.Scanner;

public class BssTest {
    
    private static String JRE = "C:\\Program Files\\Eclipse Adoptium\\jdk-17.0.1.12-hotspot\\bin\\";
    
    public static void main(String[] args) throws IOException, InterruptedException {
        ProcessBuilder pbg = new ProcessBuilder("gradlew.bat", "build")
                .inheritIO()
                .directory(new File("C:\\Users\\admin\\mods\\Bssentials\\Bssentials-Fabric"));
        pbg.start().waitFor();
        
        File out = new File("C:\\Users\\admin\\mods\\Bssentials\\Bssentials-Fabric\\build\\libs\\Bssentials-Fabric.jar");
        File to  = new File("C:\\Users\\admin\\Desktop\\Fab-1.18\\mods\\Bssentials-Fabric.jar");
        Files.move(out.toPath(), to.toPath(), StandardCopyOption.REPLACE_EXISTING);
        
        ProcessBuilder pb = new ProcessBuilder(JRE + "java.exe", "-Xmx2G", "-jar", "fabric-server-launch.jar", "nogui")
                .directory(new File("C:\\Users\\admin\\Desktop\\Fab-1.18"));
        Process p = pb.start();
        
        OutputStream stdin = p.getOutputStream();
        InputStream stdout = p.getInputStream();
        InputStream stderr = p.getErrorStream();

        BufferedReader reader = new BufferedReader(new InputStreamReader(stdout));
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(stdin));
        BufferedReader error = new BufferedReader(new InputStreamReader(stderr));
        
        new Thread(() -> {
            String read;
            try {
                while ((read = reader.readLine()) != null) {
                    if (!(read.contains("Preparing spawn") || read.contains("Ambiguity"))) {
                        System.out.println(read);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();

        new Thread(() -> {
            String read;
            try {
                while ((read = error.readLine()) != null) {
                    System.out.println(read);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();

        new Thread(() -> {
            while (true) {
                try {
                    Scanner scanner = new Scanner(System.in);
                    writer.write(scanner.nextLine());
                    writer.newLine();
                    writer.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        
        
        
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
                writer.write("stop");
                writer.newLine();
                writer.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }));
    }

}
