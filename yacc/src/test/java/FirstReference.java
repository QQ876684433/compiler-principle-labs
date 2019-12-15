import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

public class FirstReference {
    private Map<String, Set<Character>> first = new TreeMap<>();
    private Map<String, String[]> mp;

    public FirstReference(Map<String, String[]> mp) {
        super();
        this.mp = mp;
    }

    public Map<String, Set<Character>> getFirstSet() {
        return first;
    }

    private Set<Character> findFirst(String curNode, String[] rightNodes) {
        if (first.containsKey(curNode)) return first.get(curNode);
        Set<Character> st = new TreeSet<>();
        for (int i = 0; i < rightNodes.length; ++i) {
            for (int j = 0; j < rightNodes[i].length(); ++j) {
                String nextNode = "" + rightNodes[i].charAt(j);
                if (!mp.containsKey(nextNode)) {//终结点
                    st.add(nextNode.charAt(0));
                    break;
                } else {//非终结点
//                    if (j + 1 < rightNodes[i].length() && rightNodes[i].charAt(j + 1) == '\'') {
//                        nextNode += rightNodes[i].charAt(j + 1);
//                        ++j;
//                    }
                    if (mp.containsKey(nextNode)) {
                        Set<Character> tmpSt = findFirst(nextNode, mp.get(nextNode));
                        st.addAll(tmpSt);
                        if (!tmpSt.contains('$'))
                            break;
                    }
                }
            }
        }
        first.put(curNode, st);
        return st;
    }

    public String firstKernelCode() {
        String content = "";
        for (String leftNode : mp.keySet()) {
            String[] rightNodes = mp.get(leftNode);
            findFirst(leftNode, rightNodes);
        }
        //打印first集合
        System.out.println("First集合如下:");
        for (Map.Entry<String, Set<Character>> entry : first.entrySet()) {
            content += entry.getKey() + "  :  " + entry.getValue() + "\n";
            System.out.println(entry.getKey() + "  :  " + entry.getValue());
        }
        return content;
    }

    public static void main(String[] args) {


//        String[] rightLinearGrammar = {
//                "E->TE\'",
//                "E\'->+TE\'|$",
//                "T->FT\'",
//                "T\'->*FT\'|$",
//                "F->(E)|i"
//        };

        String[] rightLinearGrammar = {
                "S->ABc",
                "A->a|$",
                "B->b"
        };
        Map<String, String[]> mp = new LinkedHashMap<>();
        try {
            for (int i = 0; i < rightLinearGrammar.length; ++i) {
                String split1[] = rightLinearGrammar[i].split("->");
                String split2[] = split1[1].split("\\|");
                mp.put(split1[0], split2);
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("右线性文法错误!");
        }
        new FirstReference(mp).firstKernelCode();
    }
}