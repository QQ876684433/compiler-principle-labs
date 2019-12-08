import object.RE;

import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

public class RE2SuffixExpression {
    public static List<RE> transform(List<RE> res) {
        List<RE> suffixExps = new LinkedList<RE>();
        for (RE re : res) {
            Stack<Character> opStack = new Stack<Character>();
            String tmp = re.pattern + '#'; // 添加一个结束标识符
            StringBuilder sb = new StringBuilder();
            for (char ch : tmp.toCharArray()) {
                switch (ch) {
                    case '.':
                    case '|':
                        while (!opStack.isEmpty()) {
                            char top = opStack.peek();
                            if (top != '(') {
                                sb.append(top);
                                opStack.pop();
                            } else {
                                opStack.push(ch);
                                break;
                            }
                        }
                        if (opStack.isEmpty()) {
                            opStack.push(ch);
                        }
                        break;
                    case '*':
                        opStack.push(ch);
                        break;
                    case '(':
                        opStack.push('(');
                        break;
                    case ')':
                        while (!opStack.isEmpty()) {
                            char top = opStack.peek();
                            if (top != '(') {
                                sb.append(top);
                                opStack.pop();
                            } else {
                                opStack.pop();
                                break;
                            }
                        }
                        break;
                    case '#':
                        // 用于处理操作符栈中的剩余操作符
                        while (!opStack.empty()) {
                            sb.append(opStack.pop());
                        }
                        suffixExps.add(new RE(re.catalog, sb.toString()));
                        break;
                    default:
                        sb.append(ch);
                        break;
                }
            }
        }
        return suffixExps;
    }

    public static void main(String[] args) throws FileNotFoundException {
        List<RE> rules = new LinkedList<RE>();
        rules.add(new RE("expr1", "(a*|(a|b*)*).(a|b).(a|b)*"));
        rules.add(new RE("expr2", "a|b"));
        List<RE> suffixExp = transform(RE.loadREs());
        for (RE re : suffixExp) {
            System.out.println(re.catalog + " -> " + re.pattern);
        }
    }
}
