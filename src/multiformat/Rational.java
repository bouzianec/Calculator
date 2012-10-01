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
/*
 * A class representing a rational in fractional format
 * 
 * @author Davide Brugali, Marco Torchiano
 * @version 1.0 
 */
public class Rational {
	static final double PRECISION=10;
	static final double EPSILON = Math.pow(10,-PRECISION);
	//setting the standard numerator and denominator
	double numerator = 0.0;
	double denominator = 1.0;

	/**
     * Create a new rational with the given numerator and denominator
     * 
     * @param num The new numerator 
     * @param den The new denominator 
     */
	public Rational(double num, double den) {
		numerator = num;
		denominator = den;
		simplify();
	}
	/**
     * Create a new with the standard numerator and denominator
     * 
     */
	public Rational() {
	}
	/**
     * Create a new rational with just a single number
     * 
     * @param number The new numerator
     */
	public Rational(double number) {
		numerator = number;
		denominator = 1.0;
		canonical();
		simplify();
	}
	/**
     * Make the rational canonical
     * 
     */
	void canonical() {
		double num = Math.abs(numerator);
		double decimal = num - Math.floor(num);
		int num_digits = 0;

		while (decimal > EPSILON && num_digits < PRECISION) {
			num = num * 10;
			decimal = num - Math.floor(num);
			num_digits++;
		}

		numerator = numerator * Math.pow(10.0, num_digits);
		denominator = denominator * Math.pow(10.0, num_digits);
	}
	/**
     * Simplify the rational
     */
	public void simplify() {
		double divisor = Math.min(Math.abs(numerator), denominator);
		for (; divisor > 1.0; divisor -= 1.0) {
			double rn =	Math.abs(
					Math.IEEEremainder(Math.abs(numerator), divisor));
			double rd = Math.abs(
					Math.IEEEremainder(denominator, divisor));
			if (rn < EPSILON && rd < EPSILON) {
				numerator /= divisor;
				denominator /= divisor;
				divisor = Math.min(Math.abs(numerator), denominator);
			}
		}
	}
	
	/**
     * Adds up with another given Rational
     * 
     * @param other The rational to add 
     * @return the new rational
     */

	public Rational plus(Rational other) {
		if (denominator == other.denominator)
			return new Rational(numerator + other.numerator
								,other.denominator);
		else
			return new Rational(numerator * other.denominator + 
										denominator * other.numerator
								,denominator * other.denominator);
	}

	/**
     * Subtract another given Rational
     * 
     * @param other The rational to subtract
     * @return the new rational
     */
	
	public Rational minus(Rational other) {
		if (denominator == other.denominator)
			return new Rational(numerator - other.numerator, denominator);
		else
			return new Rational(numerator * other.denominator - 
									denominator * other.numerator
								,denominator * other.denominator);
	}
	/**
     * Multiply with another given Rational
     * 
     * @param other The rational to multiply
     * @return the new rational
     */
	
	public Rational mul(Rational other) {
		return new Rational(
			numerator * other.numerator,
			denominator * other.denominator);
	}
	/**
     * Divide with another given Rational
     * 
     * @param other The rational to divide
     * @return the new rational
     */
	
	public Rational div(Rational other) {
		return new Rational(
			numerator * other.denominator,
			denominator * other.numerator);
	}
	/**
     * Change this rational another given Rational
     * 
     * @param other The rational to change into
     */
	public void copyOf(Rational other) {
		this.numerator = other.numerator;
		this.denominator = other.denominator;
	}
}