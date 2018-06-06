package expression;

import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.SpelCompilerMode;
import org.springframework.expression.spel.SpelParserConfiguration;
import org.springframework.expression.spel.standard.SpelExpression;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author jaysunxiao
 * @version 1.0
 * @since 2017-12-21 12:07
 */
public class ExpressionTest {

    @Test
    public void helloWorldTest() {
        ExpressionParser parser = new SpelExpressionParser();
        Expression exp = parser.parseExpression("'Hello'+' World!'");
        String message = exp.getValue(String.class);
        System.out.println(message);
    }


    // 这个会编译，增大内存，实际情况一般不使用
    @Test
    public void complieTest() {
        User user = new User();

        // 1.创建解析器配置，指定编译模式和类加载器
        SpelParserConfiguration configuration =
                new SpelParserConfiguration(SpelCompilerMode.IMMEDIATE, ExpressionTest.class.getClassLoader());

        // 2.创建解析器
        SpelExpressionParser parser = new SpelExpressionParser(configuration);

        // 3.创建取值上下文
        EvaluationContext context = new StandardEvaluationContext(user);

        // 4.表达式
        String expression = "isVipMember('tom') && isVipMember('jony')";

        // 5.解析表达式
        SpelExpression spelExpression = parser.parseRaw(expression);

        // 6.取值
        System.out.println(spelExpression.getValue(context));
        System.out.println(spelExpression.getValue(context));
    }

    @Test
    public void literalTest() {// literal  ['lit·er·al || 'lɪt(ə)rəl]adj.  照字面的; 如实的, 不夸张的; 原义的; 逐字的
        ExpressionParser parser = new SpelExpressionParser();
        //String
        String helloWorld = (String) parser.parseExpression("\"Hello World\"").getValue();
        //double
        double doubleNumber = (Double) parser.parseExpression("6.0221415E+23").getValue();
        //int
        int maxValue = (Integer) parser.parseExpression("0x7FFFFFFF").getValue();
        //bool
        boolean trueValue = (Boolean) parser.parseExpression("true").getValue();
        //null
        Object nullValue = parser.parseExpression("null").getValue();

        System.out.println("Hello World = " + helloWorld);
        System.out.println("6.0221415E+23 = " + doubleNumber);
        System.out.println("0x7FFFFFFF = " + maxValue);
        System.out.println("true = " + trueValue);
        System.out.println("null = " + nullValue);
    }


    @Test
    public void collectionTest() {
        User user = new User();
        user.setUserName("tom");
        user.setCredits(100);
        user.setInterestsArray(new String[]{"Java", "C++"});
        Map<String, String> interestsMap = new HashMap<>();
        interestsMap.put("interest1", "Java");
        interestsMap.put("interest2", "C++");
        user.setInterestsMap(interestsMap);
        List<String> interestsList = new ArrayList<>();
        interestsList.add("Java");
        interestsList.add("C++");
        user.setInterestsList(interestsList);

        ExpressionParser parser = new SpelExpressionParser();
        EvaluationContext context = new StandardEvaluationContext(user);


        List<String> list = (List<String>) parser.parseExpression("interestsList").getValue(context);
        System.out.println("list:" + list);


        Map<String, String> userInfo = (Map<String, String>) parser.parseExpression("interestsMap").getValue(context);
        System.out.println("userInfo:" + userInfo);

        String interest1 = (String) parser.parseExpression("interestsArray[0]").getValue(context);
        String interest2 = (String) parser.parseExpression("interestsList[0]").getValue(context);
        String interest3 = (String) parser.parseExpression("interestsMap['interest1']").getValue(context);
        System.out.println(interest1);
        System.out.println(interest2);
        System.out.println(interest3);
    }


