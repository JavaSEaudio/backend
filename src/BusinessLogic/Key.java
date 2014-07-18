package BusinessLogic;

import java.math.BigInteger;
import java.security.SecureRandom;

public class Key {
    private final String value;

    public Key() {
        this.value = generateSecure();
    }

    //Генирация 60 символов больших малих латинскых, а также цифр(как просил Ваня)
    private String generate(){
        String temp = "";
        for(int i=0; i<64;i++){
            int vubor =(int) (Math.random()*3+1);
            switch (vubor){
                case 1:{//генирируем Большие букви
                    temp += (char) ((int)(Math.random()*25+65));
                }
                break;
                case 2:{ //генирируем цифры
                    temp += (char) ((int)(Math.random()*9+48));
                    break;
                }
                case 3:{//Генирируем маленькие буквы
                    temp+=(char)((int)(Math.random()*25+97));
                    break;
                }
            }
        }
        return temp;
    }
    // Генерация с помощью случайного рендома в виде числа
    private String generateSecure(){
        String temp = new BigInteger(130,new SecureRandom()).toString();
        return temp;
    }

    public String getValue() {
        return generate();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        if (!(o instanceof Key)) return false;

        Key key = (Key) o;

        if (!getValue().equals(key.getValue())) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    public static void main(String [] args){
        Key s = new Key();
        System.out.println(s.getValue());

    }
}