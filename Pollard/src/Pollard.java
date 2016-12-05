/*
Shaun Mbateng
Pollard's p-1 Algorithm
This Program Finds the Prime Factors of a Number With Pollard's p-1 Method
*/

import java.io.*;
import java.util.*;
import java.math.*;

class Pollard
{
	public static void main(String [] args) throws IOException // Main Function
	{
		BigInteger n,a,k,r,d,B; //BigIntegers for Parts of Method
		BigInteger one = BigInteger.valueOf(1);
		int rnd;
		Scanner kbd = new Scanner(System.in); //Read Strings
		Scanner cin=new Scanner(System.in); //Read Integers
		
		System.out.println("");
		System.out.println("***Welcome User: This Program Finds the Prime Factors of A Number***");
		System.out.println("");
		System.out.println("What number do you want to find the prime factors of?");
		n = cin.nextBigInteger();
		System.out.println("What prime bound would you like to use?");
		B = cin.nextBigInteger();
		System.out.println("Prime Factors:");
		
		while (n.isProbablePrime(1) == false)
		{
			rnd = (int)(2+n.intValue()*Math.random());
			a = BigInteger.valueOf(rnd);
			k = BigInteger.valueOf(1);
			
			for (int i=2; i<=B.intValue(); i++)
			{
				BigInteger j = BigInteger.valueOf(i);
				k = k.multiply(j);
			}
			
			r = (a.xor(k)).mod(n);
			d = n.gcd(r.subtract(one));
			
			if (d.intValue() !=1 && d.intValue() != n.intValue())
			{
				if (n.divide(d).isProbablePrime(1) == true)
				{
					System.out.println(n.divide(d));
					
					if (d.isProbablePrime(1) == false)
					{
						n = d;
					}
				}
				
				if (d.isProbablePrime(1) == true)
				{
					System.out.println(d);
					
					if (n.divide(d).isProbablePrime(1) == false)
					{
						n = n.divide(d);
					}
				}
				
				if (n.divide(d).isProbablePrime(1) == true && d.isProbablePrime(1) == true)
				{
					n = d;
				}
			}
		}
	}
}