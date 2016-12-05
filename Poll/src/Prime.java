import java.io.*;
import java.util.*;

class Prime
{
	static boolean [] isprime;
	
	static void crossoff(int c)
	{
		for(int x=2*c; x<=isprime.length; x=x+c) isprime[x] = false;
	}
	
	public static void main(String [] args)
	{
		System.out.println("Find primes up to what number? ");
		Scanner keyboard = new Scanner(System.in);
		int n=keyboard.nextInt();
		
		isprime = new boolean[n+1];
		isprime[0]=isprime[1]=false;
		for (int i=2; i<=n; i++) isprime[i]=true;
		
		for(int p=0; p*p<=n; p++)
			if(isprime[p]) crossoff(p);
		
		for(int p=0; p<=n; p++)
			if(isprime[p])
				System.out.print(p+" ");
			System.out.println("");
	}

}
	