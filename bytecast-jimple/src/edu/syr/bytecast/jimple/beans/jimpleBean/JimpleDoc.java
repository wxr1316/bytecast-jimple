/*
 * 03/25/2013 - 1.0
 * 
 * accept a JimpleClass and print to a jimple file
 * 
 * @author Peike Dai
 * 
 * 
 */

package edu.syr.bytecast.jimple.beans.jimpleBean;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

import soot.Printer;
import soot.Scene;
import soot.SourceLocator;
import soot.options.Options;



public class JimpleDoc {
    
    public JimpleDoc() {
        Scene.v().loadClassAndSupport("java.lang.Object");
        Scene.v().loadClassAndSupport("java.lang.System");
    }
    
    public void addClass(JimpleClass jclass) {
        Scene.v().addClass(jclass.getSClass());
    }
    
    public void printJimple(String classname) throws FileNotFoundException,IOException {
        String fileName = SourceLocator.v().getFileNameFor(Scene.v().getSootClass(classname), Options.output_format_jimple);
        OutputStream streamOut = new FileOutputStream(fileName);
        System.out.println("File Name = "+fileName);
        PrintWriter writerOut = new PrintWriter(new OutputStreamWriter(streamOut));
        Printer.v().printTo(Scene.v().getSootClass(classname), writerOut);
        writerOut.flush();
        streamOut.close();
    }
}