    @Test
    public void operatorTest() {
        User user = new User();
        ExpressionParser parser = new SpelExpressionParser();
        EvaluationContext context = new StandardEvaluationContext(user);

        //AND
        boolean falseValue = parser.parseExpression("true and false").getValue(Boolean.class);
        String expression = "isVipMember('tom') && isVipMember('jony')";
        boolean trueValue = parser.parseExpression(expression).getValue(context, Boolean.class);

        //OR
        trueValue = parser.parseExpression("true or false").getValue(Boolean.class);

        // NOT
        falseValue = parser.parseExpression("!true").getValue(Boolean.class);


        //关系操作符
        trueValue = parser.parseExpression("2 == 2").getValue(Boolean.class);
        falseValue = parser.parseExpression("2 < -5.0").getValue(Boolean.class);
        trueValue = parser.parseExpression("\"black\" < \"block\"").getValue(Boolean.class);

        //正则
        boolean falseValue2 = parser.parseExpression("'xyz' instanceof T(int)").getValue(Boolean.class);
        boolean trueValue2 = parser.parseExpression("'5.00' matches '^-?\\d+(\\.\\d{2})?$'").getValue(Boolean.class);
        boolean falseValue3 = parser.parseExpression("'5.0067' matches '\\^-?\\d+(\\.\\d{2})?$'").getValue(Boolean.class);


        //加法操作
        int two = parser.parseExpression("1 + 1").getValue(Integer.class); // 2
        String testString = parser.parseExpression(
                "\"test\" + ' ' + \"string\"").getValue(String.class); // test string
        //减法操作
        int four = parser.parseExpression("1 - -3").getValue(Integer.class); // 4
        double d = parser.parseExpression("1000.00 - 1e4").getValue(Double.class); // -9000
        // 乘法操作
        int six = parser.parseExpression("-2 * -3").getValue(Integer.class); // 6
        double twentyFour = parser.parseExpression("2.0 * 3e0 * 4").getValue(Double.class); //24.0
        // 除法操作
        int minusTwo = parser.parseExpression("6 / -3").getValue(Integer.class); // -2
        double one = parser.parseExpression("8.0 / 4e0 / 2").getValue(Double.class); // 1.0
        // 求余操作
        int three = parser.parseExpression("7 % 4").getValue(Integer.class); // 3
        one = parser.parseExpression("8 / 5 % 2").getValue(Integer.class); // 1
        //优先级算术运算
        int minusTwentyOne = parser.parseExpression("1+2-3*8").getValue(Integer.class); // -21
    }

    @Test
    public void ifThenElseTest() {
        User user = new User();
        user.setUserName("tom");
        user.setCredits(100);

        ExpressionParser parser = new SpelExpressionParser();
        StandardEvaluationContext context = new StandardEvaluationContext(user);

        String expression = "UserName == 'tom'? Credits+10:Credits";
        Integer credits = parser.parseExpression(expression).getValue(context, Integer.class);
        System.out.println(credits); // 110
    }

    @Test
    public void objectTest() {
        User user = new User();
        user.setUserName("tom");
        ExpressionParser parser = new SpelExpressionParser();
        EvaluationContext context = new StandardEvaluationContext(user);

        // 赋值
        context.setVariable("newUserName", "jony");
        parser.parseExpression("userName=#newUserName").getValue(context);
        System.out.println(user.getUserName()); //jony

        // 集合过滤
        Map<String, Integer> creditsMap = new HashMap();
        creditsMap.put("aTom", 95);
        creditsMap.put("bJony", 110);
        creditsMap.put("cMorin", 85);
        creditsMap.put("dMose", 120);
        creditsMap.put("eMorrow", 60);
        context.setVariable("credits", creditsMap);

        // 返回全部
        List<Integer> creditsGreater100Map =
                (List<Integer>) parser.parseExpression("#credits.![value>90]").getValue(context);
        // 获得第一个匹配值
        Object value = parser.parseExpression("#credits.^[value>90]").getValue(context);
        // 获得最后一个匹配值
        Object value2 = parser.parseExpression("#credits.$[value>90]").getValue(context);

        parser.parseExpression("userName='anyli'").getValue(context);
        System.out.println(user.getUserName());//anyli

        // 构造器
        user = parser.parseExpression("new expression.User()").getValue(User.class);
        System.out.println(user.getUserName());//tom

        // 操作类型
        Class<?> dateClass = parser.parseExpression("T(java.util.Date)").getValue(Class.class);
        Class<?> stringClass = parser.parseExpression("T(java.lang.String)").getValue(Class.class);
        Class<?> userClass = parser.parseExpression("T(expression.User)").getValue(Class.class);
        System.out.println(dateClass == java.util.Date.class);
        System.out.println(stringClass == java.lang.String.class);

        Object randomValue = parser.parseExpression("T(java.lang.Math).random()").getValue();
        System.out.println(randomValue);
    }

}
