package douyin.logic;

import java.util.Random;

public class LetterUtil {

    public static String getRandomLetter(String letter){
        if(letter==null){
            return null;
        }
        if(!letter.contains("@")){
            return letter;
        }
        String strs[]=letter.split("@");
        int size=strs.length;
        Random random=new Random();
        int index=random.nextInt(size);
        return strs[index];
    }

}
