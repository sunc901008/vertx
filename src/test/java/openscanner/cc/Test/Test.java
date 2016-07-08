package openscanner.cc.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

/**
 * Created : sunc
 * Date : 16-6-17
 * Description :
 */
public class Test{

    public static void main(String [] args) throws Exception{
////        StringBuffer sb= new StringBuffer("");
//        FileReader reader = new FileReader("en");
//
//        BufferedReader br = new BufferedReader(reader);
//
//        String str = null;
//
//        while((str = br.readLine()) != null) {
//
////            sb.append(str+"/n");
//
//            if("".equals(str)) continue;
//
//            str = "<p>" + str.substring(str.indexOf("=") + 1).trim() + "</p>";
//
//            System.out.println(str);
//        }
//
//        br.close();
//
//        reader.close();

        String[] jwt = null;
        String tokenO = "";
        if(jwt != null)
        for(int i=0; i < jwt.length; i++){
            String tmp = jwt[i].trim();
            if(tmp.startsWith("jwt")){
                tokenO = tmp.substring(tmp.indexOf("=")+1);
                break;
            }
        }
        System.out.println(tokenO);
    }
}
