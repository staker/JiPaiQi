package douyin.logic;

import java.util.Random;

public class RandomUtil {
    public final static String[] emojs={"~@^_^@~ ","~*.*~ ","#^_^#","∩__∩y","(*^@^*)","X﹏X",
            "(° ?°)~@","[{(>_<)]} ","(╯-╰)","::>_<:: ","〒_〒","%>_<%","╰_╯",">_<|||","⊙﹏⊙‖∣°","^_^\"",
            "→_→","..@_@|||||.. ","`(*^﹏^*)′","`(*∩_∩*)′","(～ o ～)~zZ",
            "(^人^) ","(^_^)/~~","└(^o^)┘","~^o^~","Y(^_^)Y","…(⊙_⊙;)…","`(*>﹏<*)′","*@_@*","O__O\"",">\"<|||| ","(*^?;^*) ","(^_^)∠※","(>﹏<)","(ˋ^ˊ〉-#","@x@",
            "(*^?;^*) ",">_<# ","一 一+ "};
    public static String getRandomEmoj(){
        int size=emojs.length;
        Random random=new Random();
        String text=emojs[random.nextInt(size)];
        return text;
    }


    /**
     * 根据传过来的概率数，判断是否正好击中
     * @param probabilityCount
     * @return
     */
    public static boolean isRandomTrue(int probabilityCount){
            if(probabilityCount==0){
                return false;
            }
            if(probabilityCount==1){
                return true;
            }
            Random random=new Random();
            int value=random.nextInt(probabilityCount);
            if(value==0){
                return true;
            }
            return false;
    }


}
