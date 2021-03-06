/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.syr.bytecast.jimple.beans.jimpleBean;

/**
 * To test our jimple class , we rewrite the test case in java format and then
 * using self-defined class to create a corresponding class
 *
 * @author xirui Wang
 */
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import soot.jimple.Jimple;

public class TestJimple {

  void doTest() {
    //create a class          
    //declare function
    //declare variable
    //assign variable
    //

    JimpleDoc jDoc = new JimpleDoc();

    JimpleClass jClass = new JimpleClass("test", 1);
    jDoc.addClass(jClass);

    // assign object for all kinds of assign
    JimpleAssign jim_ass = new JimpleAssign();
    // invoke object for all kinds of invoke
    JimpleInvoke jim_inv = new JimpleInvoke();
    // ----------------------create sum method----------------------
    ArrayList<String> parameter_type_sum = new ArrayList<String>();
    parameter_type_sum.add("int");
    parameter_type_sum.add("int");
    // create method.
    JimpleMethod sumMethod = new JimpleMethod(1, "int", "sum", parameter_type_sum, jClass);
    // assign pass-in value to local variable
    // like l0 := @parameter0 int
    List<JimpleVariable> summethod_parameters = new ArrayList<JimpleVariable>();
    List<String> paratypes = sumMethod.getParameterTypes();
    for (int i = 0; i < paratypes.size(); ++i) {
      JimpleVariable newjv = new JimpleVariable("l" + Integer.toString(i), paratypes.get(i), sumMethod);
//      JimpleAssign ja = new JimpleAssign();
      jim_ass.JimpleParameterAssign(newjv, paratypes.get(i), i, sumMethod);
      summethod_parameters.add(newjv);
    }
    // int sum;
    JimpleVariable sumtoreturn = new JimpleVariable("sum", "int", sumMethod);
    // sum = l0 + l1;
    JimpleAssign getsum = new JimpleAssign();
    getsum.JimpleAdd(sumtoreturn, summethod_parameters.get(0),
            summethod_parameters.get(1), sumMethod);
    // return sum;
    sumMethod.setReturn(sumtoreturn);

    //--------- finish creating sum method-------------------

    //create parameterList of main method 
    ArrayList<String> parameter_list_main = new ArrayList<String>();
    parameter_list_main.add("String[]");
    parameter_list_main.add("int");

    //create main method add initiate first few lines of main
    JimpleMethod jMainMethod = new JimpleMethod(9, "void", "main", parameter_list_main, jClass);

    // println object for all println
//    JimpleVariable jPrintObj = new JimpleVariable("print_line", 
//            "println", jMainMethod);   
    // int &r1;
    JimpleVariable r1 = new JimpleVariable("$r1", "int", jMainMethod);
    
    // &r1 = 0;
    jim_ass.JimpleDirectAssign(r1, 0, jMainMethod);

    // test sumBase
    JimpleVariable sumClassObj = new JimpleVariable("sumBase", jClass.getJClassName(), jMainMethod);

    // sumBase = new test;
    // specialinvoke sumBase.<test: void <init>()>();
    jim_ass.JimpleNewClass(sumClassObj, jClass, jMainMethod);

    // if (a < 1)JAssignStmt
    JimpleCondition jc1 = new JimpleCondition("<", r1, 1, jMainMethod);


//    Runtime.AddPrintlntoJimple(jMainMethod);


    // int $r2;
    JimpleVariable r2 = new JimpleVariable("$r2", "int", jMainMethod);
    // int $r3;
    JimpleVariable r3 = new JimpleVariable("$r3", "int", jMainMethod);
    // int $rsum
    JimpleVariable rsum = new JimpleVariable("$rsum", "int", jMainMethod);

     JimpleVariable testcast = new JimpleVariable("testCast", "char", jMainMethod);
     JimpleVariable testcastint = new JimpleVariable("testcastint", "int", jMainMethod);
    
     jim_ass.JimpleDirectAssign(testcast, '5', jMainMethod);
    //test for byte[]
     jim_ass.JimpleCastCharToInt(testcastint, testcast, jMainMethod);
     
     
    // byte[] testByteArray;
    JimpleVariable byteArray = new JimpleVariable("testByteArray", "byte[]", jMainMethod);

    // testByteArray = newarray (byte)[5];
    jim_ass.JimpleNewArray(byteArray, 5, jMainMethod);

    //testByteArray[0] =1;

    jim_ass.JimpleArrayAssign(byteArray, 0, 5, jMainMethod);
    jim_ass.JimpleArrayAssign(byteArray, 1, 6, jMainMethod);
    jim_ass.JimpleArrayAssign(byteArray, 2, 7, jMainMethod);

    JimpleVariable testArrayAssign = new JimpleVariable("testArrayAssign", "String", jMainMethod);

    jim_ass.JimpleAssignFromArray(testArrayAssign, byteArray, 0, jMainMethod);

    // $r2 = 1;
    jim_ass.JimpleDirectAssign(r2, 1, jMainMethod);
    // $r3 = 2;
    jim_ass.JimpleDirectAssign(r3, 2, jMainMethod);
    // $rsum = virtualinvoke sumBase.<test: int sum(int,int)>($r2, $r3);
    ArrayList<JimpleVariable> parameter_value_sum = new ArrayList<JimpleVariable>();
    parameter_value_sum.add(r2);
    parameter_value_sum.add(r3);
    jim_inv.invokeUserDefined(sumClassObj, sumMethod, parameter_value_sum, rsum, jMainMethod);

    // FOR TEST ONLY
    // int $r4;
//    JimpleVariable r4 = new JimpleVariable("$r4", "int", jMainMethod);
//    // String[] r5;
//    JimpleVariable r5 = new JimpleVariable("$r5", "String[]", jMainMethod);
//    // r4 = lengthof r5;
//    jim_ass.JimpleLengthOf(r4, r5, jMainMethod);



    // set target (if else)(must be added to method after condition been added
    // target MUST be added to JimpleMethod using addElement() explicitly
    // but could be added anywhere you want
    /// virtualinvoke print_line.<java.io.PrintStream: void println(java.lang.String)>("hello");
    ArrayList<String> parameter_print = new ArrayList<String>();
    parameter_print.add("helloworld");
    JimpleInvoke forif = new JimpleInvoke();
    forif.setAsTarget();
    forif.invokeNative("println", parameter_print, null, jMainMethod);
    jc1.setTargets(new JimpleElement[]{forif});

    // add lable0
//    jMainMethod.addElement(forif);
    // return;
    jMainMethod.setReturn(null);


    //if u want to output as class file  indicate as class file   
    // using jimple to create jimple file
    try {
      jDoc.printJimple(jClass.getJClassName(), "jimple");

    } catch (FileNotFoundException e) {
      System.out.println("file exception");
    } catch (IOException e) {
      System.out.println("IO exception");
    }


  }

  public static void main(String[] args) {
    TestJimple tJimple = new TestJimple();

    tJimple.doTest();


  }
}
