import java.net.*;
import java.io.*;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.regex.*;

public class Principal {
    
    static String ip;
    static int numsocket;
    static Socket socket;
    static String direccion;
    static String nombre;
    public static final String IPv4_REGEX ="^(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\." +
            "(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\." +
            "(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\." +
            "(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$";
    public static final Pattern IPv4_PATTERN = Pattern.compile(IPv4_REGEX);
    public static String descargas= "Desktop\\";
    
  
    
    public static void main(String[] args) throws Exception{
        try{
        if(args[0]!=null){
            ip= args[0];
            if(!isValidInet4Address(ip)){
                System.out.println("Escriba una ip valida");
            System.exit(0);
            }
        }else{
            System.out.println("Escriba una ip valida");
            System.exit(0);
        }
        }catch(Exception e){System.out.println("Escriba una IP valida");System.exit(0);}
        try{
        if(args[1]!=null){
            try {
                numsocket= Integer.parseInt(args[1]);
            } catch (Exception e) {System.out.println("Escriba un socket valido");
            System.exit(1);}
        }else{
            System.out.println("Escriba un socket valido");
            System.exit(2);
        }
        }catch(Exception e){System.out.println("Escriba un socket valido");
            System.exit(2);}
        
        try{
        if(args[2]!=null){
            direccion= args[2];
        }else{
            System.out.println("Escriba una dirección valida");
            System.exit(3);
        }
        }catch(Exception e){System.out.println("Escriba una dirección valida");System.exit(0);}
        
        
        try{
        socket= new Socket(ip, numsocket);
        }catch(Exception e){System.out.println("Hubo un eror al crear el socket");System.exit(3);}
        PrintWriter escritor=new PrintWriter(socket.getOutputStream(),true);
        BufferedReader lector = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        String datosEntrada;
        escritor.println(direccion);
	StringTokenizer tokens=new StringTokenizer(direccion, "\\");
        while(tokens.hasMoreTokens()){
            nombre=tokens.nextToken();
        }
        File file = new File(descargas + nombre);
        if (!file.exists()) {
            file.createNewFile();
        }else{
            file.delete();
            file.createNewFile();
        }
        while(true) {
                datosEntrada=lector.readLine();
                if(datosEntrada==null){
                    socket.close();
                    System.exit(0);
                }else{
                    EscribirArchivo(datosEntrada);
                    System.out.println(datosEntrada);
                }
        }
    }
    
    public static void EscribirArchivo(String cadena) throws IOException{
        BufferedWriter bw = null;
        FileWriter fw = null;
        try{
        File file = new File(descargas + nombre);
        // Si el archivo no existe, se crea!
        if (!file.exists()) {
            file.createNewFile();
        }
        // flag true, indica adjuntar información al archivo.
        fw = new FileWriter(file.getAbsoluteFile(), true);
        bw = new BufferedWriter(fw);
        bw.write(cadena + "\n");
    } catch (IOException e) {
        e.printStackTrace();
    } finally {try {
            if (bw != null)
                bw.close();
            if (fw != null)
                fw.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    }

    public static boolean isValidInet4Address(String ip) {
        if (ip == null) {
            return false;
        }
        Matcher matcher = IPv4_PATTERN.matcher(ip);
        return matcher.matches();
    }
    
}
