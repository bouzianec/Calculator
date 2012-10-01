/*
 * (C) Copyright 2005 Davide Brugali, Marco Torchiano
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA
 * 02111-1307  USA
 */
package multiformat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;


/**
 * The multiformat calculator
 */
public class Calculator {
  private Rational operand_0 = new Rational();
  private Rational operand_1 = new Rational();
  private Format format = new FixedPointFormat();
  private Base base = new DecimalBase();

  public void addOperand(String newOperand) throws FormatException, NumberBaseException {
	  String allowed = base.getDigits();
	  String cleanOperand = cleanOperand(newOperand);
	  for (int i = 0; i < cleanOperand.length(); i++){
		  String character = cleanOperand.substring(i, i+1);
				  if (allowed.indexOf(character)< 0){
					  String fout = newOperand + " is niet volgens het juiste getallenstelsel genoteerd";
					  throw new NumberBaseException(fout);
				  }
					  
	  }
      Rational previous = operand_0;
      operand_0 = format.parse(newOperand, base);
      operand_1 = previous;
  }

  public void add(){
    operand_0 = operand_1.plus(operand_0);
    operand_1 = new Rational();
  }
  public void subtract() {
    operand_0 = operand_1.minus(operand_0);
    operand_1 = new Rational();
  }
  public void multiply() {
    operand_0 = operand_1.mul(operand_0);
    operand_1 = new Rational();
  }
  public void divide() {
	  double a = operand_0.numerator;
	  double b = operand_0.denominator;
	  if ((a==0.0)||(b==0.0)){
		  System.out.println("Je kunt niet delen door 0");
	  }
	  else {
    operand_0 = operand_1.div(operand_0);
    operand_1 = new Rational();
	  }
  }
  public void delete() {
    operand_0 = operand_1;
    operand_1 = new Rational();
  }

  public String firstOperand(){
    return format.toString(operand_1,base);
  }
  public String secondOperand(){
    return format.toString(operand_0,base);
  }

  public void setBase(Base newBase){
    base = newBase;
  }
  public Base getBase(){
    return base;
  }
  public void setFormat(Format newFormat){
    format = newFormat;
  }
  public Format getFormat(){
    return format;
  }
  /**
   * Clean the newOperand string of the "standard" characters
   * @param operand
   * @return
   */
  private String cleanOperand(String operand){
	ArrayList<String> symbols = new ArrayList();
	symbols.add("^");
	symbols.add("/");
	symbols.add(".");
	symbols.add(",");
	symbols.add("*");
	symbols.add("-");
	Iterator i = symbols.iterator();
		while (i.hasNext()){
		operand = operand.replace((String)i.next(), "");
	}
	 
  return operand;
  }
}