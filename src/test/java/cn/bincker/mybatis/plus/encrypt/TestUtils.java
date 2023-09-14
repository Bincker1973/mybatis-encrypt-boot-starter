package cn.bincker.mybatis.plus.encrypt;

public class TestUtils {
    public static String propertyNameToFieldName(String propertyName){
        var result = new StringBuilder();
        for (char c : propertyName.toCharArray()) {
            if (Character.isUpperCase(c)){
                result.append('_');
            }
            result.append(c);
        }
        return result.toString();
    }
}
