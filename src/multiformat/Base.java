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

/**
 * A generic numbering base
 */
public abstract class Base {

  private String name;
  private int base;
  private String digits;

  static final int MAX_PRECISION = 10;  // max # of number after point
  double EPSILON;

  Base(String p_name, int p_base, String p_digits){
    name = p_name;
    base = p_base;
    digits = p_digits;
	EPSILON = Math.pow(base,-MAX_PRECISION);
  }

  public String getName() { return name; }
  public int getBase() { return base; }
  /*
   * Returns the possible digits
   */
  public String getDigits() { return digits; }

  double parse(String number) {
    // decodes the sign
    double sign = 1.0;
    if(number.charAt(0) == '-'){
      sign = -1.0;
      number = number.substring(1).trim();
    }else if(number.charAt(0) == '+'){
      sign = 1.0;
      number = number.substring(1).trim();
    }

    // parses the integer part and the decimal part
    int power;
    int index = number.indexOf('.');
    if(index >= 0) {
      power = index-1;
    }
    else {
      power = number.length()-1;
    }

    double result = 0.0;
    double mult = Math.pow(base,power);
    // decodes the integer part
    for(int i = 0; i < number.length(); i++)
      if(number.charAt(i)!='.'){
        result += mult * digits.indexOf(number.charAt(i));
        mult /= base;
      }
    return result * sign;
  }

  String toString(double number) {
    if(number == 0.0) return "0";
    StringBuffer result=new StringBuffer();
    if(number<0){
      result.append('-');
      number = -number;
    }

    int i;
    int power = (int)Math.floor(Math.log(number+EPSILON/2)/Math.log(base));
    if(power<0) power=0;
    double divider = Math.pow(base,power);
    int num_digits=0;

    for(i=power;  (number > EPSILON && num_digits < MAX_PRECISION) || i>=-1; --i){
      double divResult = number / divider;
      double cipher = Math.floor((number+EPSILON/2) / divider);
      if(divider < 1.0){
        num_digits++;
        if(num_digits==1){
          result.append('.');
        }
      }
      result.append(digits.charAt((int)cipher));
      number -= cipher * divider;
      divider /= base;
    }
    return result.toString();
  }
}

