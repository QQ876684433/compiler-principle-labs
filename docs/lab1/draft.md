# 实验一草稿

## 目标

Java语言词法分析器：

```java
/**
 * class Test
 * 用于演示词法分析器的多行注释
 * 注意：注释会被忽略
 */
public class Test{
	private int a;
	protected double b = 0.1;
	final public String c = "Test";
    // 演示单行注释
    static Test instance;
    
    Test(){
    	this(0);	// 演示语句后注释
    }
    
    private Test(int a){
    	this.a = a;
    }

	public void func1(){
		System.out.println("func1");
	}
	
	protected int func2(int a, int b){
		return a + b;
	}
	
	private String func3(/* 演示行内注释 */){
		return "Test";
	}
	
	public static Test getInstance(){
		if(instance == null){
			instance = new Test();
		}
		return instance;
	}  

	public static void main(String[] args){
		Test test1 = new Test();
		Test test2 = Test.getInstance();
		if(test1 == test2){
			System.out.ptintln("test1 == test2");
		}else{
			System.out.println("test1 != test2");
		}
		while(true){}
	}
}
```

## REs

- 保留字：

  ```java
  abstract, assert,
  
  boolean, break, byte,
  
  case, catch, char, class, const, continue,
  
  default, do, double,
  
  else, enum, extends,
  
  final, finally, float, for,
  
  goto,
  
  if, implements, import, instanceof, int, interface,
  
  long,
  
  native, new,
  
  package, private, protected, public,
  
  return,
  
  short, static, strictfp, super, switch, synchronized,
  
  this, throw, throws, transient, try,
  
  void, volatile,
  
  while
  ```

  这个不需要使用专门的RE，只需要在提取出类名、函数名或者变量名之后来判断是否在这些保留字中即可

- 运算符和界符

  - 运算符（operator）（?:这种三目运算符就拆分成?和:两个终结符）

    ps：为了降低复杂度，对于有歧义的表达式如`a+++++a`这种，直接报错

    ```java
    ++, --, +, -, ~, !, *, /, %, <<, >>, >>>, <, >, <=, >=, ==, !=, &, ^, |, &&, ||, =, +=, -=, *=, /=, %=, &=, ^=, |=, <<=, >>=, >>>=, ., @, ?, :
    ```

  - 界符（delimiter）

    ```java
    (, ), {, }, [, ], ;, \,(逗号运算符)
    ```

  这些是终结符

- 类名、函数名、变量名等（暂时不支持非ASCII字符）

  _、$、英文字符开头（[a-zA-Z]），并由\_、\$、数字（[0-9]）和英文字母（[a-zA-Z]）组成

  RE：$variable=>[\_\backslash\$a-zA-Z][\_\backslash\$a-zA-Z0-9]* $

- 字符串

  RE：string -> "([ ^ "\\\\] | (\\\\"))*"​

  这里的`.`实际表示的是非`"`的任意字符

- 字符 

  RE：character -> '( [ ^ \\\\' ] | \\\\[0-9btnfr\\\\'] )'​

- 数字

  由数字和`.`组成，并最多含有一个`.`

  或者由0x开头，由[0-9a-fA-F]组成

  文法：

  $number=>float|integer$

  $float=>.digit+|digit+.digit+|digit+.$

  - 对于float的文法，可以用来直接构造DFA：

    $S=>digit\;B|.A$

    $B=>digit\;B|.C$

    $A=>digit\;C$

    $C=>digit\;C|\epsilon$

  $integer=>dec|hex$

  $dec=>digit+$

  $hex=>0(x|X)(digit|[a-fA-F])+$

  $digit=>[0-9]$

  因此RE是：

  $number=>[0-9]+|0(x|X)[0-9a-fA-F]+|\backslash.[0-9]+|[0-9]+.[0-9]+|[0-9+].$

## NFAs

- 类名、函数名、变量名等

  ![](https://i.loli.net/2019/12/05/CYH4zQ9UofwcaNK.jpg)

- 字符串

  ![](https://i.loli.net/2019/12/05/irbyheg39TO12Az.jpg)

- 字符

  ![](https://i.loli.net/2019/12/05/7pKhge8lHYVDEBi.jpg)

- 数字

  ![](https://i.loli.net/2019/12/05/76mGiSYxhKWeBAb.jpg)



## NFA

这一步是将上面的NFAs合并成一个大的NFA

## DFA

将合并之后的NFA转换成DFA

## DFAo

优化DFA，使得状态数最少



































